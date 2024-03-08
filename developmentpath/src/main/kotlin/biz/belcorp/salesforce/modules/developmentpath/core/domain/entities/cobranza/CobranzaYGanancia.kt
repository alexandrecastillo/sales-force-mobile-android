package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cobranza

class CobranzaYGanancia(
    var campania: String? = "",
    var region: String? = "",
    var zona: String? = "",
    var local: Boolean? = false,
    var server: Boolean? = false,
    var seccion: String? = "",
    var montoFacturadoNeto: Float? = 0f,
    var montoRecuperado: Float? = 0f,
    var saldoDeuda: Float? = 0f,
    var consultorasDeuda: Float? = 0f,
    var recuperacion: Int? = 0,
    var retencionVSC: Int? = 0,
    var gananciaTotal: Float? = 0f,
    var ganancia6d6: Float? = 0f,
    var gananciaPedidoAltoValor: Float? = 0f,
    var gananciaCambioNivel: Float? = 0f
)
