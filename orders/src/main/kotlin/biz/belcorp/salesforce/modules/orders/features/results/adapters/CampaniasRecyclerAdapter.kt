package biz.belcorp.salesforce.modules.orders.features.results.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.orders.R
import biz.belcorp.salesforce.modules.orders.features.search.model.CampaniaModel
import kotlinx.android.synthetic.main.item_simple.view.*


class CampaniasRecyclerAdapter(private val items: List<CampaniaModel>, private val onClick: (CampaniaModel) -> Unit) :
    RecyclerView.Adapter<CampaniasRecyclerAdapter.ViewHolder>() {

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

        fun bind(item: CampaniaModel) {
            val defaultText = itemView.context.getString(R.string.show_all)
            itemView.tvTitle.text = item.nombreCorto ?: defaultText
            itemView.setOnClickListener {
                onClick.invoke(item)
            }
        }

    }

}
