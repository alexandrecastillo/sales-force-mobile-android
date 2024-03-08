package biz.belcorp.salesforce.core.entities.sql.habilidades


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel


@Table(database = AppDatabase::class, name = "DesarrolloHabilidades")
class DesarrolloHabilidadEntity : BaseModel() {

    @Column(name = "Codigo")
    @SerializedName("codigo")
    @PrimaryKey
    var codigo: Long = -1

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

    @Column(name = "IconoID")
    @SerializedName("iconoID")
    var iconoId: Int = -1

    @Column(name = "Porcentaje")
    @SerializedName("porcentaje")
    var porcentaje: Int = -1

    @Column(name = "Region")
    @SerializedName("region")
    @PrimaryKey
    var region: String = ""

    @Column(name = "Zona")
    @SerializedName("zona")
    @PrimaryKey
    var zona: String = ""

    @Column(name = "Seccion")
    @SerializedName("seccion")
    @PrimaryKey
    var seccion: String = ""

    @Column(name = "Campania")
    @SerializedName("campania")
    var campania: String = ""
}
