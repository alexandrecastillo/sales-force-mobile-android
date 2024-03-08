package biz.belcorp.salesforce.core.entities.sql.acompaniamiento


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "AcuerdosCumplimientoConsolidado")
data class CumplimientoConsolidadoEntity(

    @SerializedName("region")
    @Column(name = "Region")
    @PrimaryKey
    var region: String = "-",

    @SerializedName("zona")
    @Column(name = "Zona")
    @PrimaryKey
    var zona: String = "-",

    @SerializedName("seccion")
    @Column(name = "Seccion")
    @PrimaryKey
    var seccion: String = "-",

    @SerializedName("consultoraID")
    @Column(name = "ConsultoraId")
    @PrimaryKey
    var consultoraId: Long = -1L,

    @SerializedName("valores")
    @Column(name = "Valores")
    var valores: String = "[]"
) : BaseModel()
