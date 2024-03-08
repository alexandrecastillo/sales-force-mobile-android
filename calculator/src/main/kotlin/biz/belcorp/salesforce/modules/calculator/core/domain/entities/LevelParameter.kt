package biz.belcorp.salesforce.modules.calculator.core.domain.entities

data class LevelParameter (val nivelID: Int,
                                      val gananciaPromedioPedido: Float?,
                                      val pedidoMinimoNivel: Int?,
                                      val ventaMinimaProximoNivel: Float?,
                                      val metaIngresos: Int?,
                                      val metaCapitalizacion: Int?,
                                      val porcentajeRetencionActivas: Float?,
                                      val metaPedidos: Int?,
                                      val metaVenta: Float?,
                                      val indicadorMedicionMeta: String?,
                                      val indicadorMedicionVariable: String?,
                                      val indicadorMedicionRetencion: String?,
                                      val bonoCambioNivel: Float?,
                                      val porcentajeComisionEXAV: Float?,
                                      val porcentajeComisionEXBV: Float?,
                                      val porcentajeComisionNEAV: Float?,
                                      val porcentajeComisionNEBV: Float?)
