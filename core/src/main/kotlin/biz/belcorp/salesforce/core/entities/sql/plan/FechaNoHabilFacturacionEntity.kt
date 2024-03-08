package biz.belcorp.salesforce.core.entities.sql.plan


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "FechaNoHabilFacturacion")
class FechaNoHabilFacturacionEntity : BaseModel() {

    @Column(name = "CodigoZona")
    @SerializedName("codigoZona")
    @PrimaryKey
    var codigoZona: String? = null

    @Column(name = "Fecha")
    @SerializedName("fecha")
    @PrimaryKey
    var fecha: String? = null

}
