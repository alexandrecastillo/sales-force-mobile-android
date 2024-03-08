package biz.belcorp.salesforce.core.entities.sql.filtros


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "TiposSegmentos")
class TipoSegmentoEntity : BaseModel() {

    @Column(name = "SegmentoInternoId")
    @PrimaryKey
    @SerializedName("segmentoInternoId")
    var segmentoInternoId: Int = 0

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

    @Column(name = "Abreviatura")
    @SerializedName("abreviatura")
    var abreviatura: String? = null

    @Column(name = "EstadoActivo")
    @SerializedName("estadoActivo")
    var estadoActivo: String? = null

}
