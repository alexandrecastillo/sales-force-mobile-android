package biz.belcorp.salesforce.modules.developmentpath.utils

import android.text.TextUtils

object FormUtils {

    @JvmStatic
    fun isValidEmail(target: String): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}
