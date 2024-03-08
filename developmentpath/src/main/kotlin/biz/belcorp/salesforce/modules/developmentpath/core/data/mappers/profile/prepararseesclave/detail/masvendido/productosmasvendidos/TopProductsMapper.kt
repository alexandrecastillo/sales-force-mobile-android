package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.productosmasvendidos

import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoEsikaEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoEsikaHistoryEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoEsikaImageEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTopProductEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTriLbelEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTriLbelStayEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTrioLbelHistoryEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTrioLbelHistoryStayEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTrioLbelImageEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTrioLbelStayImageEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesConsultantTopProductHistoryEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salessection.TopSalesSeEntity
import biz.belcorp.salesforce.core.utils.takeLastTwo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.EsikaLbel
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.EsikaLbelData
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.TopSoldProduct

class TopProductsMapper {

    fun mapSe(entities: List<TopSalesSeEntity>): List<TopSoldProduct> =
        entities.firstOrNull()?.topProducts?.toList()
            ?.map { mapProduct(it) }
            ?.sortedBy { it.top } ?: emptyList()

    fun mapCo(entities: List<TopSalesCoEntity>): List<TopSoldProduct> =
        entities.firstOrNull()?.topProducts?.toList()
            ?.map { mapProduct(it) }
            ?.sortedBy { it.top } ?: emptyList()

    private fun mapProduct(productEntity: TopSalesCoTopProductEntity): TopSoldProduct =
        with(productEntity) {
            return TopSoldProduct(
                top = top,
                productName = name,
                average = units,
                histories = mapHistories(topProductsHistory.toList())
            )
        }

    private fun mapHistories(histories: List<TopSalesConsultantTopProductHistoryEntity>) =
        histories.map { mapHistory(it) }

    private fun mapHistory(entity: TopSalesConsultantTopProductHistoryEntity): TopSoldProduct.History =
        with(entity) {
            return TopSoldProduct.History(
                campaign = campaign.takeLastTwo(),
                average = units
            )
        }


    fun mapEsikaLbel(entities: List<TopSalesCoEntity>) = EsikaLbelData(
        esika = getEsika(entities),
        lBelStay = getTrioStayLbel(entities),
        lBel = getTrioLbel(entities),
    )

    private fun getTrioStayLbel(items: List<TopSalesCoEntity>) =
        items.firstOrNull()?.trioLbelStay?.toList()?.map { parseLbelStay(it) } ?: emptyList()

    private fun getTrioLbel(items: List<TopSalesCoEntity>) =
        items.firstOrNull()?.trioLbel?.toList()?.map { parseLbel(it) } ?: emptyList()

    private fun getEsika(items: List<TopSalesCoEntity>) =
        items.firstOrNull()?.esika?.toList()?.map { parseEsika(it) } ?: emptyList()

    private fun parseLbel(item: TopSalesCoTriLbelEntity) = with(item) {
        EsikaLbel(
            order = order,
            name = name,
            sku = sku,
            cuv = cuv,
            images = mapImagesLbel(imagesProductParent),
            histories = item.historyTrioLbel.map { parseLbelHistory(it) } ?: emptyList()
        )
    }

    private fun mapImagesLbel(entity: List<TopSalesCoTrioLbelImageEntity>): EsikaLbel.ImagesProduct =
        if (entity.isNotEmpty()) {
            with(entity[0]) {
                return EsikaLbel.ImagesProduct(
                    codsap = codsap,
                    photoProduct = photoProduct
                )
            }
        } else {
            EsikaLbel.ImagesProduct()
        }

    private fun parseLbelHistory(item: TopSalesCoTrioLbelHistoryEntity) = with(item) {
        EsikaLbel.Historical(
            campaign = campaign,
            bought = bought
        )
    }

    private fun parseLbelStay(item: TopSalesCoTriLbelStayEntity) = with(item) {
        EsikaLbel(
            order = order,
            name = name,
            sku = sku,
            cuv = cuv,
            images = mapImagesLbelStay(imagesProductParent),
            histories = item.historyTrioLbelStay.map { parseLbelStayHistory(it) } ?: emptyList()
        )
    }

    private fun mapImagesLbelStay(entity: List<TopSalesCoTrioLbelStayImageEntity>): EsikaLbel.ImagesProduct =
        if (entity.isNotEmpty()) {
            with(entity[0]) {
                return EsikaLbel.ImagesProduct(
                    codsap = codsap,
                    photoProduct = photoProduct
                )
            }
        } else {
            EsikaLbel.ImagesProduct()
        }


    private fun parseLbelStayHistory(item: TopSalesCoTrioLbelHistoryStayEntity) = with(item) {
        EsikaLbel.Historical(
            campaign = campaign,
            bought = bought
        )
    }

    private fun parseEsika(item: TopSalesCoEsikaEntity) = with(item) {
        EsikaLbel(
            order = order,
            name = name,
            sku = sku,
            cuv = cuv,
            images = mapImagesEsika(imagesProductParent),
            histories = item.historyEsika.map { parseEsikaHistory(it) } ?: emptyList()
        )
    }

    private fun mapImagesEsika(entity: List<TopSalesCoEsikaImageEntity>): EsikaLbel.ImagesProduct =
        if (entity.isNotEmpty()) {
            with(entity[0]) {
                return EsikaLbel.ImagesProduct(
                    codsap = codsap,
                    photoProduct = photoProduct
                )
            }
        } else {
            EsikaLbel.ImagesProduct()
        }


    private fun parseEsikaHistory(item: TopSalesCoEsikaHistoryEntity) = with(item) {
        EsikaLbel.Historical(
            campaign = campaign,
            bought = bought
        )
    }


}
