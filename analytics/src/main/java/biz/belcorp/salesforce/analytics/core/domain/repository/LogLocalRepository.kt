package biz.belcorp.salesforce.analytics.core.domain.repository

import biz.belcorp.salesforce.analytics.core.domain.entities.Event
import biz.belcorp.salesforce.analytics.core.domain.entities.Screen

interface LogLocalRepository {

    fun write(screen: Screen)

    fun write(event: Event)
}
