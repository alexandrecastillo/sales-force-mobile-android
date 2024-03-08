package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.advance.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.inspires.model.InspiraAvancesModel
import kotlinx.android.synthetic.main.item_inspire_travel_advance_campaign.view.*
import biz.belcorp.salesforce.modules.inspires.R
import biz.belcorp.salesforce.modules.inspires.util.enmascararACampania

class InspireAdvanceAdapter(
    private val list: List<InspiraAvancesModel>,
    private val limited: Boolean,
    private val active: Boolean,
    private val hasPeriod: Boolean) : RecyclerView.Adapter<InspireAdvanceAdapter.AvanceInspiraViewHolder>() {

    private val attachToRoot: Boolean = false
    private val resource: Int = R.layout.item_inspire_travel_advance_campaign

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): AvanceInspiraViewHolder {
        return AvanceInspiraViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(resource, parent, attachToRoot), parent.context)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AvanceInspiraViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getItem(position: Int): InspiraAvancesModel = list[position]

    inner class AvanceInspiraViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {

        private var items: MutableList<String> = arrayListOf()

        fun bind(model: InspiraAvancesModel) {
            itemView.tvwInspireProgressCampaignNumber?.text = context.getString(if (active) R.string.inspira_progress_campaign_prefix else R.string.inspira_progress_campaign_prefix_lost, model.campania.toString().enmascararACampania())
            itemView.tvwInspireProgressCampaignSeparator?.visibility = if (active) View.VISIBLE else View.GONE
            itemView.tvwInspireProgressCampaignDescription?.apply {
                text = context.getString(R.string.inspira_progress_campaign_points, model.puntosAcumulados)
                textColor = getTextColor(model.puntosAcumulados)
                visibility = if (active) View.VISIBLE else View.GONE
            }

            addItem(model.statusNivel, active, context, R.string.inspira_progress_campaign_1, R.string.inspira_progress_campaign_lost_1, false)
            addItem(model.porcRetencionActivas, active, context, R.string.inspira_progress_campaign_2, R.string.inspira_progress_campaign_lost_2, true)
            addItem(model.actividad, active, context, R.string.inspira_progress_campaign_3, R.string.inspira_progress_campaign_lost_3, true)
            addItem(model.rangoActividad, active, context, R.string.inspira_progress_campaign_4, R.string.inspira_progress_campaign_lost_4, true)
            addItem(model.capitalizacion, active, context, R.string.inspira_progress_campaign_5, R.string.inspira_progress_campaign_lost_5, true)
            addItem(model.porcMontoPedido, active, context, R.string.inspira_progress_campaign_6, R.string.inspira_progress_campaign_lost_6, true)
            addItem(model.porcPAV, active, context, R.string.inspira_progress_campaign_7, R.string.inspira_progress_campaign_lost_7, true)
            addItem(model.retencion, active, context, R.string.inspira_progress_campaign_8, R.string.inspira_progress_campaign_lost_8, true)

            if (limited.not() && active.not()) {
                addItem(model.puntosAcumulados, context, R.plurals.inspira_progress_campaign_lost_9)
            }
            itemView.rvwInspireProgressDetail?.apply {
                layoutManager = GridLayoutManager(context, Constant.NUMBER_ONE, GridLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false
                adapter = InspireAdvanceDetailAdapter(items)
            }

            if (Constant.NUMBER_ONE == itemCount) {
                itemView.vwInspireProgressLineBottom?.visibility = View.GONE
            }
            if (Constant.NUMBER_ZERO == adapterPosition) {
                itemView.vwInspireProgressLineTop?.visibility = View.GONE
                itemView.vwInspireProgressDot?.background = ContextCompat.getDrawable(itemView.context, R.drawable.dot_inspira_on)
                TextViewCompat.setTextAppearance(itemView.tvwInspireProgressCampaignNumber, R.style.AppTheme_Inspire_TextView_Normal_Bold)
                TextViewCompat.setTextAppearance(itemView.tvwInspireProgressCampaignDescription, R.style.AppTheme_Inspire_TextView_Normal)
            }
            if (list.lastIndex == adapterPosition) {
                itemView.vwInspireProgressLineBottom?.visibility = if (limited.not() && hasPeriod) View.VISIBLE else View.GONE
            }
            if (Constant.NUMBER_ZERO < adapterPosition) {
                itemView.rvwInspireProgressDetail?.visibility = View.GONE
            }
            if (limited) {
                itemView.tvwInspireProgressCampaignSeparator?.visibility = View.GONE
                itemView.tvwInspireProgressCampaignDescription?.visibility = View.GONE
            }
        }

        private fun getTextColor(score: Int): Int = context.getCompatColor(when (score) {
            0 -> R.color.estado_negativo
            in 1..3 -> R.color.estado_intermedio
            in 4..8 -> R.color.estado_positivo
            else -> R.color.estado_positivo
        })

        private fun addItem(value: Any?, context: Context, @PluralsRes plural: Int) {
            value?.let { items.add(context.resources.getQuantityString(plural, it as Int)) }
        }

        private fun addItem(value: Any?, active: Boolean, context: Context, @StringRes activeMessage: Int, @StringRes inactiveMessage: Int, useValue: Boolean) {
            value?.let {
                items.add(
                        if (useValue) context.getString(if (active) activeMessage else inactiveMessage, it)
                        else context.getString(if (active) activeMessage else inactiveMessage))
            }
        }
    }
}
