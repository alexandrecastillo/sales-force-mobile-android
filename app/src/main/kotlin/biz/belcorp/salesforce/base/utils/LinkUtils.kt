package biz.belcorp.salesforce.base.utils

import android.text.SpannableString
import android.text.style.URLSpan
import android.view.View
import android.widget.TextView

fun TextView.onLinkPressed(onLinkClick: (String) -> Unit) {
    val current = text as SpannableString
    val spans = current.getSpans(0, current.length, URLSpan::class.java)
    for (span in spans) {
        val start = current.getSpanStart(span)
        val end = current.getSpanEnd(span)
        current.removeSpan(span)
        current.setSpan(
            DefensiveURLSpan(span.url, onLinkClick), start, end,
            0
        )
    }
}

private class DefensiveURLSpan(
    private val url: String,
    private val onLinkClick: (String) -> Unit
) : URLSpan(url) {
    override fun onClick(widget: View) {
        onLinkClick(url)
    }
}
