package biz.belcorp.salesforce.core.entities.sql.campania


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "CampaniaUa", useBooleanGetterSetters = false)
class CampaniaUaEntity : BaseModel() {

    companion object {
        const val ORDEN_CAMPANIA_ACTUAL = 1
    }

    @Column(name = "CampaniaId")
    @SerializedName("campaniaId")
    var campaniaId: Long = -1L

    @Column(name = "Orden")
    @SerializedName("orden")
    var orden: Int = 0

    @Column(name = "Codigo")
    @SerializedName("codigo")
    @PrimaryKey
    var codigo: String = ""

    @Column(name = "FechaInicio")
    @SerializedName("fechaInicio")
    var fechaInicio: String = ""

    @Column(name = "FechaFin")
    @SerializedName("fechaFin")
    var fechaFin: String = ""

    @Column(name = "FechaInicioFacturacion")
    @SerializedName("fechaInicioFacturacion")
    var fechaInicioFacturacion: String = ""

    @Column(name = "NombreCorto")
    @SerializedName("nombreCorto")
    var nombreCorto: String = ""

    @Column(name = "Region")
    @SerializedName("region")
    @PrimaryKey
    var region: String = "-"

    @Column(name = "Zona")
    @SerializedName("zona")
    @PrimaryKey
    var zona: String = "-"

    @Column(name = "Seccion")
    @SerializedName("seccion")
    @PrimaryKey
    var seccion: String = "-"

    @Column(name = "Periodo")
    @SerializedName("flagTipoCampania")
    var periodo: String = ""

    @Column(name = "PrimerDiaFacturacion")
    @SerializedName("flagInicioFacturacion")
    var primerDiaFacturacion: Boolean = false
}
