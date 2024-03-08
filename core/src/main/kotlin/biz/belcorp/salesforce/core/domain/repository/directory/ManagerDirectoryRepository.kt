package biz.belcorp.salesforce.core.domain.repository.directory

import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

interface ManagerDirectoryRepository {
    suspend fun getManager(consultantId: Int): Person
    suspend fun getManager(uaKey: LlaveUA): Person
    suspend fun getManagers(): List<Person>
}