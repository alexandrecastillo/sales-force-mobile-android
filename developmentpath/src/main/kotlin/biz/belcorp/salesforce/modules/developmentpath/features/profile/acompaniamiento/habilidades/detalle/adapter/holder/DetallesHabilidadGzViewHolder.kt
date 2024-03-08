package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter.holder

import android.view.View
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.imageResource

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter.ComportamientoHabilidadAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter.DetalleHabilidadAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.model.HabilidadAsignadaDetalleModel
import kotlinx.android.synthetic.main.item_rdd_detalle_habilidad_asignada_gz.view.*

class DetallesHabilidadGzViewHolder(view: View) : DetallesHabilidadViewHolder(view) {

    init {
        itemView.rv_detalle_habilidad.layoutManager = NonScrollableLayoutManager()
            .withContext(view.context)
            .linearVertical()
        itemView.rv_detalle_habilidad.adapter = DetalleHabilidadAdapter()

        itemView.rv_comportamientos.layoutManager = NonScrollableLayoutManager()
            .withContext(view.context)
            .linearVertical()
        itemView.rv_comportamientos.adapter = ComportamientoHabilidadAdapter()

    }

    override fun bind(detalleHabilidad: HabilidadAsignadaDetalleModel, posicion: Int) {
        itemView.iv_habilidad.imageResource = proveedorEstiloHabilidades
            .obtenerIcono(detalleHabilidad.tipoIcono)
        itemView.tv_titulo_habilidad.text = detalleHabilidad.descripcion
        (itemView.rv_detalle_habilidad.adapter as DetalleHabilidadAdapter)
            .actualizar(detalleHabilidad.detalles)
        (itemView.rv_comportamientos.adapter as ComportamientoHabilidadAdapter)
            .actualizar(detalleHabilidad.comportamientos)
    }
}
