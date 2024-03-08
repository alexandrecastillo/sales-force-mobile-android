package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.view

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.model.HabilidadAsignadaDetalleModel

interface DetalleHabilidadesView {

    fun cargar(habilidadAsignadaDetalles: List<HabilidadAsignadaDetalleModel>)
    fun mostrarSinHabilidades(mostrar: Boolean)
    fun habilitarAsignacion(habilitar: Boolean)
    fun mostrarDetalleHabilidades(mostrar: Boolean)
}
