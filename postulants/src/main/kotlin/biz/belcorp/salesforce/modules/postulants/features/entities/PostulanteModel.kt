package biz.belcorp.salesforce.modules.postulants.features.entities

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class PostulanteModel : BasePostulante() {

    var apellidoFamiliar: String? = null
    var apellidoMaternoAval: String? = null
    var apellidoNoFamiliar: String? = null
    var apellidoPaternoAval: String? = null
    var celularAval: String? = null
    var celularEntrega: String? = null
    var celularFamiliar: String? = null
    var celularNoFamiliar: String? = null
    var ciudad: String? = null
    var codigoConsultora: String? = null
    var codigoLote: Int? = null
    var codigoOtrasMarcas: Int? = null
    var codigoPremio: String? = null
    var codigoUbigeo: String? = null
    var descripcionMeta: String? = null
    var nombreMto: String? = null
    var direccionAval: String? = null
    var direccionEntrega: String? = null
    var direccionFamiliar: String? = null
    var direccionNoFamiliar: String? = null
    var estadoCivil: String? = null
    var fechaNacimientoAval: String? = null
    var tieneLocation: Boolean = false
    var montoMeta: String? = null
    var nit: String? = null
    var nivelEducativo: String? = null
    var nombreCompleto: String? = null
    var nombreEmpresaAval: String? = null
    var nombreFamiliar: String? = null
    var nombreNoFamiliar: String? = null
    var numeroDocumentoAval: String? = null
    var numeroDocumentoLegal: String? = null
    var numeroPreimpreso: String? = null
    var observacionEntrega: String? = null
    var origenIngreso: Int? = null
    var poblacionVilla: String? = null
    var primerNombreAval: String? = null
    var razonSocial: String? = null
    var referenciaEntrega: String? = null
    var regimenContable: String? = null
    var seccion: String? = null
    var segundoNombreAval: String? = null
    var solicitudPostulanteID: Int = Constant.NUMERO_CERO
    var telefonoAval: String? = null
    var telefonoEntrega: String? = null
    var telefonoFamiliar: String? = null
    var telefonoNoFamiliar: String? = null
    var tipoDocumentoAval: String? = null
    var tipoDocumentoLegal: String? = null
    var tipoMeta: String? = null
    var tipoNacionalidad: String? = null
    var tipoPersona: Int? = null
    var tipoVia: String? = null
    var tipoVinculoAval: Int? = null
    var tipoVinculoFamiliar: Int? = null
    var tipoVinculoNoFamiliar: Int? = null
    var fechaIngreso: String? = null
    var tipoRechazo: String? = null
    var motivoRechazo: String? = null
    var imagenCDD: String? = null
    var imagenIFE: String? = null
    var imagenContrato: String? = null
    var imagenPagare: String? = null
    var imagenDniAval: String? = null
    var imagenReciboOtraMarca: String? = null
    var imagenReciboPagoAval: String? = null
    var imagenCreditoAval: String? = null
    var imagenConstanciaLaboralAval: String? = null
    var tipoRechazoExplanation: String? = null
    var ingresoAnticipado: Int = Constant.NUMERO_CERO
    var campaniaDeIngreso: Int = Constant.NUMERO_CERO
    var tipoPago: Int = Constant.NUMERO_CERO
    var tipoPagoNombre: String? = null
    var fechaEnvioValidarPorGZ: String? = null
    var link: String = Constant.EMPTY_STRING

    var ip: String? = Constant.EMPTY_STRING
    var deviceId: String? = Constant.EMPTY_STRING
    var soMobile: String? = Constant.EMPTY_STRING
    var UrlFirma: String = Constant.EMPTY_STRING

    var placeId: String? = null
    var hasChangedPlaceId: Boolean = false
}