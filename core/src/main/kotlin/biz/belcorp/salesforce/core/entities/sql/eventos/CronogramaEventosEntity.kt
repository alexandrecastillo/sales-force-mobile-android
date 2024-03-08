package biz.belcorp.salesforce.core.entities.sql.eventos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "CronogramaEventos")
class CronogramaEventosEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "Campania")
    @SerializedName("campania")
    var campania: String? = null

    @PrimaryKey
    @Column(name = "CodigoRegion")
    @SerializedName("codigoRegion")
    var codigoRegion: String? = null

    @PrimaryKey
    @Column(name = "CodigoZona")
    @SerializedName("codigoZona")
    var codigoZona: String? = null

    @PrimaryKey
    @Column(name = "CodigoActividad")
    @SerializedName("codigoActividad")
    var codigoActividad: String? = null

    @Column(name = "NombreActividad")
    @SerializedName("nombreActividad")
    var nombreActividad: String? = null

    @Column(name = "FechaInicio")
    @SerializedName("fechaInicio")
    var fechaInicio: String? = null

}
