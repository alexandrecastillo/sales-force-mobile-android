package biz.belcorp.salesforce.core.domain.usecases.browser

import biz.belcorp.salesforce.core.BuildConfig
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.repository.browser.DataReportRepository
import biz.belcorp.salesforce.core.domain.repository.browser.MyAcademyRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class GetWebUrlUseCase(
    private val dataReportRepository: DataReportRepository,
    private val myAcademyRepository: MyAcademyRepository,
    private val sessionRepository: SessionRepository
) {

    val session get() = requireNotNull(sessionRepository.getSession())

    suspend fun getUrl(topic: WebTopic): String {
        return when (topic) {
            WebTopic.MY_ACADEMY -> getMyAcademyUrl()
            WebTopic.DATA_REPORT -> getDataReportUrl()
            WebTopic.UCB -> getUCBUrl()
            WebTopic.MATERIALES_REDES_SOCIALES -> getMaterialesRS()
            else -> Constant.EMPTY_STRING
        }
    }

    private suspend fun getMyAcademyUrl(): String {
        return myAcademyRepository.getUrl(session.person.document, session.countryIso)
    }

    private suspend fun getDataReportUrl(): String {
        return when (session.rol) {
            Rol.SOCIA_EMPRESARIA -> dataReportRepository.getUrlForSE()
            else -> dataReportRepository.getUrlForGZ()
        }
    }

    private fun getMaterialesRS(): String {
        return BuildConfig.MATERIALES_REDES_SOCIALES
    }

    private fun getUCBUrl(): String {
        return UCB_HOST
    }

    companion object {

        const val UCB_HOST = "https://miscursosucb.belcorp.biz/"

    }

}
