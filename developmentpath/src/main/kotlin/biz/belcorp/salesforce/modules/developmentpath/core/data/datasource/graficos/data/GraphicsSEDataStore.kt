package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity
import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity_
import biz.belcorp.salesforce.core.entities.collection.ProfitEntity
import biz.belcorp.salesforce.core.entities.collection.ProfitEntity_
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity_
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.core.utils.inValues
import io.objectbox.kotlin.boxFor

class GraphicsSEDataStore {

    fun getCapitalization(uaKey: LlaveUA, campaigns: List<String>): List<CapitalizationEntity> {
        return boxStore.boxFor<CapitalizationEntity>().query()
            .equal(CapitalizationEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen()).and()
            .equal(CapitalizationEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen()).and()
            .equal(CapitalizationEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen()).and()
            .inValues(CapitalizationEntity_.campaign, campaigns.toTypedArray())
            .build()
            .find()
    }

    fun getNetSaleSeData(uaKey: LlaveUA, campaigns: List<String>): List<SaleOrdersEntity> {
        return boxStore.boxFor<SaleOrdersEntity>().query()
            .equal(SaleOrdersEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen()).and()
            .equal(SaleOrdersEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen()).and()
            .equal(SaleOrdersEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen()).and()
            .inValues(SaleOrdersEntity_.campaign, campaigns.toTypedArray())
            .build()
            .find()
    }

    fun getProfitSeData(uaKey: LlaveUA, campaigns: List<String>): List<ProfitEntity> {
        return boxStore.boxFor<ProfitEntity>().query()
            .equal(ProfitEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen()).and()
            .equal(ProfitEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen()).and()
            .equal(ProfitEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen()).and()
            .inValues(ProfitEntity_.campaign, campaigns.toTypedArray())
            .build()
            .find()
    }

}
