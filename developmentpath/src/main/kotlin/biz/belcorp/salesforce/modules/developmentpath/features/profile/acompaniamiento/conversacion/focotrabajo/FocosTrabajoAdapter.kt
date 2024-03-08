package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion.focotrabajo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.developmentpath.R

class FocosTrabajoAdapter : RecyclerView.Adapter<FocoTrabajoViewHolder>() {

    var focosTrabajos: List<GraficoGrModel> = emptyList()

    var listener: FocoTrabajoListener? = null

    fun actualizar(focosTrabajos: List<GraficoGrModel>) {
        this.focosTrabajos = focosTrabajos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FocoTrabajoViewHolder {
        return FocoTrabajoViewHolder(
            obtenerViewLayoutPorTipo(parent, viewType)
        )
    }

    override fun getItemCount(): Int {
        return focosTrabajos.size
    }

    override fun onBindViewHolder(holder: FocoTrabajoViewHolder, position: Int) {
        val focoTrabajo = focosTrabajos[position]
        holder.bind(focoTrabajo, listener)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) FIRST_ITEM else SECOND_ITEM
    }

    private fun obtenerViewLayoutPorTipo(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(parent.context)
                .inflate(obtenerLayoutPorTipo(viewType), parent, false)
    }

    private fun obtenerLayoutPorTipo(tipoView: Int): Int {
        return when (tipoView) {
            FIRST_ITEM -> R.layout.item_rdd_acompaniamiento_foco_trabajo_primer
            SECOND_ITEM -> R.layout.item_rdd_acompaniamiento_foco_trabajo_otros
            else -> -1
        }
    }

    interface FocoTrabajoListener {
        fun onClickFocoTrabajo(posicion: Int)
    }

    companion object {
        const val FIRST_ITEM = 1
        const val SECOND_ITEM = 2
    }
}
