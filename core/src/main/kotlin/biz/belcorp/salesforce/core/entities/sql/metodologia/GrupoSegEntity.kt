package biz.belcorp.salesforce.core.entities.sql.metodologia


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "ListadoGrupoMensajeSegmentacion")
class GrupoSegEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "Codigo")
    @SerializedName("codigo")
    var codigo: Int? = 0

    @Column(name = "Nombre")
    @SerializedName("nombre")
    var nombre: String? = null

}
