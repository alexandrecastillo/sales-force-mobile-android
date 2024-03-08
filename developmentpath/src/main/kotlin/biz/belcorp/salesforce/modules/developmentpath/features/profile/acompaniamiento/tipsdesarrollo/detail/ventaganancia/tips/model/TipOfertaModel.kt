package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.model

class TipOfertaModel(
    var descripcion: String? = "",
    var colores: List<Int> = emptyList(),
    var data: List<OfertaModel> = emptyList()
) : Comparable<TipOfertaModel> {
    override fun compareTo(other: TipOfertaModel): Int = 0
}
