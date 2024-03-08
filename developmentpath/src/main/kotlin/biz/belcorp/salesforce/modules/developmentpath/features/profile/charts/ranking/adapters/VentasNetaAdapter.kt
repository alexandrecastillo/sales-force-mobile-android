package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.model.NetSaleModel
import kotlinx.android.synthetic.main.item_venta_mn.view.*

class VentasNetaAdapter : RecyclerView.Adapter<VentasNetaAdapter.ViewHolder>() {

    private var listaMetas = mutableListOf<NetSaleModel>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaMetas[position])
    }

    override fun getItemCount() = listaMetas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_venta_mn, parent, false)

        return VentasNetaAdapter()
            .ViewHolder(view)
    }

    fun setElementsUpdate(elements: List<NetSaleModel>) {
        listaMetas = elements.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(value: NetSaleModel) = with(itemView) {
            val prctge = value.netSalePercentage
            txtPercentage?.text = context.getString(R.string.percentage_string_format, prctge)
            txtCampania?.text = value.campaign
            txtFvValue?.text = value.netSaleGoal
            txtVentaNeta?.text = value.netSale
            imgDow?.visible(!value.saleStatus)
            imgUp?.visible(value.saleStatus)
        }

    }
}
