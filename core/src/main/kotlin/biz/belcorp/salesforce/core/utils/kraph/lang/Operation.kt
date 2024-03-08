package biz.belcorp.salesforce.core.utils.kraph.lang

import java.util.*


internal class Operation(
    private val type: OperationType,
    internal val selectionSet: SelectionSet,
    internal val name: String? = null,
    internal val arguments: Argument? = null
) : GraphQLNode() {
    override fun print(
        format: PrintFormat,
        previousLevel: Int
    ): String {
        val namePart = name?.let{ " $it" } ?: ""
        val argumentPart = arguments?.print(format, previousLevel)?.let{ " $it" } ?: ""
        return "${type.name.toLowerCase(Locale.getDefault())}$namePart$argumentPart ${selectionSet.print(format, previousLevel)}"
    }
}

internal enum class OperationType {
    QUERY,
    MUTATION
}
