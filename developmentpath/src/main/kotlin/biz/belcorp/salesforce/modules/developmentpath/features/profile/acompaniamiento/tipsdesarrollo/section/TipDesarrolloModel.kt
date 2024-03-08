package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general.TipData

data class TipDesarrolloModel(
    val id: Int,
    val imagen: Int,
    val tipoImagen: TipData.TipoImagen,
    val tituto: String,
    val posicion: Int,
    val tipo: TipData.Tipo,
    val opciones: List<String>,
    val detalles: List<Detalle>
) {
    data class Detalle(
        val descripcion: String,
        val colores: List<Int>
    )
}
