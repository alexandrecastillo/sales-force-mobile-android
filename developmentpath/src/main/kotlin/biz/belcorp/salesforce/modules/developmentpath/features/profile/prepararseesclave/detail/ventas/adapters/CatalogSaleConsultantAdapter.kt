package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.adapters

import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.utils.invisible
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.core.utils.update
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model.SaleModel
import kotlinx.android.synthetic.main.item_underline_category.view.*
import biz.belcorp.salesforce.base.R as BaseR

class CatalogSaleConsultantAdapter :
    RecyclerView.Adapter<CatalogSaleConsultantAdapter.CatalogSaleViewHolder>() {

    private val sales = arrayListOf<SaleModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogSaleViewHolder {
        val view = parent.inflate(R.layout.item_underline_category)
        return CatalogSaleViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogSaleViewHolder, position: Int) {
        holder.bind(sales[position])
    }

    override fun getItemCount() = sales.size

    fun submitList(sales: List<SaleModel>) {
        this.sales.update(sales)
        notifyDataSetChanged()
    }

    class CatalogSaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: SaleModel): Unit = with(itemView) {
            tvCampaign?.text = model.campaign
            ivCampaignIndicator?.apply {
                if (model.isAmountZero) invisible()
                else visible()
                setColorFilter(model.color, PorterDuff.Mode.SRC_IN)
            }
            tvAmount?.apply {
                text = model.amount
                textColor = if (model.isAmountZero) getColor(BaseR.color.negativo)
                else getColor(BaseR.color.black)
            }
        }
    }
}
