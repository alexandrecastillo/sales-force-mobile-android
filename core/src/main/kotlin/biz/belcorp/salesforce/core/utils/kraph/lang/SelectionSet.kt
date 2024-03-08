package biz.belcorp.salesforce.core.utils.kraph.lang


internal class SelectionSet(internal val fields: List<Field>) : GraphQLNode() {

    override fun print(
        format: PrintFormat,
        previousLevel: Int
    ): String {
        val nl = getNewLineString(format)
        level = if (format == PrintFormat.PRETTY) previousLevel + 1 else 0
        val fieldStr = fields.joinToString(nl) { getIndentString(level) + it.print(format, level) }
        return "{${ nl + fieldStr + nl + getIndentString(previousLevel) }}"
    }
}
