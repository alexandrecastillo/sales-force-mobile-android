package biz.belcorp.salesforce.core.entities.sql.indicators

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel


@Table(database = AppDatabase::class, name = "IndicadorVisitasRDD")
class IndicadorVisitasRDDEntity : BaseModel() {

    @SerializedName("visitadasDV")
    @Column(name = "visitadasDV")
    var visitadasDV: Int? = 0

    @SerializedName("planificadasDV")
    @Column(name = "planificadasDV")
    var planificadasDV: Int? = 0

    @SerializedName("visitadasGR")
    @Column(name = "visitadasGR")
    var visitadasGR: Int?= 0

    @SerializedName("planificadasGR")
    @Column(name = "planificadasGR")
    var planificadasGR: Int? = 0

    @SerializedName("visitadasGZ")
    @Column(name = "visitadasGZ")
    var visitadasGZ: Int? = 0

    @SerializedName("planificadasGZ")
    @Column(name = "planificadasGZ")
    var planificadasGZ: Int? = 0

    @SerializedName("visitadasSE")
    @Column(name = "visitadasSE")
    var visitadasSE: Int? = 0

    @SerializedName("planificadasSE")
    @Column(name = "planificadasSE")
    var planificadasSE: Int? = 0

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
}
