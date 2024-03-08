package biz.belcorp.salesforce.modules.consultants.features.chatbot

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.Settings
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import java.net.URLEncoder

class ChatBotTextResolver(private val context: Context) : BaseTextResolver() {
    fun buildChatBotUrl(session: Sesion, rootUrl: String): String {
        val builder = Uri.parse(rootUrl.trim()).buildUpon()
            .appendPath(WEBCHAT_KEY)
            .appendQueryParameter(USER_ID_KEY, getUserIdByCountry(session))
            .appendQueryParameter(CONSULTANT_CODE_KEY, session.codigoUsuario)
            .appendQueryParameter(NUM_DOC_KEY, session.person.document)
            .appendQueryParameter(COUNTRY_ID_KEY, session.pais?.codigoIso.orEmpty())
            .appendQueryParameter(DEVICE_ID_KEY, getDeviceId())
            .appendQueryParameter(UA_KEY, getUaFormatted(session))
            .appendQueryParameter(BUSINESS_ID_KEY, BUSINESS_ID_VALUE)
            .appendQueryParameter(TOKEN_KEY, URLEncoder.encode(TOKEN_VALUE, UFT8))
        return builder.build().toString()
    }

    private fun getUaFormatted(session: Sesion): String = "${session.region}${session.zona}${session.seccion}"

    private fun getUserIdByCountry(session: Sesion): String? {
        return when (session.pais?.codigoIso) {
            Pais.COLOMBIA.codigoIso, Pais.PERU.codigoIso -> session.person.document
            else -> session.person.id.toString()
        }
    }

    @SuppressLint("HardwareIds")
    private fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    companion object {
        const val TOKEN_VALUE = "Lk2NCkjnrf8cosD"
        const val WEBCHAT_KEY = "webchat/"
        const val USER_ID_KEY = "userId"
        const val CONSULTANT_CODE_KEY = "codigoConsultora"
        const val NUM_DOC_KEY = "numeroDocumento"
        const val COUNTRY_ID_KEY = "countryId"
        const val DEVICE_ID_KEY = "deviceId"
        const val UA_KEY = "UA"
        const val BUSINESS_ID_KEY = "businessId"
        const val BUSINESS_ID_VALUE = "belcorp"
        const val TOKEN_KEY = "token"
        const val UFT8 = "UTF-8"
    }
}
