package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.salesconsultant

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.*
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salessection.TopSalesSeEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salessection.TopSalesSeEntity_
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.core.utils.inValues
import io.objectbox.kotlin.boxFor

class TopSalesSeDataStore {

    fun save(items: List<TopSalesSeEntity>) {
        with(boxStore) {
            val topSalesSeList = getTopSalesSe(items)
            runInTx {
                if(topSalesSeList.isNotEmpty()) {
                    deleteTopSalesSe(topSalesSeList)
                }
                boxFor<TopSalesSeEntity>().put(items)
            }
        }
    }

    fun getTopSalesSe(uaKey: LlaveUA, campaign: List<String>): List<TopSalesSeEntity> =
        with(uaKey) {
            return boxStore.boxFor<TopSalesSeEntity>().query()
                .equal(TopSalesSeEntity_.region, codigoRegion.orEmpty())
                .and().equal(TopSalesSeEntity_.zone, codigoZona.orEmpty())
                .and().equal(TopSalesSeEntity_.section, codigoSeccion.orEmpty())
                .and().inValues(TopSalesSeEntity_.campaign, campaign.toTypedArray())
                .build()
                .find()
        }

    fun getTopSalesSe(items: List<TopSalesSeEntity>): List<TopSalesSeEntity> {
        if (!items.isNullOrEmpty()) {
            with(items.first()) {
                return boxStore.boxFor<TopSalesSeEntity>().query()
                    .equal(TopSalesSeEntity_.region, region)
                    .and().equal(TopSalesSeEntity_.zone, zone)
                    .and().equal(TopSalesSeEntity_.section, section)
                    .build()
                    .find()
            }
        }
        return emptyList()
    }

    private fun deleteTopSalesSe(topSalesSeList: List<TopSalesSeEntity>) {
        with(boxStore) {
            topSalesSeList.forEach {
                boxFor<TopSalesCoBrandsEntity>().remove(it.brands)
                it.topProducts.forEach { product ->
                    boxFor<TopSalesConsultantTopProductHistoryEntity>().remove(product.topProductsHistory)
                }
                boxFor<TopSalesCoTopProductEntity>().remove(it.topProducts)
                boxFor<TopSalesSeEntity>().remove(it)
            }
        }
    }

}
