package biz.belcorp.salesforce.modules.billing.core.data.repository

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity_
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor

class BillingDataStore {

    fun getBillingAdvancement(uaKey: LlaveUA): List<SaleOrdersEntity> {
        return boxStore.boxFor<SaleOrdersEntity>().query()
            .equal(SaleOrdersEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(SaleOrdersEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(SaleOrdersEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .build()
            .find()
    }
}
