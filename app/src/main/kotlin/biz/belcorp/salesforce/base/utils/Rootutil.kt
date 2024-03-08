package biz.belcorp.salesforce.base.utils

import android.content.Context
import biz.belcorp.salesforce.base.BuildConfig
import com.scottyab.rootbeer.RootBeer

class Rootutil {
    fun isRooted(context: Context): Boolean {
        return if (BuildConfig.DEBUG) false
        else RootBeer(context).isRooted
    }
}
