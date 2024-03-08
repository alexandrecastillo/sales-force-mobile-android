package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.sugerencias.RecuperadorSugerencias
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.config.Configurador
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.CronogramaEventos
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.GeneradorDeMeses
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.Rdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Dia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import java.util.*
import kotlin.collections.HashSet

class PlanificadorRdd(
    val configurador: Configurador,
    val campaniaActual: Campania,
    val personas: List<PersonaRdd>,
    val cronogramaEventos: CronogramaEventos,
    val recuperadorSugerencias: RecuperadorSugerencias
) {

    var hoy: Calendar
    private lateinit var primerDiaCampania: Calendar
    private var visitas: MutableList<Visita>

    private val personasPlanificadas = HashSet<PersonaRdd>()

    init {
        hoy = obtenerCalendario()
        visitas = obtenerVisitasOrdenadas()
    }

    fun generar(): Rdd {
        primerDiaCampania = primerDiaCampana()

        val diasDeCampania = obtenerDiasDeCampania()
        val diasPosterioCampania = obtenerDiasPosteriorACampania()
        val diasTotales = (diasDeCampania union diasPosterioCampania).toMutableList()
        val generadorDeMeses = GeneradorDeMeses(hoy, diasTotales)
        val mesesDeCampania = generadorDeMeses.generar()
        val indiceMesSeleccionado = generadorDeMeses.mesSeleccionado
        val hoy = mesesDeCampania[indiceMesSeleccionado].hoy!!

        return Rdd(
            campaniaActual = campaniaActual,
            meses = mesesDeCampania,
            dias = diasTotales,
            hoy = hoy,
            indiceMesSeleccionado = indiceMesSeleccionado,
            personasNoPlanificadas = obtenerNoPlanificadas(),
            personasPlanificadas = personasPlanificadas.toList(),
            sugerencias = recuperadorSugerencias.obtenerSugerencias(personas)
        )
    }

    private fun obtenerVisitasOrdenadas(): MutableList<Visita> {
        val (posiblesConsultoras, otras) = personas
            .flatMap { it.agenda.visitas }
            .sortedBy { it.id }
            .partition { it.persona.rol == Rol.POSIBLE_CONSULTORA }

        return (posiblesConsultoras union otras).toMutableList()
    }

    fun obtenerDiasDeCampania(): List<Dia> {
        val diaActual = primerDiaCampana()
        val ultimoDia = ultimoDiaCampana()
        val dias = mutableListOf<Dia>()

        while (!diaActual.esPosteriorA(ultimoDia)) {
            val dia = obtenerDia(diaActual.clone() as Calendar)
            dias.add(dia)
            diaActual.add(Calendar.DAY_OF_YEAR, 1)
        }

        return dias
    }

    fun obtenerDiasPosteriorACampania(): List<Dia> {
        val diaUltimoCampania = ultimoDiaCampana()
        val diaSiguientediaUltimoCampania = (diaUltimoCampania.clone() as Calendar)
        diaSiguientediaUltimoCampania.add(Calendar.DAY_OF_YEAR, 1)

        val diasDespuesCampania = diasDespuesCampana()
        val dias = mutableListOf<Dia>()

        while (!diaSiguientediaUltimoCampania.esPosteriorA(diasDespuesCampania)) {
            val dia = obtenerDia(diaSiguientediaUltimoCampania.clone() as Calendar)
            dia.estaEnCampania = false
            dias.add(dia)
            diaSiguientediaUltimoCampania.add(Calendar.DAY_OF_YEAR, 1)
        }
        return dias
    }

    private fun obtenerDia(dia: Calendar): Dia {
        val visitasProgramadasDelDia = extraerVisitasPlanificadas(dia)
        personasPlanificadas.addAll(visitasProgramadasDelDia.map { it.persona })
        return Dia(
            fecha = dia,
            visitasProgramadas = visitasProgramadasDelDia.sortedBy { it.horaAMostrar },
            perteneceACampania = true,
            esInicioCampania = dia.es(primerDiaCampana()),
            esFinCampania = dia.es(ultimoDiaCampana()),
            esHoy = dia.es(hoy),
            seleccionado = false,
            eventos = cronogramaEventos.obtener(dia).sortedBy { it.fecha })
    }

    private fun extraerRegistradasPara(dia: Calendar): List<Visita> {
        return visitas.extract {
            it.fueRegistradaEn(dia) ||
                (dia.es(primerDiaCampania) && checkVisitasPrevias(dia, it))
        }
    }

    private fun checkVisitasPrevias(dia: Calendar, visita: Visita): Boolean {
        return visita.fechaRegistro?.before(dia)?.takeIf { it }?.also {
            visita.fechaRegistro?.apply {
                set(Calendar.HOUR_OF_DAY, 8)
                set(Calendar.MINUTE, 0)
            }
        } ?: false
    }

    private fun extraerVisitasPlanificadas(dia: Calendar): List<Visita> {
        val visitas = mutableListOf<Visita>()
        visitas.addAll(extraerRegistradasPara(dia))
        visitas.addAll(extraerReprogramadasPara(dia))
        visitas.addAll(extraerProgramadas(visitas.size, dia))
        return visitas
    }

    private fun extraerReprogramadasPara(dia: Calendar): List<Visita> {
        val visitas = mutableListOf<Visita>()
        if (dia.es(hoy)) {
            visitas.addAll(extraerReprogramadasInicialmenteParaDiasAnteriores(dia))
            visitas.addAll(extraerReprogramadasInicialmentePara(dia))
        }
        if (dia.esPosteriorA(hoy)) {
            visitas.addAll(extraerReprogramadasInicialmentePara(dia))
        }
        return visitas
    }

    private fun extraerReprogramadasInicialmenteParaDiasAnteriores(dia: Calendar): List<Visita> =
        visitas.extract {
            it.estaReprogramadaSinRegistrar && it.fechaReprogramacion!!.esAnteriorA(dia)
        }

    private fun extraerReprogramadasInicialmentePara(dia: Calendar): List<Visita> =
        visitas.extract {
            it.estaReprogramadaSinRegistrar && it.fechaReprogramacion!!.es(dia)
        }

    private fun extraerProgramadas(visitasAgregadasEnDia: Int, dia: Calendar): List<Visita> {
        if (noCabenVisitas(dia, visitasAgregadasEnDia)) return emptyList()
        val maximoVisitas = configurador.obtenerCantidadVisitasPorFecha(dia)
        val visitasPosibles = visitas
            .filter { it.esProgramable }
            .take(maximoVisitas - visitasAgregadasEnDia)
        val programador = Programador(visitasPosibles, cronogramaEventos)
            .nuevoDia(dia)
            .conHoraInicio(configurador.parametros.horaInicio)
            .conHoraFin(configurador.parametros.horaFin)
            .conDuracionVisita(configurador.parametros.duracionVisitaHoras)
        val visitasProgramadas = programador.programarVisitas()
        visitasProgramadas.forEach { visitas.remove(it) }
        return visitasProgramadas
    }

    private fun noCabenVisitas(dia: Calendar, visitasYaAgregadasEnDia: Int): Boolean {
        val noEsDiaPlanificable = !esDiaPlanificable(dia)
        val visitasPasaronLimite = visitasAgregadasPasaronLimite(visitasYaAgregadasEnDia, dia)
        return noEsDiaPlanificable || visitasPasaronLimite
    }

    private fun esDiaPlanificable(dia: Calendar): Boolean {
        val inicioFacturacionCalendar = obtenerCalendario()
        campaniaActual.inicioFacturacion?.let { inicioFacturacionCalendar.time = it }
        return dia.esAnteriorA(inicioFacturacionCalendar) &&
            !dia.esFinDeSemana() &&
            !dia.esAnteriorA(hoy) &&
            !cronogramaEventos.hayEventoTodoElDiaEn(dia)
    }

    private fun visitasAgregadasPasaronLimite(
        visitasYaAgregadasEnDia: Int,
        dia: Calendar
    ): Boolean {
        val maximoVisitasPorFecha = configurador.obtenerCantidadVisitasPorFecha(dia)
        return visitasYaAgregadasEnDia >= maximoVisitasPorFecha
    }

    private fun obtenerNoPlanificadas(): List<PersonaRdd> {
        val personas = HashSet<PersonaRdd>()
        personas.addAll(visitas.map { it.persona }.filter { it !in personasPlanificadas })
        return personas.sortedBy { it.primeraVisitaNoRegistrada?.id }.toList()
    }

    private fun primerDiaCampana(): Calendar {
        val calendar = obtenerCalendario()
        campaniaActual.inicio?.let { calendar.time = it }
        return calendar
    }

    private fun ultimoDiaCampana(): Calendar {
        val calendar = obtenerCalendario()
        campaniaActual.fin?.let { calendar.time = it }
        return calendar
    }

    private fun diasDespuesCampana(): Calendar {
        val calendar = obtenerCalendario()
        campaniaActual.fin?.let { calendar.time = it }
        val dayDiff = calendar.lastDayOfMonth - calendar.dayOfMonth
        calendar.add(Calendar.DAY_OF_YEAR, dayDiff)
        return calendar
    }

    fun diasPosteriorCampania(): Int {
        val calendar = obtenerCalendario()
        calendar.time = campaniaActual.fin
        val dayDiff = calendar.lastDayOfMonth - calendar.dayOfMonth
        calendar.add(Calendar.DAY_OF_YEAR, dayDiff)
        return dayDiff
    }

    private fun obtenerCalendario(): Calendar {
        return Calendar.getInstance(Locale.US)
    }
}
