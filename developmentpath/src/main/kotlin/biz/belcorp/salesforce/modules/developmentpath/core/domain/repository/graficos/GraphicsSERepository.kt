package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.ActivesRetention
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.CapitalizationSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.NetSaleSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.ProfitSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.pedidosu6c.OrderU6C

interface GraphicsSERepository {
    suspend fun getProfitSe(uaKey: LlaveUA, campaigns: List<String>): List<ProfitSe>
    suspend fun getCapitalization(uaKey: LlaveUA, campaigns: List<String>): List<CapitalizationSe>
    suspend fun getActivesRetention(uaKey: LlaveUA, campaigns: List<String>): List<ActivesRetention>
    suspend fun getNetSaleSe(uaKey: LlaveUA, campaigns: List<String>): List<NetSaleSe>
    suspend fun getOrdersSe(uaKey: LlaveUA, campaigns: List<String>): List<OrderU6C>
}
