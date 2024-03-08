package biz.belcorp.salesforce.core.domain.entities.people

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class ZoneManager(
    id: Long,
    document: String,
    firstName: String,
    secondName: String,
    firstSurname: String,
    secondSurname: String,
    birthDate: String,
    val productivityState: String
) : Person(
    id,
    document,
    firstName,
    secondName,
    firstSurname,
    secondSurname,
    birthDate
) {
    override val role = Rol.GERENTE_ZONA
}
