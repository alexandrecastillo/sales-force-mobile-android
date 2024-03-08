package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

class PostulanteAptaResponse(
    val esApta: Boolean = false,
    val idEstadoActividad: Int? = null,
    val mensajeError: String? = null,
    val nombreCompleto: String? = null,
    val tipoError: Int? = null,
    val tipoSolicitud: String? = null
)
