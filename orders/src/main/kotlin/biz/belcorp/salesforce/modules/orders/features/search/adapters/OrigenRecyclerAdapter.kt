package biz.belcorp.salesforce.modules.orders.features.search.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.orders.R
import biz.belcorp.salesforce.modules.orders.features.search.model.TipoOrigenModel
import kotlinx.android.synthetic.main.item_simple.view.*


class OrigenRecyclerAdapter(
    private val items: List<TipoOrigenModel>,
    private val onClick: (TipoOrigenModel) -> Unit
) : RecyclerView.Adapter<OrigenRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_simple, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: TipoOrigenModel) {
            val defaultText = itemView.context.getString(R.string.show_all)
            itemView.tvTitle?.text = item.descripcion ?: defaultText
            itemView.setOnClickListener {
                onClick.invoke(item)
            }
        }

    }

}
