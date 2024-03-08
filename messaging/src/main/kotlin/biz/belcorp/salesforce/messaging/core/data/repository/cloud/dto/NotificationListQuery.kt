package biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NotificationListQuery(
    @SerialName(APP_ID)
    val appId: Int,
    @SerialName(COUNTRY)
    val country: String,
    @SerialName(USUARIO)
    val user: String,
    @SerialName(DAYS)
    val days: Int = DEFAULT_DAYS,
    @SerialName(DAYS_BEFORE)
    val daysBefore: Int = DEFAULT_DAYS_BEFORE
) {

    companion object {

        const val APP_ID = "appID"
        const val COUNTRY = "pais"
        const val USUARIO = "usuario"
        const val DAYS = "diasReciente"
        const val DAYS_BEFORE = "diasAnterior"

        const val DEFAULT_DAYS = 21
        const val DEFAULT_DAYS_BEFORE = 0

    }

}
