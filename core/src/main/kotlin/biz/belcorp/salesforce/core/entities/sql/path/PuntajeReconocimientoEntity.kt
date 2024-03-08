package biz.belcorp.salesforce.core.entities.sql.path


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "PuntajeReconocimientos")
class PuntajeReconocimientoEntity : BaseModel() {

    @Column(name = "Campania")
    @SerializedName("campania")
    @PrimaryKey
    var campania: String? = ""

    @Column(name = "Region")
    @SerializedName("region")
    @PrimaryKey
    var region: String? = ""

    @Column(name = "Zona")
    @SerializedName("zona")
    @PrimaryKey
    var zona: String? = ""

    @Column(name = "Seccion")
    @SerializedName("seccion")
    @PrimaryKey
    var seccion: String? = ""

    @Column(name = "Rol")
    @SerializedName("rol")
    var rol: String? = ""

    @Column(name = "Puntaje")
    @SerializedName("puntaje")
    var puntaje: Float? = 0f

}
