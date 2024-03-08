package biz.belcorp.salesforce.modules.brightpath.features.brightpath

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeLevelEntity
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.BusinessPartnerChangeLevelUseCase
import biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.GetBusinessPartnerChangeLevelUseCase
import biz.belcorp.salesforce.modules.consultants.core.data.network.dto.MyPartnersDto
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.collection.GetGainPartnerUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.collection.GetGainUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.mapper.CollectionDetailMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.model.GainDetailSeModel
import kotlinx.coroutines.launch

class BrightPathChangeLevelViewModel(
    private val sessionUseCase: ObtenerSesionUseCase,
    private val kpiUseCase: GetGainUseCase,
    private val kpiUseCasePartner: GetGainPartnerUseCase,
    private val collectionMapper: CollectionDetailMapper,
    private val levelUseCase: BusinessPartnerChangeLevelUseCase,
    private val getBusinessPartnerChangeLevelUseCase: GetBusinessPartnerChangeLevelUseCase,
    private val businessPartnerChangeLevelModelMapper: BusinessPartnerChangeLevelModelMapper,
    private val sessionManager: SessionRepository,
) : ViewModel() {

    private val session get() = requireNotNull(sessionManager.getSession())

    val viewState: LiveData<UserInfoViewState>
        get() = viewModel

    private val viewModel = MutableLiveData<UserInfoViewState>()


    val viewStateLevel: LiveData<UserInfoLevelViewState>
        get() = viewModelLevel

    private val viewModelLevel = MutableLiveData<UserInfoLevelViewState>()


    private val _dataPartner = MutableLiveData<MyPartnersDto.NewBusinessPartner>()
    val dataPartner: LiveData<MyPartnersDto.NewBusinessPartner> = _dataPartner

    fun setDataPartner(data: MyPartnersDto.NewBusinessPartner) {
        _dataPartner.value = data
    }

    fun uaKeyFromPartner(): LlaveUA {
        return LlaveUA(
            codigoRegion = dataPartner.value?.region,
            codigoZona = dataPartner.value?.zone,
            codigoSeccion = dataPartner.value?.section
        )
    }


    fun getUserInformation() {
        viewModelScope.launch(handler) {
            io {
                val userInformation = sessionUseCase.obtener()
                viewModel.postValue(UserInfoViewState.SuccessSession(userInformation))
            }
        }
    }

    fun getLevelInformation(uaKey: LlaveUA? = null) {

        viewModelScope.launch(handlerLevel) {
            io {

                val level: MutableList<BusinessPartnerChangeLevelEntity> = if (uaKey.isNotNull()) {
                    levelUseCase.syncConsultantSalePartner(uaKey!!, dataPartner.value?.consultantCode!!)
                    getBusinessPartnerChangeLevelUseCase.getBusinessPartnerLevel(uaKey)
                } else {
                    levelUseCase.syncConsultantSale()
                    getBusinessPartnerChangeLevelUseCase.getBusinessPartnerLevel(session.llaveUA)
                }


                viewModelLevel.postValue(
                    UserInfoLevelViewState.SuccessLevel(businessPartnerChangeLevelModelMapper.mapToModel(level))
                )

            }
        }
    }

    fun getGainSe(uaKey: LlaveUA) {
        viewModelScope.launch(handler) {
            io {
                val data = kpiUseCase.getGainInformation(uaKey)
                val model = collectionMapper.mapToCollection(data)
                viewModel.postValue(UserInfoViewState.SuccessKpis(model))
            }
        }
    }

    fun getGainSePartner(uaKey: LlaveUA) {
        viewModelScope.launch(handler) {
            io {
                val data = kpiUseCasePartner.getGainInformation(uaKey)
                val model = collectionMapper.mapToCollection(data)
                viewModel.postValue(UserInfoViewState.SuccessKpis(model))
            }
        }
    }

    private val handlerLevel = CoroutineExceptionHandler { _, _ ->
        ui {

            val level: MutableList<BusinessPartnerChangeLevelEntity> = if (dataPartner.value.isNotNull()) {
                getBusinessPartnerChangeLevelUseCase.getBusinessPartnerLevel(uaKeyFromPartner())
            } else {
                getBusinessPartnerChangeLevelUseCase.getBusinessPartnerLevel(session.llaveUA)
            }

            viewModelLevel.postValue(
                UserInfoLevelViewState.SuccessLevel(businessPartnerChangeLevelModelMapper.mapToModel(level))
            )
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            viewModel.value = UserInfoViewState.Failure(exception.message.orEmpty())
        }
    }

    sealed class UserInfoViewState {
        class SuccessSession(val user: Sesion) : UserInfoViewState()

        class SuccessKpis(val kpi: GainDetailSeModel) : UserInfoViewState()
        class Failure(val message: String) : UserInfoViewState()
    }

    sealed class UserInfoLevelViewState {
        class SuccessLevel(val data: List<BusinessPartnerChangeLevelModel>) : UserInfoLevelViewState()

        class Failure(val message: String) : UserInfoLevelViewState()
    }

}
