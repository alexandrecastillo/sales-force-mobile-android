package biz.belcorp.salesforce.core.entities.sql.comportamientos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "ComportamientoDetallePorcentaje")
data class ComportamientoDetallePorcentajeEntity(

    @Column(name = "Id")
    @SerializedName("id")
    var id: Long? = null,

    @PrimaryKey
    @Column(name = "Campania")
    @SerializedName("campania")
    var campania: String = "-",

    @PrimaryKey
    @Column(name = "Region")
    @SerializedName("region")
    var region: String = "-",

    @PrimaryKey
    @Column(name = "Zona")
    @SerializedName("zona")
    var zona: String = "-",

    @PrimaryKey
    @Column(name = "Seccion")
    @SerializedName("seccion")
    var seccion: String = "",

    @PrimaryKey
    @Column(name = "ConsultoraID")
    @SerializedName("consultoraID")
    var consultoraID: Long = -1,

    @PrimaryKey
    @Column(name = "Comportamiento")
    @SerializedName("comportamiento")
    var comportamiento: String? = null,

    @Column(name = "ConsultoraRecomiendaID")
    @SerializedName("consultoraRecomiendaID")
    var consultoraRecomiendaID: Int? = null,

    @Column(name = "Porcentaje")
    @SerializedName("porcentaje")
    var porcentaje: Int? = null,

    @Column(name = "FechaModificacion")
    @SerializedName("fechaModificacion")
    var fechaModificacion: String? = null
) : BaseModel()
