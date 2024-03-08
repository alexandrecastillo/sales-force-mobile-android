package biz.belcorp.salesforce.core.utils

import android.content.Context
import android.view.View
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment

fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()
fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()
fun Context.sp(value: Float): Int = (value * resources.displayMetrics.scaledDensity).toInt()
fun Context.px2dip(px: Int): Float = px.toFloat() / resources.displayMetrics.density
fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity
fun Context.dimen(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)

fun View.dip(value: Int): Int = context.dip(value)
fun View.dip(value: Float): Int = context.dip(value)
fun View.sp(value: Int): Int = context.sp(value)
fun View.sp(value: Float): Int = context.sp(value)
fun View.px2dip(px: Int): Float = context.px2dip(px)
fun View.px2sp(px: Int): Float = context.px2sp(px)
fun View.dimen(@DimenRes resource: Int): Int = context.dimen(resource)

fun Fragment.dip(value: Int): Int = activity?.dip(value) ?: 0
fun Fragment.dip(value: Float): Int = activity?.dip(value) ?: 0
fun Fragment.sp(value: Int): Int = activity?.sp(value) ?: 0
fun Fragment.sp(value: Float): Int = activity?.sp(value) ?: 0
fun Fragment.px2dip(px: Int): Float = activity?.px2dip(px) ?: 0F
fun Fragment.px2sp(px: Int): Float = activity?.px2sp(px) ?: 0F
fun Fragment.dimen(resource: Int): Int = activity?.dimen(resource) ?: 0
