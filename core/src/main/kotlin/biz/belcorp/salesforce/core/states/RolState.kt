package biz.belcorp.salesforce.core.states

import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

sealed class RolState(private val rol: Rol) {

    object DV : RolState(Rol.DIRECTOR_VENTAS)
    object GR : RolState(Rol.GERENTE_REGION)
    class GZ(val rol: Rol) : RolState(rol)
    object SE : RolState(Rol.SOCIA_EMPRESARIA)
    object NONE : RolState(Rol.NINGUNO)
}

class UaInfoState(
    val uaKey: LlaveUA,
    val role: Rol,
    val person: Person?,
    val isCovered: Boolean
)
