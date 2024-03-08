package biz.belcorp.salesforce.core.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: Int) = context?.toast(message)

fun Fragment.toast(message: CharSequence) = context?.toast(message)

fun Fragment.longToast(message: Int) = context?.longToast(message)

fun Fragment.longToast(message: CharSequence) = context?.longToast(message)

fun Context.toast(message: Int): Toast = Toast
    .makeText(this, message, Toast.LENGTH_SHORT)
    .apply { show() }

fun Context.toast(message: CharSequence): Toast = Toast
    .makeText(this, message, Toast.LENGTH_SHORT)
    .apply { show() }

fun Context.longToast(message: Int): Toast = Toast
    .makeText(this, message, Toast.LENGTH_LONG)
    .apply { show() }

fun Context.longToast(message: CharSequence): Toast = Toast
    .makeText(this, message, Toast.LENGTH_LONG)
    .apply { show() }
