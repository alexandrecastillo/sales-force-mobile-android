package biz.belcorp.salesforce.core.entities.sql.unete

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "Postulante", useBooleanGetterSetters = false)
class PostulanteEntity : BaseModel() {

    @PrimaryKey
    @SerializedName("uuid")
    @Column(name = "uuid")
    var uuid: String = java.util.UUID.randomUUID().toString()

    @SerializedName("apellidoFamiliar")
    @Column(name = "ApellidoFamiliar")
    var apellidoFamiliar: String? = Constant.EMPTY_STRING

    @SerializedName("apellidoMaterno")
    @Column(name = "ApellidoMaterno")
    var apellidoMaterno: String? = Constant.EMPTY_STRING

    @SerializedName("apellidoMaternoAval")
    @Column(name = "ApellidoMaternoAval")
    var apellidoMaternoAval: String? = Constant.EMPTY_STRING

    @SerializedName("apellidoNoFamiliar")
    @Column(name = "ApellidoNoFamiliar")
    var apellidoNoFamiliar: String? = Constant.EMPTY_STRING

    @SerializedName("apellidoPaterno")
    @Column(name = "ApellidoPaterno")
    var apellidoPaterno: String? = Constant.EMPTY_STRING

    @SerializedName("apellidoPaternoAval")
    @Column(name = "ApellidoPaternoAval")
    var apellidoPaternoAval: String? = Constant.EMPTY_STRING

    @SerializedName("campaniaID")
    @Column(name = "CampaniaID")
    var campaniaID: Int = Constant.NUMERO_CERO

    @SerializedName("celularAval")
    @Column(name = "CelularAval")
    var celularAval: String? = Constant.EMPTY_STRING

    @SerializedName("celularEntrega")
    @Column(name = "CelularEntrega")
    var celularEntrega: String? = Constant.EMPTY_STRING

    @SerializedName("celularFamiliar")
    @Column(name = "CelularFamiliar")
    var celularFamiliar: String? = Constant.EMPTY_STRING

    @SerializedName("celularNoFamiliar")
    @Column(name = "CelularNoFamiliar")
    var celularNoFamiliar: String? = Constant.EMPTY_STRING

    @SerializedName("ciudad")
    @Column(name = "Ciudad")
    var ciudad: String? = Constant.EMPTY_STRING

    @SerializedName("codigoConsultora")
    @Column(name = "CodigoConsultora")
    var codigoConsultora: String? = Constant.EMPTY_STRING

    @SerializedName("codigoConsultoraRecomienda")
    @Column(name = "CodigoConsultoraRecomienda")
    var codigoConsultoraRecomienda: String? = Constant.EMPTY_STRING

    @SerializedName("codigoLote")
    @Column(name = "CodigoLote")
    var codigoLote: Int? = Constant.NUMERO_CERO

    @SerializedName("codigoOtrasMarcas")
    @Column(name = "CodigoOtrasMarcas")
    var codigoOtrasMarcas: Int? = Constant.NUMERO_CERO

    @SerializedName("codigoPostal")
    @Column(name = "CodigoPostal")
    var codigoPostal: String? = Constant.EMPTY_STRING

    @SerializedName("codigoPremio")
    @Column(name = "CodigoPremio")
    var codigoPremio: String? = Constant.EMPTY_STRING

    @SerializedName("codigoSeccion")
    @Column(name = "CodigoSeccion")
    var codigoSeccion: String? = Constant.EMPTY_STRING

    @SerializedName("codigoTerritorio")
    @Column(name = "CodigoTerritorio")
    var codigoTerritorio: String? = Constant.EMPTY_STRING

    @SerializedName("codigoUbigeo")
    @Column(name = "CodigoUbigeo")
    var codigoUbigeo: String? = Constant.EMPTY_STRING

    @SerializedName("codigoZona")
    @Column(name = "CodigoZona")
    var codigoZona: String? = Constant.EMPTY_STRING

    @SerializedName("correoElectronico")
    @Column(name = "CorreoElectronico")
    var correoElectronico: String? = Constant.EMPTY_STRING

