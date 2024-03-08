package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.grid

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.*
import kotlinx.android.synthetic.main.view_grid_kpi_content.view.*
import kotlinx.android.synthetic.main.view_grid_kpi_content_progress_bar.view.*
import kotlinx.android.synthetic.main.view_left_aligned_grid_kpi_content.view.*
import kotlinx.android.synthetic.main.view_left_aligned_grid_kpi_content.view.tvKpiDetailedChildContentLabel
import kotlinx.android.synthetic.main.view_left_aligned_grid_kpi_content.view.tvKpiDetailedChildContentValue
import kotlinx.android.synthetic.main.view_left_aligned_with_details_expanded_grid_kpi_content.view.*
import kotlinx.android.synthetic.main.view_left_aligned_with_details_grid_kpi_content.view.*


internal sealed class GridViewHolder<T : GridModel>(itemView: View) :
    BaseGridViewHolder<T>(itemView) {

    override fun bind(model: T) = with(itemView) {
        tvGridKpiLabel?.text = model.label
        tvGridKpiValue?.text = model.value
    }

    class Default(itemView: View) : GridViewHolder<DefaultGridModel>(itemView)

    class LeftAligned(itemView: View) : GridViewHolder<LeftAlignedGridModel>(itemView) {
        override fun bind(model: LeftAlignedGridModel) = with(itemView) {
            tvKpiDetailedChildContentLabel?.text = model.label
            tvKpiDetailedChildContentValue?.text = model.value
        }
    }

    class LeftAlignedWithDetails(itemView: View) : GridViewHolder<LeftAlignedWithDetailsGridModel>(itemView) {
        override fun bind(model: LeftAlignedWithDetailsGridModel) {
            with(itemView) {
                tvKpiDetailedChildContentLabel?.text = model.label
                tvKpiDetailedChildContentValue?.text = model.value
                tvKpiDetailedChildContentDetails?.text = model.details
            }
        }
    }

    class LeftAlignedWithDetailsExpanded(itemView: View) : GridViewHolder<LeftAlignedWithDetailsExpandedGridModel>(itemView) {
        override fun bind(model: LeftAlignedWithDetailsExpandedGridModel) {
            with(itemView) {
                tvKpiDetailedChildContentDetailsExpanded?.text = model.label
                tvKpiDetailedExpandedChildContentValue?.text = model.value
                tvKpiDetailedChildContentDetailsFirstRow?.text = model.detailsFirstRow
                tvKpiDetailedChildContentDetailsFirstRowValue?.text = model.detailsFirstRowValue
                tvKpiDetailedChildContentDetailsSecondRow?.text = model.detailsSecondRow
                tvKpiDetailedChildContentDetailsSeondRowValue?.text = model.detailsSecondRowValue
            }
        }
    }

    class ProgressBar(itemView: View) : GridViewHolder<ProgressBarGridModel>(itemView) {
        override fun bind(model: ProgressBarGridModel) {
            super.bind(model)
            itemView.progress?.apply {
                enableAnimation = false
                currentProgress = model.currentProgress
                maxProgress = model.maxProgress
            }
        }
    }

}


abstract class BaseGridViewHolder<O : GridModel>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    abstract fun bind(model: O)

}
