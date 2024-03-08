package biz.belcorp.salesforce.core.utils

import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.io.File
import java.util.*

class DocumentHelper(private val context: Context) {

    companion object {
        const val SLASH = "/"
        const val MIME_TYPE = "application/pdf"
    }

    private var onOpenDocumentError: ((e: Exception) -> Unit)? = null
    private var onDownloadDocumentError: ((e: Exception) -> Unit)? = null

    fun setOnOpenErrorListener(onError: (e: Exception) -> Unit) {
        this.onOpenDocumentError = onError
    }

    fun setOnDownloadErrorListener(onError: (e: Exception) -> Unit) {
        this.onDownloadDocumentError = onError
    }

    fun downloadDocument(fileUrl: String?) {
        if (fileUrl == null) return
        if (context.checkWritePermission()) {
            startDownloadFile(fileUrl)
        } else {
            context.requestWritePermission()
        }
    }

    fun openDocument(fileUrl: String?) {
        if (fileUrl == null) return
        try {
            val uri = Uri.parse(fileUrl)
            val intent = Intent(Intent.ACTION_VIEW)
            if (fileUrl.toLowerCase(Locale.getDefault()).contains(".pdf")) {
                intent.setDataAndType(uri, "application/pdf")
            } else {
                intent.data = uri
            }
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            onOpenDocumentError?.invoke(e)
        }
    }

    private fun getFilename(fileUrl: String): String {
        return fileUrl.substring(fileUrl.lastIndexOf(SLASH) + 1, fileUrl.length)
    }

    private fun startDownloadFile(fileUrl: String) {
        try {
            val request = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                requestForAndroidQandAbove(fileUrl)
            } else {
                requestForAndroidPandBellow(fileUrl)
            }
            val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)

        } catch (e: Exception) {
            onDownloadDocumentError?.invoke(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestForAndroidQandAbove(fileUrl: String): DownloadManager.Request {

        val filename = getFilename(fileUrl)

        val resolver = context.contentResolver

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, MIME_TYPE)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        return DownloadManager.Request(Uri.parse(fileUrl)).apply {
            setDescription(filename)
            setTitle(filename)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationUri(uri)
        }
    }

    @Suppress("DEPRECATION")
    private fun requestForAndroidPandBellow(fileUrl: String): DownloadManager.Request {

        val filename = getFilename(fileUrl)

        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (!directory.exists())
            directory.mkdirs()

        val file = File(directory.path, filename)

        if (file.exists())
            file.delete()

        return DownloadManager.Request(Uri.parse(fileUrl)).apply {
            setDescription(filename)
            setTitle(filename)
            allowScanningByMediaScanner()
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(directory.path, filename)
        }

    }

}
