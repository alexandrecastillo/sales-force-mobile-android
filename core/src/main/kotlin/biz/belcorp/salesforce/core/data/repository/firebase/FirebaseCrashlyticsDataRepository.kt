package biz.belcorp.salesforce.core.data.repository.firebase

import biz.belcorp.salesforce.core.data.preferences.AuthSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.data.utils.TraceabilityUserKeys.CONSULTANT_CODE
import biz.belcorp.salesforce.core.data.utils.TraceabilityUserKeys.CONSULTANT_ID
import biz.belcorp.salesforce.core.data.utils.TraceabilityUserKeys.COUNTRY
import biz.belcorp.salesforce.core.data.utils.TraceabilityUserKeys.FULLNAME
import biz.belcorp.salesforce.core.data.utils.TraceabilityUserKeys.REGION
import biz.belcorp.salesforce.core.data.utils.TraceabilityUserKeys.ROLE
import biz.belcorp.salesforce.core.data.utils.TraceabilityUserKeys.SECTION
import biz.belcorp.salesforce.core.data.utils.TraceabilityUserKeys.USERNAME
import biz.belcorp.salesforce.core.data.utils.TraceabilityUserKeys.USER_SUPPORT
import biz.belcorp.salesforce.core.data.utils.TraceabilityUserKeys.ZONE
import biz.belcorp.salesforce.core.domain.repository.firebase.FirebaseCrashlyticsRepository
import biz.belcorp.salesforce.core.utils.guionSiNull
import com.google.firebase.crashlytics.FirebaseCrashlytics

class FirebaseCrashlyticsDataRepository(
    private val userPreferences: UserSharedPreferences,
    private val authPreferences: AuthSharedPreferences
) : FirebaseCrashlyticsRepository {

    override suspend fun setUserKeys(isFull: Boolean) {
        FirebaseCrashlytics.getInstance().let {
            with(userPreferences) {
                it.setUserId(username.guionSiNull())
                it.setCustomKey(USER_SUPPORT, authPreferences.username.guionSiNull())
                it.setCustomKey(COUNTRY, codPais.guionSiNull())
                it.setCustomKey(REGION, region.takeIf { isFull }.guionSiNull())
                it.setCustomKey(ZONE, zona.takeIf { isFull }.guionSiNull())
                it.setCustomKey(SECTION, seccion.takeIf { isFull }.guionSiNull())
                it.setCustomKey(ROLE, codRol.guionSiNull())
                it.setCustomKey(USERNAME, username.guionSiNull())
                it.setCustomKey(CONSULTANT_CODE, codConsultora.takeIf { isFull }.guionSiNull())
                it.setCustomKey(CONSULTANT_ID, consultoraId.takeIf { isFull }.guionSiNull())
                it.setCustomKey(
                    FULLNAME, "$nombre $apellido $apellidoMaterno".takeIf { isFull }.guionSiNull()
                )
            }
        }
    }

}
