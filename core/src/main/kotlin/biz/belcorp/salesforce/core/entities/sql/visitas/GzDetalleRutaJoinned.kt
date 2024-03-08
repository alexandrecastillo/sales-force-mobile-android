package biz.belcorp.salesforce.core.entities.sql.visitas

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel

@QueryModel(database = AppDatabase::class)
class GzDetalleRutaJoinned {

    @Column(name = "ID")
    var id: Long = 0

    @Column(name = "PlanVisitaID")
    var idPlanVisita: Long = 0

    @Column(name = "FechaPlanificacion")
    var fechaPlanificacion: String? = null

    @Column(name = "FechaReprogramacion")
    var fechaReprogramacion: String? = null

    @Column(name = "FechaVisita")
    var fechaVisita: String? = null

    @Column(name = "Planificado")
    var planificado: Int = 0

    @Column(name = "TipsID")
    var tipsid: Int = 0

    @Column(name = "Rol")
    var rol: String? = null

    @Column(name = "PlanInicial")
    var planInicial: Int = 0

    @Column(name = "Enviado")
    var enviado: Int? = null

    @Column(name = "EsAdicional", getterName = "getEsAdicional")
    var esAdicional: Boolean = false

    @Column(name = "consultoraID")
    var consultoraId: Long? = null

    @Column(name = "PrimerApellido")
    var primerApellido: String? = null

    @Column(name = "SegundoApellido")
    var segundoApellido: String? = null

    @Column(name = "PrimerNombre")
    var primerNombre: String? = null

    @Column(name = "NroDoc")
    var nroDoc: String? = null

    @Column(name = "MailBelcorp")
    var mailBelcorp: String? = null

    @Column(name = "TelefCasa")
    var telefCasa: String? = null

    @Column(name = "TelefMovil")
    var telefMovil: String? = null

    @Column(name = "Usuario")
    var usuario: String? = null

    @Column(name = "FechaNacimiento")
    var fechaNacimiento: String? = null

    @Column(name = "Estado")
    var estado: String? = null

    @Column(name = "CodZona")
    var codZona: String? = null

    @Column(name = "EsNueva")
    var esNueva: Boolean = false

    @Column(name = "NroCampaniasComoNueva")
    var nroCampaniasComoNueva: Int = 0

    @Column(name = "UsuarioRed")
    var usuarioRed: String? = null
}
