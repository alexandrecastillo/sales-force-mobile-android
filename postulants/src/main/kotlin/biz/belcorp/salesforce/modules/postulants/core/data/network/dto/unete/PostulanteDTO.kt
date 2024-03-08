package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.google.gson.annotations.SerializedName

data class PostulanteDTO(val name: String = Constant.EMPTY_STRING) {

    @SerializedName("solicitudPostulanteID")
    var solicitudPostulanteID: Int = Constant.NUMERO_CERO
    @SerializedName("paisID")
    var paisID: Int = Constant.NUMERO_CERO
    @SerializedName("tipoSolicitud")
    var tipoSolicitud: String? = null
    @SerializedName("fuenteIngreso")
    var fuenteIngreso: String? = null
    @SerializedName("numeroPreimpreso")
    var numeroPreimpreso: String? = null
    @SerializedName("codigoZona")
    var codigoZona: String? = null
    @SerializedName("codigoSeccion")
    var codigoSeccion: String? = null
    @SerializedName("codigoTerritorio")
    var codigoTerritorio: String? = null
    @SerializedName("tipoContacto")
    var tipoContacto: String? = null
    @SerializedName("campaniaID")
    var campaniaID: Int = Constant.NUMERO_CERO
    @SerializedName("codigoConsultoraRecomienda")
    var codigoConsultoraRecomienda: String? = null
    @SerializedName("nombreConsultoraRecomienda")
    var nombreConsultoraRecomienda: String? = null
    @SerializedName("codigoConsultora")
    var codigoConsultora: String? = null
    @SerializedName("codigoPremio")
    var codigoPremio: String? = null
    @SerializedName("apellidoPaterno")
    var apellidoPaterno: String? = null
    @SerializedName("apellidoMaterno")
    var apellidoMaterno: String? = null
    @SerializedName("primerNombre")
    var primerNombre: String? = null
    @SerializedName("segundoNombre")
    var segundoNombre: String? = null
    @SerializedName("tipoDocumento")
    var tipoDocumento: String? = null
    @SerializedName("numeroDocumento")
    var numeroDocumento: String? = null
    @SerializedName("sexo")
    var sexo: String? = null
    @SerializedName("fechaNacimiento")
    var fechaNacimiento: String? = null
    @SerializedName("estadoCivil")
    var estadoCivil: String? = null
    @SerializedName("nivelEducativo")
    var nivelEducativo: String? = null
    @SerializedName("codigoOtrasMarcas")
    var codigoOtrasMarcas: Int? = null
    @SerializedName("tipoNacionalidad")
    var tipoNacionalidad: String? = null
    @SerializedName("telefono")
    var telefono: String? = null
    @SerializedName("celular")
    var celular: String? = null
    @SerializedName("correoElectronico")
    var correoElectronico: String? = null
    @SerializedName("direccion")
    var direccion: String? = null
    @SerializedName("referencia")
    var referencia: String? = null
    @SerializedName("codigoUbigeo")
    var codigoUbigeo: String? = null

