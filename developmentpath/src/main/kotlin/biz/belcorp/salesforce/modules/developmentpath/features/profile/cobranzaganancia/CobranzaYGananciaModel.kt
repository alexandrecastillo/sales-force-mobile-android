package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia

class CobranzaYGananciaModel(
    val campania: String? = "",
    val region: String? = "",
    val zona: String? = "",
    val local: Boolean? = false,
    val server: Boolean? = false,
    val seccion: String? = "",
    val montoFacturadoNeto: Float? = 0f,
    val montoRecuperado: Float? = 0f,
    val saldoDeuda: Float? = 0f,
    val consultorasDeuda: Float? = 0f,
    val recuperacion: Int? = 0,
    val retencionVSC: Int? = 0,
    val gananciaTotal: Float? = 0f,
    val ganancia6d6: Float? = 0f,
    val gananciaPedidoAltoValor: Float? = 0f,
    val gananciaCambioNivel: Float? = 0f
)
