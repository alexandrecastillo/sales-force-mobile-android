package biz.belcorp.salesforce.core.data.repository.browser

import biz.belcorp.salesforce.core.BuildConfig
import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.domain.repository.browser.DataReportRepository
import biz.belcorp.salesforce.core.utils.KEY_TYP
import biz.belcorp.salesforce.core.utils.VALUE_JWT
import biz.belcorp.salesforce.core.utils.createJwtPath
import biz.belcorp.salesforce.core.utils.parseToJson


class DataReportDataRepository(
    private val userPreferences: UserSharedPreferences
) : DataReportRepository {

    override suspend fun getUrlForSE(): String {
        val jo = createDataReportObjectForSE()
        val path = createPath(requireNotNull(jo))
        return createUrl(path)
    }

    override suspend fun getUrlForGZ(): String {
        val jo = createDataReportObjectForGZ()
        val path = createPath(requireNotNull(jo))
        return createUrl(path)
    }

    private fun createDataReportObjectForSE(): String? {
        return mapOf(
            KEY_SOURCE to VALUE_SOURCE,
            KEY_CONSULTANT to userPreferences.codUsuario,
            KEY_CONSULTANT_ID to userPreferences.consultoraId?.toIntOrNull(),
            KEY_ISO to userPreferences.codPais.orEmpty(),
            KEY_PAGE to VALUE_PAGE_SE
        ).parseToJson()
    }

    private fun createDataReportObjectForGZ(): String? {
        return mapOf(
            KEY_SOURCE to VALUE_SOURCE,
            KEY_CONSULTANT to userPreferences.username,
            KEY_ISO to userPreferences.codPais.orEmpty(),
            KEY_ROLE to userPreferences.codRol.orEmpty(),
            KEY_PAGE to VALUE_PAGE_GZ
        ).parseToJson()
    }

    private fun createUrl(path: String): String {
        return "${BuildConfig.DATA_REPORT_HOST}$path"
    }

    private fun createPath(jo: String): String {
        return createJwtPath {
            setHeaderParam(KEY_TYP, VALUE_JWT)
            setPayload(jo)
        }
    }

    companion object {

        private const val KEY_ISO = "CodigoISO"
        private const val KEY_CONSULTANT = "CodigoConsultora"
        private const val KEY_ROLE = "CodigoRol"
        private const val KEY_PAGE = "Pagina"
        private const val KEY_SOURCE = "FuenteOrigen"
        private const val KEY_CONSULTANT_ID = "ConsultoraID"

        private const val VALUE_PAGE_GZ = "DataReportGZ"
        private const val VALUE_PAGE_SE = "DATAREPORTSE"

        private const val VALUE_SOURCE = "App"

    }

}
