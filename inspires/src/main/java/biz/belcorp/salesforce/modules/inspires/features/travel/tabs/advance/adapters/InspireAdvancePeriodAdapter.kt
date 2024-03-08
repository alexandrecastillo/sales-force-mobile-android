package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.advance.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.inspires.model.InspiraAvancePeriodoModel
import kotlinx.android.synthetic.main.item_inspire_travel_advance_campaign.view.*
import biz.belcorp.salesforce.modules.inspires.R

class InspireAdvancePeriodAdapter(private val list: List<InspiraAvancePeriodoModel>) : RecyclerView.Adapter<InspireAdvancePeriodAdapter.InspiraAvancePeriodoViewHolder>() {

    private val attachToRoot: Boolean = false
    private val resource: Int = R.layout.item_inspire_travel_advance_campaign

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): InspiraAvancePeriodoViewHolder {
        return InspiraAvancePeriodoViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(resource, parent, attachToRoot), parent.context)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: InspiraAvancePeriodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getItem(position: Int): InspiraAvancePeriodoModel = list[position]

    inner class InspiraAvancePeriodoViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {

        fun bind(model: InspiraAvancePeriodoModel) {
            if (Constant.NUMBER_ONE == itemCount) {
                itemView.vwInspireProgressLineBottom?.visibility = View.GONE
            }
            if (list.lastIndex == adapterPosition) {
                itemView.vwInspireProgressLineBottom?.visibility = View.GONE
            }
            itemView.vwInspireProgressDot?.background = ContextCompat.getDrawable(context, R.drawable.dot_inspira_off)
            itemView.tvwInspireProgressCampaignNumber?.text = context.getString(R.string.inspira_progress_period_prefix, model.cardinal)
            itemView.tvwInspireProgressCampaignSeparator?.visibility = View.VISIBLE
            itemView.tvwInspireProgressCampaignDescription?.text = context.getString(R.string.inspira_progress_campaign_points, model.puntaje)
            itemView.tvwInspireProgressCampaignDescription?.textColor = getTextColor(model.puntaje)
        }

        private fun getTextColor(score: Int): Int = context.getCompatColor(when (score) {
            0 -> R.color.estado_negativo
            in 1..3 -> R.color.estado_intermedio
            in 4..8 -> R.color.estado_positivo
            else -> R.color.estado_positivo
        })
    }
}