    @SerializedName("ciudad")
    var ciudad: String? = null
    @SerializedName("tipoVia")
    var tipoVia: String? = null
    @SerializedName("poblacionVilla")
    var poblacionVilla: String? = null
    @SerializedName("codigoPostal")
    var codigoPostal: String? = null
    @SerializedName("direccionEntrega")
    var direccionEntrega: String? = null
    @SerializedName("referenciaEntrega")
    var referenciaEntrega: String? = null
    @SerializedName("telefonoEntrega")
    var telefonoEntrega: String? = null
    @SerializedName("celularEntrega")
    var celularEntrega: String? = null
    @SerializedName("observacionEntrega")
    var observacionEntrega: String? = null
    @SerializedName("apellidoFamiliar")
    var apellidoFamiliar: String? = null
    @SerializedName("nombreFamiliar")
    var nombreFamiliar: String? = null
    @SerializedName("direccionFamiliar")
    var direccionFamiliar: String? = null
    @SerializedName("telefonoFamiliar")
    var telefonoFamiliar: String? = null
    @SerializedName("celularFamiliar")
    var celularFamiliar: String? = null
    @SerializedName("tipoVinculoFamiliar")
    var tipoVinculoFamiliar: Int? = null
    @SerializedName("apellidoNoFamiliar")
    var apellidoNoFamiliar: String? = null
    @SerializedName("nombreNoFamiliar")
    var nombreNoFamiliar: String? = null
    @SerializedName("direccionNoFamiliar")
    var direccionNoFamiliar: String? = null
    @SerializedName("telefonoNoFamiliar")
    var telefonoNoFamiliar: String? = null
    @SerializedName("celularNoFamiliar")
    var celularNoFamiliar: String? = null
    @SerializedName("tipoVinculoNoFamiliar")
    var tipoVinculoNoFamiliar: Int? = null
    @SerializedName("apellidoPaternoAval")
    var apellidoPaternoAval: String? = null
    @SerializedName("apellidoMaternoAval")
    var apellidoMaternoAval: String? = null
    @SerializedName("primerNombreAval")
    var primerNombreAval: String? = null
    @SerializedName("segundoNombreAval")
    var segundoNombreAval: String? = null
    @SerializedName("direccionAval")
    var direccionAval: String? = null
    @SerializedName("telefonoAval")
    var telefonoAval: String? = null
    @SerializedName("celularAval")
    var celularAval: String? = null
    @SerializedName("tipoDocumentoAval")
    var tipoDocumentoAval: String? = null
    @SerializedName("numeroDocumentoAval")
    var numeroDocumentoAval: String? = null
    @SerializedName("tipoVinculoAval")
    var tipoVinculoAval: Int? = null
    @SerializedName("nombreEmpresaAval")
    var nombreEmpresaAval: String? = null
    @SerializedName("montoMeta")
    var montoMeta: String? = null
    @SerializedName("tipoMeta")
    var tipoMeta: String? = null
    @SerializedName("descripcionMeta")
    var descripcionMeta: String? = null
    @SerializedName("nombreMto")
    var nombreMto: String? = null
    @SerializedName("indicadorActivo")
    var indicadorActivo: String? = null
    @SerializedName("usuarioCreacion")
    var usuarioCreacion: String? = null
    @SerializedName("fechaCreacion")
    var fechaCreacion: String? = null
    @SerializedName("codigoLote")
    var codigoLote: Int? = null
    @SerializedName("estadoPostulante")
    var estadoPostulante: String? = null
    @SerializedName("estadoBurocrediticio")
    var estadoBurocrediticio: String? = null
    @SerializedName("estadoGEO")
    var estadoGEO: String? = null
    @SerializedName("respuestaGEO")
    var respuestaGEO: String? = null
    @SerializedName("regimenContable")
    var regimenContable: Int? = null
    @SerializedName("latitud")
    var latitud: String? = null
    @SerializedName("longitud")
    var longitud: String? = null
    @SerializedName("vistoPorGZ")
    var vistoPorGZ: Boolean = false
    @SerializedName("vistoPorSAC")
    var vistoPorSAC: Boolean = false
    @SerializedName("vistoPorSE")
    var vistoPorSE: Boolean = false
    @SerializedName("lugarPadre")
    var lugarPadre: String? = null
    @SerializedName("lugarHijo")
    var lugarHijo: String? = null
    @SerializedName("indicadorOptin")
    var indicadorOptin: Boolean = false
    @SerializedName("estadoTelefonico")
    var estadoTelefonico: Int = Constant.NUMERO_CERO
    @SerializedName("imagenCDDUrl")
    var imagenCDD: String? = null
    @SerializedName("imagenIFEUrl")
    var imagenIFE: String? = null
    @SerializedName("subEstadoPostulante")
    var subEstadoPostulante: Int = Constant.NUMERO_CERO
    @SerializedName("paso")
    var paso: Int = Constant.NUMERO_CERO
    @SerializedName("fechaEnvio")
    var fechaEnvio: String? = null
    @SerializedName("fechaIngreso")
    var fechaIngreso: String? = null
    @SerializedName("tipoRechazo")
    var tipoRechazo: String? = null
    @SerializedName("motivoRechazo")
    var motivoRechazo: String? = null
    @SerializedName("tieneExperiencia")
    var tieneExperiencia: Int? = null
    @SerializedName("razonSocial")
    var razonSocial: String? = null
    @SerializedName("requiereAval")
    var requiereAval: Boolean = false
    @SerializedName("fechaNacimientoAval")
    var fechaNacimientoAval: String? = null
    @SerializedName("imagenContratoUrl")
    var imagenContrato: String? = null
    @SerializedName("imagenPagareUrl")
    var imagenPagare: String? = null
    @SerializedName("imagenDniAvalUrl")
    var imagenDniAval: String? = null
    @SerializedName("tipoDocumentoLegal")
    var tipoDocumentoLegal: String? = null
    @SerializedName("numeroDocumentoLegal")
    var numeroDocumentoLegal: String? = null
    @SerializedName("nit")
    var nit: String? = null
    @SerializedName("imagenReciboOtraMarcaUrl")
    var imagenReciboOtraMarca: String? = null
    @SerializedName("imagenReciboPagoAvalUrl")
    var imagenReciboPagoAval: String? = null
    @SerializedName("imagenCreditoAvalUrl")
    var imagenCreditoAval: String? = null
    @SerializedName("imagenConstanciaLaboralAvalUrl")
    var imagenConstanciaLaboralAval: String? = null
    @SerializedName("tipoPersona")
    var tipoPersona: String? = null
    @SerializedName("origenIngreso")
    var origenIngreso: String? = null
    @SerializedName("requiereAprobacionSAC")
    var requiereAprobacionSAC: Boolean = false
    @SerializedName("devueltoPorSAC")
    var devueltoPorSAC: Boolean = false

    @SerializedName("teRecomendoOtraConsultora")
    var teRecomendoOtraConsultora: Int = Constant.NUMERO_CERO
    @SerializedName("pais")
    var pais: String? = null
    @SerializedName("edad")
    var edad: Int = Constant.NUMERO_CERO

    @SerializedName("sincronizado")
    var sincronizado: Int = Constant.NUMERO_UNO

    @SerializedName("ingresoAnticipado")
    var ingresoAnticipado: Int = Constant.NUMERO_CERO
    @SerializedName("campaniaDeIngreso")
    var campaniaDeIngreso: Int = Constant.NUMERO_CERO

    @SerializedName("vieneDe")
    var vieneDe: String? = null

    @SerializedName("tipoPago")
    var tipoPago: Int = Constant.NUMERO_CERO

    @SerializedName("tipoPagoNombre")
    var tipoPagoNombre: String? = null

    @SerializedName("fechaEnvioValidarPorGZ")
    var fechaEnvioValidarPorGZ: String? = null

    @SerializedName("termsAceptados")
    var termsAceptados: Boolean = false

    @SerializedName("UrlFirma")
    var UrlFirma: String = Constant.EMPTY_STRING

    @SerializedName("link")
    var link: String = Constant.EMPTY_STRING

}
