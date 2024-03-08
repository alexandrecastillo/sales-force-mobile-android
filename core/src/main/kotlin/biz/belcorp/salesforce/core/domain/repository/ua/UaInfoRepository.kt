package biz.belcorp.salesforce.core.domain.repository.ua

import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

interface UaInfoRepository {
    suspend fun sync(country: String, person: Person)
    suspend fun getAssociatedUaListByUaKey(uaKey: LlaveUA, excludeParent: Boolean = false): List<UaInfo>
}
