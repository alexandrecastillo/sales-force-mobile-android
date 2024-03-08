package biz.belcorp.salesforce.core.data.repository.browser

import biz.belcorp.salesforce.core.data.preferences.AuthSharedPreferences
import biz.belcorp.salesforce.core.data.repository.browser.cloud.MyAcademyCloudStore
import biz.belcorp.salesforce.core.data.repository.browser.cloud.dto.LoginResponseDto
import biz.belcorp.salesforce.core.domain.repository.browser.MyAcademyRepository
import biz.belcorp.salesforce.core.utils.*


class MyAcademyDataRepository(
    private val myAcademyCloudStore: MyAcademyCloudStore,
    private val authPreferences: AuthSharedPreferences
) : MyAcademyRepository {

    override suspend fun getUrl(document: String, country: String): String {
        val loginResponse = login(document, country)
        val urlRequest = buildRequest(loginResponse)
        return createUrl(urlRequest)
    }

    private suspend fun login(document: String, country: String): LoginResponseDto {
        val hashDocument = createDocumentHash(document)
        val loginResponse = myAcademyCloudStore.login(hashDocument, country)
        authPreferences.sbToken = loginResponse.accessToken
        return loginResponse
    }

    private suspend fun createUrl(urlRequest: HashMap<String, String?>): String {
        val urlResponse = myAcademyCloudStore.getUrl(urlRequest)
        return requireNotNull(urlResponse.data?.urlMiAcademia)
    }

    private fun buildRequest(dto: LoginResponseDto): HashMap<String, String?> {
        return hashMapOf(
            KEY_CAMPAIGN to dto.campania,
            KEY_MAIL to dto.correo,
            KEY_SEGMENT to dto.segmentoConstancia,
            KEY_IS_LEADER to dto.esLider,
            KEY_LEVEL to dto.nivelLider,
            KEY_START_LEADER_CAMPAIGN to dto.campaniaInicioLider,
            KEY_SECCION_LEADER to dto.seccionGestionLider
        )
    }

    private fun createDocumentHash(document: String): String {
        val obj = hashMapOf(KEY_DOCUMENT to document).parseToJson()
        return createPath(requireNotNull(obj))
    }

    private fun createPath(jo: String): String {
        return createJwtPath {
            setHeaderParam(KEY_ALG, VALUE_HS256)
            setHeaderParam(KEY_TYP, VALUE_JWT)
            setPayload(jo)
        }
    }

    companion object {

        private const val KEY_CAMPAIGN = "campaniaID"
        private const val KEY_MAIL = "correoConsultora"
        private const val KEY_SEGMENT = "segmentoConstancia"
        private const val KEY_IS_LEADER = "esLider"
        private const val KEY_LEVEL = "nivelLider"
        private const val KEY_START_LEADER_CAMPAIGN = "campaniaInicioLider"
        private const val KEY_SECCION_LEADER = "seccionGestionLider"

        private const val KEY_DOCUMENT = "documento"

    }

}
