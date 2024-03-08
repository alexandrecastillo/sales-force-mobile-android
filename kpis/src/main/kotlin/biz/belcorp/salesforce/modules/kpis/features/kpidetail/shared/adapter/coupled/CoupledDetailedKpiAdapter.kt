package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.coupled

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.INVALID_VIEW_TYPE
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel

class CoupledDetailedKpiAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    private val data = mutableListOf<CoupledModel>()

    private val itemType1 =
        DetailedKpiViewType.DETAILED_KPI_ITEM_1
    private val itemType2 =
        DetailedKpiViewType.DETAILED_KPI_ITEM_2
    private val itemType3 =
        DetailedKpiViewType.DETAILED_KPI_ITEM_3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            itemType1 -> getSingleViewHolder(parent)
            itemType2 -> getSingleGridViewHolder(parent)
            itemType3 -> getSingleItemWithGridViewHolder(parent)
            else -> throw Exception(INVALID_VIEW_TYPE)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is CoupledModel.SingleItem -> itemType1
            is CoupledModel.GridsItemModel -> itemType2
            is CoupledModel.GridWithHeaderItemModel -> itemType3
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when (holder) {
            is KpiViewHolder.SingleItem -> holder.bind(item as CoupledModel.SingleItem)
            is KpiViewHolder.SingleItemWithGridItems -> holder.bind(item as CoupledModel.GridWithHeaderItemModel)
            is KpiViewHolder.GridItems -> holder.bind(item as CoupledModel.GridsItemModel)
        }
    }

    fun update(data: List<CoupledModel>) {
        this.data.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    private fun getSingleViewHolder(viewGroup: ViewGroup) =
        KpiViewHolder.SingleItem(viewGroup.inflate(R.layout.view_header_kpi))

    private fun getSingleItemWithGridViewHolder(viewGroup: ViewGroup) =
        KpiViewHolder.SingleItemWithGridItems(
            viewGroup.inflate(R.layout.item_grid_kpi_with_values),
            viewPool
        )

    private fun getSingleGridViewHolder(viewGroup: ViewGroup) =
        KpiViewHolder.GridItems(
            viewGroup.inflate(R.layout.item_simple_grid),
            viewPool
        )

}
