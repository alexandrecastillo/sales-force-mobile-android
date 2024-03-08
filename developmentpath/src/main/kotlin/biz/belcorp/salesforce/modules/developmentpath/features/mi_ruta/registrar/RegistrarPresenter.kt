package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.registrar

import android.util.Log
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.core.utils.parseToDateItem
import biz.belcorp.salesforce.core.domain.entities.analytics.EventoModel
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos.CreacionAcuerdosUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.analytics.FirebaseAnalyticsUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.CargaInicialRegistroVisitaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.RegistrarVisitaUseCase
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class RegistrarPresenter(
    private val cargaInicialRegistroVisitaUseCase: CargaInicialRegistroVisitaUseCase,
    private val registrarVisitaUseCase: RegistrarVisitaUseCase,
    private val creacionAcuerdosUseCase: CreacionAcuerdosUseCase,
    private val analyticsUseCase: FirebaseAnalyticsUseCase
) {

    var registrarView: RegistrarView? = null
    var acuerdosView: AcuerdosView? = null
    val datosRegistroModel: DatosRegistroModel = DatosRegistroModel.inicializar()

    fun cargarDatosIniciales(visitaId: Long) {
        cargaInicialRegistroVisitaUseCase.ejecutar(visitaId, CargaInicialSubscriber())
    }

    fun registrar(visitaId: Long) {
        registrarView?.deshabilitarBotonRegistro()
        registrarVisita(visitaId)
        registrarEventoEnAnalytics()
    }

    private fun registrarVisita(visitaId: Long) {
        val requestRegistro = construirRequestRegistro(visitaId)

        registrarVisitaUseCase.registrarVisita(requestRegistro)
    }

    private fun construirRequestRegistro(visitaId: Long): RegistrarVisitaUseCase.Request {
        return RegistrarVisitaUseCase.Request(
            visitaId = visitaId,
            acuerdos = creacionAcuerdosUseCase.listarCreados(),
            pasaPedido = datosRegistroModel.switchHabilitado,
            subscriber = RegistrarSubscriber()
        )
    }

    private fun registrarEventoEnAnalytics() {
        if (datosRegistroModel.rolVisita == null) return
        val requestAnalytics = construirRequestAnalytics()
        val btnguardar = eventGuardar()
        analyticsUseCase.enviarEventoPorRol(requestAnalytics)
        analyticsUseCase.enviarEventoPorRol(btnguardar)
    }

    private fun construirRequestAnalytics(): FirebaseAnalyticsUseCase.RequestEventoPorRol {
        return FirebaseAnalyticsUseCase.RequestEventoPorRol(
            TagAnalytics.EVENTO_FINALIZAR_VISITA,
            requireNotNull(datosRegistroModel.rolVisita),
            EventoObserver()
        )
    }

    private fun eventGuardar(): FirebaseAnalyticsUseCase.RequestEventoPorRol {
        return FirebaseAnalyticsUseCase.RequestEventoPorRol(
            TagAnalytics.EVENTO_CREAR_ACUERDO,
            requireNotNull(datosRegistroModel.rolVisita),
            EventoObserver()
        )
    }

    fun destroy() {
        registrarVisitaUseCase.dispose()
        creacionAcuerdosUseCase.dispose()
        registrarView = null
        acuerdosView = null
    }

    fun cambiarEstadoSwitch(habilitado: Boolean) {
        datosRegistroModel.switchHabilitado = habilitado
    }

    fun crearAcuerdo(visitaId: Long, contenido: String) {
        val request = CreacionAcuerdosUseCase.CrearRequest(
            visitaId,
            contenido,
            CrearAcuerdoSubscriber()
        )
        creacionAcuerdosUseCase.crear(request)
    }

    fun eliminarAcuerdo(posicion: Int) {
        val request = CreacionAcuerdosUseCase.EliminarRequest(
            posicion,
            EliminarAcuerdoSubscriber()
        )

        creacionAcuerdosUseCase.eliminar(request)
    }

    private inner class RegistrarSubscriber : BaseObserver<Unit>() {
        override fun onNext(t: Unit) {
            registrarView?.mostrarExitoRegistro()
            registrarView?.notificarCambioEnPlanificacion()
            registrarView?.cerrar()
        }

        override fun onError(exception: Throwable) {
            registrarView?.mostrarError(exception.localizedMessage.orEmpty())
            registrarView?.habilitarBotonRegistro()
        }
    }

    private class EventoObserver : BaseSingleObserver<EventoModel>() {
        override fun onError(e: Throwable) {
            Log.e(e.localizedMessage, e.message.orEmpty())
        }
    }

    private inner class CargaInicialSubscriber :
        BaseSingleObserver<CargaInicialRegistroVisitaUseCase.Response>() {

        override fun onSuccess(t: CargaInicialRegistroVisitaUseCase.Response) {
            actualizarModelo(t)
            cargarDatosRegistroEnVista()
        }

        private fun actualizarModelo(t: CargaInicialRegistroVisitaUseCase.Response) {
            datosRegistroModel.mostrarAcuerdos = t.mostrarAcuerdos
            datosRegistroModel.rolVisita = t.visita.persona.rol
            datosRegistroModel.mostrarSwitch = t.mostrarPasaPedido
            datosRegistroModel.switchHabilitado = t.pasaPedido
            datosRegistroModel.nombrePersona =
                WordUtils.capitalizeFully(t.persona.primerNombre ?: "").trim()
        }

        private fun cargarDatosRegistroEnVista() {
            pintarNombre()
            pintarValorSwitch()
            mostrarUOcultarSwitch()
            mostrarUOcultarAcuerdos()
            habilitarBotonRegistro()
        }

        private fun mostrarUOcultarAcuerdos() {
            if (datosRegistroModel.mostrarAcuerdos) {
                acuerdosView?.habilitarCreacionAcuerdos()
            } else {
                acuerdosView?.deshabilitarCreacionAcuerdos()
            }
        }

        private fun mostrarUOcultarSwitch() {
            if (datosRegistroModel.mostrarSwitch) {
                acuerdosView?.mostrarSwitch()
            } else {
                acuerdosView?.ocultarSwitch()
            }
        }

        private fun pintarNombre() {
            acuerdosView?.pintarNombreEnSwitch(datosRegistroModel.nombrePersona)
        }

        private fun pintarValorSwitch() {
            if (datosRegistroModel.switchHabilitado) {
                acuerdosView?.habilitarSwitch()
            } else {
                acuerdosView?.deshabilitarSwitch()
            }
        }

        private fun habilitarBotonRegistro() {
            registrarView?.habilitarBotonRegistro()
        }

        override fun onError(e: Throwable) {
            acuerdosView?.mostrarError(e.message ?: "Error al obtener datos")
        }
    }

    private inner class CrearAcuerdoSubscriber : SingleObserver<Acuerdo.ModeloCreacion> {
        override fun onSubscribe(d: Disposable) = Unit

        override fun onSuccess(t: Acuerdo.ModeloCreacion) {

            val modelo = AcuerdoModel(
                t.contenido,
                t.fecha.parseToDateItem()
            )

            acuerdosView?.ocultarTeclado()
            acuerdosView?.mostrarNuevoAcuerdo(modelo)
            acuerdosView?.ocultarContenedorNuevoAcuerdo()
            acuerdosView?.limpiarCampoError()
        }

        override fun onError(exception: Throwable) {
            acuerdosView?.mostrarError(exception.localizedMessage)
        }
    }

    private inner class EliminarAcuerdoSubscriber : SingleObserver<Int> {
        override fun onSubscribe(d: Disposable) = Unit

        override fun onSuccess(t: Int) {
            acuerdosView?.quitarAcuerdoEliminado(t)
        }

        override fun onError(exception: Throwable) {
            acuerdosView?.mostrarError(exception.localizedMessage)
        }
    }
}
