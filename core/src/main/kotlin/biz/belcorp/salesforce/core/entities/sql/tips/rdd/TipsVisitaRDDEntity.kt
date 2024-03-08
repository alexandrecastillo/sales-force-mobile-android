package biz.belcorp.salesforce.core.entities.sql.tips.rdd


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "TipsVisitaRDD")
class TipsVisitaRDDEntity : BaseModel() {

    @Column(name = "ID")
    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0

    @Column(name = "Rol")
    @SerializedName("rol")
    var rol: String? = null

    @Column(name = "Segmento")
    @SerializedName("segmento")
    var segmento: String? = null

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

    @Column(name = "UrlVideo")
    @SerializedName("urlVideo")
    var urlVideo: String? = null

    @Column(name = "UrlDesc")
    @SerializedName("urlDesc")
    var urlDesc: String? = null

}
