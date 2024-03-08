package biz.belcorp.salesforce.analytics.core.domain.repository

import biz.belcorp.salesforce.analytics.core.domain.entities.Event
import biz.belcorp.salesforce.analytics.core.domain.entities.Screen
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties

interface FirebaseAnalyticsRepository {
    fun log(userProperties: UserProperties, eventModel: Event)
    fun log(userProperties: UserProperties, screen: Screen)
    fun track(userProperties: UserProperties, screen: Screen)
}
