package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.multimarkcategorie.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getDrawable
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.MultiMark
import kotlinx.android.synthetic.main.view_item_multimark_categories.view.imgActive
import kotlinx.android.synthetic.main.view_item_multimark_categories.view.textCampaign

class MultiMarkCampaignAdapter :
    RecyclerView.Adapter<MultiMarkCampaignAdapter.MultiMarkCampaignViewHolder>() {

    var items: List<MultiMark.Historical> = emptyList()


    fun actualizar(items: List<MultiMark.Historical>) {
        this.items = items
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiMarkCampaignViewHolder {
        val view = parent.inflate(R.layout.view_item_multimark_categories)
        return MultiMarkCampaignViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MultiMarkCampaignViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class MultiMarkCampaignViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: MultiMark.Historical) = with(view) {
            if (item.campaign.isNotEmpty()) {
                textCampaign.text = "C".plus(item.campaign.substring(item.campaign.length - 2))
            }

            if (item.multimarca == 1) {
                imgActive.setImageDrawable(getDrawable(R.drawable.active))
            } else {
                imgActive.setImageDrawable(getDrawable(R.drawable.active_no))
            }


        }

    }
}
