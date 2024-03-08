package biz.belcorp.salesforce.core.entities.sql.path


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "VisitaXFechaRDD")
class VisitaXFechaRDDEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "FechaVisita")
    @SerializedName("fechaVisita")
    var fechaVisita: String? = null

    @Column(name = "CantidadVisita")
    @SerializedName("cantidadVisita")
    var cantidadVisita: Int = -1

    @PrimaryKey
    @Column(name = "ID")
    @SerializedName("id")
    var planId: Long = -1

    @Column(name = "Enviado")
    @SerializedName("enviado")
    var enviado: Int? = null

}
