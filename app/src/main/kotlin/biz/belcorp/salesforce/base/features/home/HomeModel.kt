package biz.belcorp.salesforce.base.features.home

import androidx.annotation.ColorRes
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.UNO_NEGATIVO
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

data class HomeModel(
    val periodDescription: String,
    val campaignName: String,
    val role: Rol,
    val isBilling: Boolean,
    val isFirstDayBilling: Boolean,
    @ColorRes val color: Int
) {
    var uaKey: LlaveUA = LlaveUA(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, UNO_NEGATIVO.toLong())
    var showBillingBanner = false
}
