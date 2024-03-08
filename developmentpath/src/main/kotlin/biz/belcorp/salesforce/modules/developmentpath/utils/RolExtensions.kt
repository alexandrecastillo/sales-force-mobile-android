package biz.belcorp.salesforce.modules.developmentpath.utils

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

fun Rol.personasACargo() =
        when (this) {
            Rol.SOCIA_EMPRESARIA -> "consultoras"
            Rol.GERENTE_ZONA -> "socias"
            Rol.GERENTE_REGION -> "gerentes de zona"
            else -> ""
        }

