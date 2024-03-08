package biz.belcorp.salesforce.core.entities.sql.plan

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "PlanRutaRDD")
class PlanRutaRDDEntity : BaseModel() {

    @Column(name = "ID")
    @SerializedName("id")
    @PrimaryKey
    var id: Int = 0

    @Column(name = "Pais")
    @SerializedName("pais")
    var pais: String? = null

    @Column(name = "Campania")
    @SerializedName("campania")
    var campania: String? = null

    @Column(name = "Region")
    @SerializedName("region")
    var region: String? = null

    @Column(name = "Zona")
    @SerializedName("zona")
    var zona: String? = null

    @Column(name = "Seccion")
    @SerializedName("seccion")
    var seccion: String? = null

    @Column(name = "Rol")
    @SerializedName("rol")
    var rol: String? = null

    @Column(name = "Usuario")
    @SerializedName("usuario")
    var usuario: String? = null

    @Column(name = "TotalPlanificadas")
    @SerializedName("totalPlanificadas")
    var totalPlanificadas: Int = 0

    @Column(name = "TotalVisitadas")
    @SerializedName("totalVisitadas")
    var totalVisitadas: Int = 0

    @Column(name = "DiasVisitando")
    @SerializedName("diasVisitando")
    var visitedDays: Int = 0

}
