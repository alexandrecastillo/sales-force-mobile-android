package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.listar

import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.model.MetaSociaModelo

interface ListarMetaSociaView {
    fun mostrarDatos(metas: List<MetaSociaModelo>)
    fun ocultarDescripcionNoHayMetas()
    fun mostrarDescripcionNoHayMetas()
    fun ocultarContenedorMetas()
    fun mostrarContenedorMetas()
    fun ocultarBotonCrear()
    fun mostrarBotonCrear()
    fun mostrarContadorMetas()
    fun ocultarContadorMetas()
    fun cargarTotalContadorMetas(indicador: String)
    fun notificarExitoAlEliminar()
    fun mostrarMensajeError()
}
