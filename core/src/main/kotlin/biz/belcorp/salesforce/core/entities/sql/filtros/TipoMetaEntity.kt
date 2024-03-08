package biz.belcorp.salesforce.core.entities.sql.filtros


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "ListadoTiposMetas")
class TipoMetaEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "IdTipoMeta")
    @SerializedName("idTipoMeta")
    var idTipoMeta: Int = 0

    @Column(name = "CodigoMeta")
    @SerializedName("codigoMeta")
    var codigoMeta: String? = null

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

    @Column(name = "MontoMinimo")
    @SerializedName("montoMinimo")
    var montoMinimo: String? = null

    @Column(name = "MontoMaximo")
    @SerializedName("montoMaximo")
    var montoMaximo: String? = null

}
