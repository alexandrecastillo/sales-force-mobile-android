package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.conditions.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.backgroundResource
import biz.belcorp.salesforce.modules.inspires.model.InspiraCondicionesLeyendaDetalleModel
import kotlinx.android.synthetic.main.item_inspire_travel_conditions_legend_detail.view.*
import biz.belcorp.salesforce.modules.inspires.R

class InspireConditionsLegendDetailAdapter(private val list: List<InspiraCondicionesLeyendaDetalleModel>) : RecyclerView.Adapter<InspireConditionsLegendDetailAdapter.ViewHolder>() {

    private val attachToRoot: Boolean = false
    private val resource: Int = R.layout.item_inspire_travel_conditions_legend_detail

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(resource, parent, attachToRoot), parent.context)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getItem(position: Int): InspiraCondicionesLeyendaDetalleModel = list[position]

    inner class ViewHolder(view: View, var context: Context) : RecyclerView.ViewHolder(view) {

        fun bind(model: InspiraCondicionesLeyendaDetalleModel) {
            itemView.txvInspireConditionSubItemBullet?.visibility = View.GONE
            itemView.txvInspireConditionSubItemDescription?.text = model.rango
            itemView.txvInspireConditionSubItemPoints?.text = getMask(model.puntos?.toInt())
            itemView.txvInspireConditionSubItemPoints?.backgroundResource = getBackground(model.puntos?.toInt())
        }

        private fun getMask(score: Int?): String = when (score) {
            Constant.NUMBER_ZERO -> context.resources.getString(R.string.inspira_score_zero, score)
            else -> context.resources.getString(R.string.inspira_score_other, score)
        }

        private fun getBackground(score: Int?): Int = when {
            score!! == 0 -> R.drawable.background_inspira_points_item_red
            score in 1..3 -> R.drawable.background_inspira_points_item_amber
            score in 4..8 -> R.drawable.background_inspira_points_item_green
            else -> R.drawable.background_inspira_points_item_green
        }
    }
}
