package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.model.CapitalizationSeModel
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix
import kotlinx.android.synthetic.main.item_body_table_capitalizacion.view.*

class CapitalizationSeAdapter : RecyclerView.Adapter<CapitalizationSeAdapter.ViewHolder>() {

    private var items = mutableListOf<CapitalizationSeModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_body_table_capitalizacion)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setElementsUpdate(elements: List<CapitalizationSeModel>) {
        items = elements.toMutableList().asReversed()
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(value: CapitalizationSeModel) = with(itemView) {
            txtCampaniaItemCap?.text = value.campaign.maskCampaignWithPrefix()
            txtMontoIngreso?.text = value.entries.toString()
            txtMontoReingreso?.text = value.reentries.toString()
            txtMontoEgreso?.text = value.expenses.toString()
            txtMontoCapitalizacion?.textColor = context.getCompatColor(R.color.green_cav)
            txtMontoCapitalizacion?.text = value.real.toString()
        }
    }
}
