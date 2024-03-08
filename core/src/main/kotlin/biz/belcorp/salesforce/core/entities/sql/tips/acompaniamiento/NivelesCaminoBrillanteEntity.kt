package biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "NivelesCaminoBrillante")
class NivelesCaminoBrillanteEntity : BaseModel() {

    @SerializedName("codigo")
    @PrimaryKey
    @Column(name = "Codigo")
    var codigo: Int = 0

    @SerializedName("nombre")
    @Column(name = "Nombre")
    var nombre: String = ""

    @SerializedName("orden")
    @Column(name = "Orden")
    var orden: Int = 0
}
