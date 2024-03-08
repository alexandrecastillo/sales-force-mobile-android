package biz.belcorp.salesforce.core.entities.sql.inspires

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "InspiraIndicador")
class InspiraIndicadorEntity : BaseModel() {
    @Column(name = "Id")
    @SerializedName("id")
    var id: Int = 0

    @Column(name = "campania")
    @SerializedName("campania")
    var campania: Int = 0

    @Column(name = "ranking")
    @SerializedName("ranking")
    @PrimaryKey
    var ranking: Int = 0

    @Column(name = "destino")
    @SerializedName("destino")
    var destino: String = ""

    @Column(name = "nivel")
    @SerializedName("nivel")
    var nivel: String = ""

    @Column(name = "puntaje")
    @SerializedName("puntaje")
    var puntaje: String = ""

    @Column(name = "puntajeMax")
    @SerializedName("puntajeMax")
    var puntajeMax: String = ""

    @Column(name = "NombreSE")
    @SerializedName("nombreSE")
    var nombreSE: String = ""

    @Column(name = "CampaniaInicioPrograma")
    @SerializedName("campaniaInicioPrograma")
    var campaniaInicioPrograma: Int = 0

    @Column(name = "CampaniaFinPrograma")
    @SerializedName("campaniaFinPrograma")
    var campaniaFinPrograma: Int = 0

    @Column(name = "Activa", getterName = "getActiva")
    @SerializedName("activa")
    var activa: Boolean = false

    @Column(name = "TopeRanking")
    @SerializedName("topeRanking")
    var topeRanking: Int = 0

    @Column(name = "ImagenDestino")
    @SerializedName("imagenDestino")
    var imagenDestino: String? = null
}
