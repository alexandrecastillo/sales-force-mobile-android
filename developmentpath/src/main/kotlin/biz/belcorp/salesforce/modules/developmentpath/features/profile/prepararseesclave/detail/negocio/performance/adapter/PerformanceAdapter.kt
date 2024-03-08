package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.model.SuccessfulModel
import kotlinx.android.synthetic.main.item_underline_category.view.*

class PerformanceAdapter : RecyclerView.Adapter<PerformanceAdapter.ItemViewHolder>() {

    private val data = mutableListOf<SuccessfulModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = parent.inflate(R.layout.item_underline_category)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    fun update(data: List<SuccessfulModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: SuccessfulModel) = with(itemView) {
            tvCampaign?.text = model.campaign
            ivCampaignIndicator?.visibility = View.GONE
            tvAmount?.text = model.description
            tvAmount?.textColor = getColor(model.color)
        }
    }
}
