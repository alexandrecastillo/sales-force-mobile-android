package biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section

import androidx.annotation.DrawableRes

data class OtraMarcaModel(
    @DrawableRes val logo: Int,
    @DrawableRes val logoUnChecked: Int,
    val marcaDetalleId: Long,
    val marcaId: Long,
    val consultoraId: Long,
    var codigoRegion: String,
    var codigoZona: String,
    var codigoSeccion: String,
    val name: String?,
    var iconoId: Int,
    var orden: Int,
    val showFront: Boolean,
    var checked: Boolean,
    var categoria: String,
    var campania: String
)
