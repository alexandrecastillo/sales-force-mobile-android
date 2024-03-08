package biz.belcorp.salesforce.modules.consultants.features.search

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.features.search.models.TipoEstadoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSaldoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSegmentoModel
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.core.domain.constants.Constants
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.FiltrosBusqueda
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.consultora.ConsultoraUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.filtros.ObtenerFiltrosBusquedaUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.filtros.SeccionesUseCase
import biz.belcorp.salesforce.modules.consultants.features.search.mappers.FiltrosBusquedaMapper
import biz.belcorp.salesforce.modules.consultants.features.search.mappers.SeccionModelDataMapper
import biz.belcorp.salesforce.modules.consultants.features.search.mappers.mapToConsutlantFilter
import biz.belcorp.salesforce.modules.consultants.features.search.models.*


class BusquedaConsultoraPresenter(
    private val view: BusquedaConsultoraView,
    private val campaniaUsecase: ObtenerCampaniasUseCase,
    private val seccionesUseCase: SeccionesUseCase,
    private val consultoraUseCase: ConsultoraUseCase,
    private val sesionUseCase: ObtenerSesionUseCase,
    private val seccionMapper: SeccionModelDataMapper,
    private val obtenerFiltrosBusquedaUseCase: ObtenerFiltrosBusquedaUseCase,
    private val filtrosBusquedaMapper: FiltrosBusquedaMapper
) {

    private val sesion by lazy { sesionUseCase.obtener() }

    fun cargarCampania() {
        campaniaUsecase.obtenerCampaniaActual(CampaniaActualSubscriber())
    }

    fun cargarFiltros() {
        habilitarSecciones()
        obtenerFiltros()
    }

    fun habilitarSecciones() {
        when (sesion.rol) {
            Rol.SOCIA_EMPRESARIA -> view.ocultarFiltroSeccion()
            else -> obtenerSecciones()
        }
    }

    private fun obtenerSecciones() {
        seccionesUseCase.getAll(SeccionesSubscriber())
    }

    private fun obtenerFiltros() {
        obtenerFiltrosBusquedaUseCase.getAll(FiltrosBusquedaSubscriber())
    }

    private fun obtenerSeccionSE(): String? {
        if (sesion.rol == Rol.SOCIA_EMPRESARIA) {
            return sesion.seccion
        }
        return null
    }

    private fun prepararFiltroTipoEstado(list: List<TipoEstadoModel>) {
        val filtro = list.toMutableList()
        val tipoTipoEstadoModelTodos = TipoEstadoModel()
        tipoTipoEstadoModelTodos.idEstadoActividad = Constants.NUMERO_CERO
        tipoTipoEstadoModelTodos.descripcion = Constants.SEGMENTO_TODOS
        filtro.add(0, tipoTipoEstadoModelTodos)
        view.mostrarFiltroEstados(filtro)
    }

    private fun prepararFiltroTipoPedido(list: List<TipoPedidoModel>) {
        val filtro = list.toMutableList()
        val filtroTipoPedidoModelTodos = TipoPedidoModel()
        filtroTipoPedidoModelTodos.idPedido = Constants.NUMERO_DOS
        filtroTipoPedidoModelTodos.descripcion = Constants.SEGMENTO_TODOS
        filtro.add(0, filtroTipoPedidoModelTodos)
        view.mostrarFiltroPedidos(filtro)
    }

    private fun prepararFiltroTipoAutorizado() {
        val list = listOf<TipoAutorizadoModel>()
        val filtro = list.toMutableList()
        filtro.add(0, TipoAutorizadoModel(0, Constants.SEGMENTO_TODOS))
        filtro.add(1, TipoAutorizadoModel(1, Constants.YES))
        filtro.add(2, TipoAutorizadoModel(2, Constants.NO))
        view.mostrarFiltroAutorizado(filtro)
    }

    private fun prepararFiltroTipoSaldo(list: List<TipoSaldoModel>) {
        val filtro = list.toMutableList()
        val filtroTipoSaldoModelTodos = TipoSaldoModel()
        filtroTipoSaldoModelTodos.idSaldo = Constants.NUMERO_DOS
        filtroTipoSaldoModelTodos.descripcion = Constants.SEGMENTO_TODOS
        filtro.add(0, filtroTipoSaldoModelTodos)
        view.mostrarFiltroSaldos(filtro)
    }

    private fun prepararFiltroTipoSegmento(list: List<TipoSegmentoModel>) {
        val filtro = list.toMutableList()
        val tipoSegmentoModelTodos = TipoSegmentoModel()
        tipoSegmentoModelTodos.descripcion = Constants.SEGMENTO_TODOS
        filtro.add(0, tipoSegmentoModelTodos)
        view.mostrarFiltroSegmentos(filtro)
    }

    private fun prepararFiltroTipo() {
        val filtro = mutableListOf(
            Constants.SEGMENTO_TODOS,
            Constants.HAS_CASH_PAYMENT,
            Constants.HAS_NOT_CASH_PAYMENT
        ).mapIndexed { index, s -> TipoPaymentModel(index, s) }
        view.mostrarFiltroTipo(filtro)
    }

    fun verificarResultados(filtroBusqueda: FiltroConsultoraModel) {

        if (verificarCamposVacios(filtroBusqueda)) {
            view.mostrarMensajeError(R.string.ingresar_al_menos_un_filtro)
            return
        }

        if (verificarCaracteresNombre(filtroBusqueda)) {
            view.mostrarMensajeError(R.string.ingresar_al_menos_tres_caracteres)
            return
        }

        filtroBusqueda.section = obtenerSeccionSE() ?: filtroBusqueda.section

        consultoraUseCase.findSize(
            filtroBusqueda.mapToConsutlantFilter(),
            ConsultorasSizeSubscriber()
        )
    }

    private fun verificarCamposVacios(filtroBusqueda: FiltroConsultoraModel): Boolean {
        val campos = listOf(
            filtroBusqueda.code.isNullOrEmpty(),
            filtroBusqueda.name.isNullOrEmpty(),
            filtroBusqueda.document.isNullOrEmpty(),
            filtroBusqueda.section.isNullOrEmpty(),
            filtroBusqueda.status == null,
            filtroBusqueda.segment.isNullOrEmpty(),
            filtroBusqueda.level.isNullOrEmpty(),
            filtroBusqueda.requested?.takeIf { it != 0 } == null,
            filtroBusqueda.residue == null,
            filtroBusqueda.level.isNullOrEmpty(),
            filtroBusqueda.authorized == null,
            filtroBusqueda.type == null
        )
        return campos.all { it }
    }

    private fun verificarCaracteresNombre(filtroBusqueda: FiltroConsultoraModel): Boolean {
        val nameLength = filtroBusqueda.name?.length ?: 0
        return nameLength in 1..2
    }

    private fun mensajeResultadoPorRol(count: Int) {
        when (sesion.rol) {
            Rol.SOCIA_EMPRESARIA -> view.mostrarMensajeResultadosSE(count)
            else -> view.mostrarMensajeResultadosGZ(count)
        }
    }

    private inner class CampaniaActualSubscriber : BaseSingleObserver<Campania>() {
        override fun onSuccess(t: Campania) {
            val campania = t.nombreCorto
            when (t.periodo) {
                Campania.Periodo.VENTA -> view.mostrarCampaniaVenta(campania)
                Campania.Periodo.FACTURACION -> view.mostrarCampaniaFacturacion(campania)
            }
        }
    }

    private inner class SeccionesSubscriber : BaseObserver<List<Seccion>>() {
        override fun onNext(t: List<Seccion>) {
            val list = seccionMapper.transform(t).toMutableList()
            list.add(0, SeccionModel())
            view.mostrarFiltroSeccion(list)
        }
    }

    private inner class FiltrosBusquedaSubscriber : BaseObserver<FiltrosBusqueda>() {
        override fun onNext(t: FiltrosBusqueda) {
            val filtros = filtrosBusquedaMapper.parse(t)
            prepararFiltroTipoEstado(filtros.tipoEstadoList)
            prepararFiltroTipoPedido(filtros.tipoPedidoList)
            prepararFiltroTipoSaldo(filtros.tipoSaldoList)
            prepararFiltroTipoSegmento(filtros.tipoSegmentoList)
            prepararFiltroTipoAutorizado()
            prepararFiltroTipo()
        }
    }

    private inner class ConsultorasSizeSubscriber : BaseObserver<Int>() {
        override fun onNext(t: Int) {
            if (t > 0) {
                mensajeResultadoPorRol(t)
                view.mostrarResultados()
                view.limpiarMensajeError()
            } else {
                view.mostrarMensajeError(R.string.text_no_se_encontraron_resultados_en_busqueda)
            }
        }
    }

    fun destroy() {
        consultoraUseCase.dispose()
        campaniaUsecase.dispose()
        seccionesUseCase.dispose()
        obtenerFiltrosBusquedaUseCase.dispose()
    }
}
