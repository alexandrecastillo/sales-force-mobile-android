package biz.belcorp.salesforce.modules.digital.features.digital.view.header

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.lazyDeferred
import biz.belcorp.salesforce.core.utils.toHundredPercentage
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.digital.R
import biz.belcorp.salesforce.modules.digital.core.domain.usecases.GetDigitalInfoUseCase
import biz.belcorp.salesforce.modules.digital.features.digital.mappers.DigitalInfoMapper
import biz.belcorp.salesforce.modules.digital.features.digital.model.DigitalHeaderItemModel
import biz.belcorp.salesforce.modules.digital.features.digital.model.DigitalHeaderModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random

class DigitalHeaderViewModel(
    private val getDigitalInfoUseCase: GetDigitalInfoUseCase,
    private val configUseCase: ConfigurationUseCase,
    private val infoMapper: DigitalInfoMapper
) : ViewModel() {

    private val config by viewModelScope.lazyDeferred {
        configUseCase.getConfiguration()
    }

    val viewState: LiveData<DigitalHeaderViewState>
        get() = _viewState

    private var _viewState = MutableLiveData<DigitalHeaderViewState>()

    fun getDigitalInfo(uaKey: LlaveUA) {
        viewModelScope.launch(handler) {
            io {
                val mConfig = config.await()
                val info = getDigitalInfoUseCase.getDigitalInfo(uaKey)
                val list = infoMapper.map(mConfig.onlineStoreTitle, mConfig.onlineStoreType, info)
                val model = DigitalHeaderModel(list)
                val state = DigitalHeaderViewState.Success(model)
                _viewState.postValue(state)
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        ui { _viewState.postValue(DigitalHeaderViewState.NoDataAvailable) }
    }

}
