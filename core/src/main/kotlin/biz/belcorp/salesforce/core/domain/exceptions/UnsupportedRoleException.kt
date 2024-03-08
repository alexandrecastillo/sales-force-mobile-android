package biz.belcorp.salesforce.core.domain.exceptions

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class UnsupportedRoleException(val rol: Rol) : Exception("Rol ${rol.codigoRol} no soportado")
