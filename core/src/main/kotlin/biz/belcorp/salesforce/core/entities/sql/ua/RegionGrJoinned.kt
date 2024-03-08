package biz.belcorp.salesforce.core.entities.sql.ua

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel

@QueryModel(database = AppDatabase::class)
class RegionGrJoinned {

    @Column(name = "Codigo")
    var codigo: String? = null

    @Column(name = "Usuario")
    var usuario: String? = null

    @Column(name = "consultoraID")
    var consultoraId: Long? = null

    @Column(name = "PrimerNombre")
    var nombreGerente: String? = null

    @Column(name = "PrimerApellido")
    var apellidoGerente: String? = null

    @Column(name = "MailBelcorp")
    var email: String? = null

    @Column(name = "NroDoc")
    var documento: String? = null

    @Column(name = "FechaNacimiento")
    var cumpleanios: String? = null

    @Column(name = "TelefCasa")
    var telefonoCasa: String? = null

    @Column(name = "TelefMovil")
    var celular: String? = null

    @Column(name = "Estado")
    var estado: String? = null

    @Column(name = "ID")
    var planId: Long? = null

    @Column(name = "TotalPlanificadas")
    var totalPlanificadas: Int = 0

    @Column(name = "TotalVisitadas")
    var totalVisitadas: Int = 0

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
