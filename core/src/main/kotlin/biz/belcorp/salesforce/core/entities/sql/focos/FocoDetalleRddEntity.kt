package biz.belcorp.salesforce.core.entities.sql.focos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "FocosDetalleRDD")
class FocoDetalleRddEntity : BaseModel() {

    @Column(name = "Region")
    @SerializedName("region")
    @PrimaryKey
    var region: String? = null

    @Column(name = "Zona")
    @SerializedName("zona")
    @PrimaryKey
    var zona: String? = null

    @Column(name = "Seccion")
    @SerializedName("seccion")
    @PrimaryKey
    var seccion: String? = null

    @Column(name = "Usuario")
    @SerializedName("usuario")
    var usuario: String? = null

    @Column(name = "Focos")
    @SerializedName("focos")
    var focos: String? = null

    @Column(name = "Campania")
    @SerializedName("campania")
    @PrimaryKey
    var campania: String? = null

    @Column(name = "Enviado")
    @SerializedName("enviado")
    var enviado: Int = 1
}
