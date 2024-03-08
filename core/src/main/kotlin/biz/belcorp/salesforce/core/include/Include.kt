package biz.belcorp.salesforce.core.include

import androidx.annotation.IntDef

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    Include.DASHBOARD_KPIS,
    Include.CONSULTANTS_QUANTITY,
    Include.BILLING_ADVANCEMENT,
    Include.LEGACY_CONSULTANT_LIST,
    Include.PROFILE
)
annotation class Include {

    companion object {

        const val DASHBOARD_KPIS = 1
        const val CONSULTANTS_QUANTITY = 4
        const val BILLING_ADVANCEMENT = 5
        const val LEGACY_CONSULTANT_LIST = 7

        const val PROFILE = 8
    }

}
