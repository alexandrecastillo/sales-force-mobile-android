package biz.belcorp.salesforce.core.domain.entities.analytics

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.hardware.BuildVariant
import biz.belcorp.salesforce.core.domain.entities.session.Sesion

class EventoPushModel(
    val campania: Campania,
    val sesion: Sesion,
    val screenName: String,
    var tipoIngreso: String,
    var nombrePushNotification: String,
    var ambiente: BuildVariant
)
