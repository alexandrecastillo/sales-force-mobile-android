package biz.belcorp.salesforce.core.entities.sql.inspires

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "InspiraCondiciones")
class InspiraCondicionesEntity : BaseModel() {
    @Column(name = "Codigo")
    @PrimaryKey
    @SerializedName("codigo")
    var codigo: String? = null

    @Column(name = "Condicion")
    @SerializedName("condicion")
    var condicion: String? = null

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null
}

