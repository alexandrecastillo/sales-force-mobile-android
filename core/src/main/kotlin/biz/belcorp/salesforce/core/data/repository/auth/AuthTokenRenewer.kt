package biz.belcorp.salesforce.core.data.repository.auth


interface AuthTokenRenewer {

    suspend fun renewLegacyTokenForced(): Boolean

    suspend fun renewLegacyToken(): Boolean

    suspend fun renewToken(): Boolean

}
