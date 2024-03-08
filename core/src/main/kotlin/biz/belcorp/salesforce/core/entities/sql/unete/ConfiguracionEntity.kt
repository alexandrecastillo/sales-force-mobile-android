package biz.belcorp.salesforce.core.entities.sql.unete


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "UneteConfiguracion", useBooleanGetterSetters = false)
class ConfiguracionEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "UUID")
    var uuid: String = java.util.UUID.randomUUID().toString()

    @Column(name = "Pais")
    @SerializedName("pais")
    var pais: String? = null

    @Column(name = "Rol")
    @SerializedName("rol")
    var rol: String? = null

    @Column(name = "EdicionPaso1")
    @SerializedName("edicionPaso1")
    var edicionPaso1: Boolean = false

    @Column(name = "EdicionPaso2")
    @SerializedName("edicionPaso2")
    var edicionPaso2: Boolean = false

    @Column(name = "EdicionPaso3")
    @SerializedName("edicionPaso3")
    var edicionPaso3: Boolean = false

    @Column(name = "EdicionPaso4")
    @SerializedName("edicionPaso4")
    var edicionPaso4: Boolean = false

    @Column(name = "EdicionPaso5")
    @SerializedName("edicionPaso5")
    var edicionPaso5: Boolean = false

    @Column(name = "ValidarBuro")
    @SerializedName("validarBuro")
    var validarBuro: Boolean = false

    @Column(name = "RechazarPreAprobada")
    @SerializedName("rechazarPreAprobada")
    var rechazarPreAprobada: Boolean = false

    @Column(name = "RechazarEnEvaluacion")
    @SerializedName("rechazarEnEvaluacion")
    var rechazarEnEvaluacion: Boolean = false

    @Column(name = "CapturarXYPaso2")
    @SerializedName("capturarXYPaso2")
    var capturarXYPaso2: Boolean = false

    @Column(name = "AutoCompletarDireccion")
    @SerializedName("autoCompletarDireccion")
    var autoCompletarDireccion: Boolean = false

    @Column(name = "ValidarMail")
    @SerializedName("validarMail")
    var validarMail: Boolean = false

    @Column(name = "ValidarCelular")
    @SerializedName("validarCelular")
    var validarCelular: Boolean = false

}
