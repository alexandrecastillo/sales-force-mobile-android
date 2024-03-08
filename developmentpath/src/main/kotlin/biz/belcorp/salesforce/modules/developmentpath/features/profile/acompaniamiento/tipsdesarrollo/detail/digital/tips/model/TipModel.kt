package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model

data class TipModel(
    val id: Long = 0,
    val descripcion: String? = null,
    val colores: List<Int> = emptyList(),
    val orden: Int = -1
) : Comparable<TipModel> {
    override fun compareTo(other: TipModel): Int = 0
}
