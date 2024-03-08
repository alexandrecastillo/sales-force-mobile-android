package biz.belcorp.salesforce.modules.calculator.feature.calculator.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.doubleOrIntFormatWithCommas
import biz.belcorp.salesforce.modules.calculator.R
import biz.belcorp.salesforce.modules.calculator.model.CalculatingResultDetailModel
import kotlinx.android.synthetic.main.item_calculator_result.view.*

class CalculatorResultAdapter(val list: List<CalculatingResultDetailModel>, val symbol: String?) : RecyclerView.Adapter<CalculatorResultAdapter.ViewHolder>() {


    private val resource: Int = R.layout.item_calculator_result
    private val attachToRoot: Boolean = false

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(resource, parent, attachToRoot))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(model: CalculatingResultDetailModel) {
            itemView.txvCalculatorResultAmount?.text = model.etiqueta
            itemView.txvCalculatorResultDescription?.text = model.descripcion
            itemView.txvCalculatorResultValue?.text = this.getValue(model)
        }

        private fun getValue(model: CalculatingResultDetailModel): String? {
            val itemResult = model.valor?.times(model.cantidad
                ?: Constant.NUMBER_ZERO)?.doubleOrIntFormatWithCommas()
            return itemView.context?.getString(R.string.calculator_result_order_gain, symbol, itemResult)
        }
    }

}
