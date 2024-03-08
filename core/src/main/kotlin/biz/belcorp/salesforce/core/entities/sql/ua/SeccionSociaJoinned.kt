package biz.belcorp.salesforce.core.entities.sql.ua

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class)
class SeccionSociaJoinned : BaseQueryModel() {

    @Column(name = "SeccionId")
    var seccionId: Int = 0

    @Column(name = "Codigo")
    var codigoSeccion: String = ""

    @Column(name = "Zona")
    var codigoZona: String = ""

    @Column(name = "Region")
    var codigoRegion: String = ""

    @Column(name = "Nivel")
    var nivel: String = ""

    @Column(name = "Exitosa")
    var exitosa: Int = -1

    @Column(name = "Estado")
    var estado: String = ""

    @Column(name = "ConsultorasId")
    var consultoraId: Long? = null

    @Column(name = "PrimerNombre")
    var primerNombre: String? = null

    @Column(name = "PrimerApellido")
    var primerApellido: String? = null

    @Column(name = "PlanId")
    var planId: Long? = null

    @Column(name = "TotalPlanificadas")
    var planificadas: Int = 0

    @Column(name = "TotalVisitadas")
    var visitadas: Int = 0


    @Column(name = "CodigoCampania")
    var codigoCampania: String = ""

    @Column(name = "FechaInicio")
    var fechaInicio: String = ""

    @Column(name = "FechaFin")
    var fechaFin: String = ""

    @Column(name = "FechaInicioFacturacion")
    var fechaInicioFacturacion: String = ""

    @Column(name = "NombreCorto")
    var nombreCorto: String = ""

    @Column(name = "DiasVisitando")
    var visitedDays: Int = 0
}
