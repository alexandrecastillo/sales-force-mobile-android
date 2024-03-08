package biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "NivelActualCaminoBrillante")
class NivelActualCaminoBrillanteEntity : BaseModel() {

    @SerializedName("consultoraID")
    @Column(name = "ConsultoraId")
    @PrimaryKey
    var consultoraId: Long = -1L

    @SerializedName("region")
    @Column(name = "Region")
    @PrimaryKey
    var region: String = "-"

    @SerializedName("zona")
    @Column(name = "Zona")
    @PrimaryKey
    var zona: String = "-"

    @SerializedName("seccion")
    @Column(name = "Seccion")
    @PrimaryKey
    var seccion: String = "-"

    @SerializedName("actual")
    @Column(name = "Actual")
    var nivelActual: Int = 0

    @SerializedName("ventaAcumulada")
    @Column(name = "Progreso")
    var progreso: Long = -1

    @SerializedName("hito")
    @Column(name = "Hito")
    var hito: Long = -1

    @SerializedName("meta")
    @Column(name = "Meta")
    var meta: Long = -1

    @SerializedName("data")
    @Column(name = "Data")
    var data: String = "[]"
}
