package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.estadocuenta

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cuenta.CuentaCorriente
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.estadocuenta.TransactionAccountRepository

class ObtenerEstadoDeCuentaUseCase(
    private val estadoCuentaRepository: TransactionAccountRepository,
    private val sessionRepository: SessionRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun obtener(personaId: Long): List<CuentaCorriente> {
        val pais = session.pais?.codigoIso ?: EMPTY_STRING
        return estadoCuentaRepository.obtener(pais, personaId.toInt())
    }

}
