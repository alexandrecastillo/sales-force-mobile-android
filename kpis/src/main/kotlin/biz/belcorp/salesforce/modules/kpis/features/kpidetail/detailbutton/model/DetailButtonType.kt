package biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.model

import androidx.annotation.IntDef

@Retention(AnnotationRetention.RUNTIME)
@IntDef(
    DetailButtonType.NONE,
    DetailButtonType.CONSULTANT,
    DetailButtonType.BILLING
)
annotation class DetailButtonType {
    companion object {
        const val NONE = -1
        const val CONSULTANT = 1
        const val BILLING = 2
    }
}

