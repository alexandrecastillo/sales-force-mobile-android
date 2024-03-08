package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.conditions.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.inspires.model.InspiraCondicionesLeyendaDetalleModel
import biz.belcorp.salesforce.modules.inspires.model.InspiraCondicionesLeyendaModel
import kotlinx.android.synthetic.main.item_inspire_travel_conditions.view.*
import biz.belcorp.salesforce.modules.inspires.R

class InspireConditionsLegendAdapter(
    private val list: List<InspiraCondicionesLeyendaModel>,
    private val detalle: List<InspiraCondicionesLeyendaDetalleModel>) : RecyclerView.Adapter<InspireConditionsLegendAdapter.ViewHolder>() {

    private val attachToRoot: Boolean = false
    private val resource: Int = R.layout.item_inspire_travel_conditions

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(resource, parent, attachToRoot), parent.context)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getItem(position: Int): InspiraCondicionesLeyendaModel = list[position]

    inner class ViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {

        fun bind(model: InspiraCondicionesLeyendaModel) {

            doAsync {
                val filtered = detalle.filter { model.codigo.equals(it.codigo) }
                uiThread {
                    itemView.ivwInspireConditionItemIcon?.imageResource = getIcon(model.codigo)
                    itemView.txvInspireConditionItemTitle?.text = model.titulo
                    itemView.txvInspireConditionItemDescription?.text = model.descripcion
                    itemView.txvInspireConditionItemNote?.text = model.nota

                    itemView.ivwInspireConditionItemIcon?.setColorFilter(ContextCompat.getColor(context, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)
                    itemView.rvwInspireConditionItemDetail?.visibility = View.VISIBLE
                    itemView.txvInspireConditionItemNote?.visibility = if (model.nota != null) View.VISIBLE else View.GONE

                    itemView.rvwInspireConditionItemDetail?.apply {
                        layoutManager = GridLayoutManager(context, Constant.NUMBER_ONE, GridLayoutManager.VERTICAL, false)
                        adapter = InspireConditionsLegendDetailAdapter(filtered)
                        isNestedScrollingEnabled = false
                    }
                }
            }
        }

        private fun getIcon(codigo: String?): Int = when (codigo) {
            CONDITION_LEGEND_CODE_1 -> R.drawable.ic_indicator_capitalization
            CONDITION_LEGEND_CODE_2 -> R.drawable.ic_inspira_pav
            CONDITION_LEGEND_CODE_3 -> R.drawable.ic_inspira_pav
            else -> R.drawable.ic_indicator_capitalization
        }
    }

    companion object {
        private const val CONDITION_LEGEND_CODE_1 = "001"
        private const val CONDITION_LEGEND_CODE_2 = "002"
        private const val CONDITION_LEGEND_CODE_3 = "003"
    }
}
