package biz.belcorp.salesforce.analytics.core.data.repository

import biz.belcorp.salesforce.analytics.core.data.repository.cloud.FirebaseAnalyticsCloudDataStore
import biz.belcorp.salesforce.analytics.core.domain.entities.Event
import biz.belcorp.salesforce.analytics.core.domain.entities.Screen
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.analytics.core.domain.repository.FirebaseAnalyticsRepository

class FirebaseAnalyticsDataRepository(
    private val firebaseAnalyticsCloudDataStore: FirebaseAnalyticsCloudDataStore
) : FirebaseAnalyticsRepository {

    override fun log(userProperties: UserProperties, eventModel: Event) {
        firebaseAnalyticsCloudDataStore.log(userProperties, eventModel)

    }

    override fun log(userProperties: UserProperties, screen: Screen) {
        firebaseAnalyticsCloudDataStore.log(userProperties, screen)
    }

    override fun track(userProperties: UserProperties, screen: Screen) {
        firebaseAnalyticsCloudDataStore.track(userProperties, screen)
    }
}
