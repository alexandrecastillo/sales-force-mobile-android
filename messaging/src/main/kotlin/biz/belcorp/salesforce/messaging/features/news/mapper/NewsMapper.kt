package biz.belcorp.salesforce.messaging.features.news.mapper

import biz.belcorp.salesforce.core.utils.DEFAULT_DENSITY
import biz.belcorp.salesforce.core.utils.asDensity
import biz.belcorp.salesforce.messaging.core.domain.entities.NewsNotification
import biz.belcorp.salesforce.messaging.features.news.model.NewsModel


class NewsMapper(densityDpi: Int) {

    private val density = densityDpi.asDensity()

    companion object {

        const val DELIMITER: String = "xhdpi"
        const val GIF_FILE_EXTENTION = "gif"

    }

    fun map(notification: NewsNotification) = NewsModel().apply {
        id = notification.id
        topic = notification.topic
        title = notification.title
        message = notification.message
        imageUrl = notification.data?.image?.parseImageUrl()
        videoUrl = notification.data?.videoUrl
        closeIconColor = notification.data?.closeButtonColor
        closeBgColor = notification.data?.buttonBackgroundColor
        action = notification.data?.action
    }

    private fun String.parseImageUrl(): String {
        return try {
            when {
                contains(GIF_FILE_EXTENTION) -> {
                    val urlArray = split(DELIMITER).toTypedArray()
                    urlArray.first() + DEFAULT_DENSITY + urlArray[1]
                }
                contains(DELIMITER) -> {
                    val urlArray = split(DELIMITER).toTypedArray()
                    urlArray.first() + density + urlArray[1]
                }
                else -> this
            }
        } catch (e: Exception) {
            this
        }
    }

}
