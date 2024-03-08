package biz.belcorp.salesforce.core.entities.sql.inspires

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "InspiraAvances")
class InspiraAvancesEntity : BaseModel() {

    @Column(name = "Campania")
    @PrimaryKey
    @SerializedName("campania")
    var campania: Int = 0

    @Column(name = "PuntosAcumulados")
    @SerializedName("puntosAcumulados")
    var puntosAcumulados: Int = 0

    @Column(name = "StatusNivel")
    @SerializedName("statusNivel")
    var statusNivel: String? = null

    @Column(name = "PorcRetencionActivas")
    @SerializedName("porcRetencionActivas")
    var porcRetencionActivas: Int? = null

    @Column(name = "Actividad")
    @SerializedName("actividad")
    var actividad: String? = null

    @Column(name = "RangoActividad")
    @SerializedName("rangoActividad")
    var rangoActividad: Int? = null

    @Column(name = "PorcMontoPedido")
    @SerializedName("porcMontoPedido")
    var porcMontoPedido: Int? = null

    @Column(name = "PorcPAV")
    @SerializedName("porcPAV")
    var porcPAV: Int? = null

    @Column(name = "Capitalizacion")
    @SerializedName("capitalizacion")
    var capitalizacion: Int? = null

    @Column(name = "Retencion")
    @SerializedName("retencion")
    var retencion: Int? = null
}
