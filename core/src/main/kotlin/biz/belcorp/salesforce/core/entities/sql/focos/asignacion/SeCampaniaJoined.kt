package biz.belcorp.salesforce.core.entities.sql.focos.asignacion

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class)
class SeCampaniaJoined : BaseQueryModel() {

    @Column(name = "ConsultorasId")
    var consultoraId: Long = 0

    @Column(name = "PrimerNombre")
    var primerNombre: String? = null

    @Column(name = "PrimerApellido")
    var primerApellido: String? = null

    @Column(name = "Region")
    var region: String? = null

    @Column(name = "Zona")
    var zona: String? = null

    @Column(name = "Seccion")
    var seccion: String? = null

    @Column(name = "Codigo")
    var codigoCampania: String = ""

    @Column(name = "FechaInicio")
    var fechaInicio: String = ""

    @Column(name = "FechaFin")
    var fechaFin: String = ""

    @Column(name = "FechaInicioFacturacion")
    var fechaInicioFacturacion: String = ""

    @Column(name = "NombreCorto")
    var nombreCorto: String = ""

    @Column(name = "Periodo")
    var periodo: String = ""

    @Column(name = "PrimerDiaFacturacion")
    var esPrimerDiaFacturacion: Boolean = false
}
