package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.gz.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr.FocosYHabilidadesPorUa
import kotlinx.android.synthetic.main.item_rdd_detalle_dashboard_habilidades.view.*

class HabilidadAdapter : RecyclerView.Adapter<HabilidadAdapter.HabilidadViewHolder>() {


    var habilidades: List<FocosYHabilidadesPorUa.HabilidadModel> = emptyList()

    fun actualizar(habilidades: List<FocosYHabilidadesPorUa.HabilidadModel>) {
        this.habilidades = habilidades
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabilidadViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_rdd_detalle_dashboard_habilidades, parent, false)

        return HabilidadViewHolder(view)
    }

    override fun getItemCount(): Int = habilidades.size

    override fun onBindViewHolder(holder: HabilidadViewHolder, position: Int) {
        val foco = habilidades[position]
        holder.bind(foco.descripcion)
    }
    class HabilidadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(title: String?) {
            itemView.tv_titulo?.text = title
        }
    }
}
