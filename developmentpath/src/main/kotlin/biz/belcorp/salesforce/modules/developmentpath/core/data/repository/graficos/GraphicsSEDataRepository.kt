package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.data.GraphicsSEDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.graficos.GraphicsSEMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.ActivesRetention
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.CapitalizationSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.NetSaleSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.ProfitSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.pedidosu6c.OrderU6C
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraphicsSERepository

class GraphicsSEDataRepository(
    private val graphicsDataStore: GraphicsSEDataStore,
    private val graphicsMapper: GraphicsSEMapper
) : GraphicsSERepository {

    override suspend fun getProfitSe(uaKey: LlaveUA, campaigns: List<String>): List<ProfitSe> {
        return graphicsDataStore.getProfitSeData(uaKey, campaigns).map { graphicsMapper.parse(it) }
    }

    override suspend fun getCapitalization(uaKey: LlaveUA, campaigns: List<String>): List<CapitalizationSe> {
        return graphicsDataStore.getCapitalization(uaKey, campaigns).map { graphicsMapper.parse(it) }
    }

    override suspend fun getActivesRetention(uaKey: LlaveUA, campaigns: List<String>): List<ActivesRetention> {
        return graphicsDataStore.getNetSaleSeData(uaKey, campaigns).map { graphicsMapper.parseToRetention(it) }
    }

    override suspend fun getNetSaleSe(uaKey: LlaveUA, campaigns: List<String>): List<NetSaleSe> {
        return graphicsDataStore.getNetSaleSeData(uaKey, campaigns).map { graphicsMapper.parseToSales(it) }
    }

    override suspend fun getOrdersSe(uaKey: LlaveUA, campaigns: List<String>): List<OrderU6C> {
        return graphicsDataStore.getNetSaleSeData(uaKey, campaigns).map { graphicsMapper.parseToOrders(it) }
    }

}
