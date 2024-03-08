package biz.belcorp.salesforce.core.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Patterns
import android.webkit.URLUtil
import androidx.core.content.FileProvider.getUriForFile
import biz.belcorp.salesforce.core.constants.Constant
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

fun Context.shareText(text: String) {
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(Intent.EXTRA_TEXT, text)
    sendIntent.type = "text/plain"
    startActivity(sendIntent)
}

fun Context.shareImageUri(uri: Uri) {
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.type = "image/png"
    startActivity(intent)
}

fun Context.bitmapToUri(image: Bitmap, applicationId: String): Uri? {
    val imagesFolder = File(filesDir, "images")
    var uri: Uri? = null
    try {
        imagesFolder.mkdirs()
        val file = File(imagesFolder, "share_image_" + System.currentTimeMillis() + ".png")
        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, 90, stream)
        stream.flush()
        stream.close()
        uri = getUriForFile(this, "${applicationId}.FileProvider", file)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return uri
}

fun Context.openLink(url: String) {
    openLink(url) { }
}

fun Context.openLink(url: String, actionError: () -> Unit) {
    if (!url.validateUrl()) {
        actionError.invoke()
        return
    }
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        startActivity(Intent.createChooser(intent, null))
    }
}

private fun String?.validateUrl(): Boolean {
    if (this.isNullOrEmpty())
        return false
    var isURL = Patterns.WEB_URL.matcher(this).matches()
    if (!isURL) {
        val urlString = this + Constant.EMPTY_STRING
        if (URLUtil.isNetworkUrl(urlString)) {
            URL(urlString)
            isURL = true
        }
    }
    return isURL
}
