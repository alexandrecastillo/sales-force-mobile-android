package biz.belcorp.salesforce.modules.brightpath.features.container

import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync.SyncUseCase
import biz.belcorp.salesforce.base.utils.isGz
import biz.belcorp.salesforce.base.utils.isSe
import biz.belcorp.salesforce.core.base.BasePresenter
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.modules.brightpath.core.domain.sync.SyncBrightPathUseCase
import biz.belcorp.salesforce.modules.brightpath.features.utils.doPrefixWithShortCampaignName
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync.SyncConsultantsUseCase
import kotlinx.coroutines.launch

class IndicatorDetailPresenter(
    private val configurationUseCase: ConfigurationUseCase,
    private val sessionUseCase: ObtenerSesionUseCase,
    private val syncUseCase: SyncUseCase,
    private val syncBrightPathUseCase: SyncBrightPathUseCase,
    private val syncConsultantsUseCase: SyncConsultantsUseCase
) : BasePresenter() {

    private var view: IndicatorDetailView? = null

    private val session by lazy { sessionUseCase.obtener() }

    private val rol = session.rol

    private var isFlagPdvEnabled = false
    private var isEnabledPdv = false

    private var uaSegmentSelected = EMPTY_STRING


    fun create(view: IndicatorDetailView) {
        this.view = view
        launch { isFlagPdvEnabled = configurationUseCase.getConfiguration().isPdv }
    }

    fun destroy() {
        view = null
    }

    fun setUaSegmentSelected(uaSegmentSelected: String) {
        this.uaSegmentSelected = uaSegmentSelected
    }

    fun getUaSegmentSelected(): String {
        return if (rol.isSe()) session.seccion.orEmpty() else this.uaSegmentSelected
    }

    fun initViewData() {
        handleCampaign()
    }

    fun handleIndicatorViews() {
        doAsync {
            val countryConfig = configurationUseCase.getConfiguration()

            uiThread {
                isEnabledPdv = countryConfig.isPdv
                view?.doByRol()
            }
        }
    }

    fun syncConsultants() {
        view?.showLoading()
        doAsync {
            try {
                syncUseCase.sync()
                syncBrightPathUseCase.sync()
                syncConsultantsUseCase.sync(isForced = true)
            } catch (e: Exception) {
                uiThread {
                    view?.hideLoading()
                    view?.showErrorMessaje()
                }
            }

            uiThread {
                view?.hideLoading()
            }
        }
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.BRIGHT_PATH_SYNC, state)
    }

    private fun handleCampaign() {
        val campaign = session.campaign
        view?.drawHeaderDetailKpiView()
        doForCampaignAndPeriod(campaign)
    }

    private fun doForCampaignAndPeriod(campaign: Campania) {
        val shortCampaignName = session.campaign.nombreCorto
        val period = campaign.periodo ?: Campania.Periodo.FACTURACION
        view?.showCampaign(shortCampaignName.doPrefixWithShortCampaignName(period))
    }

    private fun IndicatorDetailView.doByRol() {
        apply {
            when {
                rol.isGz() -> handleGzViews()
                rol.isSe() -> handleSeView()
                else -> Unit
            }
        }
    }

    private fun handleGzViews() {
        handleSeView()
    }

    private fun handleSeView() {
        view?.addConsultantsDrillDown()
    }

}
