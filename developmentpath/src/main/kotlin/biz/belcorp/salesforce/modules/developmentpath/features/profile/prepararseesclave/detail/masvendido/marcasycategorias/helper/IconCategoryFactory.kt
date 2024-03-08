package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias.helper

import androidx.annotation.DrawableRes
import biz.belcorp.salesforce.modules.developmentpath.R

object IconCategoryFactory {

    private const val CONST_MAKE_UP = "Maquillaje"
    private const val CONST_FRAGRANCES = "Fragancias"
    private const val CONST_FACIAL_TREATMENT = "Tratamiento facial"

    @DrawableRes
    fun from(iconId: Int): Int = when (iconId) {
        CategoriasIconIdentifier.PRODUCTOS -> R.drawable.ic_category_productos
        CategoriasIconIdentifier.FRAGANCIAS -> R.drawable.ic_category_fragancias
        CategoriasIconIdentifier.MAQUILLAJE -> R.drawable.ic_category_maquillaje
        CategoriasIconIdentifier.TRATAMIENTO_FACIAL -> R.drawable.ic_category_tratamiento_facial
        CategoriasIconIdentifier.TRATAMIENTO_CORPORAL -> R.drawable.ic_category_tratamiento_corporal
        CategoriasIconIdentifier.CUIDADO_DE_PIEL -> R.drawable.ic_category_cuidado_depiel
        CategoriasIconIdentifier.BIJOUTERIE -> R.drawable.ic_category_bijouterie
        else -> error("Codigo de categoria no soportado")
    }

    @DrawableRes
    fun from(category: String) = with(category) {
        when {
            isMakeUp() -> R.drawable.ic_category_maquillaje
            isFragrances() -> R.drawable.ic_category_fragancias
            isFacialTreatment() -> R.drawable.ic_category_tratamiento_facial
            else -> R.drawable.ic_category_tratamiento_facial
        }
    }

    private fun String.isMakeUp() = this.equals(CONST_MAKE_UP, ignoreCase = true)

    private fun String.isFragrances() = this.equals(CONST_FRAGRANCES, ignoreCase = true)

    private fun String.isFacialTreatment() = this.equals(CONST_FACIAL_TREATMENT, ignoreCase = true)

}
