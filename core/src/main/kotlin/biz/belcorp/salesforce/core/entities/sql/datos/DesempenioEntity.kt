package biz.belcorp.salesforce.core.entities.sql.datos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "Desempenio")
class DesempenioEntity : BaseModel() {

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

    @Column(name = "Estado")
    @SerializedName("estado")
    var estado: String? = null

}
