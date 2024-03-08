package biz.belcorp.salesforce.modules.orders.features.search


import biz.belcorp.salesforce.core.features.search.models.TipoEstadoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSaldoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSegmentoModel
import biz.belcorp.salesforce.modules.orders.features.search.model.CampaniaModel
import biz.belcorp.salesforce.modules.orders.features.search.model.SeccionModel
import biz.belcorp.salesforce.modules.orders.features.search.model.TipoFiltroModel
import biz.belcorp.salesforce.modules.orders.features.search.model.TipoOrigenModel


interface FiltrosPedidosWebView {

    fun mostrarCampaniaDefecto(item: CampaniaModel)

    fun mostrarFiltroCampania(items: List<CampaniaModel>)

    fun mostrarTipoFiltroDefecto(item: TipoFiltroModel)

    fun mostrarTipoFiltro(items: List<TipoFiltroModel>)

    fun mostrarFiltroSeccion(items: List<SeccionModel>)

    fun mostrarFiltroEstados(items: List<TipoEstadoModel>)

    fun mostrarFiltroSaldos(items: List<TipoSaldoModel>)

    fun mostrarFiltroSegmentos(items: List<TipoSegmentoModel>)

    fun mostrarFiltroOrigen(items: List<TipoOrigenModel>)

    fun mostrarFiltrosPrevios()

    fun ocultarFiltroSeccion()

    fun ocultarTipoFiltro()

    fun abrirDatePickerDesde(year: Int, month: Int, day: Int)

    fun abrirDatePickerHasta(year: Int, month: Int, day: Int)

    fun mostrarFechaDesdeSeleccionada(fecha: String?)

    fun mostrarFechaHastaSeleccionada(fecha: String?)

}
