package biz.belcorp.salesforce.core.entities.sql.consultora

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "IndicadorCambioNivel")
class IndicadorCambioNivelEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "region")
    @SerializedName("region")
    var region: String? = null

    @PrimaryKey
    @Column(name = "zona")
    @SerializedName("zona")
    var zona: String? = null

    @PrimaryKey
    @Column(name = "seccion")
    @SerializedName("seccion")
    var seccion: String? = null

    @Column(name = "campaniaAnterior")
    @SerializedName("campaniaAnterior")
    var campaniaAnterior: String? = null

    @Column(name = "acumuladoPorNivel")
    @SerializedName("acumuladoPorNivel")
    var acumuladoPorNivel: String? = null

    @Column(name = "periodoActual")
    @SerializedName("periodoActual")
    var periodoActual: String? = null

    @Column(name = "totalCampaniaAnterior")
    @SerializedName("totalCampaniaAnterior")
    var totalCampaniaAnterior: String? = null

    @Column(name = "acumuladoPeriodo")
    @SerializedName("acumuladoPeriodo")
    var acumuladoPeriodo: String? = null

}
