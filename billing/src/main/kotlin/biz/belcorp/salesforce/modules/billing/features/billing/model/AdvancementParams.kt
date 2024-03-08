package biz.belcorp.salesforce.modules.billing.features.billing.model

import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class AdvancementParams(
    val role: Rol = Rol.NINGUNO,
    val uaKey: LlaveUA? = null,
    val person: Person? = null
)