    @SerializedName("descripcionMeta")
    @Column(name = "DescripcionMeta")
    var descripcionMeta: String? = Constant.EMPTY_STRING

    @SerializedName("nombreMto")
    @Column(name = "nombreMto")
    var nombreMto: String? = Constant.EMPTY_STRING

    @SerializedName("direccion")
    @Column(name = "Direccion")
    var direccion: String? = Constant.EMPTY_STRING

    @SerializedName("direccionAval")
    @Column(name = "DireccionAval")
    var direccionAval: String? = Constant.EMPTY_STRING

    @SerializedName("direccionEntrega")
    @Column(name = "DireccionEntrega")
    var direccionEntrega: String? = Constant.EMPTY_STRING

    @SerializedName("direccionFamiliar")
    @Column(name = "DireccionFamiliar")
    var direccionFamiliar: String? = Constant.EMPTY_STRING

    @SerializedName("direccionNoFamiliar")
    @Column(name = "DireccionNoFamiliar")
    var direccionNoFamiliar: String? = Constant.EMPTY_STRING

    @SerializedName("edad")
    @Column(name = "Edad")
    var edad: Int = Constant.NUMERO_CERO

    @SerializedName("estadoBurocrediticio")
    @Column(name = "EstadoBurocrediticio")
    var estadoBurocrediticio: String? = Constant.EMPTY_STRING

    @SerializedName("estadoCivil")
    @Column(name = "EstadoCivil")
    var estadoCivil: String? = Constant.EMPTY_STRING

    @SerializedName("estadoGEO")
    @Column(name = "EstadoGEO")
    var estadoGEO: String? = Constant.EMPTY_STRING

    @SerializedName("estadoPostulante")
    @Column(name = "EstadoPostulante")
    var estadoPostulante: String? = Constant.EMPTY_STRING

    @SerializedName("estadoTelefonico")
    @Column(name = "EstadoTelefonico")
    var estadoTelefonico: Int = Constant.NUMERO_TRES

    @SerializedName("fechaCreacion")
    @Column(name = "FechaCreacion")
    var fechaCreacion: String? = Constant.EMPTY_STRING

    @SerializedName("fechaNacimiento")
    @Column(name = "FechaNacimiento")
    var fechaNacimiento: String? = Constant.EMPTY_STRING

    @SerializedName("fechaNacimientoAval")
    @Column(name = "FechaNacimientoAval")
    var fechaNacimientoAval: String? = Constant.EMPTY_STRING

    @SerializedName("fuenteIngreso")
    @Column(name = "FuenteIngreso")
    var fuenteIngreso: String? = Constant.EMPTY_STRING

    @SerializedName("indicadorActivo")
    @Column(name = "IndicadorActivo")
    var indicadorActivo: String? = Constant.EMPTY_STRING

    @SerializedName("indicadorOptin")
    @Column(name = "IndicadorOptin")
    var indicadorOptin: Int = Constant.NUMERO_CERO

    @SerializedName("latitud")
    @Column(name = "Latitud")
    var latitud: String? = Constant.EMPTY_STRING

    @SerializedName("longitud")
    @Column(name = "Longitud")
    var longitud: String? = Constant.EMPTY_STRING

    @SerializedName("lugarHijo")
    @Column(name = "LugarHijo")
    var lugarHijo: String? = Constant.EMPTY_STRING

    @SerializedName("lugarPadre")
    @Column(name = "LugarPadre")
    var lugarPadre: String? = Constant.EMPTY_STRING

    @SerializedName("montoMeta")
    @Column(name = "MontoMeta")
    var montoMeta: String? = Constant.EMPTY_STRING

    @SerializedName("nit")
    @Column(name = "Nit")
    var nit: String? = Constant.EMPTY_STRING

    @SerializedName("nivelEducativo")
    @Column(name = "NivelEducativo")
    var nivelEducativo: String? = Constant.EMPTY_STRING

    @SerializedName("nombreConsultoraRecomienda")
    @Column(name = "NombreConsultoraRecomienda")
    var nombreConsultoraRecomienda: String? = Constant.EMPTY_STRING

