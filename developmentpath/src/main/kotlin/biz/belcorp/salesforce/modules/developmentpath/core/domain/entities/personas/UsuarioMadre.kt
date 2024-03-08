package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class UsuarioMadre(
    val nombre: String,
    val rol: Rol,
    val codigoUA: String
)
