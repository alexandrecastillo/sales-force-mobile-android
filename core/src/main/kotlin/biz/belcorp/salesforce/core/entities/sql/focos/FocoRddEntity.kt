package biz.belcorp.salesforce.core.entities.sql.focos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "FocosRDD")
class FocoRddEntity : BaseModel() {

    @Column(name = "Codigo")
    @SerializedName("codigo")
    @PrimaryKey
    var codigo: Long = -1

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

    @Column(name = "EsActivo")
    @SerializedName("esActivo")
    var esActivo: Int = -1

}
