package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.postulants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.PostulantKpiUseCase
import kotlinx.coroutines.launch

class PostulantKpiViewModel(
    private val useCase: PostulantKpiUseCase,
    private val mapper: PostulantKpiMapper
) : ViewModel() {

    val viewState: LiveData<PostulantKpiViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<PostulantKpiViewState>()

    fun getData(ua: LlaveUA) {
        viewModelScope.launch(handler) {
            io {
                val data = useCase.getPostulantsKpi(ua)

                val state = if (data.isNotEmpty()) {
                    val isParent = useCase.isParent(ua)
                    val model = mapper.map(isParent, data.first())
                    PostulantKpiViewState.Success(model)
                } else PostulantKpiViewState.Failed(EMPTY_STRING)

                _viewState.postValue(state)
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            _viewState.postValue(
                PostulantKpiViewState.Failed(
                    exception.message.orEmpty()
                )
            )
        }
    }
}
