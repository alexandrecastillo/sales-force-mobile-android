package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount

import biz.belcorp.salesforce.core.data.repository.consultants.data.ConsultantsDataStore
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.cloud.TransactionAccountCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.cloud.dto.TransactionAccountParams
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.cloud.dto.TransactionAccountQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.data.TransactionAccountDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.mapper.TransactionAccountMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cuenta.CuentaCorriente
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.estadocuenta.TransactionAccountRepository


class TransactionAccountDataRepository(
    private val cloudStore: TransactionAccountCloudStore,
    private val dataStore: TransactionAccountDataStore,
    private val consultantsDataStore: ConsultantsDataStore,
    private val mapper: TransactionAccountMapper
) : TransactionAccountRepository {

    override suspend fun obtener(pais: String, consultoraId: Int): List<CuentaCorriente> {
        sync(pais, consultoraId)
        return mapper.map(dataStore.list(consultoraId))
    }

    private suspend fun sync(pais: String, consultoraId: Int) {
        try {
            consultantsDataStore.get(consultoraId)?.let {
                val params = getParams(pais, it)
                val query = TransactionAccountQuery(params)
                val items = mapper.map(cloudStore.getTransactionAccount(query))
                dataStore.save(consultoraId, items)
            }
        } catch (ex: Exception) {
            Logger.e(ex)
        }
    }

    private fun getParams(pais: String, consultant: ConsultantEntity): TransactionAccountParams {
        return TransactionAccountParams(
            country = pais,
            region = consultant.region,
            zone = consultant.zone,
            section = consultant.section,
            consultantId = consultant.consultantId
        )
    }
}
