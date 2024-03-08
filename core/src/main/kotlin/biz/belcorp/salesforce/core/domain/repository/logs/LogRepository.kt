package biz.belcorp.salesforce.core.domain.repository.logs

import biz.belcorp.salesforce.core.domain.entities.analytics.EventoModel
import biz.belcorp.salesforce.core.domain.entities.analytics.EventoPushModel
import biz.belcorp.salesforce.core.domain.entities.analytics.PantallaModel

interface LogRepository {
    fun guardarEvento(eventoModel: EventoModel): EventoModel
    fun guardarSeleccionBoton(eventoModel: EventoModel): EventoModel
    fun guardarSeleccionOption(eventoModel: EventoModel): EventoModel
    fun guardarSeleccionSwitch(eventoModel: EventoModel): EventoModel
    fun guardarPantalla(pantallaModel: PantallaModel)
    fun guardarEventoPush(eventoPushModel: EventoPushModel): EventoPushModel
}
