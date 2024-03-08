package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.backgroundResource
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.view.ProveedorEstiloHabilidad
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.model.HabilidadModel
import biz.belcorp.salesforce.modules.developmentpath.utils.reemplazarSaltoLinea
import kotlinx.android.synthetic.main.item_rdd_habilidad_asignada.view.*

class AsignarHabilidadesAdapter : RecyclerView.Adapter<AsignarHabilidadesAdapter.AsignarHabilidadesViewHolder>() {

    lateinit var proveedorEstiloHabilidades: ProveedorEstiloHabilidad
    lateinit var listener: HabilidadesAdapterListener
    var habilidades: List<HabilidadModel> = emptyList()

    override fun getItemCount(): Int {
        return habilidades.size
    }

    override fun onBindViewHolder(holder: AsignarHabilidadesViewHolder, position: Int) {
        val habilidad = habilidades[position]

        holder.tvHabilidad.text = habilidad.descripcion
        holder.tvHabilidadComentario.text = habilidad.comentario?.reemplazarSaltoLinea()
        holder.ivHabilidad.imageResource = proveedorEstiloHabilidades.obtenerIcono(habilidad.tipoIcono)
        holder.flHabilidad.setOnClickListener { listener.onClickHabilidad(position) }
        holder.viewNoSeleccionado.visibility = if (habilidad.opacado) View.VISIBLE else View.GONE
        holder.ivCheckSeleccionado.visibility = if (habilidad.seleccionado) View.VISIBLE else View.GONE
        holder.clHabilidad.backgroundResource = proveedorEstiloHabilidades.obtenerFondo(habilidad.seleccionado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsignarHabilidadesViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_rdd_habilidad_asignada, parent, false)
        proveedorEstiloHabilidades = ProveedorEstiloHabilidad()
        return AsignarHabilidadesViewHolder(view)
    }


    interface HabilidadesAdapterListener {
        fun onClickHabilidad(posicion: Int)
    }

    class AsignarHabilidadesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val flHabilidad = view.flHabilidad
        val ivHabilidad = view.ivHabilidad
        val ivCheckSeleccionado = view.ivCheckSeleccionado
        val clHabilidad = view.clHabilidad
        val tvHabilidad = view.tvHabilidadTitulo
        val tvHabilidadComentario = view.tvHabilidadComentario
        val viewNoSeleccionado = view.view_no_seleccionado
    }
}
