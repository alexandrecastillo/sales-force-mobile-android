package biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.cloud.dto.SaleOrdersDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.mappers.SaleOrdersMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.*
import biz.belcorp.salesforce.modules.kpis.features.utils.readString
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

object SalesOrdersMockDataHelper {

    fun getSalesOrdersQueryData(): SaleOrdersDto {
        val jsonString = readString("core/data/salesorders.json")
        return JsonEncodedDefault.decodeFromString(SaleOrdersDto.serializer(), jsonString)
    }

    fun getSalesOrdersEntities(
        uaKey: LlaveUA, campaign: String, mapper: SaleOrdersMapper
    ): List<SaleOrdersIndicator> {
        return getSalesOrdersQueryData()
            .saleOrders
            .filter {
                it.campaign == campaign && uaKey.codigoRegion == it.region
                        && it.zone == uaKey.codigoZona && it.section == uaKey.codigoSeccion
            }.map {
                mapper.map(it)
            }.map {
                mapper.map(it)
            }
    }

    fun getListModel(): List<ContentModel> {
        return listOf(

            ContentModel(
                type = ContentType.SIMPLE_CARD,
                items = itemsRetencion()
            )
        )
    }

    private fun itemsRetencion(): List<ContentBaseModel> {
        return listOf(
            Single("Retencion", "5%"),
            Single("PEGs", "56"),
            Single("PEGs retenidas", "135")
        )
    }


}
