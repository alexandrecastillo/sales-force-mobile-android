package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.masvendido.productosmasvendidos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isCO
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.TopSoldProduct
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.preparingiskey.co.TopSalesCoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.preparingiskey.se.TopSalesSeRepository

class GetTopSoldProductsUseCase(
    private val personaRepository: RddPersonaRepository,
    private val campaignsRepository: CampaniasRepository,
    private val topSalesCoRepository: TopSalesCoRepository,
    private val topSalesSeRepository: TopSalesSeRepository
) {
    suspend fun getMostSoldProducts(id: Long, role: Rol): List<TopSoldProduct> {
        val persona = personaRepository.recuperarPersonaPorId(id, role)
        persona?.llaveUA?.let {
            val previousCampaign = campaignsRepository.obtenerCampaniaInmediataAnterior(it)
            return when {
                role.isSE() -> getSeData(persona.llaveUA, previousCampaign.codigo)
                role.isCO() -> getCoData(persona.personCode, previousCampaign.codigo)
                else -> emptyList()
            }
        }
        return emptyList()
    }

    private suspend fun getCoData(consultantCode: String, previousCampaign: String) =
        topSalesCoRepository.getMostSoldProducts(consultantCode, listOf(previousCampaign))

    private suspend fun getSeData(uaKey: LlaveUA, previousCampaign: String): List<TopSoldProduct> {
        return topSalesSeRepository.getMostSoldProducts(uaKey, listOf(previousCampaign))
    }

}
