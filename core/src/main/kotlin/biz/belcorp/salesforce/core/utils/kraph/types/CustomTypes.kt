package biz.belcorp.salesforce.core.utils.kraph.types


data class KraphVariableType(val value: String)

data class KraphVariable(val name: String, val type: KraphVariableType, val jsonValue: String) {
    val dollarName: String
        get() = "\$$name"
}
