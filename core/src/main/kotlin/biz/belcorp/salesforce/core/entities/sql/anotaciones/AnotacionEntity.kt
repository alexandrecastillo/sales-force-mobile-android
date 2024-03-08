package biz.belcorp.salesforce.core.entities.sql.anotaciones


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "Anotaciones")
class AnotacionEntity : BaseModel() {

    @Column(name = "ID")
    @SerializedName("id")
    var idServer: Long? = null

    @Column(name = "Tipo")
    @SerializedName("tipo")
    var tipo: String = ""

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

    @Column(name = "IDConsultora")
    @SerializedName("idConsultora")
    var personaId: Long? = null

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

    @PrimaryKey(autoincrement = true)
    @Column(name = "IDLocal")
    @SerializedName("idLocal")
    var idLocal: Long = -1L

    @Column(name = "Activo")
    @SerializedName("activo")
    var activo: Int = 0

    @Column(name = "Enviado")
    @SerializedName("enviado")
    var enviado: Int = 0

    @Column(name = "FechaModificacion")
    @SerializedName("fechaModificacion")
    var fechaModificacion: String? = null
}
