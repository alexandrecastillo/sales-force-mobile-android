package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.comportamientos

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.comportamientos.model.DesarrolloComportamientoModel
import kotlinx.android.synthetic.main.item_reconocimiento_seccion.view.*
import kotlin.properties.Delegates

class DesarrolloComportamientosAdapter :
    RecyclerView.Adapter<DesarrolloComportamientosAdapter.ViewHolder>() {

    var comportamientos: List<DesarrolloComportamientoModel>
        by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comportamientos[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_reconocimiento_seccion))
    }

    override fun getItemCount() = comportamientos.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(modelo: DesarrolloComportamientoModel) = with(itemView) {
            pgbPorcentaje?.progress = modelo.porcentaje.toFloat()
            txtDescripcion?.text = modelo.texto
            txtPorcentage?.text = context.getString(R.string.valor_porcentaje, modelo.porcentaje)
            if (modelo.iconoId != Constant.MENOS_UNO) {
                ivLogo?.imageResource = modelo.iconoId
            }
            val color = ContextCompat.getColor(itemView.context, modelo.color)
            pgbPorcentaje?.color = color
            txtPorcentage?.textColor = color
        }
    }
}
