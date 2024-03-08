package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.filtergrid.types

import androidx.annotation.StringDef

@Retention(AnnotationRetention.RUNTIME)
@StringDef(
    NewCycleGridType.LOW_VALUE,
    NewCycleGridType.HIGH_VALUE
)
annotation class NewCycleGridType {
    companion object {
        const val HIGH_VALUE = "key_filter_high_value"
        const val LOW_VALUE = "key_filter_low_value"
    }
}
