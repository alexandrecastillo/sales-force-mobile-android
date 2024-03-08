package biz.belcorp.salesforce.core.domain.usecases.features

import androidx.annotation.StringDef


@Retention(AnnotationRetention.SOURCE)
@StringDef(
    DynamicFeature.AUTH,
    DynamicFeature.BILLING,
    DynamicFeature.BRIGHTPATH,
    DynamicFeature.CONSULTANTS,
    DynamicFeature.CREDIT_INQUIRY,
    DynamicFeature.DEVELOPMENT_MATERIAL,
    DynamicFeature.DEVELOPMENT_PATH,
    DynamicFeature.KPIS,
    DynamicFeature.ORDERS,
    DynamicFeature.POSTULANTS,
    DynamicFeature.VIRTUALMETHODOLOGY,
    DynamicFeature.POLITICS_TERMS_CONDITIONS,
    DynamicFeature.CALCULATOR,
    DynamicFeature.INSPIRES
)
annotation class DynamicFeature {

    companion object {

        const val AUTH = "auth"
        const val BILLING = "billing"
        const val BRIGHTPATH = "brightpath"
        const val CONSULTANTS = "consultants"
        const val CREDIT_INQUIRY = "creditinquiry"
        const val DEVELOPMENT_MATERIAL = "developmentmaterial"
        const val DEVELOPMENT_PATH = "developmentpath"
        const val KPIS = "kpis"
        const val ORDERS = "orders"
        const val POSTULANTS = "postulants"
        const val VIRTUALMETHODOLOGY = "virtualmethodology"
        const val CALCULATOR = "calculator"
        const val POLITICS_TERMS_CONDITIONS = "termsconditions"
        const val DIGITAL = "digital"
        const val INSPIRES = "inspires"

        fun values(): List<String> {
            return listOf(
                AUTH,
                BILLING,
                BRIGHTPATH,
                CONSULTANTS,
                CREDIT_INQUIRY,
                DEVELOPMENT_MATERIAL,
                DEVELOPMENT_PATH,
                KPIS,
                ORDERS,
                POSTULANTS,
                VIRTUALMETHODOLOGY,
                CALCULATOR,
                POLITICS_TERMS_CONDITIONS,
                DIGITAL,
                INSPIRES
            )
        }

    }

}
