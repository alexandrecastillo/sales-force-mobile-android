package biz.belcorp.salesforce.core.domain.usecases.terms

import biz.belcorp.salesforce.core.BuildConfig
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TEN
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.entities.terms.SaveTerms
import biz.belcorp.salesforce.core.domain.entities.terms.TermsConditions
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.repository.terms.TermsConditionsRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.documents.RUT
import biz.belcorp.salesforce.core.utils.isSE
import java.util.*

class TermConditionsUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
    private val termsConditionsRepository: TermsConditionsRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }
    private val campaign by lazy {
        campaignsRepository.obtenerCampaniaActual(session.llaveUA)
    }

    fun getLinkUnete(): String {
        return BuildConfig.HOST_LINK_SE
            .plus(session.countryIso.toLowerCase(Locale.getDefault()))
            .plus(UNETE_FROM)
            .plus(getDocumentByCountry())
    }

    suspend fun getTerms(): List<TermsConditions> {
        return termsConditionsRepository.getTermsConditions()
    }

    suspend fun hasTerms(): Boolean {
        return termsConditionsRepository.getTermsConditions().isNotEmpty()
    }

    suspend fun isTermApproved(termCode: String): Boolean {
        return termsConditionsRepository.isTermApproved(termCode)
    }

    suspend fun isTermBlocked(termCode: String): Boolean {
        return termsConditionsRepository.isTermBlocked(termCode)
    }

    suspend fun getTerm(termCode: String): String {
        return termsConditionsRepository.getTermUrl(termCode)
    }

    suspend fun saveApprovedTerms(termCode: String): Boolean {
        return termsConditionsRepository.saveApprovedTerm(getApproveParams(termCode))
    }
    suspend fun syncApprovedTerms(){
        val role = session.codigoRol
        val countryId = session.countryIso
        val code = getUserCode()
        val typeQuery = getType()
        return termsConditionsRepository.syncApprovedTermsConditions(countryId, role, code, typeQuery)
    }

    fun getUserName() = session.person.firstName

    private fun getApproveParams(termCode: String): ApproveTermsParams {
        return ApproveTermsParams(
            countryId = session.countryIso,
            region = session.region.orEmpty(),
            zone = session.zona.orEmpty(),
            section = session.seccion.orEmpty(),
            role = session.rol.codigoRol,
            consultantCode = session.codigoConsultora,
            userCode = session.codigoUsuario,
            documentNumber = getDocumentByCountry(),
            terms = listOf(setTerm(termCode))
        )
    }

    private fun setTerm(termCode: String): SaveTerms {
        return SaveTerms(
            campaign = campaign?.codigo.orEmpty(),
            termCode = termCode,
            checked = true,
            active = false
        )
    }

    private fun getDocumentByCountry(): String {
        return with(session) {
            when (countryIso) {
                Pais.CHILE.codigoIso -> formatDocumentChile()
                Pais.PANAMA.codigoIso -> codigoConsultora
                Pais.PERU.codigoIso -> formatDocumentPeru()
                else -> person.document
            }
        }
    }

    private fun formatDocumentChile(): String {
        return with(session) {
            person.document.run {
                if (rol.isSE()) {
                    RUT.format(this)
                } else this
            }
        }
    }

    private fun formatDocumentPeru(): String {
        return with(session) {
            person.document.run {
                if (rol.isSE()) this.substring(NUMBER_TWO, NUMBER_TEN)
                else this
            }
        }
    }

    private fun getType(): String {
        val rol = Rol.Builder.construir(session.codigoRol)
        return if (rol.isSE()) {
            ApproveTermsParams.TYPE_QUERY_CONSULTANT_CODE
        } else {
            ApproveTermsParams.TYPE_QUERY_USER_CODE
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

    companion object {
        const val UNETE_FROM = "?vieneDe=LinkSE_"
    }
}
