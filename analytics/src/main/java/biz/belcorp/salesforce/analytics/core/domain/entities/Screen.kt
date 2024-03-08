package biz.belcorp.salesforce.analytics.core.domain.entities

import android.app.Activity

class Screen(
    @ScreenTag val name: String
) {
    var variant: String = Ambiente.getAmbiente()
    var activity: Activity? = null
}
