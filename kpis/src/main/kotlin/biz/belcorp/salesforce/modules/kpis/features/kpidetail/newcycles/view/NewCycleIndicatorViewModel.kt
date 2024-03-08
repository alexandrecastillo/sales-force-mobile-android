package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.newcycle.GetNewCycleIndicatorUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.mapper.NewCycleIndicatorMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.header.NewCycleHeaderViewState
import kotlinx.coroutines.launch

class NewCycleIndicatorViewModel(
    private val getNewCycleIndicatorUseCase: GetNewCycleIndicatorUseCase,
    private val newCycleIndicatorMapper: NewCycleIndicatorMapper
) : ViewModel() {

    private val mViewState = MutableLiveData<NewCycleHeaderViewState>()
    val viewState: LiveData<NewCycleHeaderViewState>
        get() = mViewState

    fun fetchNewCycleIndicator(uaKey: LlaveUA) {
        viewModelScope.launch(handler) {
            io {
                val result = getNewCycleIndicatorUseCase.getNewCycleIndicator(uaKey)
                mViewState.postValue(
                    NewCycleHeaderViewState.NewCycleIndicatorListSuccess(
                        newCycleIndicatorMapper.map(result)
                    )
                )
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            mViewState.value =
                NewCycleHeaderViewState.NewCycleIndicatorError(
                    message
                )
        }
    }
}
