package biz.belcorp.salesforce.core.entities.sql.personas

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class)
class CabeceraGRJoinned : BaseQueryModel() {

    @Column(name = "consultoraID")
    var consultoraId: Long? = null

    @Column(name = "PrimerApellido")
    var primerApellido: String? = null

    @Column(name = "SegundoApellido")
    var segundoApellido: String? = null

    @Column(name = "PrimerNombre")
    var primerNombre: String? = null

    @Column(name = "FechaNacimiento")
    var fechaNacimiento: String? = null

    @Column(name = "TelefMovil")
    var telefMovil: String? = null

    @Column(name = "MailBelcorp")
    var mailBelcorp: String? = null

    @Column(name = "Estado")
    var estado: String? = null

    @Column(name = "CodZona")
    var codigoZona: String? = null

    @Column(name = "CodRegion")
    var codigoRegion: String? = null

    @Column(name = "Usuario")
    var usuario: String? = null

    @Column(name = "NroDoc")
    var nroDoc: String? = null

    @Column(name = "Codigo")
    var campaniaCodigo: String = ""

    @Column(name = "FechaInicio")
    var campaniaInicio: String = ""

    @Column(name = "FechaFin")
    var campaniaFin: String = ""

    @Column(name = "FechaInicioFacturacion")
    var campaniaInicioFacturacion: String = ""

    @Column(name = "NombreCorto")
    var campaniaNombreCorto: String = ""

    @Column(name = "Orden")
    var campaniaOrden: Int = 0

    @Column(name = "Periodo")
    var periodo: String = ""

    @Column(name = "PrimerDiaFacturacion")
    var esPrimerDiaFacturacion: Boolean = false
}
