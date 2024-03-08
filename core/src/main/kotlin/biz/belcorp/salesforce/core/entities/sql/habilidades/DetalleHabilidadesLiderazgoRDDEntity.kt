package biz.belcorp.salesforce.core.entities.sql.habilidades


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "DetalleHabilidadesLiderazgoRDD")
class DetalleHabilidadesLiderazgoRDDEntity : BaseModel() {

    @Column(name = "CodigoHabilidadRDD")
    @SerializedName("codigoHabilidadRDD")
    @PrimaryKey
    var codigoHabilidadRDD: Int = 0

    @Column(name = "CodigoDetalle")
    @SerializedName("codigoDetalle")
    @PrimaryKey
    var codigoDetalle: Int = 0

    @Column(name = "TipoTexto")
    @SerializedName("tipoTexto")
    var tipoTexto: Int = 0

    @Column(name = "Texto")
    @SerializedName("texto")
    var texto: String? = null

}
