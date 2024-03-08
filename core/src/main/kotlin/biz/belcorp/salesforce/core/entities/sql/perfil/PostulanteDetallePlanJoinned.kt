package biz.belcorp.salesforce.core.entities.sql.perfil

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel

@QueryModel(database = AppDatabase::class)
class PostulanteDetallePlanJoinned {

    @Column(name = "SolicitudPostulanteID")
    var solicitudPostulanteId: Long = -1

    @Column(name = "PrimerNombre")
    var primerNombre: String? = null

    @Column(name = "SegundoNombre")
    var segundoNombre: String? = null

    @Column(name = "ApellidoPaterno")
    var apellidoPaterno: String? = null

    @Column(name = "ApellidoMaterno")
    var apellidoMaterno: String? = null

    @Column(name = "NumeroDocumento")
    var numeroDocumento: String? = null

    @Column(name = "Telefono")
    var telefono: String? = null

    @Column(name = "Celular")
    var celular: String? = null

    @Column(name = "CorreoElectronico")
    var correoElectronico: String? = null

    @Column(name = "Direccion")
    var direccion: String? = null

    @Column(name = "FechaNacimiento")
    var fechaNacimiento: String? = null

    @Column(name = "Latitud")
    var latitud: Double? = null

    @Column(name = "Longitud")
    var longitud: Double? = null

    @Column(name = "EstadoPostulante")
    var estadoPostulante: Int? = null

    @Column(name = "Paso")
    var paso: Int? = null

    @Column(name = "FuenteIngreso")
    var fuenteIngreso: String? = null

    @Column(name = "CodigoSeccion")
    var codigoSeccion: String? = null

    @Column(name = "DetallePlanId")
    var detallePlanId: Int? = 0

    @Column(name = "PlanVisitaID")
    var planVisitaId: Int = 0

    @Column(name = "TipoObservacion")
    var tipoObservacion: Int? = null

    @Column(name = "FechaPlanificacion")
    var fechaPlanificacion: String? = null

    @Column(name = "FechaReprogramacion")
    var fechaReprogramacion: String? = null

    @Column(name = "FechaVisita")
    var fechaVisita: String? = null

    @Column(name = "UsuarioRed")
    var usuarioRed: String? = null

    @Column(name = "Planificado")
    var planificado: Int = 0

    @Column(name = "Enviado")
    var enviado: Int = 0

    @Column(name = "TipsID")
    var tipsid: Int = 0

    @Column(name = "Data")
    var data: String? = null
}
