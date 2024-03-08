package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant

open class BasePostulante {
    var UUID: String = java.util.UUID.randomUUID().toString()
    var tipoDocumento: String? = Constant.EMPTY_STRING
    var numeroDocumento: String? = Constant.EMPTY_STRING
    var apellidoPaterno: String? = Constant.EMPTY_STRING
    var apellidoMaterno: String? = Constant.EMPTY_STRING
    var primerNombre: String? = Constant.EMPTY_STRING
    var segundoNombre: String? = Constant.EMPTY_STRING
    var edad: Int = Constant.NUMERO_CERO
    var fechaNacimiento: String? = Constant.EMPTY_STRING
    var telefono: String? = Constant.EMPTY_STRING
    var sexo: String? = Constant.EMPTY_STRING
    var celular: String? = Constant.EMPTY_STRING
    var correoElectronico: String? = Constant.EMPTY_STRING
    var direccion: String? = Constant.EMPTY_STRING
    var latitud: String? = Constant.EMPTY_STRING
    var longitud: String? = Constant.EMPTY_STRING
    var codigoPostal: String? = Constant.EMPTY_STRING
    var estadoGEO: String? = Constant.EMPTY_STRING
    var respuestaGEO: String? = Constant.EMPTY_STRING
    var codigoZona: String? = Constant.EMPTY_STRING
    var codigoSeccion: String? = Constant.EMPTY_STRING
    var sincronizado: Boolean = true
    var codigoTerritorio: String? = Constant.EMPTY_STRING
    var paisID: Int = Constant.NUMERO_CERO
    var pais: String? = Constant.EMPTY_STRING
    var paso: Int = Constant.NUMERO_CERO
    var offline: Boolean = false
    var referencia: String? = Constant.EMPTY_STRING
    var tieneExperiencia: Int? = Constant.NUMERO_CERO
    var teRecomendoOtraConsultora: Int = Constant.NUMERO_CERO
    var fechaCreacion: String? = Constant.EMPTY_STRING
    var vieneDe: String? = Constant.EMPTY_STRING
    var codigoConsultoraRecomienda: String? = Constant.EMPTY_STRING
    var nombreConsultoraRecomienda: String? = Constant.EMPTY_STRING
    var estadoPostulante: String? = Constant.EMPTY_STRING
    var lugarPadre: String? = Constant.EMPTY_STRING
    var lugarHijo: String? = Constant.EMPTY_STRING
    var fuenteIngreso: String? = Constant.EMPTY_STRING
    var tipoSolicitud: String? = Constant.EMPTY_STRING
    var tipoContacto: String? = Constant.EMPTY_STRING
    var campaniaID: String = Constant.EMPTY_STRING
    var indicadorActivo: String? = Constant.EMPTY_STRING
    var usuarioCreacion: String? = Constant.EMPTY_STRING
    var estadoBurocrediticio: String? = Constant.EMPTY_STRING
    var vistoPorGZ: Int = Constant.NUMERO_CERO
    var vistoPorSAC: Int = Constant.NUMERO_CERO
    var vistoPorSE: Int = Constant.NUMERO_CERO
    var indicadorOptin: Int = Constant.NUMERO_CERO
    var estadoTelefonico: Int = Constant.NUMERO_TRES
    var subEstadoPostulante: Int = Constant.NUMERO_CERO
    var fechaEnvio: String? = Constant.EMPTY_STRING
    var requiereAval: Int = Constant.NUMERO_CERO
    var requiereAprobacionSAC: Boolean = false
    var devueltoPorSAC: Boolean = false
    var termsAceptados: Boolean = false
    var ip: String? = Constant.EMPTY_STRING
    var deviceId: String? = Constant.EMPTY_STRING
    var soMobile: String? = Constant.EMPTY_STRING

}
