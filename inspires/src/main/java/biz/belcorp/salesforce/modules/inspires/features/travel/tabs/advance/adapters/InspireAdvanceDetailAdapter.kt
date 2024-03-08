package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.advance.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_inspire_travel_advance_campaign_detail.view.*
import biz.belcorp.salesforce.modules.inspires.R

class InspireAdvanceDetailAdapter(private val list: List<String>) : RecyclerView.Adapter<InspireAdvanceDetailAdapter.ViewHolder>() {

    private val attachToRoot: Boolean = false
    private val resource: Int = R.layout.item_inspire_travel_advance_campaign_detail

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(resource, parent, attachToRoot))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getItem(position: Int): String = list[position]

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(label: String) {
            itemView.tvwInspireProgressCampaignDescriptionInner?.text = label
        }
    }
}
