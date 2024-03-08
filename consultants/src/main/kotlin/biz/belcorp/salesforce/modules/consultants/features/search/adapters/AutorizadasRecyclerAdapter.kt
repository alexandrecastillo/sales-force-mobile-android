package biz.belcorp.salesforce.modules.consultants.features.search.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.features.search.models.TipoAutorizadoModel
import kotlinx.android.synthetic.main.item_simple_filtro_busqueda.view.*

class AutorizadasRecyclerAdapter(
    private val items: List<TipoAutorizadoModel>,
    private val onClick: (TipoAutorizadoModel) -> Unit
) : RecyclerView.Adapter<AutorizadasRecyclerAdapter.ViewHolder>() {

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

        fun bind(item: TipoAutorizadoModel) {
            val defaultText = itemView.context.getString(R.string.show_all)
            itemView.tvTitle.text = item.descripcion ?: defaultText
            itemView.setOnClickListener {
                onClick.invoke(item)
            }
        }

    }

}
