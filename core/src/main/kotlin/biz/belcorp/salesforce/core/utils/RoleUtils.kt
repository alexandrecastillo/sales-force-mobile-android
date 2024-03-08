package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

fun Rol.isDV() = this == Rol.DIRECTOR_VENTAS
fun Rol.isGR() = this == Rol.GERENTE_REGION
fun Rol.isGZ() = this == Rol.GERENTE_ZONA
fun Rol.isSE() = this == Rol.SOCIA_EMPRESARIA
fun Rol.isNone() = this == Rol.NINGUNO
fun Rol.isCO() = this == Rol.CONSULTORA
fun Rol.isPC() = this == Rol.POSIBLE_CONSULTORA
fun Rol.isMultiProfile() = when {
    isDV() || isGR() || isGZ() -> true
    else -> false
}
