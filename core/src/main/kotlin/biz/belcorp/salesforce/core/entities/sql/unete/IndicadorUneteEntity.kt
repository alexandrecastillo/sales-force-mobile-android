package biz.belcorp.salesforce.core.entities.sql.unete


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "IndicadorUnete")
class IndicadorUneteEntity : BaseModel() {

    @Column(name = "Id")
    @PrimaryKey(autoincrement = true)
    var id: Int = 0

    @Column(name = "CampaniaActual")
    @SerializedName("campaniaActual")
    var campaniaActual: String? = null

    @Column(name = "Seccion")
    @SerializedName("seccion")
    var seccion: String? = null

    @Column(name = "EnEvaluacion")
    @SerializedName("enEvaluacion")
    var enEvaluacion: Int? = null

    @Column(name = "PreAprobadas")
    @SerializedName("preAprobadas")
    var preAprobadas: Int? = null

    @Column(name = "Aprobadas")
    @SerializedName("aprobadas")
    var aprobadas: Int? = null

    @Column(name = "Rechazadas")
    @SerializedName("rechazadas")
    var rechazadas: Int? = null

    @Column(name = "Conversion")
    @SerializedName("conversion")
    var conversion: Int? = null

    @Column(name = "DiasEnEspera")
    @SerializedName("diasEnEspera")
    var diasEnEspera: Int? = null

    @Column(name = "IngresosAnticipados")
    @SerializedName("ingresosAnticipados")
    var ingresosAnticipados: Int? = null

    @Column(name = "PreInscritas")
    @SerializedName("preInscritas")
    var preInscritas: Int? = null

    @Column(name = "RegularizarDocumento")
    @SerializedName("regularizarDocumento")
    var regularizarDocumento: Int? = null
}
