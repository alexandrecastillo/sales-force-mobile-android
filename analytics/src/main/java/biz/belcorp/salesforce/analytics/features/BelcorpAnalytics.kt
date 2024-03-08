package biz.belcorp.salesforce.analytics.features

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import biz.belcorp.salesforce.analytics.core.domain.entities.Event
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.core.domain.usecases.firebase.FirebaseAnalyticsUseCase
import biz.belcorp.salesforce.core.utils.inject
import org.koin.core.KoinComponent

object BelcorpAnalytics : KoinComponent {

    private val firebaseAnalyticsUseCase by inject<FirebaseAnalyticsUseCase>()

    fun log(@ScreenTag screen: String) =
        firebaseAnalyticsUseCase.log(screen)

    fun log(event: Event) =
        firebaseAnalyticsUseCase.log(event)

    fun track(@ScreenTag screen: String, activity: FragmentActivity) =
        firebaseAnalyticsUseCase.track(screen, activity)

    fun track(@ScreenTag screen: String, activity: Activity) =
        firebaseAnalyticsUseCase.track(screen, activity)

}

fun Fragment.trackAnalytics(@ScreenTag screen: String) {
    this.activity?.let {
        BelcorpAnalytics.track(screen, it)
    }
}
