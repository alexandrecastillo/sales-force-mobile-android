package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.view.ProveedorEstiloHabilidad
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.model.HabilidadAsignadaDetalleModel

abstract class DetallesHabilidadViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    protected val proveedorEstiloHabilidades: ProveedorEstiloHabilidad by lazy {
        ProveedorEstiloHabilidad()
    }

    abstract fun bind(detalleHabilidad: HabilidadAsignadaDetalleModel, posicion: Int)
}
