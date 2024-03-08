package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.ventaganancia

data class VentaGananciaModel(
    val ventaMonto: ValorModel? = null,
    val ventaDescripcion: ValorModel? = null,
    val gananciaMonto: ValorModel? = null,
    val gananciaDescripcion: ValorModel? = null
) {

    val esValido
        get() = ventaMonto != null && ventaDescripcion != null
            && gananciaMonto != null && gananciaDescripcion != null

    data class ValorModel(val texto: String?, val colores: List<Int> = emptyList())
}

