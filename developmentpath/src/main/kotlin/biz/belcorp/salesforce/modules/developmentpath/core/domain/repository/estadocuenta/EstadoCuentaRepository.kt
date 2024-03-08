package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.estadocuenta

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cuenta.CuentaCorriente

interface TransactionAccountRepository {
    suspend fun obtener(pais: String, consultoraId: Int): List<CuentaCorriente>
}
