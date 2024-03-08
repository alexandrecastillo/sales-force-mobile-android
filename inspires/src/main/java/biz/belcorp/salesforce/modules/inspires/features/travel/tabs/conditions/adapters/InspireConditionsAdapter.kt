package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.conditions.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.inspires.model.InspiraCondicionesModel
import kotlinx.android.synthetic.main.item_inspire_travel_conditions.view.*
import biz.belcorp.salesforce.modules.inspires.R

class InspireConditionsAdapter(private val list: List<InspiraCondicionesModel>) : RecyclerView.Adapter<InspireConditionsAdapter.ViewHolder>() {

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

    fun getItem(position: Int): InspiraCondicionesModel = list[position]

    inner class ViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {

        fun bind(model: InspiraCondicionesModel) {
            val number: Int = adapterPosition + Constant.NUMBER_ONE
            itemView.ivwInspireConditionItemIcon?.imageResource = getIcon(model.codigo)
            itemView.txvInspireConditionItemTitle?.text = context.getString(R.string.inspira_condition_item_title_number, number, model.condicion)
            itemView.txvInspireConditionItemDescription?.text = model.descripcion
            itemView.txvInspireConditionItemTitle?.visibility = View.VISIBLE
            itemView.rvwInspireConditionItemDetail?.visibility = View.GONE
            itemView.txvInspireConditionItemNote?.visibility = View.GONE
        }

        private fun getIcon(codigo: String?): Int = when (codigo) {
            CONDITION_CODE_1 -> R.drawable.ic_success_level
            CONDITION_CODE_2 -> R.drawable.ic_inspira_grow
            CONDITION_CODE_3 -> R.drawable.ic_success_level
            CONDITION_CODE_4 -> R.drawable.ic_inspira_retention
            CONDITION_CODE_5 -> R.drawable.ic_inspira_order_amount
            else -> R.drawable.ic_success_level
        }
    }

    companion object {
        private const val CONDITION_CODE_1 = "201"
        private const val CONDITION_CODE_2 = "202"
        private const val CONDITION_CODE_3 = "203"
        private const val CONDITION_CODE_4 = "204"
        private const val CONDITION_CODE_5 = "205"
    }
}
