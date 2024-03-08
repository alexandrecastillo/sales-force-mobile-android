package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan

import biz.belcorp.salesforce.core.utils.finDelDia
import biz.belcorp.salesforce.core.utils.inicioDelDia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.CronogramaEventos
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Intervalo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import java.util.*

class Programador(
    private val visitas: List<Visita>,
    private val cronogramaEventos: CronogramaEventos
) {

    private lateinit var fecha: Calendar
    private var horaInicio: Int = 8
    private var horaFin: Int = 23
    private var duracionVisita: Int = 1

    fun nuevoDia(fecha: Calendar): Programador {
        this.fecha = fecha.clone() as Calendar
        return this
    }

    fun conHoraInicio(hora: Int): Programador {
        this.horaInicio = hora
        return this
    }

    fun conHoraFin(hora: Int): Programador {
        this.horaFin = hora
        return this
    }

    fun conDuracionVisita(duracion: Int): Programador {
        this.duracionVisita = duracion
        return this
    }

    fun programarVisitas(): List<Visita> {
        val planificadorIntervalos = crearPlanificadorIntervalos()
        val duracionVisitas = obtenerDuracionDeVisitas()
        val visitasProgramadas = mutableListOf<Visita>()
        duracionVisitas.forEach { duracion ->
            planificadorIntervalos.agregarIntervalosSinCruce(duracion)
        }
        planificadorIntervalos.intervalosSinCruceAgregados.forEachIndexed { index, intervalo ->
            val visita = visitas[index]
            visita.fechaProgramacion = intervalo.inicio
            visitasProgramadas.add(visita)
        }
        return visitasProgramadas
    }

    private fun obtenerDuracionDeVisitas(): List<Long> {
        return visitas.map { it.calcularDuracion(duracionVisita) }
    }

    private fun crearPlanificadorIntervalos(): PlanificadorIntervalos {
        val planificadorIntervalos = PlanificadorIntervalos(fecha)
        planificadorIntervalos.agregarIntervalo(crearIntervaloInicial())
        planificadorIntervalos.agregarIntervalos(crearIntervalosDeEventos())
        planificadorIntervalos.agregarIntervalo(crearIntervaloFinal())
        planificadorIntervalos.redondearHorasDeIntervalos()
        return planificadorIntervalos
    }

    private fun crearIntervalosDeEventos(): List<Intervalo> {
        return cronogramaEventos
            .obtenerEventosRdd(fecha)
            .filter { !it.esTodoElDia }
            .mapNotNull { it.intervalo }
    }

    fun crearIntervaloInicial(): Intervalo {
        val fin = fecha.clone() as Calendar
        fin.set(Calendar.HOUR_OF_DAY, horaInicio)
        fin.set(Calendar.MINUTE, 0)
        fin.set(Calendar.SECOND, 0)
        fin.set(Calendar.MILLISECOND, 0)
        val inicio = fecha.inicioDelDia()
        return Intervalo(inicio, fin)
    }

    fun crearIntervaloFinal(): Intervalo {
        val inicio = fecha.clone() as Calendar
        inicio.set(Calendar.HOUR_OF_DAY, horaFin)
        inicio.set(Calendar.MINUTE, 0)
        inicio.set(Calendar.SECOND, 0)
        inicio.set(Calendar.MILLISECOND, 0)
        val fin = inicio.finDelDia()
        return Intervalo(inicio, fin)
    }
}
