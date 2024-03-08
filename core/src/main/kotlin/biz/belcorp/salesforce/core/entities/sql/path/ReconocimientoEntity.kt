package biz.belcorp.salesforce.core.entities.sql.path


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "Reconocimientos")
class ReconocimientoEntity : BaseModel() {

    @Column(name = "IdReconocimiento")
    @SerializedName("idReconocimiento")
    @PrimaryKey
    var id: Long = 0

    @Column(name = "CodigoConsultoraReconoce")
    @SerializedName("codigoConsultoraReconoce")
    var idPersonaReconoce: String = ""

    @Column(name = "CodRolReconoce")
    @SerializedName("codRolReconoce")
    var rolPersonaReconoce: String = ""

    @Column(name = "CodigoPersonaReconocida")
    @SerializedName("codigoPersonaReconocida")
    var idPersonaReconocida: String = ""

    @Column(name = "CodRolReconocida")
    @SerializedName("codRolReconocida")
    var rolPersonaReconocida: String = ""

    @Column(name = "NombrePersonaReconocida")
    @SerializedName("nombrePersonaReconocida")
    var nombrePersonaReconocida: String = ""

    @Column(name = "Campania")
    @SerializedName("campania")
    var campania: String = ""

    @Column(name = "Calificacion")
    @SerializedName("calificacion")
    var valoracion: Int = 0

    @Column(name = "Comentarios")
    @SerializedName("comentarios")
    var comentarios: String? = ""

    @Column(name = "Pendiente")
    @SerializedName("pendiente")
    var envioPendiente: Int = 0

    fun grabar() {
        if (!this.exists())
            this.insert()
    }

}
