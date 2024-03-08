package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.model.ComportamientoModel
import kotlinx.android.synthetic.main.item_rdd_comportamiento.view.*

class ComportamientosAdapter : RecyclerView.Adapter<ComportamientosAdapter.ViewHolder>() {

    private var modelos = emptyList<ComportamientoModel>()

    fun actualizar(modelos: List<ComportamientoModel>) {
        this.modelos = modelos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_rdd_comportamiento)
        return ViewHolder(view)
    }

    override fun getItemCount() = modelos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelo = modelos[position]
        holder.bind(modelo)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ComportamientoModel) = with(itemView) {
            iconComportamiento?.imageResource = item.iconoId
            iconComportamiento?.setColorFilter(Color.WHITE)
            backgroundComportamiento?.setColorFilter(obtenerColorFondo(item.reconocido))
        }

        private fun obtenerColorFondo(reconocido: Boolean): Int {
            val colorId = if (reconocido) R.color.estado_positivo else R.color.gray_3
            return itemView.context.getCompatColor(colorId)
        }
    }
}
