package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.habilidades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.backgroundColor
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.model.DesarrolloHabilidadModel
import kotlinx.android.synthetic.main.item_desarrollo_habilidad.view.*

class DesarrolloHabilidadAdapter(val list: List<DesarrolloHabilidadModel>) :
    RecyclerView.Adapter<DesarrolloHabilidadAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_desarrollo_habilidad,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: DesarrolloHabilidadModel) {

            itemView.pgb_porcentaje.progress = item.porcentaje.toFloat()
            itemView.image_logo.imageResource = item.iconResId
            itemView.txt_description.text = item.descripcion

            val porcentaje = "${item.porcentaje}%"

            itemView.txt_percentage.text = porcentaje

            val color = ContextCompat.getColor(itemView.context, item.colorResId)
            itemView.pgb_porcentaje?.color = color
            itemView.txt_percentage.setTextColor(color)

        }

    }
}
