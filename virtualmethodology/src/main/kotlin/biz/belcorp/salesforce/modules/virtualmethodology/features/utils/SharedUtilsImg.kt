package biz.belcorp.salesforce.modules.virtualmethodology.features.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import biz.belcorp.salesforce.base.BuildConfig
import biz.belcorp.salesforce.modules.virtualmethodology.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class SharedUtilsImg(val context: Context) {

    private val AUTHORITIES = "${BuildConfig.APPLICATION_ID}.FileProvider"
    private val URI_FACEBOOK = "com.facebook.katana"
    private val URI_WHATSAPP = "com.whatsapp"
    private val URI_WHATSAPP_BUSINESS = "com.whatsapp.w4b"

    fun sharedFacebookPost(mImageView: ImageView) {
        val isFBInstalled = appInstalledOrNot(URI_FACEBOOK)
        val file = getLocalFile(mImageView)
        if (isFBInstalled) {
            if (file != null) {
                val bmpUri = FileProvider.getUriForFile(context, AUTHORITIES, file)
                val intent = Intent().apply {
                    setPackage(URI_FACEBOOK)
                    action = Intent.ACTION_SEND
                    type = "image/*"
                    putExtra(Intent.EXTRA_STREAM, bmpUri)
                }
                context.startActivity(intent)
            }
        } else {
            run {
                Toast.makeText(
                    context,
                    R.string.no_facebook_available,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun sharedWhatsapp(mImageView: ImageView) {
        val file = getLocalFile(mImageView)

        if (isInstalledPackageWhatsapp()) {
            if (file != null) {
                val bmpUri = FileProvider.getUriForFile(context, AUTHORITIES, file)
                val whatsappIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "image*//**//*"
                    setPackage(obtainPackageWhatsapp())
                    putExtra(Intent.EXTRA_STREAM, bmpUri)
                }
                context.startActivity(whatsappIntent)
            }
        } else {
            Toast.makeText(
                context,
                R.string.no_whatsapp_available,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun obtainPackageWhatsapp() : String = if (appInstalledOrNot(URI_WHATSAPP)) {
        URI_WHATSAPP
    } else {
        URI_WHATSAPP_BUSINESS
    }

    private fun isInstalledPackageWhatsapp() : Boolean = appInstalledOrNot(URI_WHATSAPP) || appInstalledOrNot(URI_WHATSAPP_BUSINESS)

    private fun appInstalledOrNot(uri: String): Boolean {
        val pm = context.packageManager
        return try {
            pm.getPackageInfo(uri, PackageManager.GET_META_DATA)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun getLocalFile(imageView: ImageView): File? {
        //Extract Bitmap from ImageView drawable
        val drawable = imageView.drawable
        val bmp: Bitmap? = if (drawable is BitmapDrawable) {
            (imageView.drawable as BitmapDrawable).bitmap
        } else {
            return null
        }

        // Store image to default external storage directory
        var file: File? = null
        if (bmp != null) {
            try {
                file = File(
                    "${context.filesDir}/images",
                    "share_image_${System.currentTimeMillis()}.png"
                )
                file.parentFile.mkdirs()

                val out = FileOutputStream(file)
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
                out.close()
            } catch (e: IOException) {
                e.message
            }
        }
        return file
    }
}
