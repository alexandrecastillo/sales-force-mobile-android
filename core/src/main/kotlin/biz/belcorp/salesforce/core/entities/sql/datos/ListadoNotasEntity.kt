package biz.belcorp.salesforce.core.entities.sql.datos

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "ListadoNotasConsultora")
class ListadoNotasEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "NotaConsultoraID")
    @SerializedName("notaConsultoraID")
    var notaConsultoraID: Long? = 0

    @Column(name = "ConsultoraID")
    @SerializedName("consultoraID")
    var consultoraID: Int? = 0

    @Column(name = "Comentario")
    @SerializedName("comentario")
    var comentario: String? = null

    @Column(name = "FechaModificacion")
    @SerializedName("fechaModificacion")
    var fechaModificacion: String? = null

    @Column(name = "FechaModificacionMovil")
    @SerializedName("fechaModificacionMovil")
    var fechaModificacionMovil: String? = null

    @Column(name = "Pendiente")
    var pendiente: Int? = 0

}
