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

@Table(database = AppDatabase::class, name = "EventoRdd", useBooleanGetterSetters = false)
class EventoRddEntity(

    @Column(name = "Id")
    @PrimaryKey
    @SerializedName("eventoIdLocal")
    var id: Long? = null,

    @Column(name = "FechaInicio")
    @SerializedName("fechaInicio")
    var fechaInicio: String? = null,

    @Column(name = "FechaFin")
    @SerializedName("fechaFin")
    var fechaFin: String? = null,

    @Column(name = "TodoElDia", getterName = "getTodoElDia")
    @SerializedName("todoElDia")
    var todoElDia: Boolean = false,

    @Column(name = "IdTipo")
    @SerializedName("tipoEventoId")
    var idTipo: Long = 0,

    @Column(name = "DescripcionPersonalizada")
    @SerializedName("descripcionPersonalizada")
    var descripcionPersonalizada: String? = null,

    @Column(name = "CompartirObligatorioInicial")
    @SerializedName("compartirObligatorioInicial")
    var compartirObligatorioInicial: Boolean? = null,

    @Column(name = "UsuarioCreacion")
    @SerializedName("usuarioCreacion")
    var usuarioCreacion: String? = null,

    @Column(name = "FechaModificacion")
    @SerializedName("fechaCreacion")
    var fechaModificacion: String? = null,

    @Column(name = "Alertar")
    @SerializedName("alertar")
    var alertar: Boolean = false,

    @Column(name = "TiempoAlerta")
    @SerializedName("tiempoAlerta")
    var tiempoAlerta: Int? = null,

    @Column(name = "UnidadTiempoAlerta")
    @SerializedName("unidadTiempoAlerta")
    var unidadTiempoAlerta: String? = null,

    @Column(name = "Ubicacion")
    @SerializedName("ubicacion")
    var ubicacion: String? = null,

    @Column(name = "Activo")
    @SerializedName("activo")
    var activo: Boolean = true,

    @Column(name = "Enviado")
    @SerializedName("enviado")
    var enviado: Boolean? = null,

    @Column(name = "IdServidor")
    @SerializedName("eventoId")
    var idServidor: Long? = null,

    @Column(name = "DesdeServidor")
    @SerializedName("desdeServidor")
    var desdeServidor: Boolean? = null,

    @Column(name = "Campania")
    @SerializedName("campania")
    var campania: String? = null,

    @Column(name = "Region")
    @SerializedName("Region")
    var region: String? = null,

    @Column(name = "Zona")
    @SerializedName("Zona")
    var zona: String? = null,

    @Column(name = "Seccion")
    @SerializedName("seccion")
    var seccion: String? = null


) : BaseModel() {

    fun marcarseComoNuevo() {
        fechaModificacion = Date().string(formatLong)
        activo = true
        enviado = false
        idServidor = null
        desdeServidor = false
    }

    fun marcarseComoEnviado() {
        enviado = true
        desdeServidor = true
    }
}
