package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual.detalle

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.verdetalle.ReconocerInput
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.verdetalle.ReconocerOutput
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.verdetalle.ReconocimientoCampaniaActualUseCase

class ReconocimientoCampaniaActualPresenter(
    private val view: ReconocimientoCampaniaActualView,
    private val useCase: ReconocimientoCampaniaActualUseCase,
    private val mapper: VerReconocimientoMapper
) {

    fun cargarDatosIniciales(personaId: Long, rol: Rol) {
        val request = ReconocerInput.Recuperar(personaId, rol, CargarSubscriber())
        useCase.recuperarDataInicial(request)
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
        val reconocimientos = mapper.map(t)
        view.pintarReconocimientos(reconocimientos)
    }

    private inner class CargarSubscriber : BaseSingleObserver<ReconocerOutput.Recuperar>() {
        override fun onSuccess(t: ReconocerOutput.Recuperar) {
            cargarModeloPersona(t.persona)
            cargarModeloReconocimiento(t)
        }
    }
}
