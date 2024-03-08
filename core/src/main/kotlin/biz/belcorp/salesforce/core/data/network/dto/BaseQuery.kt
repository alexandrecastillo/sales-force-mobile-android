package biz.belcorp.salesforce.core.data.network.dto

import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.core.utils.kraph.types.KraphVariable

abstract class BaseQuery {
    protected abstract val keyFunctionName: String
    protected abstract val keyCollection: String
    protected abstract val keyFilter: String
    protected abstract val keyFilterType: String
    protected abstract val keyInput: String

    abstract fun get(): Kraph
    protected abstract fun getJson(): String

    protected open fun Kraph.FieldBuilder.getInput(): KraphVariable {
        return variable(keyFilter, keyFilterType, getJson())
    }

}
