package biz.belcorp.salesforce.core.entities.sql.inspires

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "InspiraCondicionesLeyendaDetalle")
class InspiraCondicionesLeyendaDetalleEntity : BaseModel() {

    @PrimaryKey(autoincrement = true)
    @Column(name = "Id")
    @SerializedName("id")
    var id: Long? = 0

    @Column(name = "Codigo")
    @SerializedName("codigo")
    var codigo: String? = null

    @Column(name = "Rango")
    @SerializedName("rango")
    var rango: String? = null

    @Column(name = "Puntos")
    @SerializedName("puntos")
    var puntos: String? = null

}
