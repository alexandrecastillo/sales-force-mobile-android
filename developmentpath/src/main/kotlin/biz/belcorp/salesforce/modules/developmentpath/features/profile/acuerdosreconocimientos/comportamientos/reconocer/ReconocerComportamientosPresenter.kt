package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.comportamientos.reconocer.ReconocerComportamientosUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.comportamientos.reconocer.ReconocerInput
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.comportamientos.reconocer.ReconocerOutput

class ReconocerComportamientosPresenter(
    private val view: ReconocerComportamientosView,
    private val useCase: ReconocerComportamientosUseCase,
    private val mapper: GuardarReconocimientoMapper
) {

    fun cargarDatosIniciales(personaId: Long, rol: Rol) {
        val request = ReconocerInput.Recuperar(personaId, rol, CargarSubscriber())
        useCase.ejecutar(request)
    }

    fun invertirSeleccion(indice: Int) {
        val request = ReconocerInput.Invertir(indice, CargarSubscriber())
        useCase.invertirSeleccion(request)
    }

    fun guardar() {
        val request = ReconocerInput.Guardar(GuardarSubscriber())
        useCase.guardarEnCampaniaActual(request)
    }

    private fun cargarModeloPersona(persona: PersonaRdd) {
        view.mostrarDatosBasicosPersona(persona.nombreApellido)
        when (persona) {
            is ConsultoraRdd -> {
                view.mostrarDatosConsultora(persona.segmento.orEmpty(), persona.tipo)
            }
            is SociaEmpresariaRdd -> {
                view.mostrarDatosSocia(persona.nivelProductividad, persona.exitosa)
            }
        }
    }

    private fun cargarModeloReconocimiento(t: ReconocerOutput.Recuperar) {
        val model = mapper.map(t)
        view.pintarReconocimientos(model.reconocimientos)
        view.pintarRazonEnBoton(model.seleccionados, model.total)
    }

    private fun completarGuardado() {
        view.notificarCambioenReconocimiento()
        view.cerrarDialogoPrincipal()
        view.mostrarDialogoExito {
            view.cerrarDialogoPrincipal()
        }
    }

    inner class CargarSubscriber : BaseSingleObserver<ReconocerOutput.Recuperar>() {
        override fun onSuccess(t: ReconocerOutput.Recuperar) {
            cargarModeloReconocimiento(t)
            cargarModeloPersona(t.persona)
        }
    }

    inner class GuardarSubscriber : BaseCompletableObserver() {
        override fun onComplete() {
            completarGuardado()
        }
    }
}
