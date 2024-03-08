package biz.belcorp.salesforce.core.entities.sql.graficos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel


@Table(database = AppDatabase::class, name = "ConfiguracionGraficosGr")
class ConfiguracionGraficosGrEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "Region")
    @SerializedName("region")
    var region: String? = ""

    @PrimaryKey
    @Column(name = "Zona")
    @SerializedName("zona")
    var zona: String? = ""

    @PrimaryKey
    @Column(name = "Seccion")
    @SerializedName("seccion")
    var seccion: String? = ""

    @PrimaryKey
    @SerializedName("tipoGrafico")
    @Column(name = "TipoGrafico")
    var tipoGrafico: Int = 0

    @SerializedName("titulo")
    @Column(name = "titulo")
    var titulo: String? = null

    @SerializedName("valor")
    @Column(name = "valor")
    var valor: String? = null

    @SerializedName("orden")
    @Column(name = "Orden")
    var orden: Int? = null
}
