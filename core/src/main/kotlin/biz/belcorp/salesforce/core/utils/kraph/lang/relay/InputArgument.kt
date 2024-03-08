package biz.belcorp.salesforce.core.utils.kraph.lang.relay

import biz.belcorp.salesforce.core.utils.kraph.lang.Argument
import biz.belcorp.salesforce.core.utils.kraph.lang.PrintFormat


internal class InputArgument(args: Map<String, Any>) : Argument(args) {
    override fun print(
        format: PrintFormat,
        previousLevel: Int
    ): String {
        return "(input: { ${print(args, format) } })"
    }
}
