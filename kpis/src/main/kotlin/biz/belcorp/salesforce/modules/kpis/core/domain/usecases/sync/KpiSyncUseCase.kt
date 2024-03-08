package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.sync

import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiRules
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.*

class KpiSyncUseCase(
    private val saleOrdersRepository: SaleOrdersRepository,
    private val profitRepository: ProfitRepository,
    private val collectionRepository: CollectionRepository,
    private val newCycleRepository: NewCycleRepository,
    private val capitalizationRepository: CapitalizationRepository,
    private val campaignsRepository: CampaniasRepository,
    private val sessionRepository: SessionRepository,
    private val bonificationRepository: BonificationRepository,
    private val retentionRepository: RetentionRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    private val campaigns by lazy {
        campaignsRepository.obtenerCampaniasSincrono(session.llaveUA)
            .take(KpiRules.MAXIMUM_CAMPAIGNS)
    }

    suspend fun sync(@KpiType kpiType: Int) {
        when (kpiType) {
            KpiType.SALE_ORDERS -> syncSaleOrders()
            KpiType.COLLECTION -> {
                syncProfit()
                syncCollection()
                syncRetentions()
            }
            KpiType.NEW_CYCLES -> syncNewCycles()
            KpiType.CAPITALIZATION -> syncCapitalization()
        }
    }

    private suspend fun syncNewCycles() {
        newCycleRepository.sync(getKpiParams())
        bonificationRepository.sync(getKpiParams())
    }

    private suspend fun syncProfit() {
        profitRepository.sync(getKpiParams())
    }

    private suspend fun syncRetentions() {
        retentionRepository.sync(getKpiParams())
    }

    private suspend fun syncCollection() {
        val params = getKpiParams().apply {
            days = listOf(KpiQueryParams.COLLECTION_DAYS_21,KpiQueryParams.COLLECTION_DAYS_31)
        }
        collectionRepository.sync(params)
    }

    private suspend fun syncSaleOrders() {
        saleOrdersRepository.sync(getKpiParams())
    }

    private suspend fun syncCapitalization() {
        capitalizationRepository.sync(getKpiParams())
    }

    private fun getKpiParams(): KpiQueryParams {
        val uaKey = session.llaveUA
        val flags = getFlags()
        return KpiQueryParams(
            country = session.countryIso,
            campaign = campaigns.map { it.codigo },
            profile = session.rol.codigoRol,
            region = uaKey.codigoRegion.orEmpty().deleteHyphen(),
            zone = uaKey.codigoZona.orEmpty().deleteHyphen(),
            section = uaKey.codigoSeccion.orEmpty().deleteHyphen(),
            showRegions = flags.showRegions,
            showZones = flags.showZones,
            showSection = flags.showSections
        )
    }

    private fun getFlags(): Flags = with(session.rol) {
        return@with when {
            isDV() -> Flags(showRegions = true, showZones = true, showSections = false)
            isGR() -> Flags(showRegions = false, showZones = true, showSections = true)
            isGZ() -> Flags(showRegions = false, showZones = false, showSections = true)
            else -> Flags()
        }
    }

    private inner class Flags(
        val showRegions: Boolean = false,
        val showZones: Boolean = false,
        val showSections: Boolean = false
    )
}
