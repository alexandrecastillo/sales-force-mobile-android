package biz.belcorp.salesforce.core.entities.sql.unete


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "ListadoParametrosUnete")
class ParametroUneteEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "IdParametroUnete")
    @SerializedName("idParametroUnete")
    var idParametroUnete: Int = 0

    @Column(name = "FK_IdParametroUnete")
    @SerializedName("fK_IdParametroUnete")
    var idParametroUnetePadre: Int = 0

    @Column(name = "FK_IdTipoParametro")
    @SerializedName("fK_IdTipoParametro")
    var tipoParametro: Int = 0

    @Column(name = "Nombre")
    @SerializedName("nombre")
    var nombre: String? = null

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

    @Column(name = "Valor")
    @SerializedName("valor")
    var valor: String? = null

    @Column(name = "Estado")
    @SerializedName("estado")
    var estado: String? = null

}
