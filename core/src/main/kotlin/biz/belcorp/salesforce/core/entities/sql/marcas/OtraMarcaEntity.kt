package biz.belcorp.salesforce.core.entities.sql.marcas


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "OtraMarcaEntity", useBooleanGetterSetters = false)
class OtraMarcaEntity : BaseModel() {

    @SerializedName("marcaId")
    @PrimaryKey
    @Column(name = "marcaId")
    var marcaId: Long = 0L

    @SerializedName("marca")
    @Column(name = "name")
    var name: String? = null

    @SerializedName("iconoId")
    @Column(name = "iconoId")
    var iconoId: Int? = null

    @SerializedName("orden")
    @Column(name = "orden")
    var orden: Int? = null

    @SerializedName("mostrarFrente")
    @Column(name = "showFront")
    var showFront: Boolean = false

    @SerializedName("categoria")
    @Column(name = "categoria")
    var categoria: String = "-"

    @SerializedName("campania")
    @Column(name = "campania")
    var campania: String = "-"
}
