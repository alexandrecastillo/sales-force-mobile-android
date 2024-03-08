package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.core.utils.isNaNToZero
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_progreso_comportamiento.view.*
import kotlin.math.roundToInt

class ProgresoComportamientosAdapter :
    RecyclerView.Adapter<ProgresoComportamientosAdapter.ViewHolder>() {

    private var items = emptyList<ProgresoComportamientoModel>()

    fun actualizar(items: List<ProgresoComportamientoModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_progreso_comportamiento))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comportamiento = items[position]
        holder.bind(comportamiento)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ProgresoComportamientoModel) = with(itemView) {
            tvPorcentaje?.text = obtenerTextoPorcentaje(item.porcentaje)
            pgbPorcentaje?.progress = item.porcentaje
            ivComportamiento?.imageResource = item.iconoId
            pgbPorcentaje?.color = context.getCompatColor(item.color)
        }

        private fun obtenerTextoPorcentaje(porcentaje: Float): String {
            return itemView.context.getString(
                R.string.valor_porcentaje,
                porcentaje.isNaNToZero().roundToInt()
            )
        }
    }
}
