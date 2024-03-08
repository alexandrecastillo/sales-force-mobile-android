package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.planificacion

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan.PlanIdUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.RddUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class PlanificacionPresenter(
    private val view: PlanificacionView,
    private val rddUseCase: RddUseCase,
    private val planIdUseCase: PlanIdUseCase,
    private val planificacionMapper: PlanificacionMapper
) {

    private lateinit var model: PlanificacionModel
    private var personaId: Long = -1
    private lateinit var rol: Rol

    fun calcularEstado(personaId: Long, rol: Rol) {
        this.personaId = personaId
        this.rol = rol
        view.mostrarCargando()
        view.ocultarNoPlanificada()
        view.ocultarReplanificar()
        val request = PlanIdUseCase.Peticion(personaId, rol, PlanIdSubscriber())
        planIdUseCase.recuperarPlanIdPorPersona(request)
    }

    private fun cargarModeloEnVista() {
        determinarSiMostrarHistorial()
        view.cargarNombreCampania(model.nombreCortoCampania)
        view.cargarVisitas(model.visitas)

        model.apply {
            when (model) {
                is PlanificacionModel.NoPlanificada -> cargarNoPlanificada(this as PlanificacionModel.NoPlanificada)
                is PlanificacionModel.Planificada -> cargarPlanificada(this as PlanificacionModel.Planificada)
                is PlanificacionModel.Registrada -> cargarRegistrada()
                is PlanificacionModel.PuedeAdicionarVisita -> cargarAdicionable(this as PlanificacionModel.PuedeAdicionarVisita)
            }
        }
    }

    private fun determinarSiMostrarHistorial() {
        if (model.visitas.isEmpty()) {
            view.ocultarHistorialVisitas()
        } else {
            view.mostrarHistorialVisitas()
        }
    }

    private fun cargarAdicionable(model: PlanificacionModel.PuedeAdicionarVisita) {
        view.configurarBotonAdicionarVisita(model.personaId, model.rol)
        view.mostrarNoPlanificada()
        view.ocultarReplanificar()
        deshabilitarBotonRegistroPorRol()
    }

    private fun cargarNoPlanificada(model: PlanificacionModel.NoPlanificada) {
        view.configurarBotonPlanificar(model.visitaId)
        view.mostrarNoPlanificada()
        view.ocultarReplanificar()
        deshabilitarBotonRegistroPorRol()
    }

    private fun cargarPlanificada(model: PlanificacionModel.Planificada) {
        view.configurarBotonReprogramacion(model.visitaId, model.fecha)
        configurarBotonRegistro(model)
        habilitarBotonRegistroPorRol()
        view.cargarFecha(model.fechaString)
        view.cargarHora(model.horaString)
        view.mostrarReplanificar()
        view.ocultarNoPlanificada()
    }

    private fun configurarBotonRegistro(model: PlanificacionModel.Planificada) {
        if (model.registroValido) {
            view.configurarBotonRegistroValido(model.visitaId)
        } else {
            view.configurarBotonRegistroInvalido()
        }
    }

    private fun cargarRegistrada() {
        deshabilitarBotonRegistroPorRol()
        view.ocultarNoPlanificada()
        view.ocultarReplanificar()
    }

    private fun habilitarBotonRegistroPorRol() {
        when (rol) {
            Rol.CONSULTORA -> view.habilitarBotonRegistroConsultora()
            else -> view.hablitarBotonRegistroOtrosRoles()
        }
    }

    private fun deshabilitarBotonRegistroPorRol() {
        when (rol) {
            Rol.CONSULTORA -> view.deshabilitarBotonRegistroConsultora()
            else -> view.deshabilitarBotonRegistroOtrosRoles()
        }
    }

    inner class PlanIdSubscriber : BaseObserver<PlanIdUseCase.RespuestaRol>() {
        override fun onNext(t: PlanIdUseCase.RespuestaRol) {
            doAsync {
                val request = RddUseCase.Request(t.planId, PlanificadorSubscriber())
                rddUseCase.planificar(request)
            }
        }

        override fun onError(exception: Throwable) {
            view.ocultarCargando()
        }
    }

    inner class PlanificadorSubscriber : BaseObserver<RddUseCase.Response>() {

        override fun onNext(t: RddUseCase.Response) {
            doAsync {
                model = planificacionMapper.map(
                    estadoPersona = t.rdd.obtenerEstadoRdd(personaId)
                        ?: error("Persona no encontrada"),
                    campania = t.rdd.campaniaActual
                )
                uiThread {
                    view.ocultarCargando()
                    cargarModeloEnVista()
                }
            }
        }

        override fun onError(exception: Throwable) {
            view.ocultarCargando()
        }
    }
}
