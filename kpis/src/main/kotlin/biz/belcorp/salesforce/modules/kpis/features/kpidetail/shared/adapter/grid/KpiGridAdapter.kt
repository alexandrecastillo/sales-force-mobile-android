package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.grid

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.INVALID_VIEW_TYPE
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.*

internal class KpiGridAdapter : RecyclerView.Adapter<GridViewHolder<*>>() {

    private val data = mutableListOf<GridModel>()

    private val itemType1 =
        KpiGridViewType.KPI_GRID_ITEM_1
    private val itemType2 =
        KpiGridViewType.KPI_GRID_ITEM_2
    private val itemType3 =
        KpiGridViewType.KPI_GRID_ITEM_3
    private val itemType4 =
        KpiGridViewType.KPI_GRID_ITEM_4
    private val itemType5 =
        KpiGridViewType.KPI_GRID_ITEM_5


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder<*> =
        when (viewType) {
            itemType1 -> GridViewHolder.Default(parent.inflate(R.layout.view_grid_kpi_content))
            itemType2 -> GridViewHolder.LeftAligned(parent.inflate(R.layout.view_left_aligned_grid_kpi_content))
            itemType3 -> GridViewHolder.ProgressBar(parent.inflate(R.layout.view_grid_kpi_content_progress_bar))
            itemType4 -> GridViewHolder.LeftAlignedWithDetails(parent.inflate(R.layout.view_left_aligned_with_details_grid_kpi_content))
            itemType5 -> GridViewHolder.LeftAlignedWithDetailsExpanded(parent.inflate(R.layout.view_left_aligned_with_details_expanded_grid_kpi_content))

            else -> throw Exception("Incorrect viewType")
        }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int) = when (data[position]) {
        is DefaultGridModel -> itemType1
        is LeftAlignedGridModel -> itemType2
        is ProgressBarGridModel -> itemType3
        is LeftAlignedWithDetailsGridModel -> itemType4
        is LeftAlignedWithDetailsExpandedGridModel -> itemType5

        else -> throw Exception(INVALID_VIEW_TYPE)
    }

    override fun onBindViewHolder(holder: GridViewHolder<*>, position: Int) {
        data[position].let {
            when (holder) {
                is GridViewHolder.Default -> holder.bind(it as DefaultGridModel)
                is GridViewHolder.LeftAligned -> holder.bind(it as LeftAlignedGridModel)
                is GridViewHolder.ProgressBar -> holder.bind(it as ProgressBarGridModel)
                is GridViewHolder.LeftAlignedWithDetails -> holder.bind(it as LeftAlignedWithDetailsGridModel)
                is GridViewHolder.LeftAlignedWithDetailsExpanded -> holder.bind(it as LeftAlignedWithDetailsExpandedGridModel)
            }
        }
    }

    fun update(data: List<GridModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

}
