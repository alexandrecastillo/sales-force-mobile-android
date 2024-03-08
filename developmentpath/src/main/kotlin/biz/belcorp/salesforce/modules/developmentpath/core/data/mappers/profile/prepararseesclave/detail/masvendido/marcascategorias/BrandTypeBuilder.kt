package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.marcascategorias

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.BrandType

object BrandTypeBuilder {

    fun getType(mark: String) = when {
        mark.isEsikaBrand() -> BrandType.ESIKA
        mark.isLbelBrand() -> BrandType.LBEL
        mark.isCyzoneBrand() -> BrandType.CYZONE
        mark.isNoneBrand() -> BrandType.NONE
        else -> error("Marca no encontrada")
    }

    fun getType(markTypeInt: Int): String = when(markTypeInt) {
        1 ->  "Ésika"
        2 -> "L’Bel"
        3-> "Cyzone"
        else -> error("Marca no encontrada")
    }

    private fun String.isEsikaBrand() = this.equals(CONST_BRAND_ESIKA, ignoreCase = true)
    private fun String.isLbelBrand() = this.equals(CONST_BRAND_LBEL, ignoreCase = true)
    private fun String.isCyzoneBrand() = this.equals(CONST_BRAND_CYZONE, ignoreCase = true)
    private fun String.isNoneBrand() = this.equals(CONST_BRAND_NONE, ignoreCase = true)

    private const val CONST_BRAND_ESIKA = "ESIKA"
    private const val CONST_BRAND_LBEL = "LBEL"
    private const val CONST_BRAND_CYZONE = "CYZONE"
    private const val CONST_BRAND_NONE = "NONE"
}
