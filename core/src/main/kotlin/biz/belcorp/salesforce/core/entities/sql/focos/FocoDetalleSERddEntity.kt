package biz.belcorp.salesforce.core.entities.sql.focos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "FocosDetalleSE_RDD")
class FocoDetalleSERddEntity : BaseModel() {

    @Column(name = "ConsultoraID")
    @SerializedName("consultoraID")
    @PrimaryKey
    var consultoraId: Long = -1

    @Column(name = "Focos")
    @SerializedName("focos")
    var focos: String? = null

    @Column(name = "Campania")
    @SerializedName("campania")
    @PrimaryKey
    var campania: String? = null

    @Column(name = "Enviado")
    @SerializedName("enviado")
    var enviado: Int = 1

}
