package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

data class ReconocimientoASuperior(
    val idReconocimiento: Long,
    val idPersonaReconoce: String,
    val rolReconoce: Rol,
    val idPersonaReconocida: String,
    val rolReconocida: Rol,
    var valoracion: Int,
    val pendienteReconocimiento: Boolean,
    var comentarios: String?,
    val nombreReconocida: String,
    val campania: String,
    var uACodigo: String = ""
)
