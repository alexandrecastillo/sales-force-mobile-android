package biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.design.switchx.Blocker
import biz.belcorp.mobile.components.design.switchx.Switch
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_ingresos_extras_marca.view.*


class IngresosExtraAdapter(
    private var dataSet: List<OtraMarcaModel> = emptyList(),
    var callback: Callback? = null
) : RecyclerView.Adapter<IngresosExtraAdapter.IngresosExtrasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngresosExtrasViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingresos_extras_marca, parent, false)
        return IngresosExtrasViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: IngresosExtrasViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = dataSet.size

    fun actualizar(data: List<OtraMarcaModel>) {
        this.dataSet = data
        notifyDataSetChanged()
    }

    fun getItems(): List<OtraMarcaModel> {
        return dataSet
    }

    fun apagarTodo() {
        dataSet.forEach { item ->
            item.checked = false
            item.categoria = ""
        }

        notifyDataSetChanged()
    }

    inner class IngresosExtrasViewHolder(view: View, private val callback: Callback?) :
        RecyclerView.ViewHolder(view),
        Switch.OnCheckedChangeListener,
        View.OnClickListener {

        private lateinit var model: OtraMarcaModel
        private val blocker by lazy { Blocker() }

        init {
            itemView.container_item?.setOnClickListener(this)
            itemView.switch_check?.registerOnCheckedChangeListener(this)
        }

        override fun onClick(v: View?) {
            itemView.switch_check?.performClick()
        }

        override fun onCheckedChanged(view: Switch?, isChecked: Boolean, isPressed: Boolean) {
            if (isPressed || itemView.container_item?.isPressed!!) {
                checkActionView(isChecked)
            }
        }

        fun bind(item: OtraMarcaModel) = with(itemView) {
            model = item
            logo?.setImageResource(if (item.checked) item.logo else item.logoUnChecked)
            switch_check?.titleTextOn = if (item.checked) TEXT_YES else TEXT_NO
            switch_check?.isChecked = item.checked
        }

        private fun checkActionView(isChecked: Boolean) {
            val onBlockStart = {
                itemView.switch_check?.isEnabled = false
                itemView.container_item?.isEnabled = false
            }
            val onBlockFinish = {
                itemView.switch_check?.isEnabled = true
                itemView.container_item?.isEnabled = true
            }
            if (!blocker.block(onBlockStart, onBlockFinish, BLOCK_IN_MILLIS)) {
                model.checked = isChecked
                callback?.onCheckIngresosExtrasItem(model, adapterPosition)
                notifyDataSetChanged()
            }
        }
    }

    interface Callback {
        fun onCheckIngresosExtrasItem(item: OtraMarcaModel, posicion: Int)
    }

    companion object {
        private const val BLOCK_IN_MILLIS = 700L
        private const val TEXT_YES = "Sí vende"
        private const val TEXT_NO = "No vende"
    }
}
