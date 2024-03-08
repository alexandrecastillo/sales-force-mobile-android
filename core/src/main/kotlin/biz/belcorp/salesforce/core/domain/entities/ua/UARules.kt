package biz.belcorp.salesforce.core.domain.entities.ua

object UARules {

    private const val UA_REGION_1 = "00"
    private const val UA_REGION_2 = "99"
    private const val UA_REGION_3 = "90"
    private const val UA_REGION_4 = "0000"
    private const val UA_REGION_5 = "0001"
    private const val UA_REGION_6 = "88"

    val UA_REGIONS_EXCLUDED =
        listOf(UA_REGION_1, UA_REGION_2, UA_REGION_3, UA_REGION_4, UA_REGION_5, UA_REGION_6)

}
