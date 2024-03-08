package biz.belcorp.salesforce.core.domain.entities.configuration

import androidx.annotation.StringDef

@Retention(AnnotationRetention.RUNTIME)
@StringDef(
    MainBrandType.ESIKA,
    MainBrandType.LBEL
)
annotation class MainBrandType {
    companion object {
        const val ESIKA = "esika"
        const val LBEL = "lbel"
    }
}
