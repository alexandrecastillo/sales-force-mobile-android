package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.model.HabilidadAsignadaDetalleModel
import kotlinx.android.synthetic.main.item_rdd_detalle_habilidad_asignada_comportamiento.view.*

class ComportamientoHabilidadAdapter : RecyclerView.Adapter<ComportamientoHabilidadAdapter.ComportamientoHabilidadViewHolder>() {

    lateinit var comportamientos: List<HabilidadAsignadaDetalleModel.Comportamiento>

    fun actualizar(comportamientos: List<HabilidadAsignadaDetalleModel.Comportamiento>) {
        this.comportamientos = comportamientos
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ComportamientoHabilidadViewHolder, position: Int) {
        val comportamiento = comportamientos[position]
        holder.tvDetalle.text = comportamiento.descripcion
    }

    override fun getItemCount(): Int {
        return comportamientos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComportamientoHabilidadViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_rdd_detalle_habilidad_asignada_comportamiento,
                parent, false)
        return ComportamientoHabilidadViewHolder(view)
    }


    class ComportamientoHabilidadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDetalle = view.tv_descripcion_comportamiento
    }
}
