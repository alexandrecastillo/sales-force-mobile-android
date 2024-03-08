package biz.belcorp.salesforce.messaging.core.domain.constants

import androidx.annotation.IntDef


@IntDef(
    NotificationType.NONE,
    NotificationType.NEWS,
    NotificationType.POSTULANTS,
    NotificationType.RDD
)
annotation class NotificationType {
    companion object {
        const val NONE = -1
        const val NEWS = 0
        const val POSTULANTS = 1
        const val RDD = 2
    }
}
