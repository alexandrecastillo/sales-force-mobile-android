package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.busqueda

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.BusquedaViewModel

interface BusquedaView {
    var listener: Listener?

    fun cargar(secciones: List<BusquedaViewModel.Seccion>)

    fun cargarSugerencias(sugerencias: List<String>)

    fun ocultarResultados()

    fun ocultarVacio()

    fun mostrarVacio()

    fun mostrarResultados()

    fun clearSearchBar()

    interface Listener {
        fun alCambiarFiltro(filtro: String)
    }
}
