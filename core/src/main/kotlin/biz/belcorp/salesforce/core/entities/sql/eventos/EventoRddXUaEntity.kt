package biz.belcorp.salesforce.core.entities.sql.eventos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.utils.formatLong
import biz.belcorp.salesforce.core.utils.string
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import java.util.*

@Table(database = AppDatabase::class, name = "EventoRddXUa", useBooleanGetterSetters = false)
class EventoRddXUaEntity(

    @Column(name = "Id")
    @PrimaryKey
    @SerializedName("id")
    var id: Long? = null,

    @Column(name = "IdEventoLocal")
    @SerializedName("eventoIdLocal")
    var idEventoLocal: Long = 0,

    @Column(name = "IdEventoServer")
    @SerializedName("eventoId")
    var idEventoServer: Long = 0,

    @Column(name = "Region")
    @SerializedName("region")
    var region: String? = null,

    @Column(name = "Zona")
    @SerializedName("zona")
    var zona: String? = null,

    @Column(name = "Seccion")
    @SerializedName("seccion")
    var seccion: String? = null,

    @Column(name = "IdServidor")
    @SerializedName("eventoRDDPorUAId")
    var idServidor: Long? = null,

    @Column(name = "Activo")
    @SerializedName("activo")
    var activo: Boolean = true,

    @Column(name = "Asistira")
    @SerializedName("asistira")
    var asistira: Boolean = true,

    @Column(name = "FechaModificacion")
    @SerializedName("fechaCreacion")
    var fechaModificacion: String? = null,

    @Column(name = "UsuarioModificacion")
    @SerializedName("usuarioCreacion")
    var usuarioModificacion: String? = null,

    @Column(name = "EsCreador")
    @SerializedName("creadorEvento")
    var esCreador: Boolean = false,

    @Column(name = "Enviado")
    @SerializedName("enviado")
    var enviado: Boolean? = null,

    @Column(name = "DesdeServidor")
    @SerializedName("desdeServidor")
    var desdeServidor: Boolean? = null,

    @Column(name = "IndicaCumplimiento")
    @SerializedName("indicaCumplimiento")
    var indicaCumplimiento: Boolean = false,

    @Column(name = "FechaCumplimiento")
    @SerializedName("fechaCumplimiento")
    var fechaCumplimiento: String? = null,

    @SerializedName("esPropia")
    val esPropio: Boolean = false
) :

    BaseModel() {

    fun marcarseComoNuevo() {
        fechaModificacion = Date().string(formatLong)
        activo = true
        enviado = false
        asistira = true
        idServidor = null
        desdeServidor = false
    }

    fun marcarseComoCreador() {
        esCreador = true
    }

    fun marcarseComoDesdeServidor() {
        id = null
        desdeServidor = true
        enviado = true
    }

    fun marcarComoRegistrado(fechaRegistro: String) {
        indicaCumplimiento = true
        fechaCumplimiento = fechaRegistro
        enviado = false
    }

    fun marcarseComoRechazado() {
        asistira = false
        enviado = false
    }

    fun marcarseComoEliminado() {
        activo = false
    }

    fun asignarUsuario(usuario: String) {
        usuarioModificacion = usuario
    }

    fun tipificarComoEliminado(asistira: Boolean) {
        if (asistira)
            tipoCambio = TipoCambio.ELIMINADO
    }

    fun tipificarComoEditado() {
        tipoCambio = TipoCambio.EDITADO
    }

    fun tipificarComoNuevo() {
        tipoCambio = TipoCambio.NUEVO
    }

    var tipoCambio: TipoCambio? = null

    enum class TipoCambio {
        NUEVO,
        EDITADO,
        ELIMINADO
    }
}
