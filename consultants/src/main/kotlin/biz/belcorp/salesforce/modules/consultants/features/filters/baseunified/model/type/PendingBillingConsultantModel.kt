package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantModel

class PendingBillingConsultantModel(
    id: Long,
    code: String,
    uaKey: LlaveUA,
    segment: String,
    colorSegment: Int,
    name: String,
    whatsAppNumber: String,
    phoneNumber: String,
    debAmount: String,
    sbAmount: String? = null,
    orderAmount: String? = null,
    orderMtoAmount: String? = null,
    rejectedReason: String = EMPTY_STRING,
    bonus: String,
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
    debAmount,
    sbAmount,
    orderAmount,
    orderMtoAmount,
    rejectedReason,
    bonus,
    multiBrand
)
