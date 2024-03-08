package biz.belcorp.salesforce.modules.postulants.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class PostulanteUtil(val context: Context) {

    fun map64(values: List<PostulanteEntity>): List<PostulanteEntity> {
        return values.map { map64(it) }
    }

    fun map64(value: PostulanteEntity?): PostulanteEntity {

        value?.imagenCDD = toBase64(value?.imagenCDD)
        value?.imagenIFE = toBase64(value?.imagenIFE)
        value?.imagenContrato = toBase64(value?.imagenContrato)
        value?.imagenPagare = toBase64(value?.imagenPagare)
        value?.imagenDniAval = toBase64(value?.imagenDniAval)
        value?.imagenReciboOtraMarca = toBase64(value?.imagenReciboOtraMarca)
        value?.imagenReciboPagoAval = toBase64(value?.imagenReciboPagoAval)
        value?.imagenCreditoAval = toBase64(value?.imagenCreditoAval)
        value?.imagenConstanciaLaboralAval = toBase64(value?.imagenConstanciaLaboralAval)

        return value as PostulanteEntity

    }

    private fun toBase64(path: String?): String? {

        try {

            if (!path.isNullOrEmpty() && path.contains("FileProvider")) {

                val uri = Uri.parse(path)
                val imageStream = context.contentResolver.openInputStream(uri)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                val resizedImage = Bitmap.createScaledBitmap(selectedImage, 780, 940, true)

                val baos = ByteArrayOutputStream()
                resizedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)

                val b = baos.toByteArray()

                return Base64.encodeToString(b, Base64.DEFAULT)
            } else if (!path.isNullOrEmpty() && path.contains("content")) {
                try {
                    var base64 = Constant.EMPTY_STRING

                    val myPath = ContentUriUtils.getFilePath(context, Uri.parse(path))

                    val file = File(myPath)

                    return if (file.exists()) {
                        val bytes = file.readBytes()
                        base64 = Base64.encodeToString(bytes, Base64.DEFAULT)

                        base64
                    } else {
                        null
                    }
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    return null
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

}
