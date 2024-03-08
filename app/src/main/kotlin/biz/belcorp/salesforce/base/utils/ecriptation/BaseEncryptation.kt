package biz.belcorp.salesforce.base.utils.ecriptation

import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException
import java.lang.RuntimeException
import java.security.GeneralSecurityException
import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

open class BaseEncryptation {

    private val TAG = "BaseEncryption"

    val ALGORITHM = "AES/CBC/PKCS5Padding";
    val PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    val KEY_ALGORITHM = "AES";
    val DELIMITER = "]";

    val SALT = "b1Z.b31c0rp.S3cUr1Ty";
    val KEY = "B3lC0rP@pp5";
    val SALT_LENGTH = 256;
    val IV_LENGTH = 128;
    val ITERACIONES = 1000;

    val random = SecureRandom()

    fun createKey(salt: String, saltLength: Int, password: String, iteraciones: Int): SecretKey {
        return try {
            val start = System.currentTimeMillis()
            val _pwd = password.toCharArray()
            val _salt: ByteArray?
            if (salt.trim().length === 0) {
                _salt = generateBytes(saltLength / 8)
            } else {
                _salt = salt.toByteArray(Charsets.UTF_8)
            }
            val keyFactory =
                SecretKeyFactory.getInstance(PBKDF2_ALGORITHM)
            val keySpec: KeySpec =
                PBEKeySpec(_pwd, _salt, iteraciones, saltLength)
            val keyBytes = keyFactory.generateSecret(keySpec).encoded
            Log.d(TAG, "key bytes: " + toHex(keyBytes))
            val result: SecretKey =
                SecretKeySpec(keyBytes, KEY_ALGORITHM)
            val elapsed = System.currentTimeMillis() - start
            Log.d(
                TAG,
                String.format("PBKDF2 key derivation took %d [ms].", elapsed)
            )
            result
        } catch (e: GeneralSecurityException) {
            throw RuntimeException(e)
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException(e)
        }
    }

    protected fun generateBytes(length: Int): ByteArray? {
        val b = ByteArray(length)
        random.nextBytes(b)
        return b
    }

    protected fun toHex(bytes: ByteArray): String? {
        val buff = StringBuilder()
        for (b in bytes) {
            buff.append(String.format("%02X", b))
        }
        return buff.toString()
    }


    protected fun getRawKey(key: SecretKey?): String? {
        return if (key == null) {
            null
        } else toHex(key.encoded)
    }

    protected fun toBase64(bytes: ByteArray?): String? {
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    protected fun fromBase64(base64: String?): ByteArray? {
        return Base64.decode(base64, Base64.NO_WRAP)
    }
}
