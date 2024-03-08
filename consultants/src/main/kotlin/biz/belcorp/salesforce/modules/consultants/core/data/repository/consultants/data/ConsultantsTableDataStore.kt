package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.data

import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.update

class ConsultantsTableDataStore {

    fun saveConsultants(items: List<ConsultoraEntity>) {
        items.deleteAndInsert()
    }

    fun updateConsultants(items: List<ConsultoraEntity>) {
        items.update()
    }

}
