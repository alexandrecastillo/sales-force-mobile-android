package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.cobranza

data class DatosCobranzaModel(
    val ventaGanancia: String,
    val saldoPendiente: String,
    val consultoraConsecutiva: String,
    val ventaFacturada: String,
    val recaudoComisionable: String,
    val ganancia: String,
    val recaudoTotal: String,
    val recaudoNoComisionable: String,
    val gananciaVentaRetail: String,
    val ventaRetail: String
)
