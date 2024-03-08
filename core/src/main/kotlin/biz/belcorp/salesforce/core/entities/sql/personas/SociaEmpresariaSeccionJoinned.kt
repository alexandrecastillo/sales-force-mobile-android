package biz.belcorp.salesforce.core.entities.sql.personas

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class)
class SociaEmpresariaSeccionJoinned : BaseQueryModel() {

    @Column(name = "ConsultorasId")
    var consultorasId: Long = 0

    @Column(name = "Codigo")
    var codigo: String? = null

    @Column(name = "DocumentoIdentidad")
    var documentoIdentidad: String? = null

    @Column(name = "TelefonoCelular")
    var telefonoCelular: String? = null

    @Column(name = "TelefonoCasa")
    var telefonoCasa: String? = null

    @Column(name = "TelefonoTrabajo")
    var telefonoTrabajo: String? = null

    @Column(name = "PrimerNombre")
    var primerNombre: String? = null

    @Column(name = "SegundoNombre")
    var segundoNombre: String? = null

    @Column(name = "PrimerApellido")
    var primerApellido: String? = null

    @Column(name = "SegundoApellido")
    var segundoApellido: String? = null

    @Column(name = "CampaniaIngreso")
    var campaniaIngreso: String? = null

    @Column(name = "Email")
    var email: String? = null

    @Column(name = "EmailFlag")
    var emailFlag: Int = 0

    @Column(name = "EmailEditado")
    var emailEdit: String? = null

    @Column(name = "Cumpleanios")
    var cumpleanios: String? = null

    @Column(name = "EsSocia")
    var esSocia: String? = null

    @Column(name = "Latitud")
    var latitud: String? = null

    @Column(name = "Longitud")
    var longitud: String? = null

    @Column(name = "Direccion")
    var direccion: String? = null

    @Column(name = "Region")
    var region: String? = null

    @Column(name = "Zona")
    var zona: String? = null

    @Column(name = "Seccion")
    var seccion: String? = null

    @Column(name = "Nivel")
    var nivel: String? = null

    @Column(name = "Estado")
    var estado: String? = null

    @Column(name = "Exitosa")
    var exitosa: Int? = -1

    @Column(name = "ClasificacionLider")
    var clasificacionLider: String? = null

    @Column(name = "SubClasificacionLider")
    var subClasificiacionLider: String? = null

    @Column(name = "VentaGanancia")
    var ventaGanancia: String? = null

    @Column(name = "UltimaFacturacion")
    var ultimaFacturacion: String? = null

    @Column(name = "SaldoPendiente")
    var saldoPendiente: String? = null

    @Column(name = "OrigenPedido")
    var origenPedido: String? = null

    @Column(name = "ConsultoraConsecutiva")
    var consultoraConsecutiva: String? = null

    @Column(name = "VentaFacturada")
    var ventaFacturada: String? = null

    @Column(name = "RecaudoComisionable")
    var recaudoComisionable: String? = null

    @Column(name = "Ganancia")
    var ganancia: String? = null

    @Column(name = "RecaudoTotal")
    var recaudoTotal: String? = null

    @Column(name = "RecaudoNoComisionable")
    var recaudoNoComisionable: String? = null

    @Column(name = "GananciaRetail")
    var gananciaRetail: String? = null

    @Column(name = "VentaRetail")
    var ventaRetail: String? = null

    @Column(name = "CodigoCampania")
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

    @Column(name = "FechaNacimiento")
    var fechaNacimiento: String = ""

    @Column(name = "FechaAniversario")
    var fechaAniversario: String = ""
}
