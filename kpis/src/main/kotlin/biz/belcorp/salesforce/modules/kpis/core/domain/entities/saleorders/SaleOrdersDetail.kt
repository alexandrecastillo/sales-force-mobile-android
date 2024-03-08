package biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

data class SaleOrdersDetail(val role: Rol, val campaignCode: String)