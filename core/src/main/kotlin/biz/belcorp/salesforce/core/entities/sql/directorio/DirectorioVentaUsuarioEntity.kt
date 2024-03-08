package biz.belcorp.salesforce.core.entities.sql.directorio


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "DirectorioVentaUsuario", useBooleanGetterSetters = false)
class DirectorioVentaUsuarioEntity : BaseModel() {

    @Column(name = "CodCliente")
    @SerializedName("codCliente")
    @PrimaryKey
    var codCliente: String? = null

    @Column(name = "consultoraID")
    @SerializedName("consultoraID")
    var consultoraId: Long? = null

    @Column(name = "PrimerApellido")
    @SerializedName("primerApellido")
    var primerApellido: String? = null

    @Column(name = "SegundoApellido")
    @SerializedName("segundoApellido")
    var segundoApellido: String? = null

    @Column(name = "PrimerNombre")
    @SerializedName("primerNombre")
    var primerNombre: String? = null

    @Column(name = "NroDoc")
    @SerializedName("nroDoc")
    var nroDoc: String? = null

    @Column(name = "CodRegion")
    @SerializedName("codRegion")
    var codRegion: String? = null

    @Column(name = "CodZona")
    @SerializedName("codZona")
    var codZona: String? = null

    @Column(name = "CodRol")
    @SerializedName("codRol")
    var codRol: String? = null

    @Column(name = "MailBelcorp")
    @SerializedName("mailBelcorp")
    var mailBelcorp: String? = null

    @Column(name = "TelefCasa")
    @SerializedName("telefCasa")
    var telefCasa: String? = null

    @Column(name = "TelefMovil")
    @SerializedName("telefMovil")
    var telefMovil: String? = null

    @Column(name = "Usuario")
    @SerializedName("usuario")
    var usuario: String? = null

    @Column(name = "FechaIngreso")
    @SerializedName("fechaIngreso")
    var fechaIngreso: String? = null

    @Column(name = "FechaNacimiento")
    @SerializedName("fechaNacimiento")
    var fechaNacimiento: String? = null

    @Column(name = "AnoCampanaIngreso")
    @SerializedName("anoCampanaIngreso")
    var anioCampanaIngreso: String? = null

    @Column(name = "Estado")
    @SerializedName("estado")
    var estado: String? = null

    @Column(name = "ImpFacturado")
    @SerializedName("impFacturado")
    var impFacturado: Double? = null

    @Column(name = "Cobranza21")
    @SerializedName("cobranza21")
    var cobranza21: Double? = null

    @Column(name = "PorcRecuperacion")
    @SerializedName("porcRecuperacion")
    var porcRecuperacion: Double? = null

    @Column(name = "SaldoPendiente")
    @SerializedName("saldoPendiente")
    var saldoPendiente: Double? = null

    @Column(name = "ConsultorasDeuda")
    @SerializedName("consultorasDeuda")
    var consultorasDeuda: Int? = 0

    @Column(name = "EsNueva")
    @SerializedName("esNueva")
    var esNueva: Boolean = false

    @Column(name = "NroCampaniasComoNueva")
    @SerializedName("nroCampaniaComoNueva")
    var nroCampaniasComoNueva: Int = 0
}
