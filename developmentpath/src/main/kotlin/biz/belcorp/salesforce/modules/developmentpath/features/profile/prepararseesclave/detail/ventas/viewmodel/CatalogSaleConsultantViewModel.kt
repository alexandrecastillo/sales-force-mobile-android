package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.ventas.GetCatalogSaleConsultantUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.mappers.CatalogSaleConsultantMapper
import kotlinx.coroutines.launch

class CatalogSaleConsultantViewModel(
    private val saleConsultantUseCase: GetCatalogSaleConsultantUseCase,
    private val mapper: CatalogSaleConsultantMapper
) : ViewModel() {

    val viewState: LiveData<CatalogSaleConsultantViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<CatalogSaleConsultantViewState>()

    fun getCatalogSaleConsultant(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            val catalogSales = saleConsultantUseCase.getCatalogSaleConsultant(personIdentifier.code)
            val model = mapper.map(catalogSales)
            _viewState.postValue(CatalogSaleConsultantViewState.Success(model))
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        ui { _viewState.value = CatalogSaleConsultantViewState.Failure }
    }
}
