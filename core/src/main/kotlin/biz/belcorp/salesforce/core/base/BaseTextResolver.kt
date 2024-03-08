package biz.belcorp.salesforce.core.base

import android.util.Log
import biz.belcorp.salesforce.core.constants.Constant

abstract class BaseTextResolver {

    protected fun stringFormat(format: String, vararg args: Any?): String {
        var currentFormat = Constant.EMPTY_STRING
        try {
            currentFormat = String.format(format, *args)
        } catch (e: Exception) {
            Log.e("BaseTextResolver", e.localizedMessage.orEmpty())
        }
        return currentFormat
    }
}
