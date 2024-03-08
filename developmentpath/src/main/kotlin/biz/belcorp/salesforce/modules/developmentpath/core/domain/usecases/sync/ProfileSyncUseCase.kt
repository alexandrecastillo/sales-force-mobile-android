package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isCO
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.sales.SaleConsultantParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.co.TopSalesCoParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.se.TopSalesSeParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.NUMBER_SIX
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.NUMBER_THREE
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.DigitalSaleSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.SaleConsultantSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.preparingiskey.TopSalesCoSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.preparingiskey.TopSalesSeSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.capitalization.ProfileSeCapitalizationSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.profit.ProfileProfitSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.seordersu6c.ProfileSeOrdersU6CSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ranking.ProfileRankingRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.model.DigitalSaleQueryParams

class ProfileSyncUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
    private val saleConsultantSyncRepository: SaleConsultantSyncRepository,
    private val digitalSaleSyncRepository: DigitalSaleSyncRepository,
    private val mostSoldCoSyncRepository: TopSalesCoSyncRepository,
    private val mostSoldSeSyncRepository: TopSalesSeSyncRepository,
    private val profileSeOrdersU6CSyncRepository: ProfileSeOrdersU6CSyncRepository,
    private val profileSeCapitalizationSyncDataRepository: ProfileSeCapitalizationSyncRepository,
    private val profileProfitSyncDataRepository: ProfileProfitSyncRepository,
    private val profileRankingRepository: ProfileRankingRepository,
) {

    private val session get() = requireNotNull(sessionRepository.getSession())
    private var campaigns: List<String> = emptyList()

    suspend fun sync(role: Rol, uaKey: LlaveUA, consultantCode: String) {
        getPreviousCampaigns(uaKey)
        when {
            role.isCO() -> syncConsultantInfo(role, uaKey, consultantCode)
            role.isSE() -> syncPartnersInfo(role, uaKey, consultantCode)
            role.isGZ() -> syncZoneManagersInfo(uaKey)
        }
    }

    private suspend fun syncConsultantInfo(rol: Rol, uaKey: LlaveUA, consultantCode: String) {
        syncConsultantSale(consultantCode, uaKey)
        syncTopSalesCo(consultantCode, uaKey)
        syncDigitalSales(rol, uaKey, consultantCode)
    }

    private suspend fun syncPartnersInfo(rol: Rol, uaKey: LlaveUA, consultantCode: String) {
        syncTopSalesSe(uaKey)
        syncDigitalSales(rol, uaKey, consultantCode)
        syncU6CInfoSe(uaKey)
    }

    private suspend fun syncZoneManagersInfo(uaKey: LlaveUA) {
        syncRankingInfo(uaKey)
    }

    private suspend fun syncU6CInfoSe(uaKey: LlaveUA) {
        val params = getKpiParams(uaKey)
        profileSeOrdersU6CSyncRepository.sync(params)
        profileSeCapitalizationSyncDataRepository.sync(params)
        profileProfitSyncDataRepository.sync(params)
    }

    private suspend fun syncRankingInfo(uaKey: LlaveUA) {
        val params = getKpiParams(uaKey)
        val campaigns = params.campaign
        profileSeOrdersU6CSyncRepository.sync(params)
        profileRankingRepository.syncRankingInfo(uaKey, campaigns, session.countryIso)
    }

    private suspend fun syncConsultantSale(consultantCode: String, uaKey: LlaveUA) {
        val campaigns = campaigns.takeLast(NUMBER_SIX)
        val params = SaleConsultantParams(
            country = session.countryIso,
            consultantCode = consultantCode,
            campaigns = campaigns,
            region = uaKey.codigoRegion.orEmpty(),
            zone = uaKey.codigoZona.orEmpty(),
            section = uaKey.codigoSeccion.orEmpty()
        )
        saleConsultantSyncRepository.sync(params)
    }

    private suspend fun syncTopSalesCo(consultantCode: String, uaKey: LlaveUA) {
        val campaigns = campaigns.takeLast(NUMBER_ONE)
        val params = TopSalesCoParams(
            country = session.countryIso,
            campaigns = campaigns,
            consultantCode = consultantCode,
            region = uaKey.codigoRegion.orEmpty(),
            zone = uaKey.codigoZona.orEmpty(),
            section = uaKey.codigoSeccion.orEmpty()
        )
        mostSoldCoSyncRepository.sync(params)
    }

    private suspend fun syncTopSalesSe(uaKey: LlaveUA) {
        val campaigns = campaigns.takeLast(NUMBER_ONE)
        val params = TopSalesSeParams(
            country = session.countryIso,
            campaign = campaigns,
            region = uaKey.codigoRegion.orEmpty(),
            zone = uaKey.codigoZona.orEmpty(),
            section = uaKey.codigoSeccion.orEmpty()
        )
        mostSoldSeSyncRepository.sync(params)
    }

    private suspend fun syncDigitalSales(rol: Rol, uaKey: LlaveUA, consultantCode: String) {
        val campaigns = campaigns.takeLast(NUMBER_THREE)
        val params = DigitalSaleQueryParams(session.countryIso, campaigns, consultantCode, uaKey)
        digitalSaleSyncRepository.sync(rol, params)
    }


    private fun getPreviousCampaigns(uaKey: LlaveUA) {
        val previousCampaigns = campaignsRepository.obtenerPenultimasCampanias(uaKey)
        campaigns = previousCampaigns.map { it.codigo }.sorted()
    }

    private fun getKpiParams(uaKey: LlaveUA): KpiRequestParams {
        return KpiRequestParams(
            country = session.countryIso,
            campaign = campaigns.takeLast(NUMBER_SIX),
            profile = uaKey.roleAssociated.codigoRol,
            region = uaKey.codigoRegion.orEmpty(),
            zone = uaKey.codigoZona.orEmpty(),
            section = uaKey.codigoSeccion.orEmpty()
        )
    }

}
