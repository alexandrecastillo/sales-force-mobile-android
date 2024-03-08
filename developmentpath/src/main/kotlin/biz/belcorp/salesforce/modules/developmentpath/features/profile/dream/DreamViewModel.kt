package biz.belcorp.salesforce.modules.developmentpath.features.profile.dream

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.Dream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.DeleteDreamUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.GetDreamUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp.DeleteDreamBpUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp.GetBusinessPartnerDreamUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp.SyncBusinessPartnerDreamsUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.dream.viewstate.DreamBpViewState
import biz.belcorp.salesforce.modules.developmentpath.features.profile.dream.viewstate.DreamViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class DreamViewModel(
    private val dreamUseCase: GetDreamUseCase,
    private val getBusinessPartnerDreamUseCase: GetBusinessPartnerDreamUseCase,
    private val syncBusinessPartnerDreamsUseCase: SyncBusinessPartnerDreamsUseCase,
    private val deleteDreamUseCase: DeleteDreamUseCase,
    private val deleteDreamBpUseCase: DeleteDreamBpUseCase,
    private val personRepository: RddPersonaRepository,
) : ViewModel() {

    val viewState: LiveData<DreamViewState>
        get() = _viewState
    private val _viewState = MutableLiveData<DreamViewState>()

    val viewStateBp: LiveData<DreamBpViewState>
        get() = _viewStateBp
    private val _viewStateBp = MutableLiveData<DreamBpViewState>()

    private lateinit var currentPersonIdentifier: PersonIdentifier

    fun getDream(personIdentifier: PersonIdentifier) {
        currentPersonIdentifier = personIdentifier
        val person = personIdentifier.run { personRepository.recuperarPersonaPorId(id, role) }
        if (person != null) {
            if (personIdentifier.role.isSE()) {
                viewModelScope.launch(bpHandler) {
                    io {
                        syncBusinessPartnerDreamsUseCase.syncBusinessPartnerDreams(personIdentifier)
                        val model =
                            getBusinessPartnerDreamUseCase.getBusinessPartnerDream(personIdentifier)
                        _viewStateBp.postValue(DreamBpViewState.Success(model))
                    }
                }
            } else {
                viewModelScope.launch(handler) {
                    io {
                        dreamUseCase.syncDreams(personIdentifier)
                        val model = dreamUseCase.getDream(personIdentifier)
                        _viewState.postValue(DreamViewState.Success(model))
                    }
                }
            }
        }
    }

    fun deleteDream(dream: Dream?) {
        if (currentPersonIdentifier.role.isSE()) {
            viewModelScope.launch(deleteBpHandler) {
                io {
                    deleteDreamBpUseCase.deleteBusinessPartnerDream(dream)
                    syncBusinessPartnerDreamsUseCase.syncBusinessPartnerDreams(
                        currentPersonIdentifier
                    )
                    val model = getBusinessPartnerDreamUseCase
                        .getBusinessPartnerDream(currentPersonIdentifier)
                    _viewStateBp.postValue(DreamBpViewState.Success(model))
                }
            }
        } else {
            viewModelScope.launch(deleteHandler) {
                io {
                    deleteDreamUseCase.deleteDream(dream)
                    val model = dreamUseCase.getDream(currentPersonIdentifier)
                    _viewState.postValue(DreamViewState.Success(model))
                }
            }
        }
    }

    private val bpHandler = CoroutineExceptionHandler { _, _ ->
        io {
            val model = getBusinessPartnerDreamUseCase
                .getBusinessPartnerDream(currentPersonIdentifier)
            if (model.isNotNull()) {
                _viewStateBp.postValue(DreamBpViewState.Success(model))
            } else {
                _viewStateBp.postValue(DreamBpViewState.Failed)
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        io {
            val model = dreamUseCase.getDream(currentPersonIdentifier)
            _viewState.postValue(DreamViewState.Success(model))
            _viewState.postValue(DreamViewState.Failed)
        }
    }

    private val deleteBpHandler = CoroutineExceptionHandler { _, _ ->
        io {
            _viewStateBp.postValue(DreamBpViewState.Failed)
        }
    }

    private val deleteHandler = CoroutineExceptionHandler { _, _ ->
        io {
            _viewState.postValue(DreamViewState.Failed)
        }
    }
}
