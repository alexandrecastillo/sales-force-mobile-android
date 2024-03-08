package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class Jerarquizador {

    companion object {
        val roles = hashMapOf(
            Rol.CONSULTORA to 0,
            Rol.POSIBLE_CONSULTORA to 0,
            Rol.SOCIA_EMPRESARIA to 1,
            Rol.GERENTE_ZONA to 2,
            Rol.GERENTE_REGION to 3,
            Rol.DIRECTOR_VENTAS to 4
        )
    }
}

infix fun Rol.esPadreDe(rol: Rol): Boolean {
    return checkNotNull(Jerarquizador.roles[this]) > checkNotNull(
        Jerarquizador.roles[rol])
}

infix fun Rol.esPadreDirectoDe(rol: Rol): Boolean {
    return checkNotNull(Jerarquizador.roles[this]) - checkNotNull(
        Jerarquizador.roles[rol]) == 1
}

infix fun Rol.esHijoDe(rol: Rol): Boolean {
    return checkNotNull(Jerarquizador.roles[this]) < checkNotNull(
        Jerarquizador.roles[rol])
}

infix fun Rol.esHijoDirectoDe(rol: Rol): Boolean {
    return checkNotNull(Jerarquizador.roles[this]) - checkNotNull(
        Jerarquizador.roles[rol]) == -1
}

infix fun Rol.diferenciaDeNivel(rol: Rol): Int {
    return checkNotNull(Jerarquizador.roles[this]) - checkNotNull(
        Jerarquizador.roles[rol])
}

infix fun Rol.estaAlNivelDe(rol: Rol): Boolean {
    return checkNotNull(Jerarquizador.roles[this]) == checkNotNull(
        Jerarquizador.roles[rol])
}

fun Rol.primerPadre(): Rol? {
    val siguienteNivel = (Jerarquizador.roles[this] ?: return null) + 1
    return Jerarquizador.roles
        .filterValues { it == siguienteNivel }
        .map { it.key }
        .firstOrNull()
}

fun Rol.primerHijo(): Rol? {
    val anteriorNivel = (Jerarquizador.roles[this] ?: return null) - 1
    return Jerarquizador.roles
        .filterValues { it == anteriorNivel }
        .map { it.key }
        .firstOrNull()
}
