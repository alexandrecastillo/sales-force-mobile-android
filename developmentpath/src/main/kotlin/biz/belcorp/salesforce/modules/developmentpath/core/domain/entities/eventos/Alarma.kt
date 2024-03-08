package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import java.util.*

open class Alarma(val fecha: Date) {

    val activacionPendiente
        get(): Boolean {
            return fecha.after(Date())
        }

    class AlarmaEvento(
        val evento: EventoRdd,
        val persona: Persona,
        fecha: Date
    ) : Alarma(fecha)
}
