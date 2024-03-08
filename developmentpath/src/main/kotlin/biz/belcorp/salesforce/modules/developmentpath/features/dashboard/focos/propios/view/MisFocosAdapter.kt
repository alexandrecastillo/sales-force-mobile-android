package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.model.MiFocoModel
import kotlinx.android.synthetic.main.item_rdd_detalle_dashboard_focos_seccion.view.*

class MisFocosAdapter : RecyclerView.Adapter<MisFocosAdapter.ViewHolder>() {

    private var focos = mutableListOf<MiFocoModel>()

    fun actualizar(focos: MutableList<MiFocoModel>) {
        this.focos = focos
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(focos[position], position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_rdd_detalle_dashboard_focos_seccion, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = focos.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val textTitulo = view.tv_titulo

        fun bind(model: MiFocoModel, posicion: Int) {
            textTitulo.text = model.descripcion
        }
    }
}
