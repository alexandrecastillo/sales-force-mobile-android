package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.masvendido.marcasycategorias

import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.BrandWithCategoryList
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.Category
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.EsikaLbelData
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.MultiCategories
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.MultiMark
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.preparingiskey.co.TopSalesCoRepository

class GetBrandsAndCategoriesUseCase(
    private val personaRepository: RddPersonaRepository,
    private val campaignsRepository: CampaniasRepository,
    private val marksAndCategoriesRepository: TopSalesCoRepository,
    private val sessionRepository: SessionRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getBrandsAndCategoriesInfo(
        id: Long,
        rol: Rol
    ): Pair<List<Category>, List<BrandWithCategoryList>> {

        val persona = personaRepository.recuperarPersonaPorId(id, rol)
        persona?.llaveUA?.let {
            val previousCampaign = campaignsRepository.obtenerCampaniaInmediataAnterior(it)
            val info = marksAndCategoriesRepository.getBrandsAndCategories(
                persona.personCode, listOf(previousCampaign.codigo)
            )
            return Pair(info.categories, info.brands)
        }
        return Pair(listOf(), listOf())
    }

    suspend fun getSellingData(
        id: Long,
        rol: Rol
    ): Pair<String, String> {

        val persona = personaRepository.recuperarPersonaPorId(id, rol)
        persona?.llaveUA?.let {
            val previousCampaign = campaignsRepository.obtenerCampaniaInmediataAnterior(it)
            val info = marksAndCategoriesRepository.getBrandsAndCategories(
                persona.personCode, listOf(previousCampaign.codigo)
            )
            return Pair(
                info.brandToPromote ?: Constant.EMPTY_STRING,
                info.topSellingBrand ?: Constant.EMPTY_STRING
            )
        }
        return Pair(Constant.EMPTY_STRING, Constant.EMPTY_STRING)
    }

    suspend fun getMultiMarkMultiCategoriesData(
        id: Long,
        rol: Rol
    ): Pair<List<MultiMark>, List<MultiCategories>> {

        val persona = personaRepository.recuperarPersonaPorId(id, rol)
        persona?.llaveUA?.let {
            val previousCampaign = campaignsRepository.obtenerPenultimasCampanias(it, 3)

            val codes = previousCampaign.map { it -> it.codigo }.toMutableList()

            val data = marksAndCategoriesRepository.getMultiCategory(
                persona.personCode, codes
            )
            return Pair(
                data.multiMark ?: emptyList(),
                data.multiCategories ?: emptyList()
            )
        }
        return Pair(emptyList(), emptyList())
    }

    suspend fun getProductsLbelEsika(
        id: Long,
        rol: Rol
    ): EsikaLbelData {

        val persona = personaRepository.recuperarPersonaPorId(id, rol)
        persona?.llaveUA?.let {
            val previousCampaign = campaignsRepository.obtenerPenultimasCampanias(it, 3)

            val codes = previousCampaign.map { it -> it.codigo }.toMutableList()

            return marksAndCategoriesRepository.getProductsLbelEsika(
                persona.personCode, codes
            )
        }
        return EsikaLbelData(emptyList(), emptyList(), emptyList())
    }
}
