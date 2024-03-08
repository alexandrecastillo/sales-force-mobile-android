package biz.belcorp.salesforce.core.entities.sql.tips.rdd

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "DetalleTipsVisitaRDD")
class DetalleTipsVisitaRDDEntity : BaseModel() {

    @Column(name = "ID")
    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0

    @Column(name = "TipsID")
    @SerializedName("tipsID")
    var tipsID: String? = null

    @Column(name = "Rol")
    @SerializedName("rol")
    var rol: String? = null

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

}
