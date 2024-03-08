package biz.belcorp.salesforce.modules.digital.core.domain.entities

class DigitalInfo(
    val region: String,
    val zone: String,
    val section: String,
    val campaign: String,
    val actives: Int,
    val subscribed: Int,
    val share: Int,
    val buy: Int,
    val subscribedActivesRatio: Float,
    val shareActivesRatio: Float,
    val shareSubscribedRatio: Float,
    val buyActivesRatio: Float,
    val buySubscribedRatio: Float
)
