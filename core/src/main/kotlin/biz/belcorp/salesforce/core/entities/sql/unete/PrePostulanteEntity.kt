package biz.belcorp.salesforce.core.entities.sql.unete


import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "PrePostulante", useBooleanGetterSetters = false)
class PrePostulanteEntity : BaseModel() {

    @PrimaryKey
    @SerializedName("uuid")
    @Column(name = "uuid")
    var uuid: String = java.util.UUID.randomUUID().toString()

    @SerializedName("solicitudPrePostulanteID")
    @Column(name = "SolicitudPrePostulanteID")
    var solicitudPrePostulanteID: Int = Constant.NUMERO_CERO

    @SerializedName("nombres")
    @Column(name = "Nombres")
    var nombres: String? = Constant.EMPTY_STRING

    @SerializedName("apellidoPaterno")
    @Column(name = "ApellidoPaterno")
    var apellidoPaterno: String? = Constant.EMPTY_STRING

    @SerializedName("apellidoMaterno")
    @Column(name = "ApellidoMaterno")
    var apellidoMaterno: String? = Constant.EMPTY_STRING

    @SerializedName("tipoDocumento")
    @Column(name = "TipoDocumento")
    var tipoDocumento: String? = Constant.EMPTY_STRING

    @SerializedName("numeroDocumento")
    @Column(name = "NumeroDocumento")
    var numeroDocumento: String? = Constant.EMPTY_STRING

    @SerializedName("celular")
    @Column(name = "Celular")
    var celular: String? = Constant.EMPTY_STRING

    @SerializedName("correoElectronico")
    @Column(name = "CorreoElectronico")
    var correoElectronico: String? = Constant.EMPTY_STRING

    @SerializedName("fechaCreacion")
    @Column(name = "FechaCreacion")
    var fechaCreacion: String? = Constant.EMPTY_STRING

    @SerializedName("fechaModificacion")
    @Column(name = "FechaModificacion")
    var fechaModificacion: String? = Constant.EMPTY_STRING

    @SerializedName("vieneDe")
    @Column(name = "VieneDe")
    var vieneDe: String? = Constant.EMPTY_STRING

    @SerializedName("codigoConsultoraRecomienda")
    @Column(name = "CodigoConsultoraRecomienda")
    var codigoConsultoraRecomienda: String? = Constant.EMPTY_STRING

    @SerializedName("nombreConsultoraRecomienda")
    @Column(name = "NombreConsultoraRecomienda")
    var nombreConsultoraRecomienda: String? = Constant.EMPTY_STRING

    @SerializedName("rechazo")
    @Column(name = "Rechazo")
    var rechazo: String? = Constant.EMPTY_STRING

    @SerializedName("ipOrigen")
    @Column(name = "IpOrigen")
    var ipOrigen: String? = Constant.EMPTY_STRING

    @SerializedName("estadoPostulante")
    @Column(name = "EstadoPostulante")
    var estadoPostulante: String? = Constant.EMPTY_STRING

    @SerializedName("estadoPrePostulante")
    @Column(name = "EstadoPrePostulante")
    var estadoPrePostulante: String? = Constant.EMPTY_STRING

    @SerializedName("lugarPadre")
    @Column(name = "LugarPadre")
    var lugarPadre: String? = Constant.EMPTY_STRING

    @SerializedName("lugarHijo")
    @Column(name = "LugarHijo")
    var lugarHijo: String? = Constant.EMPTY_STRING

    @SerializedName("direccion")
    @Column(name = "Direccion")
    var direccion: String? = Constant.EMPTY_STRING

    @SerializedName("latitud")
    @Column(name = "Latitud")
    var latitud: String? = Constant.EMPTY_STRING

    @SerializedName("longitud")
    @Column(name = "Longitud")
    var longitud: String? = Constant.EMPTY_STRING

    @SerializedName("codigoPostal")
    @Column(name = "CodigoPostal")
    var codigoPostal: String? = Constant.EMPTY_STRING

    @SerializedName("zonificacion")
    @Column(name = "Zonificacion")
    var zonificacion: String? = Constant.EMPTY_STRING

    @SerializedName("estadoGEO")
    @Column(name = "EstadoGEO")
    var estadoGEO: String? = Constant.EMPTY_STRING

    @SerializedName("respuestaGEO")
    @Column(name = "RespuestaGEO")
    var respuestaGEO: String? = Constant.EMPTY_STRING

    @SerializedName("codigoZona")
    @Column(name = "Codigozona")
    var codigoZona: String? = Constant.EMPTY_STRING

    @SerializedName("codigoSeccion")
    @Column(name = "Codigoseccion")
    var codigoSeccion: String? = Constant.EMPTY_STRING

    @SerializedName("codigoTerritorio")
    @Column(name = "CodigoTerritorio")
    var codigoTerritorio: String? = Constant.EMPTY_STRING

