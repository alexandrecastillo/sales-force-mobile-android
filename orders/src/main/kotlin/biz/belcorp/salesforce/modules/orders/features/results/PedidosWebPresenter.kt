package biz.belcorp.salesforce.modules.orders.features.results

import biz.belcorp.salesforce.core.base.BasePresenter
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.orders.core.domain.contants.Constants.PEDIDO_BLOQUEADO
import biz.belcorp.salesforce.modules.orders.core.domain.contants.Constants.PEDIDO_DESBLOQUEADO
import biz.belcorp.salesforce.modules.orders.core.domain.exceptions.BloqueoPedidoException
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.BloquearPedidoUseCase
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.BuscarPedidosUseCase
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.CheckLockConfigUseCase
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.DesbloquearPedidoUseCase
import biz.belcorp.salesforce.modules.orders.features.results.mapper.FiltroPedidosWebMapper
import biz.belcorp.salesforce.modules.orders.features.results.mapper.ResultadoPedidosWebMapper
import biz.belcorp.salesforce.modules.orders.features.results.model.ResultadoItemPedidosWebModel
import biz.belcorp.salesforce.modules.orders.features.results.model.ResultadoItemPedidosWebModel.Companion.ORIGEN_WEB
import biz.belcorp.salesforce.modules.orders.features.results.model.ResultadoPedidosWebModel
import biz.belcorp.salesforce.modules.orders.features.search.model.FiltroPedidosWebModel
import biz.belcorp.salesforce.modules.orders.utils.customMessage


class PedidosWebPresenter(
    private val pedidosWebView: PedidosWebView,
    private val checkLockConfigUseCase: CheckLockConfigUseCase,
    private val obtenerSesionUseCase: ObtenerSesionUseCase,
    private val campaniaUsecase: ObtenerCampaniasUseCase,
    private val pedidosWebUseCase: BuscarPedidosUseCase,
    private val bloquearPedidoUseCase: BloquearPedidoUseCase,
    private val desbloquearPedidoUseCase: DesbloquearPedidoUseCase,
    private val filtroPedidosWebMapper: FiltroPedidosWebMapper,
    private val modelDataMapper: ResultadoPedidosWebMapper
) : BasePresenter() {

    private var campania: Campania? = null

    private val sesion get() = obtenerSesionUseCase.obtener()

    fun obtenerDatosConfiguracion() {
        doAsync {
            campania = campaniaUsecase.obtenerCampaniaActual()
            uiThread {
                procesarCampaniaActual(campania!!)
                procesarDatosConfiguracion(campania!!, sesion)
                iniciarBusquedaInicial(campania!!.codigo)
            }
        }
    }

    private fun procesarCampaniaActual(campania: Campania) {
        val nombre = campania.nombreCorto
        when (campania.periodo) {
            Campania.Periodo.VENTA -> pedidosWebView.mostrarCampaniaVenta(nombre)
            Campania.Periodo.FACTURACION -> pedidosWebView.mostrarCampaniaFacturacion(nombre)
        }
    }

    private suspend fun procesarDatosConfiguracion(campania: Campania, sesion: Sesion) {
        val bloqueoActivado = checkLockConfigUseCase.checkConfig()
        pedidosWebView.configurarAdapter(campania.codigo, sesion.rol, bloqueoActivado)
    }

    private fun iniciarBusquedaInicial(campania: String) {
        val filtros = FiltroPedidosWebModel()
        filtros.campania = campania
        buscarPedidos(filtros)
    }

    fun buscarPedidos(filtroPedidosModel: FiltroPedidosWebModel) {
        pedidosWebView.mostrarLoading()
        pedidosWebView.ocultarResultados()
        doAsync {
            val filtroPedidos = filtroPedidosWebMapper.map(filtroPedidosModel)
            val resultados = pedidosWebUseCase.buscarPedidos(filtroPedidos)
            val pedidos = modelDataMapper.reverseMap(resultados)
            uiThread {
                mostrarResultados(pedidos)
                pedidosWebView.ocultarLoading()
            }
        }
    }

    private fun mostrarResultados(resultados: ResultadoPedidosWebModel) {
        if (resultados.orderWebList.isNotEmpty()) {
            pedidosWebView.mostrarResultadosEncontrados()
            pedidosWebView.mostrarTotalResultados(resultados.totalPedidos)
            if (sesion.rol != Rol.SOCIA_EMPRESARIA) {
                pedidosWebView.mostrarTotalResultadosMontoMinimo(resultados.totalPedidosBajoMontoMinimo)
            }
            pedidosWebView.mostrarResultados(resultados.orderWebList)
        } else {
            pedidosWebView.mostrarResultadosNoEncontrados()
        }
    }

    fun verificarBloqueo(model: ResultadoItemPedidosWebModel) {
        if (verificarCondicionesBloqueo(model)) {
            pedidosWebView.mostrarBloqueoDialog {
                bloquearPedido(model, PEDIDO_BLOQUEADO) {
                    bloquearPedidoUseCase.bloquear(model.orderId, model.campania)
                }
            }
        }
    }

    fun verificarDesbloqueo(model: ResultadoItemPedidosWebModel) {
        if (verificarCondicionesBloqueo(model)) {
            pedidosWebView.mostrarDesloqueoDialog {
                bloquearPedido(model, PEDIDO_DESBLOQUEADO) {
                    desbloquearPedidoUseCase.desbloquear(model.orderId, model.campania)
                }
            }
        }
    }

    private fun bloquearPedido(
        model: ResultadoItemPedidosWebModel,
        bloqueo: Int,
        f: suspend () -> Unit
    ) {
        pedidosWebView.mostrarLoading()
        doAsync {
            f.invoke()
            uiThread {
                pedidosWebView.actualizarBloqueo(model.orderId, bloqueo)
                pedidosWebView.ocultarLoading()
            }
        }
    }

    private fun verificarCondicionesBloqueo(model: ResultadoItemPedidosWebModel): Boolean {
        return model.source == ORIGEN_WEB && model.campania == campania?.codigo
    }

    override fun onExceptionHandler(exception: Throwable) {
        pedidosWebView.ocultarLoading()
        when (exception) {
            is BloqueoPedidoException -> pedidosWebView.mostrarDialogoError()
            else -> pedidosWebView.mostrarMensajeError(exception.customMessage())
        }
    }

}
