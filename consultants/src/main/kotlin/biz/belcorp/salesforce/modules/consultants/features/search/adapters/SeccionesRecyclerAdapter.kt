package biz.belcorp.salesforce.modules.consultants.features.search.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.features.search.models.SeccionModel
import kotlinx.android.synthetic.main.item_simple_filtro_busqueda.view.*

class SeccionesRecyclerAdapter(
    private val items: List<SeccionModel>,
    private val onClick: (SeccionModel) -> Unit
) : RecyclerView.Adapter<SeccionesRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_simple_filtro_busqueda, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: SeccionModel) {
            val defaultText = itemView.context.getString(R.string.show_all)
            item.apply { codigo = codigo ?: defaultText }
            itemView.tvTitle.text = item.codigo
            itemView.setOnClickListener {
                onClick.invoke(item)
            }
        }

    }

}
