package biz.belcorp.salesforce.core.data.repository.newrelic

import biz.belcorp.salesforce.core.data.preferences.AuthSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.data.utils.NewRelicUserKeys.CAMPAIGN
import biz.belcorp.salesforce.core.data.utils.NewRelicUserKeys.CONSULTANT_CODE
import biz.belcorp.salesforce.core.data.utils.NewRelicUserKeys.CONSULTANT_ID
import biz.belcorp.salesforce.core.data.utils.NewRelicUserKeys.COUNTRY
import biz.belcorp.salesforce.core.data.utils.NewRelicUserKeys.FULLNAME
import biz.belcorp.salesforce.core.data.utils.NewRelicUserKeys.REGION
import biz.belcorp.salesforce.core.data.utils.NewRelicUserKeys.ROLE
import biz.belcorp.salesforce.core.data.utils.NewRelicUserKeys.SECTION
import biz.belcorp.salesforce.core.data.utils.NewRelicUserKeys.SUPPORT
import biz.belcorp.salesforce.core.data.utils.NewRelicUserKeys.USERNAME
import biz.belcorp.salesforce.core.data.utils.NewRelicUserKeys.USER_SUPPORT
import biz.belcorp.salesforce.core.data.utils.NewRelicUserKeys.ZONE
import biz.belcorp.salesforce.core.domain.repository.newrelic.NewRelicRepository
import biz.belcorp.salesforce.core.utils.guionSiNull
import com.newrelic.agent.android.NewRelic

class NewRelicDataRepository(
    private val userPreferences: UserSharedPreferences,
    private val authPreferences: AuthSharedPreferences
) : NewRelicRepository {

    override suspend fun setUserKeys(campaign: String) {
        with(userPreferences) {
            NewRelic.setUserId(username.guionSiNull())
            setAttribute(USER_SUPPORT, isUserSupport(authPreferences.username.guionSiNull()))
            setAttribute(COUNTRY, codPais.guionSiNull())
            setAttribute(REGION, region.guionSiNull())
            setAttribute(ZONE, zona.guionSiNull())
            setAttribute(SECTION, seccion.guionSiNull())
            setAttribute(ROLE, codRol.guionSiNull())
            setAttribute(USERNAME, username.guionSiNull())
            setAttribute(CONSULTANT_CODE, codConsultora.guionSiNull())
            setAttribute(CONSULTANT_ID, consultoraId.guionSiNull())
            setAttribute(FULLNAME, "$nombre $apellido $apellidoMaterno".guionSiNull())
            setAttribute(CAMPAIGN, campaign)
        }
    }

    private fun setAttribute(key: String, value: String) =
        NewRelic.setAttribute(key, value)

    private fun setAttribute(key: String, value: Boolean) =
        NewRelic.setAttribute(key, value)

    private fun isUserSupport(user :String) = user == SUPPORT
}
