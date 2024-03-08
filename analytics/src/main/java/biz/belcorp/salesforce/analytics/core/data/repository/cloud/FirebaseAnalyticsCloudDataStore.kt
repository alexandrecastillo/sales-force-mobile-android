package biz.belcorp.salesforce.analytics.core.data.repository.cloud

import android.os.Bundle
import biz.belcorp.salesforce.analytics.core.domain.entities.Event
import biz.belcorp.salesforce.analytics.core.domain.entities.LogTag
import biz.belcorp.salesforce.analytics.core.domain.entities.Screen
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalyticsCloudDataStore(
    private val firebaseClient: FirebaseAnalytics
) {

    fun log(userProperties: UserProperties, screen: Screen) {
        val params = Bundle().apply { setScreenData(screen) }
        firebaseClient.applyUserProperties(userProperties)
        firebaseClient.logEvent(LogTag.SCREEN, params)
    }

    fun log(userProperties: UserProperties, eventModel: Event) {
        val params = Bundle().apply { setEventData(eventModel) }
        firebaseClient.applyUserProperties(userProperties)
        firebaseClient.logEvent(eventModel.logName, params)
    }

    fun track(userProperties: UserProperties, screen: Screen) {
        firebaseClient.applyUserProperties(userProperties)
        screen.activity?.let {
            firebaseClient.setCurrentScreen(it, screen.name, null)
        }
    }

    private fun Bundle.setScreenData(screen: Screen) {
        this.apply {
            putString(LogTag.SCREEN_NAME, screen.name)
            putString(LogTag.VARIANT, screen.variant)
        }
    }

    private fun Bundle.setEventData(eventModel: Event) {
        this.apply {
            putString(LogTag.CATEGORY, eventModel.category)
            putString(LogTag.ACTION, eventModel.action)
            putString(LogTag.LABEL, eventModel.label)
            putString(LogTag.SCREEN_NAME, eventModel.screenName)
            putString(LogTag.VARIANT, eventModel.variant)
        }
    }

    private fun FirebaseAnalytics.applyUserProperties(userProperties: UserProperties) {
        setUserProperty(LogTag.LOGIN_STATUS, userProperties.status)
        setUserProperty(LogTag.ROLE, userProperties.session?.codigoRol)
        setUserProperty(LogTag.REGION, userProperties.session?.region.orEmpty())
        setUserProperty(LogTag.ZONE, userProperties.session?.zona.orEmpty())
        setUserProperty(LogTag.COUNTRY, userProperties.session?.countryIso)
        setUserProperty(LogTag.CAMPAIGN, userProperties.session?.campaign?.codigo)
        setUserProperty(LogTag.BRAND, userProperties.session?.cub)
        setUserProperty(LogTag.USERID, userProperties.session?.codigoUsuario)
        setUserProperty(LogTag.PERIOD, userProperties.session?.campaign?.periodo?.nombre())
    }

}
