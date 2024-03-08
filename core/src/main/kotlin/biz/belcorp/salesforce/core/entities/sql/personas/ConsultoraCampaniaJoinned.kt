package biz.belcorp.salesforce.core.entities.sql.personas

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class)
class ConsultoraCampaniaJoinned : BaseQueryModel() {

    @Column(name = "ConsultorasId")
    var consultorasId: Int = 0

    @Column(name = "Pais")
    var pais: String? = null

    @Column(name = "Region")
    var region: String? = null

    @Column(name = "Zona")
    var zona: String? = null

    @Column(name = "Seccion")
    var seccion: String? = null

    @Column(name = "FechaHoraModificacion")
    var fechaHoraModificacion: String? = null

    @Column(name = "Nombre")
    var nombre: String? = null

    @Column(name = "Codigo")
    var codigo: String? = null

    @Column(name = "TelefonoCasa")
    var telefonoCasa: String? = null

    @Column(name = "TelefonoCelular")
    var telefonoCelular: String? = null

    @Column(name = "Constancia")
    var constancia: String? = null

    @Column(name = "Segmento")
    var segmento: String? = null

    @Column(name = "SaldoPendiente")
    var saldoPendiente: String? = null

    @Column(name = "DocumentoIdentidad")
    var documentoIdentidad: String? = null

    @Column(name = "Direccion")
    var direccion: String? = null

    @Column(name = "Email")
    var email: String? = null

    @Column(name = "Cumpleanios")
    var cumpleanios: String? = null

    @Column(name = "FamiliaProtegida")
    var familiaProtegida: String? = null

    @Column(name = "UsaFlexiPago")
    var usaFlexiPago: String? = null

    @Column(name = "CampaniaIngreso")
    var campaniaIngreso: String? = null

    @Column(name = "UltimaFacturacion")
    var ultimaFacturacion: String? = null

    @Column(name = "CampanaPrimerPedido")
    var campanaPrimerPedido: String? = null

    @Column(name = "OrigenPedido")
    var origenPedido: String? = null

    @Column(name = "VentaGanancia")
    var ventaGanancia: String? = null

    @Column(name = "PromVentaConsultora")
    var promVentaConsultora: String? = null

    @Column(name = "VentaConsultora")
    var ventaConsultora: String? = null

    @Column(name = "VtaCatalogoCx")
    var vtaCatalogoCx: String? = null

    @Column(name = "VtaCatalogoCx1")
    var vtaCatalogoCx1: String? = null

    @Column(name = "VtaCatalogoCx2")
    var vtaCatalogoCx2: String? = null

    @Column(name = "VtaCatalogoCx3")
    var vtaCatalogoCx3: String? = null

    @Column(name = "VtaCatalogoCx4")
    var vtaCatalogoCx4: String? = null

    @Column(name = "VtaCatalogoCx5")
    var vtaCatalogoCx5: String? = null

    @Column(name = "VtaCatalogoCx6")
    var vtaCatalogoCx6: String? = null

    @Column(name = "FlagAv")
    var flagAv: Int = 0

    @Column(name = "FlagAv1")
    var flagAv1: Int = 0

    @Column(name = "FlagAv2")
    var flagAv2: Int = 0

    @Column(name = "FlagAv3")
    var flagAv3: Int = 0

    @Column(name = "FlagAv4")
    var flagAv4: Int = 0

    @Column(name = "FlagAv5")
    var flagAv5: Int = 0

    @Column(name = "FlagAv6")
    var flagAv6: Int = 0

    @Column(name = "VisibilidadVtaCx")
    var visibilidadVtaCx: Int = 0

    @Column(name = "VisibilidadVtaCxMenos1")
    var visibilidadVtaCxMenos1: Int = 0

    @Column(name = "VisibilidadVtaCxMenos2")
    var visibilidadVtaCxMenos2: Int = 0

    @Column(name = "VisibilidadVtaCxMenos3")
    var visibilidadVtaCxMenos3: Int = 0

    @Column(name = "VisibilidadVtaCxMenos4")
    var visibilidadVtaCxMenos4: Int = 0

    @Column(name = "VisibilidadVtaCxMenos5")
    var visibilidadVtaCxMenos5: Int = 0

    @Column(name = "VisibilidadVtaCxMenos6")
    var visibilidadVtaCxMenos6: Int = 0

    @Column(name = "PrimerNombre")
    var primerNombre: String? = null

    @Column(name = "SegundoNombre")
    var segundoNombre: String? = null

