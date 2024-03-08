package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.adapter.viewholders

import android.view.View
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.components.R
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type.PendingBillingConsultantModel
import kotlinx.android.synthetic.main.content_item_view_consultant_billing_pending.view.*
import kotlinx.android.synthetic.main.view_bottom_gift_description.view.*
import kotlinx.android.synthetic.main.view_consultant_indicator.view.*
import biz.belcorp.salesforce.core.constants.Constant

class PendingBillingViewHolder(private val flagMto: Int, itemView: View) :
    ConsultantViewHolder<PendingBillingConsultantModel>(itemView) {

    override fun bindAmounts(model: PendingBillingConsultantModel): Unit = with(itemView) {
        tvAmountDebt?.apply {
            text = model.debAmount
        }

        tvAmountOrderSb?.apply {
            text = model.sbAmount
        }
    }

    override fun bindIndicator(model: PendingBillingConsultantModel): Unit = with(itemView) {
        includedConsultantIndicatorView?.visible(model.rejectedReason.isNotEmpty())
        tvIndicator?.apply {
            text = model.rejectedReason
            background = context.getDrawable(R.drawable.bg_red_soft)
            setTextColor(getColor(R.color.colorNotCovered))
        }
    }

    override fun bindDescription(model: PendingBillingConsultantModel): Unit = with(itemView) {
        separator?.visible(model.bonus.isNotEmpty())
        tvGiftDescription?.apply {
            visible(model.bonus.isNotEmpty())
            text = model.bonus
        }
    }

    override fun bindSellingAmounts(model: PendingBillingConsultantModel): Unit = with(itemView) {
        when (flagMto) {
            Constant.TYPE_SELLYNG_MTO -> {
                clContainerAmounts2.visibility = View.VISIBLE
                titleAmount.visibility = View.VISIBLE
                txtAmountValue.visibility = View.VISIBLE

                titleAmount.text = resources.getString(R.string.title_order_mto_amount)
                txtAmountValue.text = model.orderMtoAmount
            }
            Constant.TYPE_SELLYNG_CDEI -> {
                clContainerAmounts2.visibility = View.VISIBLE
                titleAmount.visibility = View.VISIBLE
                txtAmountValue.visibility = View.VISIBLE

                titleAmount.text = resources.getString(R.string.title_order_cdei_amount)
                txtAmountValue.text = model.orderMtoAmount
            }
            Constant.TYPE_NO_SELLYNG -> {
                clContainerAmounts2.visibility = View.GONE
            }
        }
    }

    override fun bindFlagMultiBrand(model: PendingBillingConsultantModel) = with(itemView) {
        txtAmountValue2.text =
            if (model.multiBrand) resources.getString(R.string.title_indicator_multi_brand_yes)
            else resources.getString(R.string.title_indicator_multi_brand_no)
    }

}
