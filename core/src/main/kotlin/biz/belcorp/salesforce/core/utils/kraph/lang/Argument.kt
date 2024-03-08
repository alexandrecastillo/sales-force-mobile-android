package biz.belcorp.salesforce.core.utils.kraph.lang


internal open class Argument(internal val args: Map<String, Any> = mapOf()) : GraphQLNode() {
    override fun print(
        format: PrintFormat,
        previousLevel: Int
    ): String {
        return "(${print(args, format)})"
    }
}
