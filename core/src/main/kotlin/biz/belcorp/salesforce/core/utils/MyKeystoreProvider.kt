package biz.belcorp.salesforce.core.utils

import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.math.min


class MyKeystoreProvider {

    companion object {
        const val PHARSEENCRYPTER = "0000000000000000"
        const val BYTE_ARRAY_SIZE = 16
        const val POSITION = 0
    }

    private val characterEncoding = "UTF-8"
    private val cipherTransformation = "AES/CBC/PKCS5Padding"
    private val aesEncryptionAlgorithm = "AES"

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        InvalidAlgorithmParameterException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun encrypt(plainText: ByteArray, key: ByteArray, initialVector: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(cipherTransformation)
        val secretKeySpec = SecretKeySpec(key, aesEncryptionAlgorithm)
        val ivParameterSpec = IvParameterSpec(initialVector)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
        return cipher.doFinal(plainText)
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getKeyBytes(key: String): ByteArray {
        val keyBytes = ByteArray(BYTE_ARRAY_SIZE)
        val parameterKeyBytes = key.toByteArray(charset(characterEncoding))
        System.arraycopy(
            parameterKeyBytes,
            POSITION,
            keyBytes,
            POSITION,
            min(parameterKeyBytes.size, keyBytes.size)
        )
        return keyBytes
    }

    @Throws(
        UnsupportedEncodingException::class,
        InvalidKeyException::class,
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidAlgorithmParameterException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun encrypt(plainText: String, key: String): String {
        val plainTextbytes = plainText.toByteArray(charset(characterEncoding))
        val keyBytes = getKeyBytes(key)
        val encriptado =
            Base64.encodeToString(encrypt(plainTextbytes, keyBytes, keyBytes), Base64.DEFAULT)
        Log.d("password encrypt ", ":  $encriptado")
        return encriptado
    }

}
