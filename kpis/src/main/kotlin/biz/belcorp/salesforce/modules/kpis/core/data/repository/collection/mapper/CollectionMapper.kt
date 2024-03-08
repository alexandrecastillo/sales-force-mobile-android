package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.mapper

import biz.belcorp.salesforce.core.entities.collection.CollectionEntity
import biz.belcorp.salesforce.core.entities.collection.CollectionOrderEntity
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.CollectionDto
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionOrderRange

class CollectionMapper {

    fun map(data: CollectionDto): List<CollectionEntity> {
        val collections = arrayListOf<CollectionEntity>()
        data.kpiCollection.forEach {
            val collection = map(it)
            val collectionRanges = it.orders.ranges.map { range -> map(collection, range) }
            collection.ordersRange.addAll(collectionRanges)
            collections.add(collection)
        }
        return collections
    }

    fun map(data: CollectionDto.KpiCollection): CollectionEntity = with(data) {
        CollectionEntity(
            campaign = campaign,
            profile = profile,
            region = region.orEmpty(),
            zone = zone.orEmpty(),
            section = section.orEmpty(),
            days = days.orEmpty(),
            percentage = percentage,
            invoicedSale = invoicedSale,
            amountCollected = amountCollected,
            debtorConsultants = debtorConsultants,
            ordersTotalGained = orders.totalGained,
            ordersMinimalCollectionPercentage = orders.minimalCollectionPercentage,
            ordersTotalCollected = orders.totalCollected,
            ordersTotal = orders.totalOrders
        )
    }

    private fun map(
        collection: CollectionEntity,
        rangeCollection: CollectionDto.KpiCollection.Range
    ): CollectionOrderEntity = with(rangeCollection) {
        CollectionOrderEntity(
            range = range,
            collected = collected,
            total = total,
            position = pos
        ).apply {
            this.collectionParent.target = collection
        }
    }

    fun map(entity: CollectionEntity): CollectionIndicator = with(entity) {
        CollectionIndicator(
            campaign = campaign,
            region = region,
            zone = zone,
            section = section,
            percentage = percentage,
            invoicedSale = entity.invoicedSale,
            amountCollected = entity.amountCollected,
            debtorConsultants = entity.debtorConsultants,
            ordersTotalGained = ordersTotalGained,
            ordersMinimalCollectionPercentage = ordersMinimalCollectionPercentage,
            ordersTotalCollected = ordersTotalCollected,
            ordersTotal = ordersTotal,
            ordersRange = ordersRange.toList().map { map(it) }
        )
    }

    private fun map(entity: CollectionOrderEntity): CollectionOrderRange = with(entity) {
        CollectionOrderRange(
            range = this.range,
            collected = this.collected,
            total = this.total,
            position = this.position
        )
    }
}
