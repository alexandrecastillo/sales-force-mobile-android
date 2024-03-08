package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp;

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.DreamBpCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.deletebpdream.DreamDeleteBpParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.deletebpdream.DreamDeleteBpQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.dreamcreate.DreamCreateBpParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.dreamcreate.DreamCreateBpQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.editbpdream.DreamEditBpParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.editbpdream.DreamEditBpQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.getbpdreams.DreamBpParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.getbpdreams.DreamBpQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.data.DreamBpLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.mapper.DreamBpMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.Dream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.DreamBusinessPartner
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.EditCreateDream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.dream_bp.DreamBpRepository

class DreamBpDataRepository(
    private val dreamBpCloudStore: DreamBpCloudDataStore,
    private val dreamBpDataStore: DreamBpLocalDataStore,
    private val dreamBpMapper: DreamBpMapper
) : DreamBpRepository {

    override suspend fun syncBusinessPartnerDreams(uaKey: LlaveUA, country: String) {
        val region = uaKey.codigoRegion?.deleteHyphen().orEmpty()
        val zone = uaKey.codigoZona?.deleteHyphen().orEmpty()
        val section = uaKey.codigoSeccion?.deleteHyphen().orEmpty()
        val params = DreamBpParams(country, region, zone, section)
        val query = DreamBpQuery(params)
        val response = dreamBpCloudStore.syncBpDreams(query)
        val entities = dreamBpMapper.map(response)
        dreamBpDataStore.save(entities)

    }

    override suspend fun getBusinessPartnerDream(
        businessPartnerCode: String?,
        uaKey: LlaveUA
    ): List<DreamBusinessPartner> =
        dreamBpMapper.map(dreamBpDataStore.getDreamsByBusinessPartnerId(businessPartnerCode))

    override suspend fun createBusinessPartnerDream(
        uaKey: LlaveUA,
        country: String,
        dream: EditCreateDream
    ) {
        val region = uaKey.codigoRegion?.deleteHyphen().orEmpty()
        val zone = uaKey.codigoZona?.deleteHyphen().orEmpty()
        val section = uaKey.codigoSeccion?.deleteHyphen().orEmpty()
        val params = DreamCreateBpParams(
            country = country,
            code = dream.bpCode,
            zone = zone,
            region = region,
            section = section,
            dream = dream.dreamDescription,
            comments = dream.dreamComments,
            campaignsToAchieve = dream.dreamsCampaignsAchieve.toInt(),
            amountToComplete = dream.dreamAmount.toLong(),
            campaignCreated = dream.actualCampaign

        )
        val query = DreamCreateBpQuery(params)
        dreamBpCloudStore.syncSaveBusinessPartnerDreams(query)
    }

    override suspend fun deleteBusinessPartnerDream(dream: Dream?, country: String?) {
        val params = DreamDeleteBpParams(dream?.dreamId!!, country!!)
        val query = DreamDeleteBpQuery(params)
        dreamBpCloudStore.deleteBusinessPartnerDream(query)
        val entity = dreamBpDataStore.findDreamById(dream.dreamId!!)
        dreamBpDataStore.deleteDream(entity?.id!!)
    }

    override suspend fun editBusinessPartnerDreams(
        uaKey: LlaveUA,
        country: String,
        dream: EditCreateDream,
        campaign: String
    ) {
        val params = DreamEditBpParams(
            country = country,
            id = dream.dreamId,
            dream = dream.dreamDescription,
            comments = dream.dreamComments,
            campaignsToAchieve = dream.dreamsCampaignsAchieve.toInt(),
            amountToComplete = dream.dreamAmount.toLong(),
            campaignCreated = dream.actualCampaign,
            campaign = campaign
        )
        val query = DreamEditBpQuery(params)
        dreamBpCloudStore.syncEditBusinessPartnerDreams(query)
    }
}
