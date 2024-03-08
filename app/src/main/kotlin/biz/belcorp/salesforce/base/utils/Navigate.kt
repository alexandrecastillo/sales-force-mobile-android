package biz.belcorp.salesforce.base.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import biz.belcorp.salesforce.base.R

fun Fragment.navigateTo(@IdRes resId: Int, args: Bundle? = null) {
    findNavController().navigateSafe(resId, args)
}

fun Fragment.navigateToBilling(args: Bundle? = null) {
    navigateTo(R.id.globalToBilling, args)
}

fun Fragment.navigateToDevelopmentPath(args: Bundle? = null) {
    navigateTo(R.id.globalToDevelopmentPathFragment, args)
}
