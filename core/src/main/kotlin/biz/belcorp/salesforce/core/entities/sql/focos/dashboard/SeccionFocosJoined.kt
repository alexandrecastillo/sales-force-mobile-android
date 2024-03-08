package biz.belcorp.salesforce.core.entities.sql.focos.dashboard

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel

@QueryModel(database = AppDatabase::class)
class SeccionFocosJoined {

    @Column(name = "CodigoSeccion")
    var codigoSeccion: String? = null

    @Column(name = "ConsultoraID")
    var consultoraId: Long? = null

    @Column(name = "PrimerNombre")
    var primerNombre: String? = null

    @Column(name = "PrimerApellido")
    var primerApellido: String? = null

    @Column(name = "Focos")
    var focos: String? = null

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
