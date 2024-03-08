package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos

import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.Brand
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.preparingiskey.se.TopSalesSeRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.masvendido.marcasycategorias.CountryBrandFactory

class GetBrandsUseCase(
    private val personaRepository: RddPersonaRepository,
    private val campaignsRepository: CampaniasRepository,
    private val sessionRepository: SessionRepository,
    private val topSalesSeRepository: TopSalesSeRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getBrandsInfo(id: Long, rol: Rol): List<Brand> {

        val persona = personaRepository.recuperarPersonaPorId(id, rol)
        persona?.llaveUA?.let {
            val previousCampaign = campaignsRepository.obtenerCampaniaInmediataAnterior(it)
            val data = topSalesSeRepository.getBrands(it, listOf(previousCampaign.codigo))
            return CountryBrandFactory.getFilteredBrandsOld(
                session.countryIso,
                data.brands
            )
        }
        return emptyList()
    }

}
