package biz.belcorp.salesforce.core.entities.sql.eventos

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class)
class EventoTipoEventoJoined(

    @Column(name = "Id")
    var id: Long = 0,

    @Column(name = "FechaInicio")
    var fechaInicio: String? = null,

    @Column(name = "FechaFin")
    var fechaFin: String? = null,

    @Column(
        name = "TodoElDia",
        getterName = "getTodoElDia"
    )
    var todoElDia: Boolean = false,

    @Column(name = "IdTipo")
    var idTipo: Long = 0,

    @Column(name = "DescripcionPersonalizada")
    var descripcionPersonalizada: String? = null,

    @Column(
        name = "CompartirObligatorioInicial",
        getterName = "getCompartirObligatorioInicial"
    )
    var compartirObligatorioInicial: Boolean = false,

    @Column(
        name = "Alertar",
        getterName = "getAlertar"
    )
    var alertar: Boolean = false,

    @Column(name = "TiempoAlerta")
    var tiempoAlerta: Int? = null,

    @Column(name = "UnidadTiempoAlerta")
    var unidadTiempoAlerta: String? = null,

    @Column(name = "Ubicacion")
    var ubicacion: String? = null,

    @Column(name = "Descripcion")
    var descripcion: String? = null,

    @Column(
        name = "CompartirObligatorio",
        getterName = "getCompartirObligatorio"
    )
    var compartirObligatorio: Boolean = false,

    @Column(name = "Rol")
    var rol: String? = null,

    @Column(
        name = "AceptaDescripcionPersonalizada",
        getterName = "getAceptaDescripcionPersonalizada"
    )
    var aceptaDescripcionPersonalizada: Boolean = false,

    @Column(name = "Region")
    var region: String = "-",

    @Column(
        name = "IndicaCumplimiento",
        getterName = "getIndicaCumplimiento"
    )
    var indicaCumplimiento: Boolean = false,

    @Column(
        name = "FechaCumplimiento",
        getterName = "getFechaCumplimiento"
    )
    var fechaCumplimiento: String? = null,

    @Column(name = "Zona")
    var zona: String = "-",

    @Column(name = "Seccion")
    var seccion: String = "-",

    @Column(
        name = "Activo",
        getterName = "getActivo"
    )
    var activo: Boolean = false

) : BaseQueryModel()
