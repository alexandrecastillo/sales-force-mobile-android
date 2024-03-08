package biz.belcorp.salesforce.core.domain.repository.businesspartner

import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

interface BusinessPartnerRepository {
    suspend fun getBusinessPartners(): List<Person>
    suspend fun getBusinessPartner(uaKey: LlaveUA): Person
    suspend fun getBusinessPartner(consultantCode: String): Person
}
