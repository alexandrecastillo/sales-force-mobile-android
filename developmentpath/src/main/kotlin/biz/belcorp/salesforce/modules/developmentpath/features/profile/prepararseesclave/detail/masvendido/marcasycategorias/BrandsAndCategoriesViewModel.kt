package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.masvendido.marcasycategorias.GetBrandsAndCategoriesUseCase
import kotlinx.coroutines.launch

class BrandsAndCategoriesViewModel(
    private val useCase: GetBrandsAndCategoriesUseCase,
    private val brandsMapper: BrandModelMapper,
    private val categoriesMapper: CategoryModelMapper
) : ViewModel() {

    val viewState: LiveData<BrandsAndCategoriesViewState>
        get() = _viewState

    val sellingViewState: LiveData<SellingDataViewState>
        get() = _sellingViewState

    private val _viewState = MutableLiveData<BrandsAndCategoriesViewState>()
    private val _sellingViewState = MutableLiveData<SellingDataViewState>()

    fun getBrandsAndCategoriesInfo(id: Long, rol: Rol) {
        viewModelScope.launch(handler) {
            val data = useCase.getBrandsAndCategoriesInfo(id, rol)
            val pairModel = Pair(
                data.first.map { categoriesMapper.parse(it) },
                data.second
            )
            _viewState.postValue(
                BrandsAndCategoriesViewState.Success(pairModel)
            )
        }
    }

    fun getSellingData(id: Long, rol: Rol) {
        viewModelScope.launch(handler) {
            val data = useCase.getSellingData(id, rol)
            val pairModel = Pair(
                data.first,
                data.second
            )
            _sellingViewState.postValue(
                SellingDataViewState.SellingData(pairModel)
            )
        }
    }

    val handler = CoroutineExceptionHandler { _, exception ->
        io {
            _viewState.postValue(BrandsAndCategoriesViewState.Failed(exception.message.orEmpty()))
            Logger.loge(TAG, exception.message)
        }
    }

    companion object {
        private val TAG = BrandsAndCategoriesViewModel::class.java.simpleName
    }

}
