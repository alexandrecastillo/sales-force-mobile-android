package biz.belcorp.salesforce.modules.developmentpath.features.resultadovisitas

import android.os.Handler
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.formatearConComas
import biz.belcorp.salesforce.core.utils.redondearDosDecimales
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.ResultadoPedido
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.RecuperarResultadoVisitasUseCase
import biz.belcorp.salesforce.modules.developmentpath.utils.agregarDigitoVerificador
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class ListarResultadoVisitasPresenter(
    private val view: ListarResultadoVisitasView? = null,
    private val recuperarResultadoVisitasUseCase: RecuperarResultadoVisitasUseCase
) {

    private lateinit var modelo: ListarResultadoVisitasModel

    fun obtenerFacturadas() {
        recuperarResultadoVisitasUseCase
            .recuperarFacturadas(RecuperarResultadoSubscriber())
    }

    fun obtenerNoFacturadas() {
        recuperarResultadoVisitasUseCase
            .recuperarNoFacturadas(RecuperarResultadoSubscriber())
    }

    fun filtrar(filtro: String) {
        recuperarResultadoVisitasUseCase.filtrar(filtro, FiltrarResultadosSubscriber())
    }

    fun mostrarConsultorasConPedido() {
        view?.bloquearSwitch()
        view?.mostrarCargando()
        recuperarResultadoVisitasUseCase.mostrarConsultorasConPedido(MostrarPedidosSubscriber())
    }

    fun ocultarConsultorasConPedido() {
        recuperarResultadoVisitasUseCase.ocultarConsultorasConPedido(MostrarPedidosSubscriber())
    }

    private fun cargarModeloEnVista() {
        cargarCampaniaEnVista()
        cargarTitulo()
        cargarSubtitulo()
        cargarCantidaTotalDeConsultorasEnVista()
        cargarSugerencias()
        Handler().postDelayed({ cargarConsultorasEnVista() }, 50)
    }

    private fun cargarCampaniaEnVista() {
        view?.mostrarCampania(modelo.campaniaNombreLargo)
    }

    private fun cargarTitulo() {
        if (modelo.facturacion) {
            view?.mostrarTituloFacturaron()
        } else {
            view?.mostrarTituloNoFacturaron()
        }
    }

    private fun cargarSubtitulo() {
        if (modelo.facturacion) {
            view?.mostrarSubtituloFacturaron(modelo.campaniaNombreCorto)
        } else {
            view?.mostrarSubtituloNoFacturaron(modelo.campaniaNombreCorto)
        }
    }

    private fun cargarCantidaTotalDeConsultorasEnVista() {
        view?.mostrarCantidadTotalDeConsultoras(modelo.cantidadConsultoras)
    }

    private fun cargarSugerencias() {
        view?.mostrarSugerencias(modelo.sugerencias)
    }

    private fun cargarCantidadConsultorasFiltradas() {
        when {
            modelo.filtro.isBlank() -> {
                view?.ocultarCantidadFiltradas()
                view?.ocultarSinResultadosBusqueda()
            }
            modelo.cantidadConsultoras > 0 -> {
                view?.pintarCantidadFiltradas(modelo.cantidadConsultoras)
                view?.mostrarCantidadFiltradas()
                view?.ocultarSinResultadosBusqueda()
            }
            else -> {
                view?.pintarCantidadFiltradas(modelo.cantidadConsultoras)
                view?.mostrarCantidadFiltradas()
                view?.mostrarSinResultadosBusqueda()
            }
        }
    }

    private fun cargarConsultorasEnVista() {
        view?.mostrarConsultoras(modelo.consultoras, modelo.campaniaAnteriorNombreCorto)
    }

    private inner class RecuperarResultadoSubscriber :
        BaseSingleObserver<RecuperarResultadoVisitasUseCase.ResponseRecuperar>() {

        override fun onSuccess(t: RecuperarResultadoVisitasUseCase.ResponseRecuperar) {
            doAsync {
                modelo = parse(t)

                uiThread { cargarModeloEnVista() }
            }
        }
    }

    private inner class MostrarPedidosSubscriber :
        BaseSingleObserver<RecuperarResultadoVisitasUseCase.ResponseMostrarPedido>() {

        override fun onSuccess(t: RecuperarResultadoVisitasUseCase.ResponseMostrarPedido) {
            doAsync {
                modelo.consultoras = t.resultadosPedido.map { parse(it) }

                uiThread {
                    cargarConsultorasEnVista()
                    view?.desbloquearSwitch()
                    view?.ocultarCargado()
                }
            }
        }

        override fun onError(e: Throwable) {
            view?.desbloquearSwitch()
            view?.ocultarCargado()
            view?.resetearSwitch()
            view?.mostrarErrorSwitch()
        }
    }

    private inner class FiltrarResultadosSubscriber :
        BaseSingleObserver<RecuperarResultadoVisitasUseCase.ResponseFiltrar>() {

        override fun onSuccess(t: RecuperarResultadoVisitasUseCase.ResponseFiltrar) {
            doAsync {
                modelo.consultoras = t.resultadosPedido.map { parse(it) }
                modelo.filtro = t.filtro

                uiThread {
                    cargarConsultorasEnVista()
                    cargarCantidadConsultorasFiltradas()
                }
            }
        }
    }

    private fun parse(response: RecuperarResultadoVisitasUseCase.ResponseRecuperar):
        ListarResultadoVisitasModel {
        val campaniaNombreLargo = construirNombreLargoCampania(response.campania)
        val campaniaNombreCorto = construirNombreCortoCampania(response.campania)
        val campaniaAnteriorNombreCorto = construirNombreCortoCampania(response.campaniaAnterior)
        val consultoras = response.resultadosPedido.map { parse(it) }
        val sugerencias = response.sugerencias

        return ListarResultadoVisitasModel(
            campaniaNombreLargo = campaniaNombreLargo,
            campaniaNombreCorto = campaniaNombreCorto,
            campaniaAnteriorNombreCorto = campaniaAnteriorNombreCorto,
            facturacion = response.facturada,
            consultoras = consultoras,
            sugerencias = sugerencias
        )
    }

    private fun construirNombreLargoCampania(campania: Campania): String {
        return when (campania.periodo) {
            Campania.Periodo.VENTA -> "Venta ${campania.nombreCorto}"
            Campania.Periodo.FACTURACION -> "Facturacion ${campania.nombreCorto}"
            else -> Constant.EMPTY_STRING
        }
    }

    private fun construirNombreCortoCampania(campania: Campania): String {
        return campania.nombreCorto.replace("-", "")
    }

    private fun parse(resultadoPedido: ResultadoPedido): ConsultoraModel {
        val consultora = resultadoPedido.consultora

        return ConsultoraModel(
            id = consultora.id,
            rol = consultora.rol,
            codigo = consultora.codigo,
            codigoMasDigito = consultora.codigo
                .agregarDigitoVerificador(consultora.digitoVerificador),
            nombre = consultora.nombreCompleto,
            telefono = resultadoPedido.numeroConPrefijo,
            mostrarPedido = resultadoPedido.tienePedido,
            montoPedido = convertirMontoAString(resultadoPedido.montoPedido),
            tipo = consultora.tipo,
            segmento = consultora.segmentoInterno,
            cantidadProductoPPU = consultora.cantidadProductoPPU,
            mostrarPPU = consultora.mostrarCantidadProductosPPU()
        )
    }

    private fun convertirMontoAString(monto: Double): String {
        return monto.redondearDosDecimales().formatearConComas()
    }
}
