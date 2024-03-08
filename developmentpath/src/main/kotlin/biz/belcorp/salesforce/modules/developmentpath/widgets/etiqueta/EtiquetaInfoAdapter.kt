package biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Grupo

class EtiquetaInfoAdapter(
    var grupos: List<Grupo> = emptyList()
) : RecyclerView.Adapter<EtiquetaInfoAdapter.GrupoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoViewHolder {
        val view = GrupoEtiquetaLayout(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return GrupoViewHolder(view)
    }

    override fun getItemCount() = grupos.size

    override fun onBindViewHolder(holder: GrupoViewHolder, position: Int) {
        holder.grupoLayout.modelo = grupos[position]
    }

    fun actualizar(grupos: List<Grupo>) {
        this.grupos = grupos
        notifyDataSetChanged()
    }

    inner class GrupoViewHolder(val grupoLayout: GrupoEtiquetaLayout) :
        RecyclerView.ViewHolder(grupoLayout)
}
