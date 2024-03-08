package biz.belcorp.salesforce.base.utils.ecriptation

import android.util.Base64
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.security.Key

class JwtEncryption : BaseEncryptation(), BelcorpEncryptation {

    private val TAG = "JwtEncryption"

    companion object {
        private var instance: JwtEncryption? = null

        fun newInstance(): JwtEncryption? {
            if (null == instance) {
                instance = JwtEncryption()
            }
            return instance
        }
    }

    override fun encrypt(value: String): String? {
        val key: Key = createKey(SALT, SALT_LENGTH, KEY, ITERACIONES)
        return Jwts.builder().setSubject(value)
            .signWith(SignatureAlgorithm.HS512, key).compact()
    }

    override fun encrypt(key: String, value: String): String? {
        val key: String = Base64.encodeToString(key.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
        return Jwts.builder().setPayload(value)
            .signWith(SignatureAlgorithm.HS256, key).compact()
    }

    override fun decrypt(value: String): String? {
        val key: Key = createKey(SALT, SALT_LENGTH, KEY, ITERACIONES)
        return Jwts.parser().setSigningKey(key).parseClaimsJws(value).body.subject
    }


}
