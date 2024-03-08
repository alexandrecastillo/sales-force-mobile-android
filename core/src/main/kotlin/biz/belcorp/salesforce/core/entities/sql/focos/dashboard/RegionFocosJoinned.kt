package biz.belcorp.salesforce.core.entities.sql.focos.dashboard

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel

@QueryModel(database = AppDatabase::class)
class RegionFocosJoinned {

    @Column(name = "consultoraID")
    var consultoraId: Long? = null

    @Column(name = "Codigo")
    var codigo: String? = null

    @Column(name = "CodCliente")
    var codigoCliente: String? = null

    @Column(name = "GerenteZona")
    var gerenteZona: String? = null

    @Column(name = "Usuario")
    var usuario: String? = null

    @Column(name = "PrimerNombre")
    var primerNombre: String? = null

    @Column(name = "PrimerApellido")
    var primerApellido: String? = null

    @Column(name = "Focos")
    var focos: String? = null

    @Column(name = "Habilidades")
    var habilidades: String? = null

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

    @Column(name = "Periodo")
    var periodo: String = ""

    @Column(name = "PrimerDiaFacturacion")
    var esPrimerDiaFacturacion: Boolean = false
}
