package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.CollectionTextResolver
import kotlinx.coroutines.launch

class CollectionDetailViewModel(
    private val textResolver: CollectionTextResolver
) : ViewModel() {

    val viewState: LiveData<GainDetailViewState> get() = _viewState

    private val _viewState = MutableLiveData<GainDetailViewState>()

    fun getGainInformation(uaKey: LlaveUA) {
        viewModelScope.launch(handler) {
            io {
                val title = textResolver.obtainGridTitleDetail(uaKey.roleAssociated)
                _viewState.postValue(GainDetailViewState.Success(title))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value = GainDetailViewState.Failed(message)
        }
    }
}
