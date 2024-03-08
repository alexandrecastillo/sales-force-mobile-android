package biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.rejectedorders.RejectedOrderDetailEntity
import biz.belcorp.salesforce.core.entities.rejectedorders.RejectedOrderEntity
import biz.belcorp.salesforce.core.entities.rejectedorders.RejectedOrderEntity_
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor

class RejectedOrdersDataStore {

    fun saveRejectedOrders(rejectedOrders: List<RejectedOrderEntity>) {
        boxStore.boxFor<RejectedOrderEntity>()
            .deleteAndInsert(rejectedOrders)
    }

    fun saveRejectedOrdersDetail(rejectOrdersDetails: List<RejectedOrderDetailEntity>) {
        boxStore.boxFor<RejectedOrderDetailEntity>()
            .deleteAndInsert(rejectOrdersDetails)
    }

    fun getRejectedOrders(uaKey: LlaveUA, campaignCode: String): List<RejectedOrderDetailEntity> {
        val parent = boxStore.boxFor<RejectedOrderEntity>().query()
            .equal(RejectedOrderEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(RejectedOrderEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(RejectedOrderEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .equal(RejectedOrderEntity_.campaign, campaignCode)
            .build()
            .findFirst()
        return parent?.reasons ?: emptyList()
    }

}
