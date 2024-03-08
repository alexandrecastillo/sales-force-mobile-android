package biz.belcorp.salesforce.core.utils

import android.content.Context
import android.content.pm.PackageManager
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING


object PackageUtils {

    fun versionName(context: Context): String {
        var versionName: String = EMPTY_STRING
        try {
            versionName = context.packageManager
                .getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionName
    }
}
