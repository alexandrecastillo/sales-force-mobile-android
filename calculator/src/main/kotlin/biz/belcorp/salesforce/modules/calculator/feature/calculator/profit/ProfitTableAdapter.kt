package biz.belcorp.salesforce.modules.calculator.feature.calculator.profit

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.backgroundDrawable
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.calculator.R
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraMontoFijoModel
import kotlinx.android.synthetic.main.item_profit_table.view.*

class ProfitTableAdapter : RecyclerView.Adapter<ProfitTableAdapter.ProfitTableViewHolder>() {

    private var calculadoraMontoFijoList: List<CalculadoraMontoFijoModel> = emptyList()
    private var isPar: Boolean = false
    private var symbol: String = Constant.EMPTY_STRING

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ProfitTableViewHolder {
        val view = parent.inflate(R.layout.item_profit_table)

        return ProfitTableViewHolder(view)
    }

    fun setElements(list: List<CalculadoraMontoFijoModel>) {
        calculadoraMontoFijoList = list
    }

    fun setSymbol(symbol: String) {
        this.symbol = symbol
    }

    override fun getItemCount(): Int {
        return calculadoraMontoFijoList.size
    }

    override fun onBindViewHolder(viewHolder: ProfitTableViewHolder, position: Int) {
        viewHolder.bind(calculadoraMontoFijoList[position])
        isPar = position % 2 == Constant.NUMBER_ZERO
    }

    inner class ProfitTableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: CalculadoraMontoFijoModel) = with(itemView) {

            tvRango?.text = item.textoInput
            tvComision?.text = context.getString(
                R.string.calculator_dashboard_tab_otro_nivel_ganancia,
                symbol,
                item.textoBonoExitoso
            )
            val drawable = if (isPar) {
                R.drawable.background_profit_table_row_ligth
            } else {
                R.drawable.background_profit_table_row_dark
            }
            llItemRango.backgroundDrawable = ContextCompat.getDrawable(context, drawable)
        }

    }

}
