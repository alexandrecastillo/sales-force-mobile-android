package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.presenter

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.ConfirmarEventoAtraccionUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.EliminarEventoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.RecuperarEventoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.RecuperarRelacionObservadorEventoUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.model.DetalleEventoMapper
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.model.DetalleEventoModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.view.DetalleEventoView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoModelMapper

class DetalleEventoPresenter(private var view: DetalleEventoView? = null,
                             private val recuperarEventoUseCase: RecuperarEventoUseCase,
                             private val eliminarEventoUseCase: EliminarEventoUseCase,
                             private val confirmarEventoUseCase: ConfirmarEventoAtraccionUseCase,
                             private val mapper: DetalleEventoMapper,
                             private val alertaMapper: AgregarEventoModelMapper) {

    fun getEvento(eventoXUaId: Long) {
        recuperarEventoUseCase.recuperar(eventoXUaId, RecuperarEventoSubscriber())
    }

    fun eliminar(eventoXUaId: Long) {
        eliminarEventoUseCase.eliminar(eventoXUaId, EliminacionSubscriber())
    }

    fun confirmarEvento(eventoXUaId: Long) {
        confirmarEventoUseCase.confirmar(eventoXUaId, ConfirmacionSubscriber())
    }

    private inner class RecuperarEventoSubscriber: BaseSingleObserver<RecuperarEventoUseCase.Response>() {

        override fun onSuccess(t: RecuperarEventoUseCase.Response) {

            doAsync {
                val modelo = mapper.parse(t.evento)
                uiThread {
                    modelo.pintarModelo()
                    when {
                        !modelo.activo -> pintarComoEliminado(t.rolMadre)
                        modelo.registrado -> pintarEventoRegistrado()
                        else -> permitirSegunTipo(t.relacion)
                    }
                }
            }
        }

        override fun onError(e: Throwable) = e.printStackTrace()
    }


    private fun permitirSegunTipo(tipo : RecuperarRelacionObservadorEventoUseCase.Tipo) {
        when (tipo) {
            is RecuperarRelacionObservadorEventoUseCase.Tipo.OrganizadorViendoEventoPropio -> permitirAcciones()
            is RecuperarRelacionObservadorEventoUseCase.Tipo.OrganizadorViendoEventoAjeno -> prohibirAcciones()
            is RecuperarRelacionObservadorEventoUseCase.Tipo.AsistenteViendoEventoPropio -> permitirSoloEliminar()
            is RecuperarRelacionObservadorEventoUseCase.Tipo.AsistenteViendoEventoAjeno -> prohibirAcciones()
        }
    }

    private fun permitirAcciones() {

        view?.permitirEditar()
        view?.permitirEliminar()
        view?.mostrarConfirmar()
    }

    private fun prohibirAcciones() {
        view?.prohibirEditar()
        view?.prohibirEliminar()
    }

    private fun permitirSoloEliminar() {
        view?.permitirEliminar()
        view?.prohibirEditar()
        view?.mostrarConfirmar()
    }

    private fun pintarEventoRegistrado() {
        view?.prohibirEditar()
        view?.prohibirEliminar()
        view?.eventoRegistrado()
    }

    private fun pintarComoEliminado(rol: Rol) {
        view?.mostrarComoEliminado(rol.comoTexto())
        view?.prohibirEditar()
        view?.prohibirEliminar()
    }

    private inner class EliminacionSubscriber: BaseCompletableObserver() {

        override fun onComplete() {
            view?.emitirBroadcast()
            view?.cerrar()
            view?.mostrarToast()
        }
    }

    private inner class ConfirmacionSubscriber: BaseCompletableObserver(){
        override fun onComplete() {
            view?.actualizarEventos()
            pintarEventoRegistrado()
        }
    }

    private fun DetalleEventoModel.pintarModelo() {
        view?.pintarTitulo(titulo)
        view?.pintarUbicacion(ubicacion)
        view?.pintarFecha(fecha)

        if (esTodoElDia)
            view?.pintarTodoElDia()
        else
            view?.pintarIntervalo(horaInicio, horaFin)

        view?.pintarAlerta(alerta?.valor ?: 0, alertaMapper.convertir(alerta?.unidad))
        view?.mostrarMensageRegistrado(registrado)
    }
}
