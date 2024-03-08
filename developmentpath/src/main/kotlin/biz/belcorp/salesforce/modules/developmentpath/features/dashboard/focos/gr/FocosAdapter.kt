package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_rdd_detalle_dashboard_focos_seccion.view.*

class FocosAdapter : RecyclerView.Adapter<FocosAdapter.FocoViewHolder>() {

    var focos: List<FocosYHabilidadesPorUa.FocoModel> = emptyList()

    fun actualizar(focos: List<FocosYHabilidadesPorUa.FocoModel>) {
        this.focos = focos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FocoViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_rdd_detalle_dashboard_focos_seccion, parent, false)
        return FocoViewHolder(view)
    }

    override fun getItemCount(): Int = focos.size

    override fun onBindViewHolder(holder: FocoViewHolder, position: Int) {
        val foco = focos[position]
        holder.bind(foco.descripcion)
    }

    class FocoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(title: String?) {
            itemView.tv_titulo?.text = title
        }
    }
}
