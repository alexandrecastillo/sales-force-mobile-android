package biz.belcorp.salesforce.core.domain.usecases.terms

import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams.Companion.TYPE_QUERY_CONSULTANT_CODE
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams.Companion.TYPE_QUERY_USER_CODE
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.repository.terms.TermsConditionsRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isSE

class SyncTermsConditionsUseCase(
    private val sessionRepository: SessionRepository,
    private val termsConditionsRepository: TermsConditionsRepository
) {

    private val session get() = requireNotNull(sessionRepository.getSession())

    suspend fun sync() {
        val role = session.codigoRol
        val country = session.countryIso
        termsConditionsRepository.syncTermsConditions(country, role)
    }

    suspend fun syncApprovedTerms() {
        val role = session.codigoRol
        val country = session.countryIso
        val consultantCode = getUserCode()
        val typeQuery = getType()
        termsConditionsRepository.syncApprovedTermsConditions(
            country, role, consultantCode, typeQuery
        )
    }

    private fun getType(): String {
        val rol = Rol.Builder.construir(session.codigoRol)
        return if (rol.isSE()) {
            TYPE_QUERY_CONSULTANT_CODE
        } else {
            TYPE_QUERY_USER_CODE
        }
    }

    private fun getUserCode(): String {
        val rol = Rol.Builder.construir(session.codigoRol)
        return if (rol.isSE()) {
            session.codigoConsultora
        } else {
            session.codigoUsuario
        }
    }

}
