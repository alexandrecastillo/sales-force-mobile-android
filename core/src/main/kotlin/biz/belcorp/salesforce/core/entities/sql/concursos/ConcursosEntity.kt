package biz.belcorp.salesforce.core.entities.sql.concursos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "Concursos")
class ConcursosEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "PersonaId")
    @SerializedName("personaId")
    var personaId: Long = -1

    @PrimaryKey
    @Column(name = "Region")
    @SerializedName("region")
    var region: String = "-"

    @PrimaryKey
    @Column(name = "Zona")
    @SerializedName("zona")
    var zona: String = "-"

    @PrimaryKey
    @Column(name = "Seccion")
    @SerializedName("seccion")
    var seccion: String = "-"

    @Column(name = "Data")
    @SerializedName("data")
    var data: String = "[]"
}
