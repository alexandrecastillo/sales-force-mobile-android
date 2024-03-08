package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.model.MiHabilidadModel
import kotlinx.android.synthetic.main.item_rdd_detalle_dashboard_habilidades.view.*

class MisHabilidadesAdapter : RecyclerView.Adapter<MisHabilidadesAdapter.ViewHolder>() {

    private var habilidades = mutableListOf<MiHabilidadModel>()

    fun actualizar(habilidades: List<MiHabilidadModel>) {
        this.habilidades = habilidades.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habilidades[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_rdd_detalle_dashboard_habilidades, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = habilidades.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val textTitulo = view.tv_titulo

        fun bind(model: MiHabilidadModel, posicion: Int) {
            textTitulo.text = model.descripcion
        }
    }
}
