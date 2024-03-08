package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.productosmasvendidos

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.update
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_producto_mas_vendido_new.view.*
import biz.belcorp.salesforce.base.R as BaseR

class TopSoldProductsAdapter :
    RecyclerView.Adapter<TopSoldProductsAdapter.ProductosMasVendidosViewHolder>() {

    private val items = mutableListOf<TopSoldProductCampaignModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductosMasVendidosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_mas_vendido_new, parent, false)
        return ProductosMasVendidosViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductosMasVendidosViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun update(items: List<TopSoldProductCampaignModel>) {
        this.items.update(items)
        notifyDataSetChanged()
    }

    class ProductosMasVendidosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val adapter: CampaignHistoryAdapter by lazy { CampaignHistoryAdapter() }

        fun bind(model: TopSoldProductCampaignModel) = with(itemView) {
            txtNombreProducto?.text = model.productName
            txtValorPromedio?.text = model.average
            txtValorPromedio?.setTextColor(getColor(context, model.isTopSold))
            vIndicador?.background?.setColorFilter(
                getColor(context, model.isTopSold),
                PorterDuff.Mode.SRC_ATOP
            )
            rvCampaniasProducto?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvCampaniasProducto?.setHasFixedSize(true)
            rvCampaniasProducto?.adapter = adapter
            adapter.update(model.campaigns)
        }

        private fun getColor(context: Context, isTopSold: Boolean): Int {
            val color = if (isTopSold) BaseR.color.magenta else BaseR.color.gray_4
            return ContextCompat.getColor(context, color)
        }
    }
}
