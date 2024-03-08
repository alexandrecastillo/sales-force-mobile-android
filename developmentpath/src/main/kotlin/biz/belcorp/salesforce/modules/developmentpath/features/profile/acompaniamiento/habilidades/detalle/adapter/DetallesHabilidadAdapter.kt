package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.ObtenerHabilidadesDetalleUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter.holder.DetallesHabilidadGrViewHolder
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter.holder.DetallesHabilidadGzViewHolder
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter.holder.DetallesHabilidadViewHolder
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.model.HabilidadAsignadaDetalleModel

class DetallesHabilidadAdapter : RecyclerView.Adapter<DetallesHabilidadViewHolder>() {

    var detalleHabilidades: List<HabilidadAsignadaDetalleModel> = emptyList()

    fun actualizar(detalleHabilidades: List<HabilidadAsignadaDetalleModel>) {
        this.detalleHabilidades = detalleHabilidades
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return detalleHabilidades.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetallesHabilidadViewHolder {
        return obtenerViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: DetallesHabilidadViewHolder, position: Int) {
        val detalleHabilidad = detalleHabilidades[position]
        holder.bind(detalleHabilidad, position)
    }

    override fun getItemViewType(position: Int): Int {
        return detalleHabilidades[position].tipoDetalle.codigo
    }

    private fun obtenerViewHolder(parent: ViewGroup,
                                  codigoTipoDetalle: Int):
        DetallesHabilidadViewHolder {

        return when (codigoTipoDetalle) {
            ObtenerHabilidadesDetalleUseCase.TipoDetalle.DETALLE_PARA_GZ.codigo -> {
                DetallesHabilidadGzViewHolder(obtenerLayoutPorTipoDetalle(parent, codigoTipoDetalle))
            }
            ObtenerHabilidadesDetalleUseCase.TipoDetalle.DETALLE_PARA_GR.codigo -> {
                DetallesHabilidadGrViewHolder(obtenerLayoutPorTipoDetalle(parent, codigoTipoDetalle))
            }
            else -> {
                error("Tipo detalle no valido")
            }
        }
    }

    private fun obtenerLayoutPorTipoDetalle(parent: ViewGroup,
                                            codigoTipoDetalle: Int): View {
        return when (codigoTipoDetalle) {
            ObtenerHabilidadesDetalleUseCase.TipoDetalle.DETALLE_PARA_GZ.codigo -> {
                LayoutInflater
                    .from(parent.context)
                    .inflate(
                        R.layout.item_rdd_detalle_habilidad_asignada_gz,
                        parent, false)
            }
            ObtenerHabilidadesDetalleUseCase.TipoDetalle.DETALLE_PARA_GR.codigo -> {
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_rdd_detalle_habilidad_asignada_gr,
                        parent, false)
            }
            else -> {
                error("Tipo detalle no valido")
            }
        }
    }
}
