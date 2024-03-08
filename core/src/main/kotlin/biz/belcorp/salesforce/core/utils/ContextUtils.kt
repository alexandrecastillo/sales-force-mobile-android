package biz.belcorp.salesforce.core.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import biz.belcorp.salesforce.core.R
import biz.belcorp.salesforce.core.constants.Constant
import com.google.android.material.snackbar.Snackbar
import android.content.Intent


fun Context.getCompatDrawable(id: Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}

fun Context.getCompatColor(id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun Context.getTypeFace(fontId: Int): Typeface {
    return requireNotNull(ResourcesCompat.getFont(this, fontId))
}

fun Context.checkCallPermission(): Boolean {
    return checkPermission(Manifest.permission.CALL_PHONE)
}

fun Context.requestCallPermission(requestCode: Int = 0) {
    requestPermission(requestCode, Manifest.permission.CALL_PHONE)
}

fun Context.checkWritePermission(): Boolean {
    return permissionsWriteStorage().all {
        checkPermission(it)
    }
}

fun Context.requestWritePermission(requestCode: Int = 0) {
    requestPermission(requestCode, *permissionsWriteStorage())
}

fun Context.requestPermission(requestCode: Int = 0, vararg permission: String) {
    val activity = this as? Activity?
    activity?.let { requestPermissions(it, permission, requestCode) }
}

fun Context.checkPermission(permission: String): Boolean {
    return ContextCompat
        .checkSelfPermission(this, permission) ==
        PackageManager.PERMISSION_GRANTED
}

private fun permissionsWriteStorage(): Array<String> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_AUDIO
        )
    } else {
        arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}

fun Context.enviarAWhatsapp(numero: String, message: String? = null): Boolean {
    val numeroFormateado = numero.replace(" ", "").replace("+", "")
    val sendIntent = Intent("android.intent.action.MAIN")
    sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.type = "text/plain"
    sendIntent.putExtra(Intent.EXTRA_TEXT, message)
    sendIntent.putExtra("jid", "$numeroFormateado@s.whatsapp.net")
    sendIntent.`package` = "com.whatsapp"
    return try {
        this.startActivity(sendIntent)
        true
    } catch (exception: ActivityNotFoundException) {
        enviarAWhatsappBusiness(sendIntent, numeroFormateado, message.orEmpty())
    }
}

fun Context.enviarAWhatsappBusiness(sendIntent: Intent, number: String, message: String): Boolean {
    sendIntent.component = ComponentName("com.whatsapp.w4b", "com.whatsapp.Conversation")
    sendIntent.`package` = "com.whatsapp.w4b"
    return try {
        this.startActivity(sendIntent)
        true
    } catch (exception: ActivityNotFoundException) {
        sendAWhatsappAPI(number, message)
        false
    }
}

fun Context.sendAWhatsappAPI(number: String, message: String): Boolean {
    val uri = Uri.parse("https://api.whatsapp.com/send?phone=$number&text=$message")
    val sendIntent = Intent(Intent.ACTION_VIEW, uri)

    return try {
        startActivity(sendIntent)
        true
    } catch (exception: Exception) {
        toast("No se ha encontrado Whatsapp en el teléfono.")
        false
    }
}

@SuppressLint("MissingPermission")
fun Context.llamarATelefono(number: String): Boolean {
    try {
        val numberToCall = number.split(" ").getOrNull(1) ?: number
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$numberToCall")
        return if (checkCallPermission()) {
            startActivity(callIntent)
            true
        } else {
            requestCallPermission()
            false
        }
    } catch (e: Exception) {
        return false
    }
}

fun Context.enviarACorreo(mail: String) {
    val i = Intent(Intent.ACTION_SEND)
    i.type = "message/rfc822"
    i.putExtra(Intent.EXTRA_EMAIL, arrayOf(mail))
    try {
        if (mail.contains(Constant.HYPHEN, false)) {
            toast(getString(R.string.has_no_mail))
        } else {
            startActivity(Intent.createChooser(i, getString(R.string.send_mail)))
        }
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        toast(getString(R.string.mail_app_not_found))
    }
}

fun Context.hideKeyboardFrom(view: View?) {
    if (view == null) return
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showSnackBar(message: String, view: View, duration: Int = Snackbar.LENGTH_LONG) {
    val sb = Snackbar.make(view, message, duration)
    val text = sb.view.findViewById<TextView?>(R.id.snackbar_text)
    text?.typeface = ResourcesCompat.getFont(this, R.font.mulish_regular)
    sb.show()
}

fun Context.share(text: String, subject: String = ""): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(android.content.Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, null))
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}

inline fun lollipopOrAbove(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        block()
    }
}

fun Int.singularOPlural() = if (this > 1 || this == 0) "S" else ""

fun FragmentActivity.safeUnregisterReceiver(receiver: BroadcastReceiver) {
    try {
        unregisterReceiver(receiver)
    } catch (e: Exception) {
        Logger.e(javaClass.simpleName, e.message.orEmpty(), e)
    }
}

const val INTENT_TYPE_IMAGE = "image/*"

fun createImageChooserIntent(title: String): Intent? {
    return try {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = INTENT_TYPE_IMAGE
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, INTENT_TYPE_IMAGE)
        val chooserIntent = Intent.createChooser(getIntent, title)
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))
        chooserIntent
    } catch (e: Exception) {
        Logger.e(e)
        null
    }
}
