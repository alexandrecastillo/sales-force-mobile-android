package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.unidadadministrativa

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class UaRegresable(
    val codigoCampania: String,
    val tipoUa: String,
    val codigoUa: String,
    val nombreResponsable: String,
    val rolLiderAsociado: Rol,
    val salir: Boolean
)