    @SerializedName("nombreEmpresaAval")
    @Column(name = "NombreEmpresaAval")
    var nombreEmpresaAval: String? = Constant.EMPTY_STRING

    @SerializedName("nombreFamiliar")
    @Column(name = "NombreFamiliar")
    var nombreFamiliar: String? = Constant.EMPTY_STRING

    @SerializedName("nombreNoFamiliar")
    @Column(name = "NombreNoFamiliar")
    var nombreNoFamiliar: String? = Constant.EMPTY_STRING

    @SerializedName("numeroDocumento")
    @Column(name = "NumeroDocumento")
    var numeroDocumento: String? = Constant.EMPTY_STRING

    @SerializedName("numeroDocumentoAval")
    @Column(name = "NumeroDocumentoAval")
    var numeroDocumentoAval: String? = Constant.EMPTY_STRING

    @SerializedName("numeroDocumentoLegal")
    @Column(name = "NumeroDocumentoLegal")
    var numeroDocumentoLegal: String? = Constant.EMPTY_STRING

    @SerializedName("numeroPreimpreso")
    @Column(name = "NumeroPreimpreso")
    var numeroPreimpreso: String? = Constant.EMPTY_STRING

    @SerializedName("observacionEntrega")
    @Column(name = "ObservacionEntrega")
    var observacionEntrega: String? = Constant.EMPTY_STRING

    @SerializedName("origenIngreso")
    @Column(name = "OrigenIngreso")
    var origenIngreso: Int? = Constant.NUMERO_CERO

    @SerializedName("paso")
    @Column(name = "Paso")
    var paso: Int = Constant.NUMERO_CERO

    @SerializedName("paisID")
    @Column(name = "PaisID")
    var paisID: Int = Constant.NUMERO_CERO

    @SerializedName("pais")
    @Column(name = "Pais")
    var pais: String? = Constant.EMPTY_STRING

    @SerializedName("poblacionVilla")
    @Column(name = "PoblacionVilla")
    var poblacionVilla: String? = Constant.EMPTY_STRING

    @SerializedName("primerNombre")
    @Column(name = "PrimerNombre")
    var primerNombre: String? = Constant.EMPTY_STRING

    @SerializedName("primerNombreAval")
    @Column(name = "PrimerNombreAval")
    var primerNombreAval: String? = Constant.EMPTY_STRING

    @SerializedName("razonSocial")
    @Column(name = "RazonSocial")
    var razonSocial: String? = Constant.EMPTY_STRING

    @SerializedName("referencia")
    @Column(name = "Referencia")
    var referencia: String? = Constant.EMPTY_STRING

    @SerializedName("referenciaEntrega")
    @Column(name = "ReferenciaEntrega")
    var referenciaEntrega: String? = Constant.EMPTY_STRING

    @SerializedName("requiereAval")
    @Column(name = "RequiereAval")
    var requiereAval: Int = Constant.NUMERO_CERO

    @SerializedName("requiereAprobacionSAC")
    @Column(name = "RequiereAprobacionSAC")
    var requiereAprobacionSAC: Boolean = false

    @SerializedName("respuestaGEO")
    @Column(name = "RespuestaGEO")
    var respuestaGEO: String? = Constant.EMPTY_STRING

    @SerializedName("regimenContable")
    @Column(name = "RegimenContable")
    var regimenContable: Int? = Constant.NUMERO_CERO

    @SerializedName("segundoNombre")
    @Column(name = "SegundoNombre")
    var segundoNombre: String? = Constant.EMPTY_STRING

    @SerializedName("segundoNombreAval")
    @Column(name = "SegundoNombreAval")
    var segundoNombreAval: String? = Constant.EMPTY_STRING

    @SerializedName("sexo")
    @Column(name = "Sexo")
    var sexo: String? = Constant.EMPTY_STRING

    @SerializedName("solicitudPostulanteID")
    @Column(name = "SolicitudPostulanteID")
    var solicitudPostulanteID: Int = Constant.NUMERO_CERO

    @SerializedName("subEstadoPostulante")
    @Column(name = "SubEstadoPostulante")
    var subEstadoPostulante: Int = Constant.NUMERO_CERO

