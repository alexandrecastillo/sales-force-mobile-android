package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.filtergrid.types

import androidx.annotation.StringDef

@Retention(AnnotationRetention.RUNTIME)
@StringDef(
    CapitalizationGridType.PEGS,
    CapitalizationGridType.CAPITALIZATION
)
annotation class CapitalizationGridType {
    companion object {
        const val PEGS = "key_filter_pegs"
        const val CAPITALIZATION = "key_filter_capitalization"
    }
}
