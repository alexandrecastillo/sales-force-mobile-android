package biz.belcorp.salesforce.core.entities.calculator

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "UpperLevel")
class UpperLevelEntity : BaseModel() {

    @Column(name = "ID")
    @SerializedName("nivelID")
    @PrimaryKey
    var nivelID: Int? = null

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

}