    @SerializedName("telefono")
    @Column(name = "Telefono")
    var telefono: String? = Constant.EMPTY_STRING

    @SerializedName("celular")
    @Column(name = "Celular")
    var celular: String? = Constant.EMPTY_STRING

    @SerializedName("telefonoAval")
    @Column(name = "TelefonoAval")
    var telefonoAval: String? = Constant.EMPTY_STRING

    @SerializedName("telefonoEntrega")
    @Column(name = "TelefonoEntrega")
    var telefonoEntrega: String? = Constant.EMPTY_STRING

    @SerializedName("telefonoFamiliar")
    @Column(name = "TelefonoFamiliar")
    var telefonoFamiliar: String? = Constant.EMPTY_STRING

    @SerializedName("telefonoNoFamiliar")
    @Column(name = "TelefonoNoFamiliar")
    var telefonoNoFamiliar: String? = Constant.EMPTY_STRING

    @SerializedName("teRecomendoOtraConsultora")
    @Column(name = "TeRecomendoOtraConsultora")
    var teRecomendoOtraConsultora: Int = Constant.NUMERO_CERO

    @SerializedName("tieneExperiencia")
    @Column(name = "TieneExperiencia")
    var tieneExperiencia: Int? = Constant.NUMERO_CERO

    @SerializedName("tipoContacto")
    @Column(name = "TipoContacto")
    var tipoContacto: String? = Constant.EMPTY_STRING

    @SerializedName("tipoDocumento")
    @Column(name = "TipoDocumento")
    var tipoDocumento: String? = Constant.EMPTY_STRING

    @SerializedName("tipoDocumentoAval")
    @Column(name = "TipoDocumentoAval")
    var tipoDocumentoAval: String? = Constant.EMPTY_STRING

    @SerializedName("tipoDocumentoLegal")
    @Column(name = "TipoDocumentoLegal")
    var tipoDocumentoLegal: String? = Constant.EMPTY_STRING

    @SerializedName("tipoMeta")
    @Column(name = "TipoMeta")
    var tipoMeta: String? = Constant.EMPTY_STRING

    @SerializedName("tipoNacionalidad")
    @Column(name = "TipoNacionalidad")
    var tipoNacionalidad: String? = Constant.EMPTY_STRING

    @SerializedName("tipoPersona")
    @Column(name = "TipoPersona")
    var tipoPersona: Int? = Constant.NUMERO_CERO

    @SerializedName("tipoSolicitud")
    @Column(name = "TipoSolicitud")
    var tipoSolicitud: String? = Constant.EMPTY_STRING

    @SerializedName("tipoVia")
    @Column(name = "TipoVia")
    var tipoVia: String? = Constant.EMPTY_STRING

    @SerializedName("tipoVinculoAval")
    @Column(name = "TipoVinculoAval")
    var tipoVinculoAval: Int? = Constant.NUMERO_CERO

    @SerializedName("tipoVinculoFamiliar")
    @Column(name = "TipoVinculoFamiliar")
    var tipoVinculoFamiliar: Int? = Constant.NUMERO_CERO

    @SerializedName("tipoVinculoNoFamiliar")
    @Column(name = "TipoVinculoNoFamiliar")
    var tipoVinculoNoFamiliar: Int? = Constant.NUMERO_CERO

    @SerializedName("usuarioCreacion")
    @Column(name = "UsuarioCreacion")
    var usuarioCreacion: String? = Constant.EMPTY_STRING

    @SerializedName("vistoPorGZ")
    @Column(name = "VistoPorGZ")
    var vistoPorGZ: Int = Constant.NUMERO_CERO

    @SerializedName("vistoPorSAC")
    @Column(name = "VistoPorSAC")
    var vistoPorSAC: Int = Constant.NUMERO_CERO

    @SerializedName("vistoPorSE")
    @Column(name = "VistoPorSE")
    var vistoPorSE: Int = Constant.NUMERO_CERO

    @SerializedName("fechaEnvio")
    @Column(name = "FechaEnvio")
    var fechaEnvio: String? = Constant.EMPTY_STRING

