package biz.belcorp.salesforce.core.entities.calculator

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "PaymentDetail")
class PaymentDetailEntity : BaseModel() {

    @Column(name = "Id")
    @SerializedName("id")
    @PrimaryKey(autoincrement = true)
    var id: Long = 0

    @Column(name = "Constancia")
    @SerializedName("constancia")
    var constancia: String? = null

    @Column(name = "Cantidad")
    @SerializedName("cantidad")
    var cantidad: Int? = null

    @Column(name = "Mensaje")
    @SerializedName("mensaje")
    var mensaje: String? = null

}
