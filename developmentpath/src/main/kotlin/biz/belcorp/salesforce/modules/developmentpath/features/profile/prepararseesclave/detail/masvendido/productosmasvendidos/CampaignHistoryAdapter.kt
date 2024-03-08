package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.productosmasvendidos

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.update
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_campania_producto_mas_vendido.view.*

class CampaignHistoryAdapter :
    RecyclerView.Adapter<CampaignHistoryAdapter.CampaignHistoryViewHolder>() {

    private val items = mutableListOf<TopSoldProductCampaignModel.CampaignModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignHistoryViewHolder {
        val view = parent.inflate(R.layout.item_campania_producto_mas_vendido)
        return CampaignHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CampaignHistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun update(items: List<TopSoldProductCampaignModel.CampaignModel>) {
        this.items.update(items)
    }

    class CampaignHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: TopSoldProductCampaignModel.CampaignModel) = with(itemView) {
            txtCampania.text = model.name
            txtCantidad.text = model.value
        }
    }
}
