package biz.belcorp.salesforce.core.entities.sql.focos.asignacion

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class)
class GzCampaniaJoined : BaseQueryModel() {

    @Column(name = "consultoraID")
    var consultoraId: Long? = null

    @Column(name = "CodRegion")
    var codRegion: String? = null

    @Column(name = "CodZona")
    var codZona: String? = null

    @Column(name = "Estado")
    var estado: String? = null

    @Column(name = "PrimerApellido")
    var primerApellido: String? = null

    @Column(name = "PrimerNombre")
    var primerNombre: String? = null

    @Column(name = "Usuario")
    var usuario: String? = null

    @Column(name = "EsNueva", getterName = "getEsNueva")
    var esNueva: Boolean = false

    @Column(name = "NroCampaniasComoNueva")
    var nroCampaniasComoNueva: Int = 0

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
