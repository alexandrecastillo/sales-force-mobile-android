package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.adapter.detail

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.ContentBaseModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.ItemType

class SaleOrdersItemAdapter(private val viewPool: RecyclerView.RecycledViewPool? = null) :
    RecyclerView.Adapter<ItemViewHolder>() {

    private val data = arrayListOf<ContentBaseModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            ItemType.SINGLE ->
                ItemViewHolder.SingleViewHolder(
                    createLayout(parent, R.layout.item_sale_orders_single)
                )
            ItemType.SINGLE_LEFT ->
                ItemViewHolder.SingleLeftViewHolder(
                    createLayout(parent, R.layout.item_sale_orders_single_left)
                )
            ItemType.SEPARATOR ->
                ItemViewHolder.SeparatorViewHolder(
                    createLayout(parent, R.layout.item_sale_orders_separator)
                )
            ItemType.COMPACT ->
                ItemViewHolder.CompactViewHolder(
                    createLayout(parent, R.layout.item_sale_orders_compact),
                    viewPool
                )
            ItemType.COMPLEX ->
                ItemViewHolder.ComplexViewHolder(
                    createLayout(parent,R.layout.item_sale_orders_complex), viewPool)
            ItemType.MULTIPLE ->
                ItemViewHolder.MultipleViewHolder(
                    createLayout(parent,R.layout.item_sale_orders_multiple
                    )
                )
            else -> throw ClassNotFoundException("No existe el tipo de vista asignado")
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int) = data[position]

    override fun getItemViewType(position: Int) = getItem(position).type

    private fun createLayout(parent: ViewGroup, @LayoutRes layout: Int): View {
        return parent.inflate(layout)
    }

    fun addData(data: List<ContentBaseModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}
