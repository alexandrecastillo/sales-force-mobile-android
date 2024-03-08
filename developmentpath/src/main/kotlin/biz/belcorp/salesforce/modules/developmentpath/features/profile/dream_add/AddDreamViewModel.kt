package biz.belcorp.salesforce.modules.developmentpath.features.profile.dream_add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.EditCreateDream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.GetDreamUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp.CreateDreamBusinessPartnerUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp.EditDreamBpUseCase
import kotlinx.coroutines.launch

class AddDreamViewModel(
    private val dreamUseCase: GetDreamUseCase,
    private val createDreamBusinessPartnerUseCase: CreateDreamBusinessPartnerUseCase,
    private val editDreamBpUseCase: EditDreamBpUseCase,
    private val campaignsRepository: CampaniasRepository,
    private val personRepository: RddPersonaRepository,
) : ViewModel() {

    private val dream: EditCreateDream = EditCreateDream()
    private lateinit var uaKey: LlaveUA

    fun setDreamId(dreamId: String) {
        dream.dreamId = dreamId
    }

    fun onDreamDescriptionChanged(dreamDescription: String) {
        dream.dreamDescription = dreamDescription
    }

    fun onDreamCommentsChanged(dreamComments: String) {
        dream.dreamComments = dreamComments
    }

    fun onDreamsCampaignsAchieveChanged(dreamsCampaignsAchieve: String) {
        dream.dreamsCampaignsAchieve = dreamsCampaignsAchieve
    }

    fun onDreamAmountChanged(dreamAmount: String) {
        dream.dreamAmount = dreamAmount
    }

    fun onDreamCampaignChanged(campaignCreated: String) {
        dream.actualCampaign = campaignCreated
    }


    val viewState: LiveData<UiState>
        get() = _viewState
    private val _viewState = MutableLiveData<UiState>()

    data class UiState(
        val validateDreamDescription: Boolean = false,
        val validateDreamComments: Boolean = false,
        val validateDreamAmount: Boolean = false,
        val validateDreamsCampaignsAchieve: Boolean = false
    )


    val viewEvent: LiveData<UiEvent>
        get() = _viewEvent
    private val _viewEvent = MutableLiveData<UiEvent>()

    sealed class UiEvent {
        object OnFinished : UiEvent()
        object OnError : UiEvent()
    }

    fun checkData() {
        _viewState.value = UiState(
            validateDreamDescription = dream.dreamDescription.isEmpty(),
            validateDreamComments = dream.dreamComments.isEmpty(),
            validateDreamAmount = dream.dreamAmount.isEmpty(),
            validateDreamsCampaignsAchieve = dream.dreamsCampaignsAchieve.isEmpty()
        )
    }

    fun actualCampaign(personIdentifier: PersonIdentifier) {
        val person = personIdentifier.run { personRepository.recuperarPersonaPorId(id, role) }
        uaKey = requireNotNull(person?.llaveUA)
        if (person != null) {
            if (personIdentifier.role.isSE()) {
                dream.bpCode = person.personCode
            } else {
                dream.consultantCode = person.personCode
            }
        }
        val campaign = campaignsRepository.obtenerCampaniaActual(uaKey)
        dream.actualCampaign = campaign?.codigo.toString()
    }

    fun saveDream(personIdentifier: PersonIdentifier) {
        val person = personIdentifier.run { personRepository.recuperarPersonaPorId(id, role) }
        uaKey = requireNotNull(person?.llaveUA)
        if (person != null) {
            if (personIdentifier.role.isSE()) {
                viewModelScope.launch(handlerBp) {
                    io {
                        createDreamBusinessPartnerUseCase.createBusinessPartnerDream(
                            uaKey,
                            dream
                        )
                        _viewEvent.postValue(UiEvent.OnFinished)
                    }
                }
            } else {
                viewModelScope.launch(handler) {
                    io {
                        dreamUseCase.createDream(uaKey, dream)
                        _viewEvent.postValue(UiEvent.OnFinished)
                    }
                }
            }
        }
    }

    fun editDream(personIdentifier: PersonIdentifier) {
        val person = personIdentifier.run { personRepository.recuperarPersonaPorId(id, role) }
        uaKey = requireNotNull(person?.llaveUA)
        if (personIdentifier.role.isSE()) {
            viewModelScope.launch(handlerBp) {
                io {
                    editDreamBpUseCase.editBusinessPartnerDream(uaKey, dream)
                    _viewEvent.postValue(UiEvent.OnFinished)
                }
            }
        } else {
            viewModelScope.launch(handler) {
                io {
                    dreamUseCase.editDream(uaKey, dream)
                    _viewEvent.postValue(UiEvent.OnFinished)
                }
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        io {
            _viewEvent.postValue(UiEvent.OnError)
        }
    }

    private val handlerBp = CoroutineExceptionHandler { _, _ ->
        io {
            _viewEvent.postValue(UiEvent.OnError)
        }
    }
}
