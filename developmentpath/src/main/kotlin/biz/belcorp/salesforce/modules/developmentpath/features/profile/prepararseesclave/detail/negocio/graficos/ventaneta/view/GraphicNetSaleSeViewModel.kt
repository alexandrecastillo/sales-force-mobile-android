package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.GraphicNetSaleSeUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.mappers.GraphicNetSaleSeMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.models.NetSaleBarEntryMapper
import kotlinx.coroutines.launch

class GraphicNetSaleSeViewModel(
    private val graphicNetSaleSeUseCase: GraphicNetSaleSeUseCase,
    private val mapper: GraphicNetSaleSeMapper,
    private val barEntryMapper: NetSaleBarEntryMapper
) : ViewModel() {

    val viewState: LiveData<GraphicNetSaleSeViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<GraphicNetSaleSeViewState>()

    fun getNetSalesSE(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            val data = graphicNetSaleSeUseCase.getGraphicNetSaleSe(personIdentifier)
            val netSales = data.map { mapper.reverseMap(it) }
            val entrySet = barEntryMapper.map(netSales)
            if (data.isNotEmpty()) {
                _viewState.postValue(
                    GraphicNetSaleSeViewState.Success(mapper.mapToModel(netSales, entrySet))
                )
            } else _viewState.postValue(GraphicNetSaleSeViewState.Empty)
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        ui { _viewState.value = GraphicNetSaleSeViewState.Failure }
    }
}
