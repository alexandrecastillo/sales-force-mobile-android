package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.penetracionmarca.marcas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.GetBrandsUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias.BrandModelMapper
import kotlinx.coroutines.launch

class BrandsViewModel(
    private val useCase: GetBrandsUseCase,
    private val mapper: BrandModelMapper
) : ViewModel() {

    val viewState: LiveData<BrandsViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<BrandsViewState>()

    fun getBrands(personIdentifier: PersonIdentifier) = with(personIdentifier) {
        viewModelScope.launch(handler) {
            val brandsInfo = useCase.getBrandsInfo(id, role)
            val brandsMapped = brandsInfo.map { mapper.parse(it) }
            _viewState.postValue(BrandsViewState.Success(brandsMapped))
        }
    }

    val handler = CoroutineExceptionHandler { _, exception ->
        io {
            _viewState.postValue(BrandsViewState.Failure(exception.message.toString()))
            Logger.loge(TAG, exception.message)
        }
    }

    companion object {
        private val TAG = BrandsViewModel::class.java.simpleName
    }

}
