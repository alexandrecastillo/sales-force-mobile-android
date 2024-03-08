package biz.belcorp.salesforce.core.entities.sql.datos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import java.util.*

@Table(database = AppDatabase::class, name = "ListadoMetasConsultoras")
class MetasEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "ConsultoraID")
    @SerializedName("consultoraID")
    var consultoraId: Int? = obtenerIdRandom()

    @Column(name = "Codigo")
    @SerializedName("codigo")
    var codigo: String? = null

    @Column(name = "IdMetaRegistro")
    @SerializedName("idMetaRegistro")
    var idMetaRegistro: Int? = 0

    @Column(name = "Monto")
    @SerializedName("monto")
    var monto: String? = "0"

    @Column(name = "IdTipoMeta")
    @SerializedName("idTipoMeta")
    var idTipoMeta: Int? = 0

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

    @Column(name = "Camp_Inic")
    @SerializedName("campInic")
    var campInic: String? = null

    @Column(name = "Camp_Fin")
    @SerializedName("campFin")
    var campFin: String? = null

    @Column(name = "Observaciones")
    @SerializedName("observaciones")
    var observaciones: String? = null

    @Column(name = "fechaRegistro")
    @SerializedName("fechaMovil")
    var fechaRegistro: String? = null

    @Column(name = "Pendiente")
    @SerializedName("pendiente")
    var pendiente: Int = 0


    private fun obtenerIdRandom() = Random().nextInt(60000)

    fun marcarseComoPendiente() {
        pendiente = 1
    }

    fun marcarseComoEnviado() {
        pendiente = 0
    }
}
