package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.ConsultantSaleEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.ConsultantSaleEntity_
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.core.utils.inValues
import io.objectbox.kotlin.boxFor

open class SaleConsultantDataStore {

    fun saveConsultantSales(sales: List<ConsultantSaleEntity>) {
        boxStore.boxFor<ConsultantSaleEntity>().put(sales)
    }

    fun getConsultantSales(
        consultantCode: String,
        campaigns: List<String>
    ): List<ConsultantSaleEntity> {
        return boxStore.boxFor<ConsultantSaleEntity>()
            .query()
            .equal(ConsultantSaleEntity_.consultantCode, consultantCode).and()
            .inValues(ConsultantSaleEntity_.campaign, campaigns.toTypedArray())
            .build()
            .find()
    }

    fun hasConsultantSales(consultantCode: String, campaigns: List<String>): Boolean {
        return getConsultantSales(consultantCode, campaigns).isNotEmpty()
    }
}
