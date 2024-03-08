package biz.belcorp.salesforce.core.domain.entities.ua

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class UaInfo(
    val code: Long,
    val country: String,
    var person: Person? = null,
    var campaign: Campania? = null,
    val uaKey: LlaveUA,
    val role: Rol,
    var isCovered: Boolean,
    val isThirdPerson: Boolean
)
