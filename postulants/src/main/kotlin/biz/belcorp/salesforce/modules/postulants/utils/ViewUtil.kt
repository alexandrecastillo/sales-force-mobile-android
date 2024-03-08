package biz.belcorp.salesforce.modules.postulants.utils

import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.text.format.DateUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import biz.belcorp.salesforce.core.utils.emptyIfNull
import biz.belcorp.salesforce.modules.postulants.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
fun TextView.setHtmlText(html: String) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(html)
    }
}

fun View.cambiarVisibilidad() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}


fun ImageView.cargarImagen(ruta: String, placeholer: Int = -1, error: Int = -1) {
    var glide = Glide.with(context)
    if (placeholer != -1 && error != -1) {
        glide = glide.setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_upload_image)
                .error(error)
        )
    }

    glide.load(ruta)
        .thumbnail(0.1f)
        .into(this)
}

fun EditText.onTextChanged(onTextWatcher: (Editable) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable) {
            onTextWatcher.invoke(s)
        }
    })
}

fun String.toUpperCase(): String = this.toUpperCase(Locale.getDefault())
fun String.toLowerCase(): String = this.toLowerCase(Locale.getDefault())

fun TextView.setBlackFont() {
    this.typeface =
        ResourcesCompat.getFont(this.context, biz.belcorp.salesforce.base.R.font.mulish_black)
}

fun TextView.setBookFont() {
    this.typeface =
        ResourcesCompat.getFont(this.context, biz.belcorp.salesforce.base.R.font.mulish_regular)
}

fun String.formatPubDate(pubDate: String): String {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val date = dateFormat.parse(pubDate)
    val flags =
        DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_ABBREV_MONTH

    return DateUtils.getRelativeTimeSpanString(
        date.time, System.currentTimeMillis(),
        DateUtils.MINUTE_IN_MILLIS, flags
    ).toString()
}

fun String.isNullOrEmptyStringResult(stringTrue: String?): String {
    return if (this.isNullOrEmpty()) {
        stringTrue.emptyIfNull()
    } else {
        this
    }
}
