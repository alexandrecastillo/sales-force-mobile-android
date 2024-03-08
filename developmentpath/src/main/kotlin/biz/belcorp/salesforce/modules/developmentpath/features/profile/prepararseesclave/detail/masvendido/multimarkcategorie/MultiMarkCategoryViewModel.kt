package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.multimarkcategorie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.masvendido.marcasycategorias.GetBrandsAndCategoriesUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MultiMarkCategoryViewModel(
    private val useCase: GetBrandsAndCategoriesUseCase,
    private val sessionRepository: SessionRepository
) : ViewModel() {


    private val session by lazy { requireNotNull(sessionRepository.getSession()) }


    private val _viewState = MutableLiveData<MultiMarkCategoryViewState>()
    val viewState: LiveData<MultiMarkCategoryViewState>
        get() = _viewState

    fun isMx(): Boolean {
        return session.countryIso == Pais.MEXICO.codigoIso
    }

    fun getMultiMarkCategoriesInfo(id: Long, rol: Rol) {
        viewModelScope.launch(handler) {
            val data = useCase.getMultiMarkMultiCategoriesData(id, rol)


            _viewState.postValue(
                MultiMarkCategoryViewState.Success(data)
            )
        }
    }

    fun getProductsInfo(id: Long, rol: Rol) {
        viewModelScope.launch(handler) {
            val data = useCase.getProductsLbelEsika(id, rol)


            _viewState.postValue(
                MultiMarkCategoryViewState.SuccessProducts(data)
            )
        }
    }

    val handler = CoroutineExceptionHandler { _, exception ->
        io {
            _viewState.postValue(MultiMarkCategoryViewState.Failed(exception.message.orEmpty()))
        }
    }


}


