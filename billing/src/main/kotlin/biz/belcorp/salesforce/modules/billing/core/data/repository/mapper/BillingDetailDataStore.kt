package biz.belcorp.salesforce.modules.billing.core.data.repository.mapper

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity
import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity_
import biz.belcorp.salesforce.core.entities.newcycles.NewCycleEntity
import biz.belcorp.salesforce.core.entities.newcycles.NewCycleEntity_
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.core.utils.inValues
import io.objectbox.kotlin.boxFor

class BillingDetailDataStore {

    fun getPegsRetainedOrders(
        uaKey: LlaveUA,
        campaignCodes: List<String>
    ): List<CapitalizationEntity> {
        return boxStore.boxFor<CapitalizationEntity>()
            .query()
            .equal(CapitalizationEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(CapitalizationEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(CapitalizationEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .inValues(CapitalizationEntity_.campaign, campaignCodes.toTypedArray())
            .build()
            .find()
    }

    fun getNewCyclePendingOrders(
        uaKey: LlaveUA,
        campaignCodes: List<String>
    ): List<NewCycleEntity> {
        return boxStore.boxFor<NewCycleEntity>().query()
            .equal(NewCycleEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(NewCycleEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(NewCycleEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .inValues(NewCycleEntity_.campaign, campaignCodes.toTypedArray())
            .build()
            .find()
    }
}
