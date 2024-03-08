package biz.belcorp.salesforce.core.domain.repository.firebase

import biz.belcorp.salesforce.core.domain.entities.analytics.EventoModel
import biz.belcorp.salesforce.core.domain.entities.analytics.EventoPushModel
import biz.belcorp.salesforce.core.domain.entities.analytics.PantallaModel

interface FirebaseAnalyticsRepository {
    fun enviarEvento(eventoModel: EventoModel): EventoModel
    fun enviarSeleccionBoton(eventoModel: EventoModel): EventoModel
    fun enviarSeleccionOpcion(eventoModel: EventoModel): EventoModel
    fun enviarSeleccionSwitch(eventoModel: EventoModel): EventoModel
    fun enviarPantalla(pantallaModel: PantallaModel)
    fun enviarEventoPush(eventoPushModel: EventoPushModel): EventoPushModel
}
