package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.model

data class MetaSocialModeloContendor(
    var metas: List<MetaSociaModelo> = emptyList(),
    val limiteMetas: Int
) {

    val puedeCrearMeta get() = metas.size < limiteMetas

    val contadorMetas get() = String.format("%d/%d", metas.size, limiteMetas)

    companion object {

        private const val LIMITE = 3

        fun crearModelo(): MetaSocialModeloContendor {
            return MetaSocialModeloContendor(limiteMetas = LIMITE)
        }
    }
}
