package biz.belcorp.salesforce.core.domain.entities.terms

class ApproveTermsParams(
    val countryId: String,
    val region: String,
    val zone: String,
    val section: String,
    val role: String,
    val consultantCode: String,
    val userCode: String,
    val documentNumber: String,
    val terms: List<SaveTerms>
) {
    companion object{
        const val LINK_SE = "LINKSE"
        const val TERM = "TC"
        const val PRIVACY = "PP"
        const val PUBLICITY = "PB"
        const val COOKIES = "CO"


        const val TYPE_QUERY_CONSULTANT_CODE = "CC"
        const val TYPE_QUERY_USER_CODE = "CU"
    }
}
