package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.retention.RetentionEntity
import biz.belcorp.salesforce.core.entities.retention.RetentionEntity_
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.core.utils.inValues
import io.objectbox.kotlin.boxFor

class RetentionDataStore {

    fun saveRetention(retention: List<RetentionEntity>) {
        with(ObjectBox.boxStore) {
            runInTx {
                if (retention.isNotEmpty()) {
                    boxFor<RetentionEntity>().query()
                        .equal(RetentionEntity_.region, retention[0].region).and()
                        .equal(RetentionEntity_.zone, retention[0].zone).and()
                        .equal(RetentionEntity_.section, retention[0].section)
                        .build()
                        .remove()
                }
                boxFor<RetentionEntity>().put(retention)
            }
        }
    }

    fun getRetentionByCampaigns(uaKey: LlaveUA, campaigns: List<String>): List<RetentionEntity> {
        return ObjectBox.boxStore.boxFor<RetentionEntity>().query()
            .equal(RetentionEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(RetentionEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(RetentionEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .inValues(RetentionEntity_.campaign, campaigns.toTypedArray())
            .build()
            .find()
    }

}
