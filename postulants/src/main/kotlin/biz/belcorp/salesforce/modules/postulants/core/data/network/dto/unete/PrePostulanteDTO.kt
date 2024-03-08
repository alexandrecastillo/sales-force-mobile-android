package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.google.gson.annotations.SerializedName

data class PrePostulanteDTO(val name: String = Constant.EMPTY_STRING) {

    @SerializedName("solicitudPrePostulanteID")
    var solicitudPrePostulanteID: Int = Constant.NUMERO_CERO

    @SerializedName("nombres")
    var nombres: String? = Constant.EMPTY_STRING

    @SerializedName("apellidoPaterno")
    var apellidoPaterno: String? = Constant.EMPTY_STRING

    @SerializedName("apellidoMaterno")
    var apellidoMaterno: String? = Constant.EMPTY_STRING

    @SerializedName("tipoDocumento")
    var tipoDocumento: String? = Constant.EMPTY_STRING

    @SerializedName("numeroDocumento")
    var numeroDocumento: String? = Constant.EMPTY_STRING

    @SerializedName("celular")
    var celular: String? = Constant.EMPTY_STRING

    @SerializedName("correoElectronico")
    var correoElectronico: String? = Constant.EMPTY_STRING

    @SerializedName("fechaCreacion")
    var fechaCreacion: String? = Constant.EMPTY_STRING

    @SerializedName("fechaModificacion")
    var fechaModificacion: String? = Constant.EMPTY_STRING

    @SerializedName("vieneDe")
    var vieneDe: String? = Constant.EMPTY_STRING

    @SerializedName("codigoConsultoraRecomienda")
    var codigoConsultoraRecomienda: String? = Constant.EMPTY_STRING

    @SerializedName("nombreConsultoraRecomienda")
    var nombreConsultoraRecomienda: String? = Constant.EMPTY_STRING

    @SerializedName("rechazo")
    var rechazo: String? = Constant.EMPTY_STRING

    @SerializedName("ipOrigen")
    var ipOrigen: String? = Constant.EMPTY_STRING

    @SerializedName("estadoPostulante")
    var estadoPostulante: String? = Constant.EMPTY_STRING

    @SerializedName("lugarPadre")
    var lugarPadre: String? = Constant.EMPTY_STRING

    @SerializedName("lugarHijo")
    var lugarHijo: String? = Constant.EMPTY_STRING

    @SerializedName("direccion")
    var direccion: String? = Constant.EMPTY_STRING

    @SerializedName("latitud")
    var latitud: String? = Constant.EMPTY_STRING

    @SerializedName("longitud")
    var longitud: String? = Constant.EMPTY_STRING

    @SerializedName("codigoPostal")
    var codigoPostal: String? = Constant.EMPTY_STRING

    @SerializedName("zonificacion")
    var zonificacion: String? = Constant.EMPTY_STRING

    @SerializedName("estadoGEO")
    var estadoGEO: String? = Constant.EMPTY_STRING

    @SerializedName("respuestaGEO")
    var respuestaGeo: String? = Constant.EMPTY_STRING

    @SerializedName("paisID")
    var paisID: Int = Constant.NUMERO_CERO

    @SerializedName("campaniaDeRegistro")
    var campaniaDeRegistro: Int? = Constant.NUMERO_CERO

    @SerializedName("codigoRegion")
    var codigoRegion: String? = Constant.EMPTY_STRING

    @SerializedName("codigoSeccion")
    var codigoSeccion: String? = Constant.EMPTY_STRING

    @SerializedName("codigoTerritorio")
    var codigoTerritorio: String? = Constant.EMPTY_STRING

    @SerializedName("codigoZona")
    var codigoZona: String? = Constant.EMPTY_STRING

    @SerializedName("fuenteIngreso")
    var fuenteIngreso: String? = Constant.EMPTY_STRING

    @SerializedName("estadoPrePostulante")
    var estadoPrePostulante: String? = Constant.EMPTY_STRING

    @SerializedName("nroRegistros")
    var nroRegistros: Int? = Constant.NUMERO_CERO

    @SerializedName("pais")
    var pais: String? = Constant.EMPTY_STRING

    @SerializedName("sincronizado")
    var sincronizado: Int = Constant.NUMERO_UNO

    @SerializedName("paso")
    var paso: Int = Constant.NUMERO_CERO

    @SerializedName("edad")
    var edad: Int = Constant.NUMERO_CERO

    @SerializedName("primerNombre")
    var primerNombre: String? = Constant.EMPTY_STRING

    @SerializedName("segundoNombre")
    var segundoNombre: String? = Constant.EMPTY_STRING

    @SerializedName("fechaNacimiento")
    var fechaNacimiento: String? = Constant.EMPTY_STRING

    @SerializedName("sexo")
    var sexo: String? = Constant.EMPTY_STRING

    @SerializedName("telefono")
    var telefono: String? = Constant.EMPTY_STRING

    @SerializedName("referencia")
    var referencia: String? = Constant.EMPTY_STRING

    @SerializedName("tieneExperiencia")
    var tieneExperiencia: Int? = Constant.NUMERO_CERO

    @SerializedName("teRecomendoOtraConsultora")
    var teRecomendoOtraConsultora: Int = Constant.NUMERO_CERO

    @SerializedName("tipoSolicitud")
    var tipoSolicitud: String? = Constant.EMPTY_STRING

    @SerializedName("tipoContacto")
    var tipoContacto: String? = Constant.EMPTY_STRING

    @SerializedName("campaniaID")
    var campaniaID: Int = Constant.NUMERO_CERO

    @SerializedName("indicadorActivo")
    var indicadorActivo: String? = Constant.EMPTY_STRING

    @SerializedName("usuarioCreacion")
    var usuarioCreacion: String? = Constant.EMPTY_STRING

    @SerializedName("estadoBurocrediticio")
    var estadoBurocrediticio: String? = Constant.EMPTY_STRING

    @SerializedName("vistoPorGZ")
    var vistoPorGZ: Boolean = false

    @SerializedName("vistoPorSE")
    var vistoPorSE: Boolean = false

    @SerializedName("vistoPorSAC")
    var vistoPorSAC: Boolean = false

    @SerializedName("indicadorOptin")
    var indicadorOptin: Boolean = false

    @SerializedName("estadoTelefonico")
    var estadoTelefonico: Int = Constant.NUMERO_CERO

    @SerializedName("subEstadoPostulante")
    var subEstadoPostulante: Int = Constant.NUMERO_CERO

    @SerializedName("fechaEnvio")
    var fechaEnvio: String? = Constant.EMPTY_STRING

    @SerializedName("requiereAval")
    var requiereAval: Boolean = false

    @SerializedName("offline")
    var offline: Boolean = false

    @SerializedName("requiereAprobacionSAC")
    var requiereAprobacionSAC: Boolean = false

    @SerializedName("devueltoPorSAC")
    var devueltoPorSAC: Boolean = false

    @SerializedName("flagConsultoraDigital")
    var flagConsultoraDigital: String? = Constant.EMPTY_STRING

}
