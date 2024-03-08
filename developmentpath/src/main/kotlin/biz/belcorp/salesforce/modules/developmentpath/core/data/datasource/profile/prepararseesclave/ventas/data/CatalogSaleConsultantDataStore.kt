package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.ConsultantSaleEntity
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor

class CatalogSaleConsultantDataStore : SaleConsultantDataStore() {

    fun getCatalogSale(
        consultantCode: String,
        campaigns: List<String>
    ): Pair<List<ConsultantSaleEntity>, ConsultantEntity?> {
        val sales = super.getConsultantSales(consultantCode, campaigns)
        return Pair(sales, getConsultant(consultantCode))
    }

    private fun getConsultant(consultantCode: String): ConsultantEntity? {
        return boxStore.boxFor<ConsultantEntity>()
            .query()
            .equal(ConsultantEntity_.code, consultantCode)
            .build()
            .findFirst()
    }
}
