package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.adapter.viewholders

import android.view.View
import biz.belcorp.salesforce.components.R
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type.GeneralConsultantModel
import kotlinx.android.synthetic.main.view_single_amount.view.*

class DebtViewHolder(itemView: View) : ConsultantViewHolder<GeneralConsultantModel>(itemView) {

    override fun bindAmounts(model: GeneralConsultantModel): Unit = with(itemView) {
        tvTitleSingle?.apply {
            text = context.getString(R.string.title_debt)
        }
        tvAmountSingle?.apply {
            text = model.debAmount
        }
    }

}
