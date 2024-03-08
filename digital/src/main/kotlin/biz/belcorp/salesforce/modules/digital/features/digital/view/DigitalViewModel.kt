package biz.belcorp.salesforce.modules.digital.features.digital.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.features.uainfo.UaInfoMapper
import biz.belcorp.salesforce.core.features.uainfo.UaInfoModel
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.digital.features.constants.DigitalFilterType
import biz.belcorp.salesforce.modules.digital.features.utils.DigitalTextResolver
import kotlinx.coroutines.launch

class DigitalViewModel(
    private val sessionUseCase: ObtenerSesionUseCase,
    private val configUseCase: ConfigurationUseCase,
    private val uaUseCase: UaInfoUseCase,
    private val uaMapper: UaInfoMapper,
    private val textResolver: DigitalTextResolver
) : ViewModel() {

    private val session by lazy { sessionUseCase.obtener() }

    private var storeTitle = Constant.EMPTY_STRING

    val uaViewState: LiveData<DigitalViewState>
        get() = _uaViewState

    private val _uaViewState = MutableLiveData<DigitalViewState>()

    private var lastFilterType = DigitalFilterType.NONE
    private var lastUaInfoModel: UaInfoModel? = null

    fun getUaInformation() {
        viewModelScope.launch(handler) {
            io {
                storeTitle = configUseCase.getConfiguration().onlineStoreTitle
                val uas = uaUseCase.getAssociatedUaListByUaKey(session.llaveUA)
                val selectorModel = uaMapper.mapToSelector(uas)
                val state = DigitalViewState.Success(session.rol, session.llaveUA, selectorModel)
                _uaViewState.postValue(state)
            }
        }
    }

    fun updateChildInfo(model: UaInfoModel) {
        lastUaInfoModel = model
        _uaViewState.value = getInfoViewState(true)
    }

    fun updateSelectors(@DigitalFilterType type: Int) {
        lastFilterType = type
        _uaViewState.value = getInfoViewState(false)
    }

    private fun getInfoViewState(updateHeader: Boolean): DigitalViewState.ShowInfo {
        val uaKey = lastUaInfoModel?.uaKey ?: session.llaveUA
        return DigitalViewState.ShowInfo(
            uaKey = uaKey,
            type = lastFilterType,
            asParent = lastUaInfoModel?.isThirdPerson != true,
            detailTitle = getSubtitleText(uaKey),
            childDescription = getChildUaDescription(),
            updateHeader = updateHeader
        )
    }

    private fun getChildUaDescription(): String {
        val model = lastUaInfoModel?.takeIf { it.isThirdPerson } ?: return Constant.EMPTY_STRING
        val uaName = uaMapper.map(model.label, role = model.role)
        return textResolver.getChildInformationText(uaName, storeTitle, lastFilterType)
    }

    private fun getSubtitleText(uaKey: LlaveUA): String {
        val campaign = lastUaInfoModel?.campaign ?: session.campaign
        return textResolver.getSubtitleDetailText(uaKey.roleAssociated, campaign.shortNameOnly)
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

}
