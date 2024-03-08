package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.ConsultantSaleEntity
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.info.GetProfileInfoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.ventas.SaleBrightPathConsultantUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.view.BrightPathCabeceraMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.view.BrightPathCabeceraModel
import kotlinx.coroutines.launch

class BrightPathViewModel(
    private val useCase: GetProfileInfoUseCase,
    private val useCaseSales: SaleBrightPathConsultantUseCase,
    private val mapper: BrightPathCabeceraMapper,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    val session by lazy { requireNotNull(sessionRepository.getSession()) }

    fun getCurrentPeriod(): String {
        return session.campaign.nombreCorto
    }

    sealed class BrightPathViewState {
        class ShowPersonInfo(val person: BrightPathCabeceraModel) : BrightPathViewState()
        class ShoSaleInfo(val sale: List<ConsultantSaleEntity>) : BrightPathViewState()
    }

    val viewState: LiveData<BrightPathViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<BrightPathViewState>()

    val viewStatePersonalInfo: LiveData<BrightPathViewState>
        get() = _viewStatePersonalInfo

    private val _viewStatePersonalInfo = MutableLiveData<BrightPathViewState>()

    fun fetchPersonInfo(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            val person = useCase.getInfo(personIdentifier)
            val model = mapper.parse(person)
            _viewStatePersonalInfo.postValue(BrightPathViewState.ShowPersonInfo(model))
        }
    }

    fun fetchSalesInfo(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            val data = useCaseSales.getSaleBrightPathConsultant(personIdentifier.code)
            _viewState.postValue(BrightPathViewState.ShoSaleInfo(data))
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

}
