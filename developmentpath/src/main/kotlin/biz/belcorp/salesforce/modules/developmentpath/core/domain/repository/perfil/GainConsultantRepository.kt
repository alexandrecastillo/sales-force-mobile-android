package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.ventas.GainConsultant

interface GainConsultantRepository {
    suspend fun getGainConsultant(
        consultantCode: String,
        campaigns: List<String>
    ): List<GainConsultant>
}
