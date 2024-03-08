package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.adapter.detail

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.ContentModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.ContentType

class SaleOrdersContentAdapter : RecyclerView.Adapter<ContentViewHolder>() {

    private val data = arrayListOf<ContentModel>()
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return when (viewType) {
            ContentType.SIMPLE_CARD ->
                ContentViewHolder.SimpleViewHolder(
                    createLayout(parent, R.layout.item_simple_card), viewPool
                )
            ContentType.TIP_CARD ->
                ContentViewHolder.TipViewHolder(
                    createLayout(parent, R.layout.item_tip_card), viewPool
                )
            else -> throw ClassNotFoundException("No existe el tipo de vista asignado")
        }
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int) = getItem(position).type

    private fun getItem(position: Int) = data[position]

    private fun createLayout(parent: ViewGroup, @LayoutRes layout: Int): View {
        return parent.inflate(layout)
    }

    fun addData(data: List<ContentModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}
