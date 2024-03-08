package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.crediticio.Bloqueo
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class BuroResponse {

    var primerNombre: String = Constant.EMPTY_STRING
    var segundoNombre: String = Constant.EMPTY_STRING
    var primerApellido: String = Constant.EMPTY_STRING
    var segundoApellido: String = Constant.EMPTY_STRING

    var buroResponse: String? = null

    var esPostulante: Boolean? = null
    var esConsultora: Boolean? = null
    var esPotencial: Boolean? = false
    var nombreCompleto: String? = null
    var estadoActividad: Int? = null
    var tipoSolicitud: String? = null
    var mensajeError: String = ""

    var bloqueosInternos: Boolean? = null

    var estadoCrediticio: Boolean? = null
    var enumEstadoCrediticioID: Int? = null
    var bloqueosExternos: Boolean? = null
    var respuestaServicio: String? = null
    var fechaNacimiento: String? = null

    var requiereAprobacionSAC: Boolean? = null

    var offline: Boolean = false
    var motivoBloqueo: String? = null
    var valoracionBelcorp: String? = null
    var bloqueos: List<Bloqueo>? = null
    var deudaBelcorp: String? = null

}
