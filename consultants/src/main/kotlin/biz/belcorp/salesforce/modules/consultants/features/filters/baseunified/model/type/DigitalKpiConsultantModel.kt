package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantModel

class DigitalKpiConsultantModel(
    id: Long,
    code: String,
    uaKey: LlaveUA,
    segment: String,
    colorSegment: Int,
    name: String,
    whatsAppNumber: String,
    phoneNumber: String,
    orderMtoAmount: String,
    val indicatorText: String,
    @ColorRes val indicatorTextColor: Int,
    @DrawableRes val indicatorBackground: Int,
    multiBrand: Boolean
) : ConsultantModel(
    id,
    code,
    uaKey,
    segment,
    colorSegment,
    name,
    whatsAppNumber,
    phoneNumber,
    EMPTY_STRING,
    EMPTY_STRING,
    EMPTY_STRING,
    EMPTY_STRING,
    EMPTY_STRING,
    EMPTY_STRING,
    multiBrand
)
