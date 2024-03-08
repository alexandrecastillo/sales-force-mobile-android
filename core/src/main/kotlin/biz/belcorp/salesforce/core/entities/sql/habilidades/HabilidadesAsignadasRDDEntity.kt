package biz.belcorp.salesforce.core.entities.sql.habilidades


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "HabilidadesAsignadasRDD")
class HabilidadesAsignadasRDDEntity : BaseModel() {

    @Column(name = "Habilidades")
    @SerializedName("habilidades")
    var habilidades: String? = null

    @Column(name = "Campania")
    @SerializedName("campania")
    @PrimaryKey
    var campania: String? = ""

    @Column(name = "Zona")
    @SerializedName("zona")
    @PrimaryKey
    var codigoZona: String? = ""

    @Column(name = "Region")
    @SerializedName("region")
    @PrimaryKey
    var region: String? = ""

    @Column(name = "Seccion")
    @SerializedName("seccion")
    @PrimaryKey
    var seccion: String? = ""

    @Column(name = "Usuario")
    @SerializedName("usuario")
    var usuario: String? = null

    @Column(name = "Enviado")
    @SerializedName("enviado")
    var enviado: Int = 1

}
