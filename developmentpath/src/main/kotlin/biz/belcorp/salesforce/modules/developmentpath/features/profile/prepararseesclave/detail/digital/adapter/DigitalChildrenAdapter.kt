package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.textColorResource
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleCampaignModel
import kotlinx.android.synthetic.main.item_digital_campania_horizontal.view.*
import kotlinx.android.synthetic.main.item_digital_campania_vertical.view.recycler as recycler_vertical
import kotlinx.android.synthetic.main.item_digital_campania_vertical.view.text_title as text_title_vertical

class DigitalChildrenAdapter(@LayoutOrientation val orientation: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewType { HORIZONTAL, VERTICAL, }

    var dataSet: List<DigitalSaleCampaignModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.HORIZONTAL.ordinal -> {
                val view = parent.inflate(R.layout.item_digital_campania_horizontal)
                DigitalCampaniaHorizontalViewHolder(view)
            }
            ViewType.VERTICAL.ordinal -> {
                val view = parent.inflate(R.layout.item_digital_campania_vertical)
                DigitalCampaniaVerticalViewHolder(view)
            }
            else -> throw Exception("ViewType incorrecto")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataSet[position]
        when (holder) {
            is DigitalCampaniaHorizontalViewHolder -> holder.bind(item)
            is DigitalCampaniaVerticalViewHolder -> holder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int = when (orientation) {
        LayoutOrientation.VERTICAL -> ViewType.VERTICAL.ordinal
        else -> ViewType.HORIZONTAL.ordinal
    }

    override fun getItemCount(): Int = dataSet.size

    fun actualizar(data: List<DigitalSaleCampaignModel>) {
        this.dataSet = data
        notifyDataSetChanged()
    }
}

class DigitalCampaniaHorizontalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: DigitalSaleCampaignModel) = with(itemView) {
        text_title?.text = item.campaignCode
        val itemChild = item.saleCampaignChild[0]
        text_value?.apply {
            text = itemChild.value
            textColorResource = itemChild.colorRes
        }
    }
}

class DigitalCampaniaVerticalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: DigitalSaleCampaignModel) = with(itemView) {
        text_title_vertical?.text = item.campaignCode
        val childLayoutManager = LinearLayoutManager(itemView.context)
        childLayoutManager.orientation = LinearLayoutManager.VERTICAL
        childLayoutManager.initialPrefetchItemCount = 4
        val childAdapter =
            DigitalChildAdapter()
        recycler_vertical?.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            layoutManager = childLayoutManager
            adapter = childAdapter
        }
        childAdapter.actualizar(item.saleCampaignChild)
    }
}