    @SerializedName("fechaIngreso")
    @Column(name = "FechaIngreso")
    var fechaIngreso: String? = Constant.EMPTY_STRING

    @SerializedName("tipoRechazo")
    @Column(name = "TipoRechazo")
    var tipoRechazo: String? = Constant.EMPTY_STRING

    @SerializedName("motivoRechazo")
    @Column(name = "MotivoRechazo")
    var motivoRechazo: String? = Constant.EMPTY_STRING

    @SerializedName("imagenCDDUrl")
    @Column(name = "ImagenCDD")
    var imagenCDD: String? = Constant.EMPTY_STRING

    @SerializedName("imagenIFEUrl")
    @Column(name = "ImagenIFE")
    var imagenIFE: String? = Constant.EMPTY_STRING

    @SerializedName("imagenContratoUrl")
    @Column(name = "ImagenContrato")
    var imagenContrato: String? = Constant.EMPTY_STRING

    @SerializedName("imagenPagareUrl")
    @Column(name = "ImagenPagare")
    var imagenPagare: String? = Constant.EMPTY_STRING

    @SerializedName("imagenDniAvalUrl")
    @Column(name = "ImagenDniAval")
    var imagenDniAval: String? = Constant.EMPTY_STRING

    @SerializedName("imagenReciboOtraMarcaUrl")
    @Column(name = "ImagenReciboOtraMarca")
    var imagenReciboOtraMarca: String? = Constant.EMPTY_STRING

    @SerializedName("imagenReciboPagoAvalUrl")
    @Column(name = "ImagenReciboPagoAval")
    var imagenReciboPagoAval: String? = Constant.EMPTY_STRING

    @SerializedName("imagenCreditoAvalUrl")
    @Column(name = "ImagenCreditoAval")
    var imagenCreditoAval: String? = Constant.EMPTY_STRING

    @SerializedName("imagenConstanciaLaboralAvalUrl")
    @Column(name = "ImagenConstanciaLaboralAval")
    var imagenConstanciaLaboralAval: String? = Constant.EMPTY_STRING

    @SerializedName("sincronizado")
    @Column(name = "Sincronizado")
    var sincronizado: Boolean = true

    @SerializedName("devueltoPorSAC")
    @Column(name = "DevueltoPorSAC")
    var devueltoPorSAC: Boolean = false

    @SerializedName("ingresoAnticipado")
    @Column(name = "EsIngresoAnticipado")
    var ingresoAnticipado: Int = Constant.NUMERO_CERO

    @SerializedName("campaniaDeIngreso")
    @Column(name = "CampaniaDeIngreso")
    var campaniaDeIngreso: Int = Constant.NUMERO_CERO

    @SerializedName("vieneDe")
    @Column(name = "VieneDe")
    var vieneDe: String? = Constant.EMPTY_STRING

    @SerializedName("tipoPago")
    @Column(name = "TipoPago")
    var tipoPago: Int = Constant.NUMERO_CERO

    @SerializedName("tipoPagoNombre")
    @Column(name = "TipoPagoNombre")
    var tipoPagoNombre: String? = Constant.EMPTY_STRING

    @SerializedName("fechaEnvioValidarPorGZ")
    @Column(name = "FechaEnvioValidarPorGZ")
    var fechaEnvioValidarPorGZ: String? = null

    @SerializedName("appId")
    var appId = Constant.NUMERO_UNO

    @SerializedName("termsAceptados")
    var termsAceptados: Boolean = false

    @SerializedName("ip")
    @Column(name = "ip")
    var ip: String? = Constant.EMPTY_STRING

    @SerializedName("deviceId")
    @Column(name = "deviceId")
    var deviceId: String? = Constant.EMPTY_STRING

    @SerializedName("soMobile")
    @Column(name = "soMobile")
    var soMobile: String? = Constant.EMPTY_STRING

    @SerializedName("UrlFirma")
    @Column(name = "UrlFirma")
    var UrlFirma: String = Constant.EMPTY_STRING

    @SerializedName("link")
    @Column(name = "Link")
    var link: String = Constant.EMPTY_STRING

    var tipoRechazoExplanation: String? = Constant.EMPTY_STRING

}
