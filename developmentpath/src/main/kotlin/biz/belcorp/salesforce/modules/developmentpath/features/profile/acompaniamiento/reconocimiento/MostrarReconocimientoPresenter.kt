package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.reconocimiento

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.MostrarReconocimientoUseCase

class MostrarReconocimientoPresenter(
    private val view: MostrarReconocimientoView,
    private val useCase: MostrarReconocimientoUseCase
) {

    fun validarYMostrar(personaId: Long, rol: Rol) {
        val request = MostrarReconocimientoUseCase.Request(
            personaId = personaId,
            rol = rol,
            subscriber = MostrarSubscriber()
        )
        useCase.validarYMostrar(request)
    }

    private inner class MostrarSubscriber : BaseObserver<MostrarReconocimientoUseCase.Response>() {
        override fun onNext(t: MostrarReconocimientoUseCase.Response) {
            when (t.loQueSeDebeMostrar) {
                MostrarReconocimientoUseCase.LoQueSeDebeMostrar.COMPORTAMIENTOS ->
                    view.mostrarReconocimientoComportamientos()
                MostrarReconocimientoUseCase.LoQueSeDebeMostrar.HABILIDADES ->
                    view.mostrarReconocimientoHabilidades()
                MostrarReconocimientoUseCase.LoQueSeDebeMostrar.NADA -> {
                    println(
                        "Se invalidó el mostrar la ventana de comportamientos." +
                            "\n$t"
                    )
                }
            }
        }

        override fun onError(exception: Throwable) {
            view.mostrarMensaje(exception.localizedMessage)
        }
    }
}
