package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.DreamCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.createdream.DreamCreateParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.createdream.DreamCreateQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.deletedream.DreamDeleteParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.deletedream.DreamDeleteQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.editdream.DreamEditParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.editdream.DreamEditQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.getdreams.DreamConsultantParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.getdreams.DreamConsultantQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.data.DreamLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.mapper.DreamMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.Dream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.EditCreateDream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.dream.DreamRepository

class DreamDataRepository(
    private val dreamCloudStore: DreamCloudDataStore,
    private val dreamDataStore: DreamLocalDataStore,
    private val dreamMapper: DreamMapper
) : DreamRepository {

    override suspend fun syncDreams(uaKey: LlaveUA, country: String) {
        val region = uaKey.codigoRegion?.deleteHyphen().orEmpty()
        val zone = uaKey.codigoZona?.deleteHyphen().orEmpty()
        val section = uaKey.codigoSeccion?.deleteHyphen().orEmpty()
        val params = DreamConsultantParams(country, region, zone, section)
        val query = DreamConsultantQuery(params)
        val response = dreamCloudStore.syncConsultantDreams(query)
        val entities = dreamMapper.map(response)
        dreamDataStore.save(entities)
    }

    override suspend fun getDreams(consultantCode: String?, uaKey: LlaveUA): List<Dream> =
        dreamMapper.map(dreamDataStore.getDreamsByConsultantId(consultantCode))

    override suspend fun deleteDream(dream: Dream?, country: String?) {
        val params = DreamDeleteParams(dream?.dreamId!!, country!!)
        val query = DreamDeleteQuery(params)
        dreamCloudStore.deleteDream(query)
        val entity = dreamDataStore.findDreamById(dream.dreamId!!)
        dreamDataStore.deleteDream(entity?.id!!)
    }

    override suspend fun createDreams(uaKey: LlaveUA, country: String, dream: EditCreateDream) {
        val region = uaKey.codigoRegion?.deleteHyphen().orEmpty()
        val zone = uaKey.codigoZona?.deleteHyphen().orEmpty()
        val section = uaKey.codigoSeccion?.deleteHyphen().orEmpty()
        val params = DreamCreateParams(
            country = country,
            consultantCode = dream.consultantCode,
            zone = zone,
            region = region,
            section = section,
            dream = dream.dreamDescription,
            comments = dream.dreamComments,
            campaignsToAchieve = dream.dreamsCampaignsAchieve.toInt(),
            amountToComplete = dream.dreamAmount.toLong(),
            campaignCreated = dream.actualCampaign

        )
        val query = DreamCreateQuery(params)
        dreamCloudStore.syncSaveConsultantDreams(query)
    }

    override suspend fun editDreams(
        uaKey: LlaveUA,
        country: String,
        dream: EditCreateDream,
        campaign: String
    ) {
        val params = DreamEditParams(
            country = country,
            id = dream.dreamId,
            dream = dream.dreamDescription,
            comments = dream.dreamComments,
            campaignsToAchieve = dream.dreamsCampaignsAchieve.toInt(),
            amountToComplete = dream.dreamAmount.toLong(),
            campaignCreated = dream.actualCampaign,
            campaign = campaign
        )
        val query = DreamEditQuery(params)
        dreamCloudStore.syncEditConsultantDreams(query)
    }
}
