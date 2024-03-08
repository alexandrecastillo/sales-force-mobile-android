package biz.belcorp.salesforce.modules.developmentpath.features.utils

import android.content.Context
import biz.belcorp.salesforce.core.features.utils.CoreTextResolver
import biz.belcorp.salesforce.modules.developmentpath.R

class ModuleTextResolver(context: Context) : CoreTextResolver(context) {

    fun getSuccessfulText(): String {
        return context.getString(R.string.text_successful)
    }

    fun getNotSuccessfulText(): String {
        return context.getString(R.string.text_not_successful)
    }

}
