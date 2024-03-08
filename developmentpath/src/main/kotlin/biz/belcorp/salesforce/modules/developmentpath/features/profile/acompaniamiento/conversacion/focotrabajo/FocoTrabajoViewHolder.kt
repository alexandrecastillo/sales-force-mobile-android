package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion.focotrabajo

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.developmentpath.R

class FocoTrabajoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvTituloFocoTrabajo = view.findViewById<TextView>(R.id.tvTituloFocoTrabajo)
    private val tvSubtituloFocoTrabajo = view.findViewById<TextView>(R.id.tvSubtituloFocoTrabajo)
    private val ivFocoTrabajo = view.findViewById<ImageView>(R.id.ivFocoTrabajo)
    private val clFocoTrabajo = view.findViewById<ConstraintLayout>(R.id.clFocoTrabajo)

    private val proveedorIconoFocosTrabajo: ProveedorIconoFocosTrabajo
            by lazy { ProveedorIconoFocosTrabajo() }

    fun bind(graficoGr: GraficoGrModel, listener: FocosTrabajoAdapter.FocoTrabajoListener?) {
        tvTituloFocoTrabajo?.text = graficoGr.titulo
        tvSubtituloFocoTrabajo?.text = graficoGr.valor
        ivFocoTrabajo?.imageResource = proveedorIconoFocosTrabajo
                .obtenerIconoPorTipoGrafico(graficoGr.tipoGrafico.codigo)
        ivFocoTrabajo?.setColorFilter(R.color.black)
        clFocoTrabajo?.setOnClickListener { listener?.onClickFocoTrabajo(adapterPosition) }
    }
}
