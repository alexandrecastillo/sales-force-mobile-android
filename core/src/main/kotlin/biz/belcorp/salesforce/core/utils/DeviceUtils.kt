package biz.belcorp.salesforce.core.utils

import android.content.Context

inline val Context.displayMetrics: android.util.DisplayMetrics
    get() = resources.displayMetrics
