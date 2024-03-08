package biz.belcorp.salesforce.core.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import biz.belcorp.salesforce.core.constants.Constant

fun String.highlight(text: String): SpannableStringBuilder {

    val array = text.split("\\s+".toRegex())

    val spannableBuilder = SpannableStringBuilder(this)

    try {
        array.forEach { item ->
            val startIndex = indexOf(item, ignoreCase = true)
                .takeIf { it != -1 }
            val endIndex = startIndex?.plus(item.length)
            if (startIndex != null && endIndex != null) {
                spannableBuilder.setSpan(
                    StyleSpan(Typeface.BOLD),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

            }
        }
    } catch (e: Throwable) {
        e.printStackTrace()
    }

    return spannableBuilder

}

fun spannable(func: () -> SpannableString) = func()
fun span(text: String) = SpannableString(text)
fun span(text: String, any: Any) = SpannableString(text)
    .apply { setSpan(any, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }

fun span(text: String, vararg args: Any) = SpannableString(text)
    .apply { args.forEach { setSpan(it, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)  } }

fun bold(text: String) = span(text, StyleSpan(Typeface.BOLD))
fun bold(text: SpannableString) = span(text.toString(), StyleSpan(Typeface.BOLD))
fun italic(text: String) = span(text, StyleSpan(Typeface.ITALIC))
fun italic(text: SpannableString) = span(text.toString(), StyleSpan(Typeface.ITALIC))
fun color(text: String, color: Int) = span(text, ForegroundColorSpan(color))
fun color(text: SpannableString, color: Int) = span(text.toString(), ForegroundColorSpan(color))
fun space() = SpannableString(Constant.BLANK_SPACE)

operator fun SpannableString.plus(s: SpannableString) = SpannableString(TextUtils.concat(this, s))
