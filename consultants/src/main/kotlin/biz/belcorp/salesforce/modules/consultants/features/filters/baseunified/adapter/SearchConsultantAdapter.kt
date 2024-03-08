package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.adapter.viewholders.*
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type.BilledConsultantModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type.DigitalKpiConsultantModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type.GeneralConsultantModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type.PendingBillingConsultantModel

class SearchConsultantAdapter :
    ListAdapter<ConsultantModel, ConsultantViewHolder<*>>(SearchDiffUtil) {

    companion object {
        private const val ORDER_AMOUNT_VIEW_TYPE = 111
        private const val BILLING_PENDING_VIEW_TYPE = 112
        private const val BILLED_ORDERS_VIEW_TYPE = 113
        private const val DIGITAL_KPI_VIEW_TYPE = 114
        private const val NONE = -1
    }

    private var flagMto: Int = Constant.ONE_NEGATIVE
    private var onClick: ((PersonIdentifier) -> Unit)? = null

    fun setFlagMto(flagMto: Int) {
        this.flagMto = flagMto
    }

    override fun getItemViewType(position: Int) =
        when (currentList[position]) {
            is GeneralConsultantModel -> ORDER_AMOUNT_VIEW_TYPE
            is PendingBillingConsultantModel -> BILLING_PENDING_VIEW_TYPE
            is BilledConsultantModel -> BILLED_ORDERS_VIEW_TYPE
            is DigitalKpiConsultantModel -> DIGITAL_KPI_VIEW_TYPE
            else -> NONE
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConsultantViewHolder<*> {
        return when (viewType) {
            ORDER_AMOUNT_VIEW_TYPE -> createOrderAmountViewHolder(parent)
            BILLING_PENDING_VIEW_TYPE -> createBillingPendingViewHolder(parent)
            BILLED_ORDERS_VIEW_TYPE -> createBilledOrdersViewHolder(parent)
            DIGITAL_KPI_VIEW_TYPE -> createDigitalKpiViewHolder(parent)
            else -> throw Exception("Incorrect viewType")
        }
    }

    override fun onBindViewHolder(holder: ConsultantViewHolder<*>, position: Int) {
        val model = currentList[position]
        when (holder) {
            is DebtViewHolder -> holder.bind(model as GeneralConsultantModel, onClick)
            is PendingBillingViewHolder -> holder.bind(
                model as PendingBillingConsultantModel,
                onClick
            )
            is BilledViewHolder -> holder.bind(model as BilledConsultantModel, onClick)
            is DigitalKpiViewHolder -> holder.bind(model as DigitalKpiConsultantModel, onClick)
            else -> throw Exception("Incorrect viewHolder")
        }
    }

    private fun createOrderAmountViewHolder(viewGroup: ViewGroup) =
        DebtViewHolder(viewGroup.inflate(R.layout.item_consultant_single_amount))

    private fun createBillingPendingViewHolder(viewGroup: ViewGroup) =
        PendingBillingViewHolder(flagMto, viewGroup.inflate(R.layout.item_consultant_pending_billing))

    private fun createBilledOrdersViewHolder(viewGroup: ViewGroup) =
        BilledViewHolder(flagMto, viewGroup.inflate(R.layout.item_consultant_billed))

    private fun createDigitalKpiViewHolder(viewGroup: ViewGroup) =
        DigitalKpiViewHolder(viewGroup.inflate(R.layout.item_consultant_indicator))

    fun setOnClickListener(onClick: (PersonIdentifier) -> Unit) {
        this.onClick = onClick
    }

}
