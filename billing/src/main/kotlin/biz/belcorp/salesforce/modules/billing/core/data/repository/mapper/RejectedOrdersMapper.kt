package biz.belcorp.salesforce.modules.billing.core.data.repository.mapper

import biz.belcorp.salesforce.core.entities.rejectedorders.RejectedOrderDetailEntity
import biz.belcorp.salesforce.core.entities.rejectedorders.RejectedOrderEntity
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.cloud.dto.RejectedOrdersDto
import biz.belcorp.salesforce.modules.billing.core.domain.entities.RejectedOrdersBilling

class RejectedOrdersMapper {

    fun map(data: RejectedOrdersDto.Data): Pair<List<RejectedOrderEntity>, List<RejectedOrderDetailEntity>> {
        val orders = data.rejectedOrders.map { map(it) }
        val parents: List<RejectedOrderEntity> = orders.map { it.first }
        val children: List<RejectedOrderDetailEntity> = orders.flatMap { it.second }
        return Pair(parents, children)
    }

    fun map(data: RejectedOrdersDto.Data.RejectedOrders): Pair<RejectedOrderEntity, List<RejectedOrderDetailEntity>> {
        val rejectedOrders = RejectedOrderEntity().apply {
            campaign = data.campaign
            region = data.region
            zone = data.zone
            section = data.section
        }
        val rejectedOrdersDetail = data.reasons.map { map(rejectedOrders, it) }
        return Pair(rejectedOrders, rejectedOrdersDetail)
    }

    private fun map(
        rejectedOrder: RejectedOrderEntity,
        rejectedOrderDetail: RejectedOrdersDto.Data.RejectedOrders.RejectedOrdersDetail
    ): RejectedOrderDetailEntity {
        return RejectedOrderDetailEntity(
            name = rejectedOrderDetail.name,
            quantity = rejectedOrderDetail.quantity
        ).apply {
            rejectedOrderParent.target = rejectedOrder
        }
    }

    fun map(rejectedOrdersDetail: RejectedOrderDetailEntity): RejectedOrdersBilling {
        return RejectedOrdersBilling(
            title = rejectedOrdersDetail.name,
            quantity = rejectedOrdersDetail.quantity
        )
    }
}
