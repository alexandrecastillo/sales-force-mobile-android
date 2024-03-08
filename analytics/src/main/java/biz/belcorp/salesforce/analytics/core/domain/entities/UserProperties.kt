package biz.belcorp.salesforce.analytics.core.domain.entities

import biz.belcorp.salesforce.analytics.core.domain.entities.LoginStatus.Companion.STARTED
import biz.belcorp.salesforce.core.domain.entities.session.Sesion

object UserProperties {
    @LoginStatus
    var status: String = STARTED
    var session: Sesion? = null
}
