package biz.belcorp.salesforce.core.entities.sql.consultora


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel


@Table(database = AppDatabase::class, name = "ConsultorasPdVCierrePeriodo")
class ConsultorasPdVCierrePeriodoEntity : BaseModel() {

    @Column(name = "consultoraID")
    @SerializedName("consultoraID")
    @PrimaryKey
    var consultoraID: Int = 0

    @Column(name = "codigoConsultora")
    @SerializedName("codigoConsultora")
    var codigoConsultora: String? = null

    @Column(name = "seccion")
    @SerializedName("seccion")
    var seccion: String? = null

    @Column(name = "nivel")
    @SerializedName("nivel")
    var nivelActual: String? = null

}
