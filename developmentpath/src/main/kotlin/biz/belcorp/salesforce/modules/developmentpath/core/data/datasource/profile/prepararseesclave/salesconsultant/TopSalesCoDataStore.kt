package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.salesconsultant

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.*
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.core.utils.inValues
import io.objectbox.kotlin.boxFor

class TopSalesCoDataStore {

    fun saveSalesConsultants(items: List<TopSalesCoEntity>) {
        with(boxStore) {
            val topSalesCoList = getSalesConsultant(items)
            runInTx {
                if (topSalesCoList.isNotEmpty()) {
                    deleteTopSalesCo(topSalesCoList)
                }
                boxFor<TopSalesCoEntity>().put(items)
            }
        }
    }

    fun getSalesConsultant(
        consultantCode: String,
        campaign: List<String>
    ): List<TopSalesCoEntity> {
        return boxStore.boxFor<TopSalesCoEntity>().query()
            .equal(TopSalesCoEntity_.consultantCode, consultantCode)
            .and().inValues(TopSalesCoEntity_.campaign, campaign.toTypedArray())
            .build()
            .find()
    }

    fun getSalesConsultant(items: List<TopSalesCoEntity>): List<TopSalesCoEntity> {
        if (!items.isNullOrEmpty()) {
            with(items.first()) {
                return boxStore.boxFor<TopSalesCoEntity>().query()
                    .equal(TopSalesCoEntity_.consultantCode, consultantCode)
                    .build()
                    .find()
            }
        }
        return emptyList()
    }

    private fun deleteTopSalesCo(topSalesCoList: List<TopSalesCoEntity>) {
        with(boxStore) {
            topSalesCoList.forEach {
                boxFor<TopSalesCoBrandCategoriesEntity>().remove(it.brandCategories)
                boxFor<TopSalesCoBrandsEntity>().remove(it.brands)
                boxFor<TopSalesCoCategoriesEntity>().remove(it.categories)
                it.topProducts.forEach { product ->
                    boxFor<TopSalesConsultantTopProductHistoryEntity>().remove(product.topProductsHistory)
                }
                boxFor<TopSalesCoTopProductEntity>().remove(it.topProducts)
                boxFor<TopSalesCoMultiMarkEntity>().remove(it.multiMark)
                boxFor<TopSalesCoEntity>().remove(it)
            }
        }
    }

    fun hasTopSalesConsultants(consultantCode: String, campaign: List<String>) =
        getSalesConsultant(consultantCode, campaign).isNotEmpty()

}
