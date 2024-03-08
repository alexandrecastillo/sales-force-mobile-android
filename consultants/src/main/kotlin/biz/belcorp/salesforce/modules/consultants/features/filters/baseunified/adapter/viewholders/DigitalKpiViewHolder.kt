package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.adapter.viewholders

import android.view.View
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.utils.backgroundResource
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type.DigitalKpiConsultantModel
import kotlinx.android.synthetic.main.view_consultant_indicator.view.*

class DigitalKpiViewHolder(itemView: View) :
    ConsultantViewHolder<DigitalKpiConsultantModel>(itemView) {

    override fun bindIndicator(model: DigitalKpiConsultantModel): Unit = with(itemView) {
        tvIndicator?.apply {
            visible(model.indicatorText.isNotEmpty())
            text = model.indicatorText
            backgroundResource = model.indicatorBackground
            setTextColor(getColor(model.indicatorTextColor))
        }
    }

}