    @SerializedName("paisID")
    @Column(name = "PaisID")
    var paisID: Int = Constant.NUMERO_CERO

    @SerializedName("pais")
    @Column(name = "Pais")
    var pais: String? = Constant.EMPTY_STRING

    @SerializedName("sincronizado")
    @Column(name = "Sincronizado")
    var sincronizado: Boolean = true

    @SerializedName("paso")
    @Column(name = "Paso")
    var paso: Int = Constant.NUMERO_CERO

    @SerializedName("edad")
    @Column(name = "Edad")
    var edad: Int = Constant.NUMERO_CERO

    @SerializedName("primerNombre")
    @Column(name = "PrimerNombre")
    var primerNombre: String? = Constant.EMPTY_STRING

    @SerializedName("segundoNombre")
    @Column(name = "SegundoNombre")
    var segundoNombre: String? = Constant.EMPTY_STRING

    @SerializedName("fechaNacimiento")
    @Column(name = "FechaNacimiento")
    var fechaNacimiento: String? = Constant.EMPTY_STRING

    @SerializedName("sexo")
    @Column(name = "Sexo")
    var sexo: String? = Constant.EMPTY_STRING

    @SerializedName("telefono")
    @Column(name = "Telefono")
    var telefono: String? = Constant.EMPTY_STRING

    @SerializedName("referencia")
    @Column(name = "Referencia")
    var referencia: String? = Constant.EMPTY_STRING

    @SerializedName("tieneExperiencia")
    @Column(name = "TieneExperiencia")
    var tieneExperiencia: Int? = Constant.NUMERO_CERO

    @SerializedName("teRecomendoOtraConsultora")
    @Column(name = "TeRecomendoOtraConsultora")
    var teRecomendoOtraConsultora: Int = Constant.NUMERO_CERO

    @SerializedName("campaniaDeRegistro")
    @Column(name = "CampaniaDeRegistro")
    var campaniaDeRegistro: Int? = Constant.NUMERO_CERO

    @SerializedName("tipoSolicitud")
    @Column(name = "TipoSolicitud")
    var tipoSolicitud: String? = Constant.EMPTY_STRING

    @SerializedName("fuenteIngreso")
    @Column(name = "FuenteIngreso")
    var fuenteIngreso: String? = Constant.EMPTY_STRING

    @SerializedName("tipoContacto")
    @Column(name = "TipoContacto")
    var tipoContacto: String? = Constant.EMPTY_STRING

    @SerializedName("campaniaID")
    @Column(name = "CampaniaID")
    var campaniaID: Int = Constant.NUMERO_CERO

    @SerializedName("indicadorActivo")
    @Column(name = "IndicadorActivo")
    var indicadorActivo: String? = Constant.EMPTY_STRING

    @SerializedName("usuarioCreacion")
    @Column(name = "UsuarioCreacion")
    var usuarioCreacion: String? = Constant.EMPTY_STRING

    @SerializedName("estadoBurocrediticio")
    @Column(name = "EstadoBurocrediticio")
    var estadoBurocrediticio: String? = Constant.EMPTY_STRING

    @SerializedName("vistoPorGZ")
    @Column(name = "VistoPorGZ")
    var vistoPorGZ: Int = Constant.NUMERO_CERO

    @SerializedName("vistoPorSE")
    @Column(name = "VistoPorSE")
    var vistoPorSE: Int = Constant.NUMERO_CERO

    @SerializedName("vistoPorSAC")
    @Column(name = "VistoPorSAC")
    var vistoPorSAC: Int = Constant.NUMERO_CERO

    @SerializedName("indicadorOptin")
    @Column(name = "IndicadorOptin")
    var indicadorOptin: Int = Constant.NUMERO_CERO

    @SerializedName("estadoTelefonico")
    @Column(name = "EstadoTelefonico")
    var estadoTelefonico: Int = Constant.NUMERO_TRES

    @SerializedName("subEstadoPostulante")
    @Column(name = "SubEstadoPostulante")
    var subEstadoPostulante: Int = Constant.NUMERO_CERO

    @SerializedName("fechaEnvio")
    @Column(name = "FechaEnvio")
    var fechaEnvio: String? = Constant.EMPTY_STRING

    @SerializedName("requiereAval")
    @Column(name = "RequiereAval")
    var requiereAval: Int = Constant.NUMERO_CERO

    @SerializedName("offline")
    @Column(name = "Offline")
    var offline: Boolean = false

    @SerializedName("requiereAprobacionSAC")
    @Column(name = "RequiereAprobacionSAC")
    var requiereAprobacionSAC: Boolean = false

    @SerializedName("devueltoPorSAC")
    @Column(name = "DevueltoPorSAC")
    var devueltoPorSAC: Boolean = false

    @SerializedName("flagConsultoraDigital")
    @Column(name = "FlagConsultoraDigital")
    var flagConsultoraDigital: String? = Constant.EMPTY_STRING

}
