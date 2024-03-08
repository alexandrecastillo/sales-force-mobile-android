package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.model.FocoModel
import kotlinx.android.synthetic.main.item_rdd_dashboard_se_focos.view.*


class FocosSEAdapter : RecyclerView.Adapter<FocosSEAdapter.FocosViewHolder>() {

    private var focos: List<FocoModel> = emptyList()

    fun actualizar(focos: List<FocoModel>) {
        this.focos = focos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FocosViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_rdd_dashboard_se_focos, parent, false)
        return FocosViewHolder(view)
    }

    override fun getItemCount() = focos.size

    override fun onBindViewHolder(holder: FocosViewHolder, position: Int) {
        val foco = focos[position]

        holder.titulo?.text = foco.descripcion
    }

    class FocosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView? = view.text_descripcion_se_focos
    }
}
