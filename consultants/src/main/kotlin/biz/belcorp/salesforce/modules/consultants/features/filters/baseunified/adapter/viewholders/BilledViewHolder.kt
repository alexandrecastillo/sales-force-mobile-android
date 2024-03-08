package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.adapter.viewholders

import android.view.View
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.components.R
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type.BilledConsultantModel
import kotlinx.android.synthetic.main.content_item_view_consultant_billed.view.*
import kotlinx.android.synthetic.main.view_bottom_gift_description.view.*
import kotlinx.android.synthetic.main.view_single_amount.view.*

class BilledViewHolder(private val flagMto: Int, itemView: View) :
    ConsultantViewHolder<BilledConsultantModel>(itemView) {

    override fun bindAmounts(model: BilledConsultantModel): Unit = with(itemView) {
        tvTitleSingle?.apply {
            text = context.getString(R.string.title_orders_sb_amount)
        }
        tvAmountSingle?.apply {
            text = model.orderAMount
        }
    }

    override fun bindDescription(model: BilledConsultantModel): Unit = with(itemView) {
        separator?.visible(model.bonus.isNotEmpty())
        tvGiftDescription?.apply {
            visible(model.bonus.isNotEmpty())
            text = model.bonus
        }
    }

    override fun bindSellingAmounts(model: BilledConsultantModel): Unit = with(itemView) {
        when (flagMto) {
            Constant.TYPE_SELLYNG_MTO -> {
                clContainerAmounts2.visibility = View.VISIBLE
                titleAmount.text = resources.getString(R.string.title_order_mto_amount)
                txtAmountValue.text = model.orderMtoAmount
            }
            Constant.TYPE_SELLYNG_CDEI -> {
                clContainerAmounts2.visibility = View.VISIBLE
                titleAmount.text = resources.getString(R.string.title_order_cdei_amount)
                txtAmountValue.text = model.orderMtoAmount
            }
            Constant.TYPE_NO_SELLYNG -> {
                clContainerAmounts2.visibility = View.GONE
            }
        }
    }

}
