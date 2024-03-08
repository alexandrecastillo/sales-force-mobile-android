package biz.belcorp.salesforce.core.utils.kraph.lang.relay

import biz.belcorp.salesforce.core.utils.kraph.lang.Argument
import biz.belcorp.salesforce.core.utils.kraph.lang.Field
import biz.belcorp.salesforce.core.utils.kraph.lang.SelectionSet


internal class CursorConnection(
    name: String,
    argument: Argument,
    selectionSet: SelectionSet
) : Field(name, arguments = argument, selectionSet = selectionSet)

internal class Edges(
    nodeSelectionSet: SelectionSet,
    additionalField: List<Field> = listOf()
) : Field("edges", selectionSet = SelectionSet(listOf(Field("node", selectionSet = nodeSelectionSet)) + additionalField))

internal class PageInfo (pageSelectionSet: SelectionSet) : Field("pageInfo", selectionSet = pageSelectionSet)
