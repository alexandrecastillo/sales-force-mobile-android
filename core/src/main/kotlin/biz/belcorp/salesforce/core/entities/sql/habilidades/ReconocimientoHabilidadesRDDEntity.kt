package biz.belcorp.salesforce.core.entities.sql.habilidades


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "ReconocimientoHabilidadesRDD")
class ReconocimientoHabilidadesRDDEntity : BaseModel() {

    @Column(name = "Campania")
    @SerializedName("campania")
    @PrimaryKey
    var campania: String = ""

    @Column(name = "Region")
    @SerializedName("region")
    @PrimaryKey
    var region: String = ""

    @Column(name = "Zona")
    @SerializedName("zona")
    @PrimaryKey
    var zona: String = ""

    @Column(name = "Seccion")
    @SerializedName("seccion")
    @PrimaryKey
    var seccion: String = ""

    @Column(name = "UsuarioReconoce")
    @SerializedName("usuarioReconoce")
    var usuarioReconoce: String? = null

    @Column(name = "UsuarioReconocida")
    @SerializedName("usuarioReconocida")
    var usuarioReconocida: String? = null

    @Column(name = "Habilidades")
    @SerializedName("habilidades")
    var habilidades: String? = null

    @Column(name = "Enviado")
    @SerializedName("enviado")
    var enviado: Int = 1

}
