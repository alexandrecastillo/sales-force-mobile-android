package biz.belcorp.salesforce.modules.digital.features.digital.adapters

import android.graphics.Typeface
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.charts.pie.PieItem
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TEN
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.digital.R
import biz.belcorp.salesforce.modules.digital.features.digital.model.DigitalHeaderItemModel
import kotlinx.android.synthetic.main.item_digital_header.view.*
import kotlin.math.roundToInt
import biz.belcorp.salesforce.base.R as BaseR

class DigitalHeaderAdapter : RecyclerView.Adapter<DigitalHeaderAdapter.ViewHolder>() {

    private val items = mutableListOf<DigitalHeaderItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_digital_header, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)
    }

    fun updateList(newItems: List<DigitalHeaderItemModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(model: DigitalHeaderItemModel) = with(itemView) {
            tvTitle?.text = model.title
            ivIcon?.setImageResource(model.iconResId)
            setupPieChart(model.percentageNumber)
            tvPercentageValue?.text = model.percentageValue
            tvPercentageDescription?.text = model.percentageDescription
            tvProgressDescription?.text = createSpannable(model.progressValue, model.progressDescription)
        }

        private fun setupPieChart(value: Float) = with(itemView) {
            pieChart?.data = listOf(
                PieItem(BaseR.color.magenta, EMPTY_STRING, (value * NUMBER_TEN).roundToInt()),
                PieItem(BaseR.color.gray_2, EMPTY_STRING, (NUMBER_TEN - (value * NUMBER_TEN)).roundToInt())
            )
        }

        private fun createSpannable(value: String, description: String): Spannable {
            val colorInt = itemView.context.getCompatColor(BaseR.color.magenta)
            val valueSpan = span(value, StyleSpan(Typeface.BOLD), ForegroundColorSpan(colorInt))
            return spannable { valueSpan + space() + span(description)}
        }

    }

}
