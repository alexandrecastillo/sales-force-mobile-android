package biz.belcorp.salesforce.modules.calculator.model

import biz.belcorp.salesforce.core.constants.Constant


data class PartnerVariableModel (
    var nivelID: Int,
    var indicadorNuevaLider: String?,
    var metaPedido: Int?,
    var metaVenta: Double?,
    var numeroActivasIniciales: Int?,
    var porcentajeMetaRecuperacion: Double,
    var promedioVentaPedidosAV: Double?,
    var promedioVentaPedidosBV: Double?,
    var nivelCambioCampania: String?,
    var metaIngresos: Int?,
    var metaCapitalizacion: Int?,
    var indicadorMedicionVariable: String?,
    var indicadorMedicionMeta: String?,
    var porcentajeComisionEXAV: Double?,
    var porcentajeComisionEXBV: Double?,
    var porcentajeComisionNEAV: Double?,
    var porcentajeComisionNEBV: Double?,
    var bonoCambioNivel: Double?,
    var currencySymbol: String = Constant.EMPTY_STRING,
    var campaign: String = Constant.EMPTY_STRING,
    var indicadorMedicionRetencion: String? = null)
