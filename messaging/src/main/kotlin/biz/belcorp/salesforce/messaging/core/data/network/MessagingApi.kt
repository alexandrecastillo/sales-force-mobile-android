package biz.belcorp.salesforce.messaging.core.data.network

import biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface MessagingApi {

    @Headers("Content-Type: application/json")
    @POST("notificacion/leido")
    suspend fun updateNotificationSeen(@Body request: NotificationSeenQuery): Response<NotificationSeenResponse>

    @Headers("Content-Type: application/json")
    @POST("notificacion/app")
    suspend fun getNotifications(@Body request: NotificationListQuery): Response<NotificationListResponse>

    @Headers("Content-Type: application/json")
    @POST("seguridad")
    suspend fun getToken(@Body request: SecurityQuery): Response<SecurityResponse>

}
