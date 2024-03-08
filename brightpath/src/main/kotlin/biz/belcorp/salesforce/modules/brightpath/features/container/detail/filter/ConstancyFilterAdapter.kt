package biz.belcorp.salesforce.modules.brightpath.features.container.detail.filter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.brightpath.R
import biz.belcorp.salesforce.base.R as baseR
import biz.belcorp.salesforce.modules.brightpath.features.container.detail.constants.FilterConstants.TODOS
import biz.belcorp.salesforce.modules.brightpath.features.container.detail.constants.FilterConstants.CONSULTORA_AAMBAR
import biz.belcorp.salesforce.modules.brightpath.features.container.detail.constants.FilterConstants.CONSULTORA_BRILLANTE
import biz.belcorp.salesforce.modules.brightpath.features.container.detail.constants.FilterConstants.CONSULTORA_CONSULTORA
import biz.belcorp.salesforce.modules.brightpath.features.container.detail.constants.FilterConstants.CONSULTORA_CORAL
import biz.belcorp.salesforce.modules.brightpath.features.container.detail.constants.FilterConstants.CONSULTORA_PERLA
import biz.belcorp.salesforce.modules.brightpath.features.container.detail.constants.FilterConstants.CONSULTORA_TOPACIO
import kotlinx.android.synthetic.main.item_view_filtro_constancia.view.*
import java.util.*

class ConstancyFilterAdapter(
    onFilterConstancySelected: OnSelectedLevelFilter,
    private val selectionType: Int = 0
) : RecyclerView.Adapter<ConstancyFilterAdapter.FiltroConstanciaViewHolder>() {

    private var listener: OnSelectedLevelFilter? = null
    private var itemSelectableHandlerUtil: ItemSelectableHandleUtil? = null

    private val labels = mutableListOf<String>()

    init {
        initFilters()
        this.listener = onFilterConstancySelected
        this.itemSelectableHandlerUtil = ItemSelectableHandleUtil.init(0, labels)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FiltroConstanciaViewHolder {
        parent.inflate(R.layout.item_view_filtro_constancia)
        val view = parent.inflate(R.layout.item_view_filtro_constancia)
        return FiltroConstanciaViewHolder(view)
    }

    override fun getItemCount() = labels.size

    override fun onBindViewHolder(holder: FiltroConstanciaViewHolder, pos: Int) {
        holder.bind(labels[pos])
        handleItemSelected(holder.itemView.tvwFilterLabel, pos)
    }

    private fun handleItemSelected(holder: TextView, position: Int) {
        if (itemSelectableHandlerUtil?.isThisPosSelected(position) == true) {
            selectView(holder)
        } else {
            unSelectView(holder)
        }
    }

    private fun initFilters() {
        Locale.getDefault().let {
            labels.add(TODOS.toLowerCase(it).capitalize())

            if (selectionType == 4) {
                labels.add(CONSULTORA_CONSULTORA.toLowerCase(it).capitalize())
            }

            labels.add(CONSULTORA_CORAL.toLowerCase(it).capitalize())
            labels.add(CONSULTORA_AAMBAR.toLowerCase(it).capitalize())
            labels.add(CONSULTORA_PERLA.toLowerCase(it).capitalize())
            labels.add(CONSULTORA_TOPACIO.toLowerCase(it).capitalize())
            labels.add(CONSULTORA_BRILLANTE.toLowerCase(it).capitalize())
        }
    }

    private fun unSelectView(tv: TextView) {
        tv.apply {
            isSelected = false
            setTextColor(ContextCompat.getColor(tv.context, R.color.gris_escala_5))
        }
    }

    private fun selectView(tv: TextView) {
        tv.apply {
            isSelected = true
            setTextColor(ContextCompat.getColor(tv.context, baseR.color.white))
        }
    }

    inner class FiltroConstanciaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(label: String) {
            itemView.apply {
                isSelected = true
                itemView.tvwFilterLabel.text = label
            }
        }

        private fun doOnItemSelected(pos: Int) {
            itemSelectableHandlerUtil?.changeItemToSelected(pos)
            notifyItemChanged(pos)
        }

        override fun onClick(v: View?) {
            v?.apply {
                if (isSelected) return

                itemSelectableHandlerUtil?.let {
                    notifyItemChanged(it.getItemSelectedPos())
                }

                doOnItemSelected(adapterPosition)

                labels[adapterPosition]
                    .replace(TODOS.toLowerCase(Locale.getDefault()).capitalize(), EMPTY_STRING)
                    .let { listener?.onSelectedLevelFilter(it) }

            }
        }
    }
}
