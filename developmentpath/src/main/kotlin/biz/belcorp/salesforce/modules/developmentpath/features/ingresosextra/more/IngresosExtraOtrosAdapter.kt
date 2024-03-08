package biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.more

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getDrawable
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.OtraMarcaModel
import kotlinx.android.synthetic.main.item_ingresos_extras_marca_otros.view.*
import biz.belcorp.salesforce.base.R as BaseR

class IngresosExtraOtrosAdapter(
    var dataSet: List<OtraMarcaModel> = emptyList(),
    var callback: Callback? = null
) : RecyclerView.Adapter<IngresosExtraOtrosAdapter.IngresosExtrasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngresosExtrasViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingresos_extras_marca_otros, parent, false)
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

    fun checkedItem(position: Int) {
        dataSet[position].checked = !dataSet[position].checked
        notifyItemChanged(position)
    }

    fun clearCheckedItems() {
        for (i in dataSet.indices) {
            dataSet[i].checked = false
        }
        notifyDataSetChanged()
    }

    fun getItemsObject(): List<OtraMarcaModel> {
        return dataSet
    }

    inner class IngresosExtrasViewHolder(
        view: View, val callback: Callback?
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var model: OtraMarcaModel

        init {
            itemView.container_item.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                checkedItem(pos)
                callback?.onCheckMarcaItem(model, pos)
            }
        }

        fun bind(item: OtraMarcaModel) = with(itemView) {
            model = item
            text_title.text = item.name
            val icon = if (item.checked) {
                getDrawable(BaseR.drawable.abc_btn_check_to_on_mtrl_015)
            } else {
                getDrawable(BaseR.drawable.abc_btn_check_to_on_mtrl_000)
            }
            icon_check.setImageDrawable(icon?.mutate())
        }
    }

    interface Callback {
        fun onCheckMarcaItem(item: OtraMarcaModel, posicion: Int)
    }
}
