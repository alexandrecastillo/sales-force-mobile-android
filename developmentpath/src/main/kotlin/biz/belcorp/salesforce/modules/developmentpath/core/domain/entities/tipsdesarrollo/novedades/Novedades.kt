package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades

data class Novedades(
    val titulo: String,
    var detalleNovedades: List<DetalleNovedades> = emptyList()
)
