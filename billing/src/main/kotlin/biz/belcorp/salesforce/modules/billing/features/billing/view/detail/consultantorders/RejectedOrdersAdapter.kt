package biz.belcorp.salesforce.modules.billing.features.billing.view.detail.consultantorders

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.modules.billing.R
import biz.belcorp.salesforce.modules.billing.features.billing.model.detail.BillingRejectedOrderModel
import kotlinx.android.synthetic.main.item_billing_rejected_orders.view.*

internal class RejectedOrdersAdapter :
    RecyclerView.Adapter<RejectedOrdersAdapter.RejectedOrdersHolder>() {

    private val data = arrayListOf<BillingRejectedOrderModel>()

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RejectedOrdersHolder {
        val view = parent.inflate(R.layout.item_billing_rejected_orders)
        return RejectedOrdersHolder(view)
    }

    override fun onBindViewHolder(holder: RejectedOrdersHolder, position: Int) {
        holder.bind(data[position])
    }

    fun submitList(data: List<BillingRejectedOrderModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class RejectedOrdersHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: BillingRejectedOrderModel) = with(itemView) {
            title?.text = model.title
            summary?.text = model.quantity.toString()
        }
    }
}
