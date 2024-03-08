package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter.holder

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter.DetalleHabilidadAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.model.HabilidadAsignadaDetalleModel
import kotlinx.android.synthetic.main.item_rdd_detalle_habilidad_asignada_gr.view.*

class DetallesHabilidadGrViewHolder(view: View) : DetallesHabilidadViewHolder(view) {

    init {
        itemView.rv_detalle_habilidad.layoutManager = LinearLayoutManager(view.context)
        itemView.rv_detalle_habilidad.adapter = DetalleHabilidadAdapter()
        itemView.rv_detalle_habilidad.isNestedScrollingEnabled = false
    }

    override fun bind(detalleHabilidad: HabilidadAsignadaDetalleModel, posicion: Int) {
        itemView.iv_habilidad.imageResource = proveedorEstiloHabilidades.obtenerIcono(detalleHabilidad.tipoIcono)
        itemView.tv_titulo_habilidad.text = detalleHabilidad.descripcion
        (itemView.rv_detalle_habilidad.adapter as DetalleHabilidadAdapter).actualizar(detalleHabilidad.detalles)
        itemView.separador_detalles.visibility = obtenerVisibilidad(posicion)
    }

    private fun obtenerVisibilidad(posicion: Int): Int {
        return if (posicion == 1) View.GONE else View.VISIBLE
    }
}
