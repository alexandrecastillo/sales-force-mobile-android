package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.ventas

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.data.GainConsultantDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.ventas.GainConsultantMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.ventas.GainConsultant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.GainConsultantRepository

class GainConsultantDataRepository(
    private val gainConsultantDataStore: GainConsultantDataStore,
    private val gainConsultantMapper: GainConsultantMapper
) : GainConsultantRepository {

    override suspend fun getGainConsultant(
        consultantCode: String,
        campaigns: List<String>
    ): List<GainConsultant> {
        return gainConsultantDataStore.getGainConsultant(consultantCode, campaigns)
            .map(gainConsultantMapper::map)
    }
}
