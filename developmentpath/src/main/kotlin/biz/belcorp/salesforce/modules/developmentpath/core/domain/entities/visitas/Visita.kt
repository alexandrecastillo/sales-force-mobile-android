package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas

import biz.belcorp.salesforce.core.utils.es
import biz.belcorp.salesforce.core.utils.toCalendar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.buscador.BuscadorRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.menu.VisibilidadMenuHandler
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general.TipData
import java.io.Serializable
import java.util.*

class Visita(
    val id: Long,
    val planId: Long,
    var fechaRegistro: Calendar?,
    var fechaProgramacionInicial: Calendar?,
    var fechaProgramacion: Calendar?,
    var fechaReprogramacion: Calendar?,
    var enviado: Boolean,
    var tips: Int,
    val planificacionInicial: Boolean,
    var forzarEliminado: Boolean,
    val esAdicional: Boolean,
    var usuarioRed: String,
    var data: ArrayList<TipData> = arrayListOf()
) : BuscadorRdd.Buscable, Serializable {

    lateinit var persona: PersonaRdd

    val visibilidadMenuHandler get() = VisibilidadMenuHandler.porVisita(this)

    val estaReprogramada get() = fechaReprogramacion != null

    val estaReprogramadaSinRegistrar get() = estaReprogramada && !estaRegistrada

    val esRegistrable get() = !estaRegistrada && (estaReprogramada || estaProgramada)

    val estaProgramada get() = fechaProgramacion != null

    val esProgramable
        get() = !forzarEliminado && !estaReprogramada && !estaRegistrada

    val estaRegistrada get() = fechaRegistro != null

    val horaAMostrar get() = fechaAMostrar?.time ?: Date()

    val fechaAMostrar get() = fechaRegistro ?: fechaReprogramacion ?: fechaProgramacion

    fun establecerPersona(persona: PersonaRdd) {
        this.persona = persona
    }

    fun fueRegistradaEn(dia: Calendar) = fechaRegistro?.es(dia) ?: false

    fun registrar(fecha: Calendar) {
        fechaRegistro = fecha
        enviado = false
    }

    fun registrarAhora() {
        registrar(Date().toCalendar())
    }

    fun eliminar() {
        forzarEliminado = true
        fechaReprogramacion = null
        enviado = false
    }

    fun programar(fecha: Calendar) {
        fechaReprogramacion = fecha
        forzarEliminado = false
        enviado = false
    }

    fun reprogramar(fecha: Calendar) {
        fechaReprogramacion = fecha
        enviado = false
    }

    fun validarRegistroArrojandoExcepcion() {
        if (!this.persona.puedeRegistrarVisitaHoy)
            throw Exception("No se puede registrar dos visitas el mismo día.")
    }

    fun calcularDuracion(horas: Int): Long {
        val inicio = Calendar.getInstance()
        val fin = (inicio.clone() as Calendar)
        fin.add(Calendar.HOUR, horas)
        return fin.timeInMillis - inicio.timeInMillis
    }

    override fun match(filtro: String): Boolean {
        return super.match(filtro) || persona.match(filtro)
    }

    override fun sugerir(): String? {
        return null
    }
}
