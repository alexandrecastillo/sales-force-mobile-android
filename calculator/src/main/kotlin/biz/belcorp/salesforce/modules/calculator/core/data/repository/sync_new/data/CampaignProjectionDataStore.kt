package biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.campaignprojection.CampaignProjectionEntity
import biz.belcorp.salesforce.core.entities.campaignprojection.CampaignProjectionEntity_
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor

class CampaignProjectionDataStore {

    fun save(item: CampaignProjectionEntity) {
        ObjectBox.boxStore.boxFor<CampaignProjectionEntity>().deleteAndInsert(listOf(item))
    }

    fun getSavedCampaignProjection(uaKey: LlaveUA, sectionPartner: String?): CampaignProjectionEntity? =
        ObjectBox.boxStore.boxFor<CampaignProjectionEntity>()
            .query()
            .equal(CampaignProjectionEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(CampaignProjectionEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(CampaignProjectionEntity_.section, sectionPartner ?: uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .build()
            .findFirst()
}
