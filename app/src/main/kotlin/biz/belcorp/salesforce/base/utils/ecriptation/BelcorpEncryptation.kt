package biz.belcorp.salesforce.base.utils.ecriptation

interface BelcorpEncryptation {

    fun encrypt(value: String): String?

    fun encrypt(key: String, value: String): String?

    fun decrypt(value: String): String?

}