    @Column(name = "PrimerApellido")
    var primerApellido: String? = null

    @Column(name = "SegundoApellido")
    var segundoApellido: String? = null

    @Column(name = "ConsultoraConsecutiva")
    var consultoraConsecutiva: String? = null

    @Column(name = "RecaudoComisionable")
    var recaudoComisionable: String? = null

    @Column(name = "Ganancia")
    var ganancia: String? = null

    @Column(name = "VentaFacturada")
    var ventaFacturada: String? = null

    @Column(name = "RecaudoTotal")
    var recaudoTotal: String? = null

    @Column(name = "RecaudoNoComisionable")
    var recaudoNoComisionable: String? = null

    @Column(name = "GananciaRetail")
    var gananciaRetail: String? = null

    @Column(name = "VentaRetail")
    var ventaRetail: String? = null

    @Column(name = "MontoPedidoSolicitado")
    var montoPedidoSolicitado: String? = null

    @Column(name = "MotivoRechazo")
    var motivoRechazo: String? = null

    @Column(name = "Aniversario")
    var aniversario: String? = null

    @Column(name = "VisitasRealizadas")
    var visitasRealizadas: String? = null

    @Column(name = "VisitasPlanificadas")
    var visitasPlanificadas: String? = null

    @Column(name = "EsSocia")
    var esSocia: String? = null

    @Column(name = "DetalleVisita")
    var detalleVisita: String? = null

    @Column(name = "Meta")
    var meta: String? = null

    @Column(name = "UnidadesCompradas")
    var unidadesCompradas: String? = null

    @Column(name = "Pedido")
    var pedido: Int? = null

    @Column(name = "Estado")
    var estado: Int? = null

    @Column(name = "TelefonoTrabajo")
    var telefonoTrabajo: String? = null

    @Column(name = "TipoTelefonoDefault")
    var tipoTelefonoDefault: Int = 0

    @Column(name = "FlagDeuda")
    var flagDeuda: Int = 0

    @Column(name = "FlagGanaMas")
    var flagGanaMas: Int = 0

    @Column(name = "FlagCompraGanaMas")
    var flagCompraGanaMas: Int = 0

    @Column(name = "CantidadProductoPPU")
    var cantidadProductoPPU: Int = 0

    @Column(name = "FlagFueIpUnico")
    var flagFueIpUnico: Int = 0

    @Column(name = "TelefonoTrabajoEditado")
    var phoneWorkEdit: String? = null

    @Column(name = "EmailEditado")
    var emailEdit: String? = null

    @Column(name = "TipoTelefonoEditado")
    var typePhoneEdit: Int = 0

    @Column(name = "TelefonoTrabajoFlag", defaultValue = "0")
    var phoneWorkFlag: Int = 0

    @Column(name = "EmailFlag", defaultValue = "0")
    var emailFlag: Int = 0

    @Column(name = "TipoTelefonoFlag", defaultValue = "0")
    var typePhoneFlag: Int = 0

    @Column(name = "Latitud")
    var latitud: String? = null

    @Column(name = "Longitud")
    var longitud: String? = null

    @Column(name = "SegmentoInternoFFVV")
    var segmentoInternoFFVV: String? = null

    @Column(name = "pasoPedido")
    var pasoPedido: Int = 0

    @Column(name = "DigVerif")
    var digitoVerificador: String? = null

    @Column(name = "CampaniaCodigo")
    var campaniaCodigo: String = ""

    @Column(name = "CampaniaInicio")
    var campaniaInicio: String = ""

    @Column(name = "CampaniaFin")
    var campaniaFin: String = ""

    @Column(name = "CampaniaInicioFacturacion")
    var campaniaInicioFacturacion: String = ""

    @Column(name = "CampaniaNombreCorto")
    var campaniaNombreCorto: String = ""

    @Column(name = "CampaniaOrden")
    var campaniaOrden: Int = 0

    @Column(name = "Periodo")
    var periodo: String = ""

    @Column(name = "PrimerDiaFacturacion")
    var esPrimerDiaFacturacion: Boolean = false

    @Column(name = "nivel")
    var nivel: String = ""

    @Column(name = "FechaNacimiento")
    var fechaNacimiento: String = ""

    @Column(name = "FechaAniversario")
    var fechaAniversario: String = ""

    @Column(name = "LastCampaignUpdate")
    var lastCampaignUpdate: String = ""

    @Column(name = "SelectedCategory")
    var selectedCategory: String = ""

}
