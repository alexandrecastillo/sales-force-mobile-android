package biz.belcorp.salesforce.modules.developmentpath.features.profile.dream

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.dream_campaign_number_item.view.*

class DreamCampaignsNumberAdapter(
    private val totalCampaignNumbers: Int,
    private val totalCampaignSelectedNumbers: Int,
    private val context: Context
    ) : RecyclerView.Adapter<DreamCampaignsNumberAdapter.DreamCampaignNumberViewHolder>()  {


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): DreamCampaignsNumberAdapter.DreamCampaignNumberViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.dream_campaign_number_item, viewGroup, false)
        return DreamCampaignNumberViewHolder(view)
    }

    override fun getItemCount(): Int {
        return totalCampaignNumbers
    }

    override fun onBindViewHolder(viewHolder: DreamCampaignsNumberAdapter.DreamCampaignNumberViewHolder, position: Int) {
        viewHolder.bind(position+1)
    }



    inner class DreamCampaignNumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            if(position<totalCampaignNumbers){
                itemView.tv_campaign_number.text = "$position- "
            }else{
                itemView.tv_campaign_number.text = position.toString()
            }

            if(position<=totalCampaignSelectedNumbers){
                itemView.tv_campaign_number.setTextColor(context.resources.getColor(R.color.purple_dream))
            }else{
                itemView.tv_campaign_number.setTextColor(context.resources.getColor(R.color.disable_gray))
            }
        }

    }


}
