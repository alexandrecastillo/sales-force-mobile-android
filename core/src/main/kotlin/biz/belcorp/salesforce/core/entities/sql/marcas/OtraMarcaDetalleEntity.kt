package biz.belcorp.salesforce.core.entities.sql.marcas


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "OtraMarcaDetalleEntity", useBooleanGetterSetters = false)
class OtraMarcaDetalleEntity : BaseModel() {

    @SerializedName("marcaVentaId")
    @Column(name = "marcaVentaId")
    var marcaDetalleId: Long = 0L

    @SerializedName("marcaId")
    @PrimaryKey
    @Column(name = "marcaId")
    var marcaId: Long = 0L

    @SerializedName("consultoraId")
    @PrimaryKey
    @Column(name = "consultoraId")
    var consultoraId: Long = 0L

    @SerializedName("region")
    @PrimaryKey
    @Column(name = "region")
    var region: String = "-"

    @SerializedName("zona")
    @PrimaryKey
    @Column(name = "zona")
    var zona: String = "-"

    @SerializedName("seccion")
    @PrimaryKey
    @Column(name = "seccion")
    var seccion: String = "-"

    @SerializedName("esVendido")
    @Column(name = "checked")
    var checked: Boolean = false

    @SerializedName("enviado")
    @Column(name = "enviado")
    var enviado: Boolean = true

    @SerializedName("categoria")
    @Column(name = "categoria")
    var categoria: String = "-"

    @SerializedName("campania")
    @Column(name = "campania")
    var campania: String = "-"
}
