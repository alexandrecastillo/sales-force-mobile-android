package biz.belcorp.salesforce.components.features.search.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.components.R
import biz.belcorp.salesforce.core.features.search.models.TipoSegmentoModel
import kotlinx.android.synthetic.main.item_filter.view.*

class SegmentosRecyclerAdapter(
    private val items: List<TipoSegmentoModel>,
    private val onClick: (TipoSegmentoModel) -> Unit
) : RecyclerView.Adapter<SegmentosRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_filter, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: TipoSegmentoModel) {
            val defaultText = itemView.context.getString(R.string.show_all)
            itemView.tvTitle.text = item.descripcion ?: defaultText
            itemView.setOnClickListener {
                onClick.invoke(item)
            }
        }

    }

}
