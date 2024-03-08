package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model

import androidx.annotation.ColorInt
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

open class ConsultantModel(
    val id: Long,
    val code: String,
    val uaKey: LlaveUA,
    val segment: String,
    @ColorInt val colorSegment: Int,
    val name: String,
    val whatsAppNumber: String,
    val phoneNumber: String,
    val debAmount: String,
    val sbAmount: String? = null,
    val orderAMount: String? = null,
    val orderMtoAmount: String? = null,
    val rejectedReason: String = EMPTY_STRING,
    val bonus: String,
    val multiBrand: Boolean
)
