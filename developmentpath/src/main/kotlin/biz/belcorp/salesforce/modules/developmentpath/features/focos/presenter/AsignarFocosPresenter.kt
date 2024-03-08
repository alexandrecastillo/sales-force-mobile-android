package biz.belcorp.salesforce.modules.developmentpath.features.focos.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.AsignarFocosUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.focos.AsignarView

class AsignarFocosPresenter(
    private val asignarFocosUseCase: AsignarFocosUseCase
) {

    var view: AsignarView? = null

    fun obtenerTodo() {
        asignarFocosUseCase.obtener(
            AsignarFocosSubscriber(view)
        )
    }

    fun obtnerFocosPropio() {
        asignarFocosUseCase.obtenerFocosPropios(
            AsignarFocosSubscriber(view)
        )
    }

    fun obtenerParaPersona(personaId: Long) {
        asignarFocosUseCase.obtenerPorPersona(
            personaId,
            AsignarFocosSubscriber(
                view
            )
        )
    }

    fun seleccionarFocoPropio(posicion: Int) {
        asignarFocosUseCase.cambiarSeleccionFocoPropio(
            posicion,
            AsignarFocosSubscriber(
                view
            )
        )
    }

    fun seleccionarFoco(posicion: Int) {
        asignarFocosUseCase.cambiarSeleccionFoco(
            posicion,
            AsignarFocosSubscriber(
                view
            )
        )
    }

    fun seleccionarSocia(posicion: Int) {
        asignarFocosUseCase.cambiarSeleccionSocia(posicion, AsignarFocosSubscriber(view))
    }

    fun guardar() {
        asignarFocosUseCase.guardar(
            GuardarSubscriber(view)
        )
    }

    fun guardarFocosPropio() {
        asignarFocosUseCase.guardarFocosPropio(
            GuardarSubscriberPropio(view)
        )
    }

    fun obtenerRol(): String {
        return asignarFocosUseCase.optenerRol()
    }

    inner class GuardarSubscriberPropio(val view: AsignarView?) : BaseObserver<Unit>() {

        override fun onComplete() = Unit

        override fun onError(exception: Throwable) {
            view?.mostrarMensaje(exception.localizedMessage)
        }

        override fun onNext(t: Unit) {
            view?.notificarActualizacionEnMisFocos()
            view?.cerrar()
        }
    }

}
