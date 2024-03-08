package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class Postulante : BasePostulante() {

    var apellidoFamiliar: String? = Constant.EMPTY_STRING
    var apellidoMaternoAval: String? = Constant.EMPTY_STRING
    var apellidoNoFamiliar: String? = Constant.EMPTY_STRING
    var apellidoPaternoAval: String? = Constant.EMPTY_STRING
    var celularAval: String? = Constant.EMPTY_STRING
    var celularEntrega: String? = Constant.EMPTY_STRING
    var celularFamiliar: String? = Constant.EMPTY_STRING
    var celularNoFamiliar: String? = Constant.EMPTY_STRING
    var ciudad: String? = Constant.EMPTY_STRING
    var codigoConsultora: String? = Constant.EMPTY_STRING
    var codigoLote: Int? = Constant.NUMERO_CERO
    var codigoOtrasMarcas: Int? = Constant.NUMERO_CERO
    var codigoPremio: String? = Constant.EMPTY_STRING
    var codigoUbigeo: String? = Constant.EMPTY_STRING
    var descripcionMeta: String? = Constant.EMPTY_STRING
    var nombreMto: String? = Constant.EMPTY_STRING
    var direccionAval: String? = Constant.EMPTY_STRING
    var direccionEntrega: String? = Constant.EMPTY_STRING
    var direccionFamiliar: String? = Constant.EMPTY_STRING
    var direccionNoFamiliar: String? = Constant.EMPTY_STRING
    var estadoCivil: String? = Constant.EMPTY_STRING
    var fechaNacimientoAval: String? = Constant.EMPTY_STRING
    var montoMeta: String? = Constant.EMPTY_STRING
    var nit: String? = Constant.EMPTY_STRING
    var nivelEducativo: String? = Constant.EMPTY_STRING
    val nombreCompleto: String
        get() = String.format(
            "%s %s %s %s",
            primerNombre,
            segundoNombre,
            apellidoPaterno,
            apellidoMaterno
        )
    var nombreEmpresaAval: String? = Constant.EMPTY_STRING
    var nombreFamiliar: String? = Constant.EMPTY_STRING
    var nombreNoFamiliar: String? = Constant.EMPTY_STRING
    var numeroDocumentoAval: String? = Constant.EMPTY_STRING
    var numeroDocumentoLegal: String? = Constant.EMPTY_STRING
    var numeroPreimpreso: String? = Constant.EMPTY_STRING
    var observacionEntrega: String? = Constant.EMPTY_STRING
    var origenIngreso: Int? = Constant.NUMERO_CERO
    var poblacionVilla: String? = Constant.EMPTY_STRING
    var primerNombreAval: String? = Constant.EMPTY_STRING
    var razonSocial: String? = Constant.EMPTY_STRING
    var referenciaEntrega: String? = Constant.EMPTY_STRING
    var regimenContable: Int? = Constant.NUMERO_CERO
    var segundoNombreAval: String? = Constant.EMPTY_STRING
    var solicitudPostulanteID: Int = Constant.NUMERO_CERO
    var telefonoAval: String? = Constant.EMPTY_STRING
    var telefonoEntrega: String? = Constant.EMPTY_STRING
    var telefonoFamiliar: String? = Constant.EMPTY_STRING
    var telefonoNoFamiliar: String? = Constant.EMPTY_STRING
    var tipoDocumentoAval: String? = Constant.EMPTY_STRING
    var tipoDocumentoLegal: String? = Constant.EMPTY_STRING
    var tipoMeta: String? = Constant.EMPTY_STRING
    var tipoNacionalidad: String? = Constant.EMPTY_STRING
    var tipoPersona: Int? = Constant.NUMERO_CERO
    var tipoVia: String? = Constant.EMPTY_STRING
    var tipoVinculoAval: Int? = Constant.NUMERO_CERO
    var tipoVinculoFamiliar: Int? = Constant.NUMERO_CERO
    var tipoVinculoNoFamiliar: Int? = Constant.NUMERO_CERO
    var fechaIngreso: String? = Constant.EMPTY_STRING
    var tipoRechazo: String? = Constant.EMPTY_STRING
    var motivoRechazo: String? = Constant.EMPTY_STRING
    var imagenCDD: String? = Constant.EMPTY_STRING
    var imagenIFE: String? = Constant.EMPTY_STRING
    var imagenContrato: String? = Constant.EMPTY_STRING
    var imagenPagare: String? = Constant.EMPTY_STRING
    var imagenDniAval: String? = Constant.EMPTY_STRING
    var imagenReciboOtraMarca: String? = Constant.EMPTY_STRING
    var imagenReciboPagoAval: String? = Constant.EMPTY_STRING
    var imagenCreditoAval: String? = Constant.EMPTY_STRING
    var imagenConstanciaLaboralAval: String? = Constant.EMPTY_STRING
    var tipoRechazoExplanation: String? = Constant.EMPTY_STRING
    var ingresoAnticipado: Int = Constant.NUMERO_CERO
    var campaniaDeIngreso: Int = Constant.NUMERO_CERO
    var tipoPago: Int = Constant.NUMERO_CERO
    var tipoPagoNombre: String? = Constant.EMPTY_STRING
    var fechaEnvioValidarPorGZ: String? = Constant.EMPTY_STRING
    var UrlFirma: String = Constant.EMPTY_STRING
    var link: String = Constant.EMPTY_STRING
    var googlePlaceId: String = Constant.EMPTY_STRING
    var isGoogleDirection = false

    fun tieneLocation(): Boolean {
        return !(latitud.isNullOrEmpty() && longitud.isNullOrEmpty())
    }
}
