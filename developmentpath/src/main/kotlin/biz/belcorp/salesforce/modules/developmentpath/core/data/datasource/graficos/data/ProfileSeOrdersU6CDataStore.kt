package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity_
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.core.utils.inValues
import io.objectbox.kotlin.boxFor

class ProfileSeOrdersU6CDataStore {

    fun save(items: List<SaleOrdersEntity>) {
        boxStore.boxFor<SaleOrdersEntity>().put(items)
    }

    fun getStoredDataByUa(uaKey: LlaveUA): List<SaleOrdersEntity> =
        boxStore.boxFor<SaleOrdersEntity>().query()
            .equal(SaleOrdersEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and().equal(SaleOrdersEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and().equal(SaleOrdersEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .build()
            .find()

    fun getOrdersByUaAndCampaign(uaKey: LlaveUA, campaigns: List<String>): List<SaleOrdersEntity> =
        boxStore.boxFor<SaleOrdersEntity>().query()
            .equal(SaleOrdersEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and().equal(SaleOrdersEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and().equal(SaleOrdersEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and().inValues(SaleOrdersEntity_.campaign, campaigns.toTypedArray())
            .build()
            .find()

}
