package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.planificacion

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import java.util.*

interface PlanificacionView {
    fun mostrarCargando()
    fun ocultarCargando()
    fun mostrarNoPlanificada()
    fun mostrarReplanificar()
    fun ocultarReplanificar()
    fun ocultarNoPlanificada()
    fun hablitarBotonRegistroOtrosRoles()
    fun deshabilitarBotonRegistroOtrosRoles()
    fun habilitarBotonRegistroConsultora()
    fun deshabilitarBotonRegistroConsultora()
    fun configurarBotonAdicionarVisita(personaId: Long, rol: Rol)
    fun configurarBotonPlanificar(visitaId: Long)
    fun configurarBotonReprogramacion(visitaId: Long, fechaAnterior: Date)
    fun configurarBotonRegistroValido(visitaId: Long)
    fun configurarBotonRegistroInvalido()
    fun cargarFecha(fecha: String?)
    fun cargarHora(hora: String?)
    fun mostrarHistorialVisitas()
    fun ocultarHistorialVisitas()
    fun cargarNombreCampania(campania: String)
    fun cargarVisitas(visitas: List<VisitaModel>)
    fun notificarAOtrosModulos()
}
