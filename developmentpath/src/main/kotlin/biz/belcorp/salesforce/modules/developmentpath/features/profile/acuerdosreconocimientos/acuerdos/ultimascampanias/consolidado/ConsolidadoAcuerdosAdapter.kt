package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.consolidado

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.salesforce.core.utils.dip
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado.CumplimientoCampania
import kotlinx.android.synthetic.main.item_digital_campania_horizontal.view.*

class ConsolidadoAcuerdosAdapter :
    RecyclerView.Adapter<ConsolidadoAcuerdosAdapter.CumplimientoViewHolder>() {

    private var cumplimientos = emptyList<CumplimientoModel>()
    private val diffCallback = ConsolidadoAcuerdosDiffCallback()

    fun actualizar(cumplimientos: List<CumplimientoModel>) {
        diffCallback.establecer(this.cumplimientos, cumplimientos)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.cumplimientos = cumplimientos
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CumplimientoViewHolder {
        val view = parent.inflate(R.layout.item_digital_campania_horizontal)
        view.layoutParams = ViewGroup.LayoutParams(
            parent.context.dip(40),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return CumplimientoViewHolder(view)
    }

    override fun getItemCount() = cumplimientos.size

    override fun onBindViewHolder(holder: CumplimientoViewHolder, position: Int) {
        val cumplimiento = cumplimientos[position]
        holder.bind(cumplimiento)
    }

    class CumplimientoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(modelo: CumplimientoModel) = with(itemView) {
            text_title.text = modelo.campania
            text_value.text = itemView.context.getString(obtenerRecursoTexto(modelo.estado))
            text_value.textColor = getColor(obtenerRecursoColor(modelo.estado))
        }

        private fun obtenerRecursoTexto(estado: CumplimientoCampania.Estado): Int {
            return when (estado) {
                CumplimientoCampania.Estado.CUMPLIDO -> R.string.acuerdos_consolidado_cumplido
                CumplimientoCampania.Estado.NO_CUMPLIDO -> R.string.acuerdos_consolidado_no_cumplido
                CumplimientoCampania.Estado.NINGUNO -> R.string.acuerdos_consolidado_ninguno
            }
        }

        private fun obtenerRecursoColor(estado: CumplimientoCampania.Estado): Int {
            return when (estado) {
                CumplimientoCampania.Estado.CUMPLIDO -> R.color.estado_positivo
                CumplimientoCampania.Estado.NO_CUMPLIDO -> R.color.estado_negativo
                CumplimientoCampania.Estado.NINGUNO -> R.color.gray_4
            }
        }
    }
}
