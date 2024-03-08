package biz.belcorp.salesforce.core.entities.sql.geo


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel


@Table(database = AppDatabase::class, name = "RutaOptima")
class RutaOptimaEntity : BaseModel() {

    @Column(name = "ID")
    @PrimaryKey
    @SerializedName("id")
    var id: Long = 0

    @Column(name = "Puntos")
    @SerializedName("puntos")
    var puntos: String = ""

    @Column(name = "PuntosDePaso")
    @SerializedName("waypoints")
    var waypoints: String = ""

    @Column(name = "Origen")
    @SerializedName("origen")
    var origen: String = ""

    @Column(name = "Destino")
    @SerializedName("destino")
    var destino: String = ""

}
