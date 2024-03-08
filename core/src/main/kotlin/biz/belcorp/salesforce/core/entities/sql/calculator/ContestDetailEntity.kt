package biz.belcorp.salesforce.core.entities.calculator

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "ContestDetail")
class ContestDetailEntity : BaseModel() {

    @Column(name = "NivelSE")
    @SerializedName("nivelSE")
    var nivelSE: Int? = null

    @Column(name = "DescripcionNivel")
    @SerializedName("descripcionNivel")
    var descripcionNivel: String? = null

    @Column(name = "Variable1")
    @SerializedName("variable1")
    var variable1: String? = null

    @Column(name = "NivelBT1")
    @SerializedName("nivelBT1")
    @PrimaryKey
    var nivelBT1: Int? = null

    @Column(name = "Variable2")
    @SerializedName("variable2")
    var variable2: String? = null

    @Column(name = "NivelBT2")
    @SerializedName("nivelBT2")
    @PrimaryKey
    var nivelBT2: Int? = null

    @Column(name = "Bono")
    @SerializedName("bono")
    var bono: Double? = null

}
