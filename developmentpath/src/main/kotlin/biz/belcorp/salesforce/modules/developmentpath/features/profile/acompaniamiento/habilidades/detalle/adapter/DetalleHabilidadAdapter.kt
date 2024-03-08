package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.model.HabilidadAsignadaDetalleModel
import kotlinx.android.synthetic.main.item_rdd_detalle_habilidad_asignada_detalle.view.*

class DetalleHabilidadAdapter : RecyclerView.Adapter<DetalleHabilidadAdapter.DetalleHabilidadViewHolder>() {

    lateinit var detalles: List<HabilidadAsignadaDetalleModel.Detalle>

    fun actualizar(detalles: List<HabilidadAsignadaDetalleModel.Detalle>) {
        this.detalles = detalles
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DetalleHabilidadViewHolder, position: Int) {
        val detalle = detalles[position]
        holder.tvDetalle.text = detalle.descripcion
    }

    override fun getItemCount(): Int {
        return detalles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleHabilidadViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_rdd_detalle_habilidad_asignada_detalle,
                parent, false)
        return DetalleHabilidadViewHolder(view)
    }


    class DetalleHabilidadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDetalle = view.tv_descripcion_detalle
    }
}
