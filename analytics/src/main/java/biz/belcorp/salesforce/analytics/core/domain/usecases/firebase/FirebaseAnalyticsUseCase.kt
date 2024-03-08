package biz.belcorp.salesforce.analytics.core.domain.usecases.firebase

import android.app.Activity
import biz.belcorp.salesforce.analytics.core.domain.entities.Event
import biz.belcorp.salesforce.analytics.core.domain.entities.Screen
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.analytics.core.domain.repository.FirebaseAnalyticsRepository
import biz.belcorp.salesforce.analytics.core.domain.repository.LogLocalRepository
import biz.belcorp.salesforce.core.BuildConfig
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository

class FirebaseAnalyticsUseCase(
    private val firebaseAnalyticsRepository: FirebaseAnalyticsRepository,
    private val logLocalDataRepository: LogLocalRepository,
    private val sessionDataRepository: SessionRepository
) {

    fun log(@ScreenTag screen: String) =
        log(Screen(screen))

    fun track(@ScreenTag screen: String, activity: Activity) =
        track(Screen(screen).apply { this.activity = activity })

    fun track(screen: Screen) {
        firebaseAnalyticsRepository.track(userMetaData(), screen)
        writeFile(screen)
    }

    fun log(screen: Screen) {
        firebaseAnalyticsRepository.log(userMetaData(), screen)
        writeFile(screen)
    }

    fun log(event: Event) {
        firebaseAnalyticsRepository.log(userMetaData(), event)
        writeFile(event)
    }

    private fun writeFile(event: Event) {
        if (BuildConfig.DEBUG) {
            logLocalDataRepository.write(event)
        }
    }

    private fun writeFile(event: Screen) {
        if (BuildConfig.DEBUG) {
            logLocalDataRepository.write(event)
        }
    }

    private fun userMetaData(): UserProperties {
        return UserProperties.apply {
            session = sessionDataRepository.getSession()
        }
    }

}
