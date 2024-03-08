package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model

data class CalculadoraMontoFijo(
    val codigoRango: String,
    val tipoRango: String,
    val valorMinimoRango: Int,
    val valorMaximoRango: Double,
    val bonoExitosa: Double,
    val numPedidosTotales: Int,
    val numPedidosMetaCobranzas: Int,
    val promedioVentaRango: Double,
    val textoBonoExitoso: String,
    val textoInput: String,
    val mensaje: String,
    val campaniaProceso: String,
    val bonoNoExitosa: Double,
    val textoBonoNoExitoso: String
)
