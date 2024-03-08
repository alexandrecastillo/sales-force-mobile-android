package biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Etiqueta
import kotlinx.android.synthetic.main.item_grupo_etiqueta.view.*

class GrupoEtiquetaAdapter : RecyclerView.Adapter<GrupoEtiquetaAdapter.EtiquetaViewHolder>() {

    private var etiquetas: List<Etiqueta> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtiquetaViewHolder {
        val view = parent.inflate(R.layout.item_grupo_etiqueta)
        return EtiquetaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return etiquetas.size
    }

    override fun onBindViewHolder(holder: EtiquetaViewHolder, position: Int) {
        holder.textViewEtiqueta.modelo = etiquetas[position]
    }

    val estaVacio get() = etiquetas.isEmpty()

    fun actualizar(etiquetas: List<Etiqueta>) {
        this.etiquetas = etiquetas
        notifyDataSetChanged()
    }

    inner class EtiquetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewEtiqueta: EtiquetaTextView = itemView.textViewEtiqueta
    }
}
