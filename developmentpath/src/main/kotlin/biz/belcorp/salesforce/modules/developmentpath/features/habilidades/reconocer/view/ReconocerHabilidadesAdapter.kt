package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.backgroundResource
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.model.HabilidadModel
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.view.ProveedorEstiloHabilidad
import kotlinx.android.synthetic.main.item_rdd_habilidades.view.*

class ReconocerHabilidadesAdapter : RecyclerView.Adapter<ReconocerHabilidadesAdapter.ReconocerHabilidadesViewHolder>() {

    lateinit var proveedorEstiloHabilidades: ProveedorEstiloHabilidad
    lateinit var listener: HabilidadesAdapterListener
    var habilidades: List<HabilidadModel> = emptyList()

    override fun onBindViewHolder(holder: ReconocerHabilidadesViewHolder, position: Int) {
        val habilidad = habilidades[position]

        holder.tvHabilidad.text = habilidad.descripcion
        holder.ivHabilidad.imageResource = proveedorEstiloHabilidades.obtenerIcono(habilidad.tipoIcono)
        holder.ivHabilidad.setColorFilter(proveedorEstiloHabilidades.obtenerColorSeleccionado(habilidad.seleccionado))
        holder.flHabilidad.setOnClickListener { listener.onClickHabilidad(position) }
        holder.ivCheckSeleccionado.visibility = if (habilidad.seleccionado) View.VISIBLE else View.GONE
        holder.llHabilidad.backgroundResource = proveedorEstiloHabilidades.obtenerFondo(habilidad.seleccionado)
        holder.flHabilidad.isEnabled = habilidad.esClickeable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReconocerHabilidadesViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_rdd_habilidades, parent, false)
        proveedorEstiloHabilidades = ProveedorEstiloHabilidad()
        return ReconocerHabilidadesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return habilidades.size
    }

    interface HabilidadesAdapterListener {
        fun onClickHabilidad(posicion: Int)
    }

    class ReconocerHabilidadesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val flHabilidad = view.flHabilidad
        val ivHabilidad = view.iv_habilidad
        val ivCheckSeleccionado = view.ivCheckSeleccionado
        val llHabilidad = view.llHabilidad
        val tvHabilidad = view.tv_habilidad
    }
}
