package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias

import androidx.annotation.IntDef

class Brand(@BrandType val brandType: Int, var average: Double)

@Retention(AnnotationRetention.RUNTIME)
@IntDef(
    BrandType.NONE,
    BrandType.ESIKA,
    BrandType.LBEL,
    BrandType.CYZONE
)

annotation class BrandType {
    companion object {
        const val NONE = 0
        const val ESIKA = 1
        const val LBEL = 2
        const val CYZONE = 3
    }
}

