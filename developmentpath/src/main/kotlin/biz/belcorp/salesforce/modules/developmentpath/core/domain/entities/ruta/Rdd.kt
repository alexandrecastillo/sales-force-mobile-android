package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.buscador.BuscadorRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PosibleConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Dia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.DiaDeMes

class Rdd(
    val campaniaActual: Campania,
    val meses: List<Mes>,
    val dias: List<Dia>,
    val hoy: DiaDeMes,
    private var indiceMesSeleccionado: Int,
    val personasNoPlanificadas: List<PersonaRdd>,
    val personasPlanificadas: List<PersonaRdd>,
    val sugerencias: List<String>
) {

    val diaSeleccionado
        get() = mesSeleccionado.diaSeleccionado

    val visitasDelDiaSeleccionado
        get() = mesSeleccionado.diaSeleccionado?.visitasProgramadas ?: emptyList()

    val eventosDelDiaSeleccionado
        get() = mesSeleccionado.diaSeleccionado?.eventos ?: emptyList()

    val buscador = BuscadorRdd(dias, personasNoPlanificadas)

    val noPlanificadasNoPosiblesConsultoras =
        personasNoPlanificadas.filter { it !is PosibleConsultoraRdd }

    val noPlanificadasProactivas =
        personasNoPlanificadas.filter {
            it is PosibleConsultoraRdd && it.tipo == PosibleConsultoraRdd.Tipo.PROACTIVA
        }

    val noPlanificadasNoProactivas =
        personasNoPlanificadas.filter {
            it is PosibleConsultoraRdd && it.tipo == PosibleConsultoraRdd.Tipo.NO_PROACTIVA
        }

    val mesSeleccionado: Mes
        get() {
            return meses[indiceMesSeleccionado]
        }

    val mesSiguienteValido: Boolean
        get() {
            return indiceMesSeleccionado + 1 < meses.size
        }

    val mesAnteriorValido: Boolean
        get() {
            return indiceMesSeleccionado > 0
        }

    fun mesSiguiente() {
        if (!mesSiguienteValido) return
        indiceMesSeleccionado += 1
    }

    fun mesAnterior() {
        if (!mesAnteriorValido) return
        indiceMesSeleccionado -= 1
    }

    fun seleccionarDia(indice: Int) {
        meses.forEach { it.diaSeleccionado?.seleccionado = false }
        meses[indiceMesSeleccionado].seleccionarDia(indice)
    }

    val personasPlanificadasHoy get() = hoy.dia.visitasProgramadas.map { it.persona }

    fun obtenerEstadoRdd(personaId: Long): Pair<PersonaRdd, EstadoRdd>? {
        (personasPlanificadas union personasNoPlanificadas).find { (it.id) == personaId }?.apply {
            return when {
                this.agenda.tieneAlMenosUnaRegistrable -> this to EstadoRdd.Planificada
                this.puedeAdicionarVisita -> this to EstadoRdd.PuedeAdicionarVisita
                this.agenda.todasRegistradas -> this to EstadoRdd.Registrada
                else -> this to EstadoRdd.NoPlanificada
            }
        }
        return null
    }

    enum class EstadoRdd {
        Registrada,
        Planificada,
        PuedeAdicionarVisita,
        NoPlanificada
    }
}
