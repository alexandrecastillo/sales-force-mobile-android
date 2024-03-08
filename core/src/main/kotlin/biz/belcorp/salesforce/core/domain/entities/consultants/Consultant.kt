package biz.belcorp.salesforce.core.domain.entities.consultants

import biz.belcorp.salesforce.core.constants.OrderStatusSic.FACTURADO

class Consultant(
    val id: Int,
    val name: String,
    val campaign: String,
    val region: String,
    val zone: String,
    val section: String,
    val code: String,
    val multiMarca: Boolean,
    var suggestedMessage: String?,
    var digitalSegment: String?,
    val address: String,
    val phone: String,
    val whatsApp: String,
    val isPeg: Boolean,
    val isNew: Boolean,
    val shortSegmentCode: String,
    val segmentDescription: String,
    val constancyNew: String,
    val brightSegmentCode: String,
    val brightSegmentDescription: String,
    val campaignAdmission: String,
    val pendingDebt: Double,
    val orderStatus: Int,
    val document: String,
    val orderAmount: Double,
    val sbAmount: Double,
    val orderMtoAmount: Double,
    val catalogSaleAmount: Double,
    val isNewInconstant: Boolean,
    val digital: Digital
) {
    val hasBilledOrder: Boolean
        get() = orderStatus == FACTURADO

    class Digital(
        val isSubscribed: Boolean = false,
        val share: Boolean = false,
        val buy: Boolean = false
    )

}
