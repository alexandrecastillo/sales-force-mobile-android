package biz.belcorp.salesforce.modules.billing.features.billing.utils

import android.content.Context
import android.text.Spannable
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.HYPHEN
import biz.belcorp.salesforce.core.features.uainfo.UaInfoModel
import biz.belcorp.salesforce.core.utils.*

object BillingUaTextBuilder {

    var context: Context? = null
    var model: UaInfoModel? = null

    fun init(context: Context): BillingUaTextBuilder {
        this.context = context
        return this
    }

    fun with(model: UaInfoModel): BillingUaTextBuilder {
        this.model = model
        return BillingUaTextBuilder
    }

    fun build(): Spannable {
        return model?.run {
            return if (isCovered) coveredInfo(this) else uncoveredInfo(this)
        } ?: run { span(EMPTY_STRING) }
    }

    private fun uncoveredInfo(model: UaInfoModel): Spannable = with(model) {
        val description = context?.getString(BaseR.string.uncovered).orEmpty()
        return spannable {
            color(
                description,
                ContextCompat.getColor(requireNotNull(context), model.userInformationColor)
            )
        }
    }

    private fun coveredInfo(model: UaInfoModel): Spannable = with(model) {
        if (context.isNull()) return span(EMPTY_STRING)
        val text = when {
            role.isGR() -> context?.getString(BaseR.string.region)
            role.isGZ() -> context?.getString(BaseR.string.zone)
            role.isSE() -> context?.getString(BaseR.string.section)
            else -> EMPTY_STRING
        }
        return spannable {
            bold(span("${text.orEmpty()} ${model.label} ")) + span("$HYPHEN ${model.fullName}")
        }
    }
}