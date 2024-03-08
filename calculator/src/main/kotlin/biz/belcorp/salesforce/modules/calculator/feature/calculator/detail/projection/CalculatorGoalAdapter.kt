package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.utils.onTextChanged
import biz.belcorp.salesforce.modules.calculator.R
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraMontoFijoModel
import kotlinx.android.synthetic.main.item_calculator_current_level_goal.view.*

class CalculatorGoalAdapter(
    private val list: List<CalculadoraMontoFijoModel>
) : RecyclerView.Adapter<CalculatorGoalAdapter.ViewHolder>() {

    private val attachToRoot: Boolean = false
    private val resource: Int = R.layout.item_calculator_current_level_goal
    var onFocusListener: OnFocusListener? = null

    fun getItem(position: Int): CalculadoraMontoFijoModel = list[position]
    fun isEmpty(): Boolean = list.isEmpty()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(resource, parent, attachToRoot))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: CalculadoraMontoFijoModel) {
            with(itemView.letGoal) {
                setLabelText(item.textoInput)
                getEditText().inputType = android.text.InputType.TYPE_CLASS_NUMBER
                getEditText().setOnFocusChangeListener { _, hasFocus ->
                    onFocusListener?.onFocusListenerEditText(item)
                }
                getEditText().onTextChanged {
                    item.inputTextUser = if (it.isEmpty()) EMPTY_STRING else it.toString()
                    onFocusListener?.onTextChangeListenerEditText()
                }
            }
        }

    }

    interface OnFocusListener {
        fun onFocusListenerEditText(item: CalculadoraMontoFijoModel)
        fun onTextChangeListenerEditText()
    }
}
