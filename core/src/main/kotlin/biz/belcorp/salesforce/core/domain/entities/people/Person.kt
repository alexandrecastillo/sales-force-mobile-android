package biz.belcorp.salesforce.core.domain.entities.people

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.UNO_NEGATIVO
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

open class Person(
    val id: Long,
    val document: String,
    val firstName: String,
    val secondName: String,
    val firstSurname: String,
    val secondSurname: String,
    val birthDate: String
) {

    open val role = Rol.NINGUNO
    var uaKey: LlaveUA = LlaveUA(
        EMPTY_STRING,
        EMPTY_STRING,
        EMPTY_STRING,
        UNO_NEGATIVO.toLong()
    )
}
