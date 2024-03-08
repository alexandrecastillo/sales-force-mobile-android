package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.RecuperarDatosAgregarEventoUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel

class RecuperarDatosHandler(viewHandler: AgregarEventoViewHandler,
                            model: AgregarEventoViewModel,
                            modelMapper: AgregarEventoModelMapper,
                            private val recuperarTiposEventoUseCase: RecuperarDatosAgregarEventoUseCase
) {

    private val rolHandler = RolHandler(viewHandler, model)

    private val tiposEventoHandler = TiposEventoHandler(viewHandler,
                                                        model,
                                                        modelMapper)

    private val tiemposAlertaHandler = TiemposAlertaHandler(viewHandler,
                                                            model,
                                                            modelMapper)

    private val otrosDatosHandler = OtrosDatosEventoHandler(viewHandler,
                                                            model)

    fun recuperarUsandoPlan(planId: Long) {
        val request = RecuperarDatosAgregarEventoUseCase
                .Request
                .APartirDePlan(planId, RecuperarDatosSubscriber())

        recuperarTiposEventoUseCase.ejecutar(request)
    }

    fun recuperarUsandoEvento(eventoId: Long) {
        val request = RecuperarDatosAgregarEventoUseCase
                .Request
                .APartirDeEvento(eventoId, RecuperarDatosSubscriber())

        recuperarTiposEventoUseCase.ejecutar(request)
    }

    fun seleccionarTipoEvento(posicion: Int) {
        tiposEventoHandler.seleccionarTipoEvento(posicion)
    }

    fun seleccionarAlerta(posicion: Int) {
        tiemposAlertaHandler.seleccionarTiempoAlerta(posicion)
    }

    inner class RecuperarDatosSubscriber :
            BaseSingleObserver<RecuperarDatosAgregarEventoUseCase.Response>() {
        override fun onSuccess(t: RecuperarDatosAgregarEventoUseCase.Response) {
            rolHandler.manejarRespuesta(t.rol)
            otrosDatosHandler.manejarRespuesta(t.eventoRdd)
            tiposEventoHandler.manejarRespuesta(t.seleccionadorTiposEvento)
            tiemposAlertaHandler.manejarRespuesta(t.seleccionadorAlertas)
        }
    }
}
