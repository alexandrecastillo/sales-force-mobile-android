package biz.belcorp.salesforce.modules.orders.features.search

import biz.belcorp.salesforce.core.base.BasePresenter
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.search.models.TipoEstadoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSaldoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSegmentoModel
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.orders.core.domain.contants.Constants
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.filters.GetSearchFiltersUseCase
import biz.belcorp.salesforce.modules.orders.features.search.mapper.FiltrosBusquedaMapper
import biz.belcorp.salesforce.modules.orders.features.search.model.CampaniaModel
import biz.belcorp.salesforce.modules.orders.features.search.model.SeccionModel
import biz.belcorp.salesforce.modules.orders.features.search.model.TipoFiltroModel
import biz.belcorp.salesforce.modules.orders.features.search.model.TipoOrigenModel
import java.util.*


class FiltrosPedidosWebPresenter(
    private val view: FiltrosPedidosWebView,
    private val sesionUseCase: ObtenerSesionUseCase,
    private val obtenerFiltrosBusquedaUseCase: GetSearchFiltersUseCase,
    private val filtrosBusquedaMapper: FiltrosBusquedaMapper
) : BasePresenter() {

    private val sesion get() = sesionUseCase.obtener()

    fun cargarFiltros() {
        obtenerFiltros()
        view.mostrarFiltrosPrevios()
    }

    private fun obtenerFiltros() {
        doAsync {

            val t = obtenerFiltrosBusquedaUseCase.getFilters()
            val filtros = filtrosBusquedaMapper.map(t)

            uiThread {
                prepararFiltroTipoEstado(filtros.tipoEstadoList)
                prepararFiltroTipoSaldo(filtros.tipoSaldoList)
                prepararFiltroTipoSegmento(filtros.tipoSegmentoList)
                prepararFiltroSecciones(filtros.seccionesList)
                prepararFiltroCampanias(filtros.campaniasList)
                prepararFiltroTipoOrigen()
                prepararFiltroTipoFiltro()
            }
        }
    }

    private fun prepararFiltroCampanias(list: Collection<CampaniaModel>) {
        val filtro = list.toMutableList()
        filtro.firstOrNull()?.also { view.mostrarCampaniaDefecto(it) }
        view.mostrarFiltroCampania(filtro)
    }

    private fun prepararFiltroTipoFiltro() {
        when (sesion.rol) {
            Rol.SOCIA_EMPRESARIA -> {
                val filtro = mutableListOf<TipoFiltroModel>()
                filtro.add(
                    TipoFiltroModel(
                        TipoFiltroModel.NO_VALIDADOS_CODIGO,
                        TipoFiltroModel.NO_VALIDADOS
                    )
                )
                filtro.add(
                    TipoFiltroModel(
                        TipoFiltroModel.VALIDADOS_CODIGO,
                        TipoFiltroModel.VALIDADOS
                    )
                )
                filtro.add(
                    TipoFiltroModel(
                        TipoFiltroModel.RECHAZADOS_CODIGO,
                        TipoFiltroModel.RECHAZADOS
                    )
                )
                filtro.add(
                    TipoFiltroModel(
                        TipoFiltroModel.FACTURADOS_CODIGO,
                        TipoFiltroModel.FACTURADOS
                    )
                )
                filtro.firstOrNull()?.also { view.mostrarTipoFiltroDefecto(it) }
                view.mostrarTipoFiltro(filtro)
            }
            else -> view.ocultarTipoFiltro()
        }
    }

    private fun prepararFiltroSecciones(list: Collection<SeccionModel>) {
        if (list.isNotEmpty()) {
            val filtro = list.toMutableList()
            filtro.add(0, SeccionModel())
            view.mostrarFiltroSeccion(filtro)
        } else {
            view.ocultarFiltroSeccion()
        }
    }

    private fun prepararFiltroTipoEstado(list: List<TipoEstadoModel>) {
        val filtro = list.toMutableList()
        filtro.add(0, TipoEstadoModel())
        view.mostrarFiltroEstados(filtro)
    }

    private fun prepararFiltroTipoSaldo(list: List<TipoSaldoModel>) {
        val filtro = list.toMutableList()
        filtro.add(0, TipoSaldoModel())
        view.mostrarFiltroSaldos(filtro)
    }

    private fun prepararFiltroTipoSegmento(list: List<TipoSegmentoModel>) {
        val filtro = list.toMutableList()
        filtro.add(0, TipoSegmentoModel())
        view.mostrarFiltroSegmentos(filtro)
    }

    private fun prepararFiltroTipoOrigen() {
        val filtro = mutableListOf<TipoOrigenModel>()
        filtro.add(TipoOrigenModel())
        filtro.add(TipoOrigenModel(Constants.ORIGEN_WEB_CODIGO, Constants.ORIGEN_WEB_NOMBRE))
        filtro.add(
            TipoOrigenModel(
                Constants.ORIGEN_DIGITACION_CODIGO,
                Constants.ORIGEN_DIGITACION_NOMBRE
            )
        )
        view.mostrarFiltroOrigen(filtro)
    }

    fun seleccionarFechaDesde(fechaSeleccionada: String) {
        val calendar = obtenerFecha(fechaSeleccionada)
        view.abrirDatePickerDesde(calendar.year, calendar.month, calendar.dayOfMonth)
    }

    fun fechaDesdeSeleccionada(year: Int, month: Int, day: Int) {
        val fecha = obtenerFechaString(year, month, day)
        view.mostrarFechaDesdeSeleccionada(fecha)
    }

    fun seleccionarFechaHasta(fechaSeleccionada: String) {
        val calendar = obtenerFecha(fechaSeleccionada)
        view.abrirDatePickerHasta(calendar.year, calendar.month, calendar.dayOfMonth)
    }

    fun fechaHastaSeleccionada(year: Int, month: Int, day: Int) {
        val fecha = obtenerFechaString(year, month, day)
        view.mostrarFechaHastaSeleccionada(fecha)
    }

    private fun obtenerFecha(fecha: String): Calendar {
        return Calendar.getInstance().apply {
            time = fecha.toDate(formatShort2) ?: Date()
        }
    }

    private fun obtenerFechaString(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
        val fecha = calendar.time.string(formatShort2)
        return fecha.orEmpty()
    }

}
