package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.mypartners

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.consultants.core.data.network.dto.MyPartnersParams
import biz.belcorp.salesforce.modules.consultants.core.data.repository.mypartner.MyPartnerRepository

class MyPartnersUseCase(
    private val repository: MyPartnerRepository,
    private val sessionManager: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
) {

    private val session get() = requireNotNull(sessionManager.getSession())


    suspend fun syncMyPartners() {

        val campaigns = getPreviousCampaigns(session.llaveUA)!!.codigo

        val params = MyPartnersParams(
            country = session.countryIso,
            campaigns = campaigns,
            region = session.region.orEmpty(),
            zone = session.zona.orEmpty(),
        )

        repository.sync(params)
    }


    private fun getPreviousCampaigns(uaKey: LlaveUA): Campania? {
        return campaignsRepository.obtenerPenultimasCampanias(uaKey, Constant.NUMBER_ONE).first()
    }

}
