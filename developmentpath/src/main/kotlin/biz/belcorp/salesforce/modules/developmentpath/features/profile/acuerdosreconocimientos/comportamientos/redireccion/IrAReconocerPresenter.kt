package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.redireccion

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.MostrarIrAReconocerUseCase

class IrAReconocerPresenter(
    private val view: IrAReconocerView,
    private val useCase: MostrarIrAReconocerUseCase
) {

    fun calcularVisibilidad(personaId: Long, rol: Rol) {
        val subscriber = CalcularSubscriber(view)
        val request = MostrarIrAReconocerUseCase.Request(personaId, rol, subscriber)
        useCase.obtener(request)
    }

    inner class CalcularSubscriber(private val view: IrAReconocerView) :
        BaseSingleObserver<MostrarIrAReconocerUseCase.Response>() {
        override fun onSuccess(t: MostrarIrAReconocerUseCase.Response) {
            if (t.sePuedeReconocer) {
                view.mostrar()
            } else {
                view.ocultar()
            }
        }
    }
}
