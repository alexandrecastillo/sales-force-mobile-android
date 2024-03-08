package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.formatearConComas
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.model.ProfitSeModel
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix
import kotlinx.android.synthetic.main.item_underline_category.view.*
import biz.belcorp.salesforce.base.R as BaseR

class ProfitSeAdapter : RecyclerView.Adapter<ProfitSeAdapter.ViewHolder>() {

    private var maxValue = 0f
    private var data = mutableListOf<ProfitSeModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_underline_category)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], maxValue)
    }

    override fun getItemCount() = data.size

    fun update(data: List<ProfitSeModel>, maxValue: Float) {
        this.maxValue = maxValue
        this.data.clear()
        this.data.addAll(data.asReversed())
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(model: ProfitSeModel, maxValue: Float) = with(itemView) {
            tvCampaign?.text = model.campaign.maskCampaignWithPrefix()
            ivCampaignIndicator?.visibility = View.GONE
            tvAmount?.text = getCampaignAmount(model, maxValue)
            tvAmount?.textColor = getColor(BaseR.color.black)
        }

        private fun getCampaignAmount(model: ProfitSeModel, maxValue: Float): String {
            val monto = model.total
            return if (monto == maxValue) {
                itemView.context.getString(R.string.monto_maximo, monto.formatearConComas())
            } else {
                monto.formatearConComas()
            }
        }
    }
}
