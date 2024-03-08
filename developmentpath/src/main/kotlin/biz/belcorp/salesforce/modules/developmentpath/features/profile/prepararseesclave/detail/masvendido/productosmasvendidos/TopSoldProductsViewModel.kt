package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.productosmasvendidos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.masvendido.productosmasvendidos.GetTopSoldProductsUseCase
import kotlinx.coroutines.launch

class TopSoldProductsViewModel(
    private val useCase: GetTopSoldProductsUseCase,
    private val mapper: TopSoldProductsModelMapper
) : ViewModel() {

    val viewState: LiveData<TopSoldProductsViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<TopSoldProductsViewState>()

    fun getMostSoldProductsInfo(personIdentifier: PersonIdentifier) = with(personIdentifier) {
        viewModelScope.launch(handler) {
            io {
                val products = useCase.getMostSoldProducts(id, role)
                val model = mapper.parse(products)

                if (model.isNotEmpty()) postSuccessViewState(model)
                else postFailedViewState(EMPTY_STRING)
            }
        }
    }

    private fun postSuccessViewState(model: List<TopSoldProductCampaignModel>) {
        _viewState.postValue(TopSoldProductsViewState.Success(model))
    }

    private fun postFailedViewState(message: String) {
        _viewState.postValue(TopSoldProductsViewState.Failed(message))
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            postFailedViewState(exception.message.orEmpty())
        }
    }
}
