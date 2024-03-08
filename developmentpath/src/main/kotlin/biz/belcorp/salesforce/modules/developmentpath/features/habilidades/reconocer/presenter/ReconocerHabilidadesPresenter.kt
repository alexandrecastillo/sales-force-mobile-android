package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.presenter

import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.ReconocerHabilidadesUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.view.ReconocerHabilidadesView

class ReconocerHabilidadesPresenter(
    private val view: ReconocerHabilidadesView,
    private val obtenerSesionUseCase: ObtenerSesionUseCase,
    private val reconocerHabilidadesUseCase: ReconocerHabilidadesUseCase
) {


    val sesion get() = obtenerSesionUseCase.obtener()

    fun obtenerHabilidadesParaVisualizacion(personaId: Long, rol: Rol, campania: String) {
        val request = ReconocerHabilidadesUseCase
            .RequestVisualizacion(
                personaId, rol, campania,
                ReconocerHabilidadesSubscriber(view)
            )

        reconocerHabilidadesUseCase.recuperarParaVisualizacion(request)
    }

    fun obtenerSpanCountPorRol(): Int {
        return when (sesion.rol) {
            Rol.DIRECTOR_VENTAS -> 2
            else -> 3
        }
    }

    fun obtenerHabilidadesParaInsercion(personaId: Long, rol: Rol) {
        val request = ReconocerHabilidadesUseCase
            .RequestInsercion(
                personaId, rol,
                ReconocerHabilidadesSubscriber(view)
            )

        reconocerHabilidadesUseCase.recuperarParaInsercion(request)
    }

    fun cambiarSeleccionHabilidad(posicion: Int) {
        reconocerHabilidadesUseCase.cambiarSeleccionHabilidad(
            posicion,
            ReconocerHabilidadesSubscriber(view)
        )
    }

    fun reconocerHabilidades() {
        reconocerHabilidadesUseCase.reconocer(GuardarReconocerHabilidadesSubscriber(view))
    }
}
