package biz.belcorp.salesforce.core.data.repository.directory

import biz.belcorp.salesforce.core.data.repository.directory.data.ManagerDirectoryDataStore
import biz.belcorp.salesforce.core.data.repository.directory.mappers.ManagerDirectoryMapper
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.directory.ManagerDirectoryRepository

class ManagerDirectoryDataRepository(
    private val dataStore: ManagerDirectoryDataStore,
    private val mapper: ManagerDirectoryMapper
) : ManagerDirectoryRepository {

    override suspend fun getManager(consultantId: Int): Person {
        val entity = dataStore.getManager(consultantId)
        return mapper.map(entity)
    }

    override suspend fun getManager(uaKey: LlaveUA): Person {
        val entity = dataStore.getManager(uaKey)
        return mapper.map(entity)
    }

    override suspend fun getManagers(): List<Person> {
        val entities = dataStore.getManagers()
        return entities.map { mapper.map(it) }
    }
}