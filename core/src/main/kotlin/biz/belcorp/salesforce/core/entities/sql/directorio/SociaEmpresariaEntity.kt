package biz.belcorp.salesforce.core.entities.sql.directorio


import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "SociaEmpresaria")
class SociaEmpresariaEntity : BaseModel() {

    @Column(name = "ConsultorasId")
    @SerializedName("consultorasID")
    @PrimaryKey
    var consultorasId: Long = 0

    @Column(name = "Codigo")
    @SerializedName("codigo")
    var codigo: String? = null

    @Column(name = "DocumentoIdentidad")
    @SerializedName("documentoIdentidad")
    var documentoIdentidad: String? = null

    @Column(name = "PrimerNombre")
    @SerializedName("primerNombre")
    var primerNombre: String? = null

    @Column(name = "SegundoNombre")
    @SerializedName("segundoNombre")
    var segundoNombre: String? = null

    @Column(name = "PrimerApellido")
    @SerializedName("primerApellido")
    var primerApellido: String? = null

    @Column(name = "SegundoApellido")
    @SerializedName("segundoApellido")
    var segundoApellido: String? = null

    @Column(name = "Email")
    @SerializedName("email")
    var email: String? = null

    @Column(name = "TelefonoCelular")
    @SerializedName("telefonoCelular")
    var telefonoCelular: String? = null

    @Column(name = "TelefonoCasa")
    @SerializedName("telefonoCasa")
    var telefonoCasa: String? = null

    @Column(name = "TelefonoTrabajo")
    @SerializedName("telefonoTrabajo")
    var telefonoTrabajo: String? = null

    @Column(name = "Cumpleanios")
    @SerializedName("cumpleanios")
    var cumpleanios: String? = null

    @Column(name = "Aniversario")
    @SerializedName("aniversario")
    var aniversario: String? = null

    @Column(name = "Latitud")
    @SerializedName("latitud")
    var latitud: String? = null

    @Column(name = "Longitud")
    @SerializedName("longitud")
    var longitud: String? = null

    @Column(name = "Direccion")
    @SerializedName("direccion")
    var direccion: String? = null

    @Column(name = "Region")
    @SerializedName("region")
    var region: String? = null

    @Column(name = "Zona")
    @SerializedName("zona")
    var zona: String? = null

    @Column(name = "Seccion")
    @SerializedName("seccion")
    var seccion: String? = null

    @Column(name = "CampaniaIngreso")
    @SerializedName("campaniaIngreso")
    var campaniaIngreso: String? = null

    @Column(name = "UltimaFacturacion")
    @SerializedName("ultimaFacturacion")
    var ultimaFacturacion: String? = null

    @Column(name = "SaldoPendiente")
    @SerializedName("saldoPendiente")
    var saldoPendiente: String? = null

    @Column(name = "VentaGanancia")
    @SerializedName("ventaGanancia")
    var ventaGanancia: String? = null

    @Column(name = "ConsultoraConsecutiva")
    @SerializedName("consultoraConsecutiva")
    var consultoraConsecutiva: String? = null

    @Column(name = "VentaFacturada")
    @SerializedName("ventaFacturada")
    var ventaFacturada: String? = null

    @Column(name = "RecaudoComisionable")
    @SerializedName("recaudoComisionable")
    var recaudoComisionable: String? = null

    @Column(name = "Ganancia")
    @SerializedName("ganancia")
    var ganancia: String? = null

    @Column(name = "RecaudoTotal")
    @SerializedName("recaudoTotal")
    var recaudoTotal: String? = null

    @Column(name = "RecaudoNoComisionable")
    @SerializedName("recaudoNoComisionable")
    var recaudoNoComisionable: String? = null

    @Column(name = "GananciaRetail")
    @SerializedName("gananciaRetail")
    var gananciaRetail: String? = null

    @Column(name = "VentaRetail")
    @SerializedName("ventaRetail")
    var ventaRetail: String? = null

    @Column(name = "FechaNacimiento")
    @SerializedName("fechaNacimiento")
    var fechaNacimiento: String = ""

    @Column(name = "FechaAniversario")
    @SerializedName("fechaAniversario")
    var fechaAniversario: String = ""

    @Column(name = "Exitosa")
    @SerializedName("exitosa")
    var exitosa: Int? = null

    @Column(name = "Nivel")
    var nivel: String = EMPTY_STRING

}
