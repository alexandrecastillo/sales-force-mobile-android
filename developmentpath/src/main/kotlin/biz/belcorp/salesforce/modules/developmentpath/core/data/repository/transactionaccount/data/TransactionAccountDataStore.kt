package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.entities.transactionaccount.TransactionAccountEntity
import biz.belcorp.salesforce.core.entities.transactionaccount.TransactionAccountEntity_
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.equal

class TransactionAccountDataStore {

    fun save(consultoraId: Int, items: List<TransactionAccountEntity>) {
        with(boxStore) {
            runInTx {
                boxFor<TransactionAccountEntity>().query()
                    .equal(TransactionAccountEntity_.consultantId, consultoraId)
                    .build().remove()
                boxFor<TransactionAccountEntity>().put(items)
            }
        }
    }

    fun list(consultantId: Int): List<TransactionAccountEntity> =
        boxStore.boxFor<TransactionAccountEntity>().query()
            .equal(TransactionAccountEntity_.consultantId, consultantId)
            .build()
            .find()

}
