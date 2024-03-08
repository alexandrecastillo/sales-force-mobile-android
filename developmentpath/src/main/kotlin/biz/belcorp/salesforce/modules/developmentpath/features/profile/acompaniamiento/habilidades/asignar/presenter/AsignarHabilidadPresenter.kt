package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.presenter

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.AsignarHabilidadesUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.view.AsignarHabilidadView

class AsignarHabilidadPresenter(
    private val view: AsignarHabilidadView,
    private val asignarHabilidadesUseCase: AsignarHabilidadesUseCase
) {

    fun obtenerHabilidades(zonaId: Long) {
        asignarHabilidadesUseCase
            .obtenerHabilidadesAsignadasAOtro(
                AsignarHabilidadesUseCase.Request(zonaId),
                AsignarHabilidadesSubscriber(view)
            )
    }

    fun obtenerMisHabilidades() {
        asignarHabilidadesUseCase.obtenerHabilidadesAsignadasPropias(
            AsignarHabilidadesSubscriber(view)
        )
    }

    fun cambiarSeleccionHabilidad(posicion: Int) {
        asignarHabilidadesUseCase.cambiarSeleccionHabilidad(
            posicion,
            AsignarHabilidadesSubscriber(view)
        )
    }

    fun asignarHabilidades() {
        asignarHabilidadesUseCase.asignarHabilidadesAOtro(GuardarAsignarHabilidadesSubscriber(view))
    }

    fun asignarMiHabilidades() {
        asignarHabilidadesUseCase.asignarHabilidadesPropias(GuardarAsignarHabilidadesSubscriber(view))
    }

    fun obtenerRol(): String {
        return asignarHabilidadesUseCase.obtenerRol()
    }
}
