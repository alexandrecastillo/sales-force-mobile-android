package biz.belcorp.salesforce.core.data.repository.businesspartners

import biz.belcorp.salesforce.core.data.repository.businesspartners.data.BusinessPartnerDataStore
import biz.belcorp.salesforce.core.data.repository.businesspartners.mappers.BusinessPartnerMapper
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository

class BusinessPartnerDataRepository(
    private val dataStore: BusinessPartnerDataStore,
    private val mapper: BusinessPartnerMapper
) : BusinessPartnerRepository {

    override suspend fun getBusinessPartners(): List<Person> {
        val data = dataStore.getBusinessPartners()
        return data.map { mapper.map(it) }
    }

    override suspend fun getBusinessPartner(uaKey: LlaveUA): Person {
        val data = dataStore.getBusinessPartner(uaKey)
        return mapper.map(data)
    }

    override suspend fun getBusinessPartner(consultantCode: String): Person {
        val data = dataStore.getBusinessPartner(consultantCode)
        return mapper.map(data)
    }
}
