package biz.belcorp.salesforce.core.domain.entities.people

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class SalesDirector(
    id: Long,
    document: String,
    firstName: String,
    secondName: String,
    firstSurname: String,
    secondSurname: String,
    birthDate: String
) : Person(
    id,
    document,
    firstName,
    secondName,
    firstSurname,
    secondSurname,
    birthDate
) {
    override val role = Rol.DIRECTOR_VENTAS
}
