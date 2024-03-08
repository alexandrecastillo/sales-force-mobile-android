package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.collection.ProfitEntity
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.CollectionDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.ProfitDto
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.mapper.CollectionMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator
import biz.belcorp.salesforce.modules.kpis.features.utils.readString
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault

object CollectionMockDataHelper {

    private fun fetchCollection(): CollectionDto {
        val jsonString = readString("core/data/collection.json")
        return JsonEncodedDefault.decodeFromString(CollectionDto.serializer(), jsonString)
    }

    fun fetchProfit(): ProfitDto {
        val jsonString = readString("core/data/profit.json")
        return JsonEncodedDefault.decodeFromString(ProfitDto.serializer(), jsonString)
    }

    fun getCollectionEntities(
        uaKey: LlaveUA
    ): List<CollectionIndicator> {
        val mapper = CollectionMapper()
        return fetchCollection()
            .kpiCollection
            .filter {
                uaKey.codigoRegion == it.region &&
                        uaKey.codigoZona == it.zone && it.section == uaKey.codigoSeccion
            }.map { mapper.map(it) }.map { mapper.map(it) }
    }

    val profitListEntity: List<ProfitEntity>
        get() = listOf(
            profitEntity
        )

    private val profitEntity = ProfitEntity(
        campaign = "201919",
        profile = "er",
        region = "er",
        section = "A",
        zone = "45"
    )
}

