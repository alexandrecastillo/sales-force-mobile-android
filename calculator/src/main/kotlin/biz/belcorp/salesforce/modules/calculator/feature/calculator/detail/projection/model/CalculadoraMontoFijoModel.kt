package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model

import biz.belcorp.salesforce.core.constants.Constant

data class CalculadoraMontoFijoModel(
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
) {
    var inputTextUser = Constant.EMPTY_STRING
}
