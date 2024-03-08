package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.core.utils.update
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model.GainModel
import kotlinx.android.synthetic.main.item_underline_category.view.*

class GainConsultantAdapter : RecyclerView.Adapter<GainConsultantAdapter.GainViewHolder>() {

    private val gains = arrayListOf<GainModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GainViewHolder {
        return GainViewHolder(parent.inflate(R.layout.item_underline_category))
    }

    override fun onBindViewHolder(holder: GainViewHolder, position: Int) {
        holder.bind(gains[position])
    }

    override fun getItemCount() = gains.size

    fun submitList(gains: List<GainModel>) {
        this.gains.update(gains)
        notifyDataSetChanged()
    }

    class GainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: GainModel): Unit = with(itemView) {
            tvCampaign?.text = model.campaign
            ivCampaignIndicator?.gone()
            tvAmount?.apply {
                text = model.amount
                textColor = model.color
            }
        }
    }
}
