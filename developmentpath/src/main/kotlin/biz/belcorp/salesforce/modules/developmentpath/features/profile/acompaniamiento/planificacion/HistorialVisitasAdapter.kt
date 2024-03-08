package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.planificacion

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_historial_visitas.view.*

class HistorialVisitasAdapter : RecyclerView.Adapter<HistorialVisitasAdapter.VisitaHolder>() {

    private var modelos = emptyList<VisitaModel>()

    fun actualizar(modelos: List<VisitaModel>) {
        this.modelos = modelos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitaHolder {
        val view = parent.inflate(R.layout.item_historial_visitas)
        return VisitaHolder(view)
    }

    override fun getItemCount() = modelos.size

    override fun onBindViewHolder(holder: VisitaHolder, position: Int) {
        val modelo = modelos[position]
        holder.tvFechaHora.text = modelo.fechaHora
    }

    inner class VisitaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFechaHora: TextView = itemView.tvFechaHora
    }
}
