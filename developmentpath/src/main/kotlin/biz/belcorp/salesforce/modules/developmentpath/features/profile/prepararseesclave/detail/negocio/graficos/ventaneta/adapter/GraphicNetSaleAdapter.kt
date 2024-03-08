package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.formatearConComas
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.models.NetSaleSeModel
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix
import kotlinx.android.synthetic.main.item_underline_category.view.*
import biz.belcorp.salesforce.base.R as BaseR

class GraphicNetSaleAdapter : RecyclerView.Adapter<GraphicNetSaleAdapter.ItemViewHolder>() {

    private val data = mutableListOf<NetSaleSeModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = parent.inflate(R.layout.item_underline_category)
        return ItemViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    fun actualizar(data: List<NetSaleSeModel>) {
        this.data.clear()
        this.data.addAll(data.asReversed())
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(modelo: NetSaleSeModel) = with(itemView) {
            tvCampaign?.text = modelo.campaign?.maskCampaignWithPrefix()
            ivCampaignIndicator?.visibility = View.GONE
            tvAmount?.text = modelo.amount?.formatearConComas()
            tvAmount.textColor = getColor(BaseR.color.black)
        }
    }
}
