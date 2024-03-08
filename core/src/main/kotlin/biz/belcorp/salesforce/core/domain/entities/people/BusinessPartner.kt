package biz.belcorp.salesforce.core.domain.entities.people

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class BusinessPartner(
    id: Long,
    document: String,
    firstName: String,
    secondName: String,
    firstSurname: String,
    secondSurname: String,
    birthDate: String,
    val anniversaryDate: String,
    val code: String,
    val levelCode: String,
    val level: String,
    val status: String,
    val leaderClassification: String
) : Person(
    id,
    document,
    firstName,
    secondName,
    firstSurname,
    secondSurname,
    birthDate
) {
    override val role = Rol.SOCIA_EMPRESARIA

    var levelType: Level = Level.None

    sealed class Level {
        object PreBronze : Level()
        object Bronze : Level()
        object Golden : Level()
        object Silver : Level()
        object Platinum : Level()
        object Diamond : Level()
        object None : Level()
    }
}
