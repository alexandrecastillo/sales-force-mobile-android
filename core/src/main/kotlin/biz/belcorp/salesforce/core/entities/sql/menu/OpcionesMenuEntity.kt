package biz.belcorp.salesforce.core.entities.sql.menu


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "OpcionesMenu")
class OpcionesMenuEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "Id")
    @SerializedName("id")
    var id: Int = 0

    @Column(name = "CodOpcion")
    @SerializedName("codOpcion")
    var codOpcion: Int = 0

    @Column(name = "OpcionMenu")
    @SerializedName("opcionMenu")
    var opcionMenu: String? = null

    @Column(name = "Posicion")
    @SerializedName("posicion")
    var posicion: Int = 0

    @Column(name = "Tipo")
    @SerializedName("tipo")
    var tipo: String? = null

}
