package biz.belcorp.salesforce.core.entities.sql.consultora


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "Consultora", useBooleanGetterSetters = false)
class ConsultoraEntity : BaseModel() {

    @Column(name = "ConsultorasId")
    @SerializedName("consultorasId")
    @PrimaryKey
    var consultorasId: Int = 0

    @Column(name = "Region")
    @SerializedName("region")
    var region: String? = null

    @Column(name = "Zona")
    @SerializedName("zona")
    var zona: String? = null

    @Column(name = "Seccion")
    @SerializedName("seccion")
    var seccion: String? = null

    @Column(name = "Nombre")
    @SerializedName("nombre")
    var nombre: String? = null

    @Column(name = "Codigo")
    @SerializedName("codigo")
    var codigo: String? = null

    @Column(name = "TelefonoCasa")
    @SerializedName("telefonoCasa")
    var telefonoCasa: String? = null

    @Column(name = "TelefonoCelular")
    @SerializedName("telefonoCelular")
    var telefonoCelular: String? = null

    @Column(name = "Constancia")
    @SerializedName("constancia")
    var constancia: String? = null

    @Column(name = "Segmento")
    @SerializedName("segmento")
    var segmento: String? = null

    @Column(name = "SaldoPendiente")
    @SerializedName("saldoPendiente")
    var saldoPendiente: String? = null

    @Column(name = "DocumentoIdentidad")
    @SerializedName("documentoIdentidad")
    var documentoIdentidad: String? = null

    @Column(name = "Direccion")
    @SerializedName("direccion")
    var direccion: String? = null

    @Column(name = "Email")
    @SerializedName("email")
    var email: String? = null

    @Column(name = "Cumpleanios")
    @SerializedName("cumpleanios")
    var cumpleanios: String? = null

    @Column(name = "CampaniaIngreso")
    @SerializedName("campaniaIngreso")
    var campaniaIngreso: String? = null

    @Column(name = "UltimaFacturacion")
    @SerializedName("ultimaFacturacion")
    var ultimaFacturacion: String? = null

    @Column(name = "CampanaPrimerPedido")
    @SerializedName("campanaPrimerPedido")
    var campanaPrimerPedido: String? = null

    @Column(name = "PrimerNombre")
    @SerializedName("primerNombre")
    var primerNombre: String? = null

    @Column(name = "SegundoNombre")
    @SerializedName("segundoNombre")
    var segundoNombre: String? = null

    @Column(name = "PrimerApellido")
    @SerializedName("primerApellido")
    var primerApellido: String? = null

    @Column(name = "SegundoApellido")
    @SerializedName("segundoApellido")
    var segundoApellido: String? = null

    @Column(name = "VentaGanancia")
    @SerializedName("ventaGanancia")
    var ventaGanancia: String? = null

    @Column(name = "ConsultoraConsecutiva")
    @SerializedName("consultoraConsecutiva")
    var consultoraConsecutiva: String? = null

    @Column(name = "RecaudoComisionable")
    @SerializedName("recaudoComisionable")
    var recaudoComisionable: String? = null

    @Column(name = "Ganancia")
    @SerializedName("ganancia")
    var ganancia: String? = null

    @Column(name = "VentaFacturada")
    @SerializedName("ventaFacturada")
    var ventaFacturada: String? = null

    @Column(name = "RecaudoTotal")
    @SerializedName("recaudoTotal")
    var recaudoTotal: String? = null

    @Column(name = "RecaudoNoComisionable")
    @SerializedName("recaudoNoComisionable")
    var recaudoNoComisionable: String? = null

    @Column(name = "GananciaRetail")
    @SerializedName("gananciaRetail")
    var gananciaRetail: String? = null

    @Column(name = "VentaRetail")
    @SerializedName("ventaRetail")
    var ventaRetail: String? = null

    @Column(name = "CantidadProductoPPU")
    @SerializedName("cantidadProductoPPU")
    var cantidadProductoPPU: Int = 0

    @Column(name = "Latitud")
    @SerializedName("latitud")
    var latitud: String? = null

    @Column(name = "Longitud")
    @SerializedName("longitud")
    var longitud: String? = null

    @Column(name = "SegmentoInternoFFVV")
    @SerializedName("segmentoInternoFFVV")
    var segmentoInternoFFVV: String? = null

    @Column(name = "pasoPedido")
    @SerializedName("pasoPedido")
    var pasoPedido: Int = 0

    @Column(name = "DigVerif")
    @SerializedName("digVerif")
    var digitoVerificador: String? = null

    @Column(name = "Pedido")
    @SerializedName("pedido")
    var pedido: Int? = null

    @Column(name = "Estado")
    @SerializedName("estado")
    var estado: Int? = null

    @Column(name = "Autoriza")
    @SerializedName("autoriza")
    var autorizada: String? = null

    @Column(name = "FlagDeuda")
    @SerializedName("flagDeuda")
    var flagDeuda: Int = 0

    var nivel: String? = null

    @Column(name = "ComparteCatalogo")
    var comparteCatalogo: Boolean = false

    @Column(name = "MensajeSugerido")
    var mensajeSugerido: String? = null

    @Column(name = "DigitalSegment")
    var digitalSegment: String? = null

    @Column(name = "HasCashPayment")
    var hasCashPayment: Boolean = false

    @Column(name = "ConstanciaPDV")
    var constanciaPDV: String? = null

    @Column(name = "NivelPDV")
    var nivelPDV: String? = null

}
