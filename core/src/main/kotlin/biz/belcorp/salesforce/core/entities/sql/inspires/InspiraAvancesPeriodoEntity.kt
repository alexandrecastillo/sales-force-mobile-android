package biz.belcorp.salesforce.core.entities.sql.inspires

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "InspiraAvancesPeriodo")
class InspiraAvancesPeriodoEntity : BaseModel() {

    @Column(name = "CodigoPeriodo")
    @PrimaryKey
    @SerializedName("codigoPeriodo")
    var codigoPeriodo: String = ""

    @Column(name = "Periodo")
    @SerializedName("periodo")
    var periodo: String = ""

    @Column(name = "CampaniaFinPeriodo")
    @SerializedName("campaniaFinPeriodo")
    var campaniaFinPeriodo: String = ""

    @Column(name = "Puntaje")
    @SerializedName("puntaje")
    var puntaje: Int = 0
}
