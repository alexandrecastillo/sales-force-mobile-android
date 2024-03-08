package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gz.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.SeccionFocoModel
import kotlinx.android.synthetic.main.item_rdd_detalle_dashboard_focos_seccion.view.*

class FocosAdapter : RecyclerView.Adapter<FocosAdapter.FocoViewHolder>() {

    var focos: List<SeccionFocoModel.FocoModel> = emptyList()

    fun actualizar(focos: List<SeccionFocoModel.FocoModel>) {
        this.focos = focos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FocoViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_rdd_detalle_dashboard_focos_seccion, parent, false)

        return FocoViewHolder(
                view)
    }

    override fun getItemCount(): Int = focos.size


    override fun onBindViewHolder(holder: FocoViewHolder, position: Int) {
        val foco = focos[position]

        holder.tvTitulo.text = foco.descripcion
    }

    class FocoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.tv_titulo
    }
}
