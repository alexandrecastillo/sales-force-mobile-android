package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Dia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.DiaDeMes
import java.util.*

class Mes(val fecha: Calendar, val dias: List<DiaDeMes>) {

    private var indiceDiaSeleccionado: Int? = null

    val diaSeleccionado: Dia?
        get() {
            indiceDiaSeleccionado?.let { return dias[it].dia }
            return null
        }

    private val indiceHoy: Int get() = dias.indexOfFirst { it.dia.esHoy }

    val hoy: DiaDeMes?
        get() {
            if (indiceHoy >= 0) {
                return dias[indiceHoy]
            }
            return null
        }

    fun seleccionarHoy() {
        if (indiceHoy >= 0) {
            seleccionarDia(indiceHoy)
        }
    }

    fun seleccionarDia(indice: Int) {
        diaSeleccionado?.seleccionado = false
        indiceDiaSeleccionado = indice
        diaSeleccionado?.seleccionado = true
    }
}
