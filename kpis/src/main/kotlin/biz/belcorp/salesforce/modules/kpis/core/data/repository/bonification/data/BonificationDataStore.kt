package biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.bonification.BonificationEntity
import biz.belcorp.salesforce.core.entities.bonification.BonificationEntity_
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor

class BonificationDataStore {

    fun saveBonification(entities: List<BonificationEntity>) {
        boxStore
            .boxFor<BonificationEntity>()
            .deleteAndInsert(entities)
    }

    fun getBonification(uaKey: LlaveUA, campaign: String): List<BonificationEntity> {
        return boxStore.boxFor<BonificationEntity>().query()
            .equal(BonificationEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(BonificationEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(BonificationEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .equal(BonificationEntity_.campaign, campaign)
            .build()
            .find()
    }

}
