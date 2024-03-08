package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.textColorResource
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleCampaignChildModel
import kotlinx.android.synthetic.main.item_digital_campania_child.view.*

class DigitalChildAdapter : RecyclerView.Adapter<DigitalCampaniaChildViewHolder>() {

    var dataSet: List<DigitalSaleCampaignChildModel> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DigitalCampaniaChildViewHolder {
        val view = parent.inflate(R.layout.item_digital_campania_child)
        return DigitalCampaniaChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: DigitalCampaniaChildViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = dataSet.size

    fun actualizar(data: List<DigitalSaleCampaignChildModel>) {
        this.dataSet = data
        notifyDataSetChanged()
    }
}

class DigitalCampaniaChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: DigitalSaleCampaignChildModel) = with(itemView) {
        textNumber?.apply {
            text = item.number
            textColorResource = item.colorRes
        }
        textValue?.apply {
            text = item.value
            textColorResource = item.colorRes
        }
    }
}
