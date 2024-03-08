package biz.belcorp.salesforce.core.utils.kraph.lang.relay

import biz.belcorp.salesforce.core.utils.kraph.lang.Field
import biz.belcorp.salesforce.core.utils.kraph.lang.SelectionSet


internal class Node(name: String, additionalFields: List<Field>) : Field(name) {
    init {
        selectionSet = SelectionSet(additionalFields.toMutableList().apply {
           add(0, Field("id"))
        })
    }
}
