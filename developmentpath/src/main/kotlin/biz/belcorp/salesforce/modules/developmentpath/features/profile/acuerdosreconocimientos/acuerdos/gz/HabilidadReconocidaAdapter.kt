package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.gz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.view.ProveedorEstiloHabilidad
import kotlinx.android.synthetic.main.item_rdd_habilidad_reconocida.view.*
import biz.belcorp.salesforce.core.utils.imageResource

class HabilidadReconocidaAdapter : RecyclerView.Adapter<HabilidadReconocidaAdapter.HabilidadReconocidaViewHolder>() {

    lateinit var proveedorEstiloHabilidades: ProveedorEstiloHabilidad
    var habilidades: List<HabilidadModel> = emptyList()

    fun actualizar(habilidades: List<HabilidadModel>) {
        this.habilidades = habilidades
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HabilidadReconocidaViewHolder, position: Int) {
        val habilidad = habilidades[position]
        holder.ivHabilidadReconocida.imageResource = proveedorEstiloHabilidades
            .obtenerIconoDetalle(habilidad.tipoIconoId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabilidadReconocidaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_rdd_habilidad_reconocida, parent, false)
        proveedorEstiloHabilidades = ProveedorEstiloHabilidad()
        return HabilidadReconocidaViewHolder(
            view)
    }

    override fun getItemCount(): Int {
        return habilidades.size
    }


    class HabilidadReconocidaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivHabilidadReconocida = view.iv_habilidad
    }
}
