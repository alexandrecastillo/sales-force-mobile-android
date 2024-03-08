package biz.belcorp.salesforce.modules.developmentpath.features.profile.dream

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.DreamCampaign
import kotlinx.android.synthetic.main.dream_campaign_item.view.*

class DreamCampaignsAdapter(
    private val items: List<DreamCampaign>,
    private val context: Context,
    private val currentCurrency: String?
) : RecyclerView.Adapter<DreamCampaignsAdapter.DreamCampaignViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): DreamCampaignViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.dream_campaign_item, viewGroup, false)
        return DreamCampaignViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: DreamCampaignViewHolder, position: Int) {
        viewHolder.bind(items[position], position)
    }

    inner class DreamCampaignViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: DreamCampaign, position: Int) {
            itemView.tv_campaign_title.text = context.getString(R.string.dream_item_title, position+1, item.campaign )
            itemView.tv_campaign_gain_amount.text = context.getString(R.string.dream_item_value, (currentCurrency + item.gainAmount))
        }

    }


}
