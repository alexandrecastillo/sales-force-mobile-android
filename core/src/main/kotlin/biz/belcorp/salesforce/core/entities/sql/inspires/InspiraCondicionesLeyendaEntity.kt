package biz.belcorp.salesforce.core.entities.sql.inspires

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "InspiraCondicionesLeyenda")
class InspiraCondicionesLeyendaEntity : BaseModel() {
    @Column(name = "Codigo")
    @PrimaryKey
    @SerializedName("codigo")
    var codigo: String? = null

    @Column(name = "Titulo")
    @SerializedName("titulo")
    var titulo: String? = null

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

    @Column(name = "Nota")
    @SerializedName("nota")
    var nota: String? = null
}
