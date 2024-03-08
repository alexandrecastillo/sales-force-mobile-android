package biz.belcorp.salesforce.core.entities.sql.datos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "AcuerdosVisitaRDD")
class AcuerdoEntity : BaseModel() {

    @Column(name = "ID")
    @SerializedName("id")
    var serverId: Long? = null

    @Column(name = "Campania")
    @SerializedName("campania")
    var campania: String? = null

    @Column(name = "Region")
    @SerializedName("region")
    var region: String? = null

    @Column(name = "Zona")
    @SerializedName("zona")
    var zona: String? = null

    @Column(name = "Seccion")
    @SerializedName("seccion")
    var seccion: String? = null

    @Column(name = "ConsultoraID")
    @SerializedName("consultoraID")
    var consultoraId: Long? = null

    @Column(name = "Contenido")
    @SerializedName("contenido")
    var comentario: String? = null

    @Column(name = "FechaRegistro")
    @SerializedName("fechaRegistro")
    var fechaComentario: String? = null

    @Column(name = "Cumplimiento")
    @SerializedName("cumplimiento")
    var cumplimiento: Int? = null

    @Column(name = "Activo")
    @SerializedName("activo")
    var activo: Int? = null

    @Column(name = "IdLocal")
    @PrimaryKey(autoincrement = true)
    @SerializedName("idLocal")
    var idLocal: Long = 0

    @Column(name = "Enviado")
    @SerializedName("enviado")
    var enviado: Int? = null

    companion object {
        const val CUMPLIDO = 1
    }
}
