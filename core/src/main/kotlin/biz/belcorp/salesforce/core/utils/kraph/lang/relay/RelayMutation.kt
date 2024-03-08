package biz.belcorp.salesforce.core.utils.kraph.lang.relay

import biz.belcorp.salesforce.core.utils.kraph.lang.Field
import biz.belcorp.salesforce.core.utils.kraph.lang.SelectionSet


internal class Mutation(name: String, arguments: InputArgument, selectionSet: SelectionSet) : Field(name, arguments, selectionSet)
