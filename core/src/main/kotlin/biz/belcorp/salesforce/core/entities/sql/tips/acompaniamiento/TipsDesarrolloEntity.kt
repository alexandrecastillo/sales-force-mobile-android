package biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "TipsDesarrollo")
class TipsDesarrolloEntity : BaseModel() {

    @SerializedName("personaId")
    @PrimaryKey
    @Column(name = "PersonaId")
    var personaId: Long = -1L

    @SerializedName("region")
    @PrimaryKey
    @Column(name = "Region")
    var region: String = "-"

    @SerializedName("zona")
    @PrimaryKey
    @Column(name = "Zona")
    var zona: String = "-"

    @SerializedName("seccion")
    @PrimaryKey
    @Column(name = "Seccion")
    var seccion: String = "-"

    @SerializedName("data")
    @Column(name = "Data")
    var data: String = "[]"
}
