package biz.belcorp.salesforce.core.data.repository.businesspartners.data

import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity
import biz.belcorp.salesforce.core.utils.deleteAndInsert

class BusinessPartnerTableDataStore {

    fun saveBusinessPartners(partners: List<SociaEmpresariaEntity>) {
        partners.deleteAndInsert()
    }

}
