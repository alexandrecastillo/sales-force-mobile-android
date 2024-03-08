package biz.belcorp.salesforce.core.entities.sql.plan


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "DetallePlanRutaRDD", useBooleanGetterSetters = false)
class DetallePlanRutaRDDEntity : BaseModel() {

    @Column(name = "ID")
    @SerializedName("id")
    @PrimaryKey
    var id: Long = 0

    @Column(name = "PlanVisitaID")
    @SerializedName("planVisitaID")
    var idPlanVisita: Long = 0

    @Column(name = "CodigoConsultora")
    @SerializedName("codigoConsultora")
    var codigoConsultora: String? = null

    @Column(name = "NumeroDocumento")
    @SerializedName("numeroDocumento")
    var numeroDocumento: String? = null

    @Column(name = "TipoObservacion")
    @SerializedName("tipoObservacion")
    var tipoObservacion: Int? = null

    @Column(name = "FechaPlanificacion")
    @SerializedName("fechaPlanificacion")
    var fechaPlanificacion: String? = null

    @Column(name = "FechaReprogramacion")
    @SerializedName("fechaReprogramacion")
    var fechaReprogramacion: String? = null

    @Column(name = "FechaVisita")
    @SerializedName("fechaVisita")
    var fechaVisita: String? = null

    @Column(name = "UsuarioRed")
    @SerializedName("usuarioRed")
    var usuarioRed: String? = null

    @Column(name = "Planificado")
    @SerializedName("planificado")
    var planificado: Int = 0

    @Column(name = "TipsID")
    @SerializedName("tipsID")
    var tipsid: Int = 0

    @Column(name = "Enviado")
    @SerializedName("enviado")
    var enviado: Int? = null

    @Column(name = "ConsultorasID")
    @SerializedName("consultorasID")
    var consultoraId: Long? = null

    @Column(name = "Rol")
    @SerializedName("rol")
    var rol: String? = null

    @Column(name = "PlanInicial")
    @SerializedName("planInicial")
    var planInicial: Int = 0

    @Column(name = "IDLocal")
    @SerializedName("idLocal")
    var idLocal: Long = 0

    @Column(name = "EsAdicional")
    @SerializedName("esAdicional")
    var esAdicional: Boolean = false
}
