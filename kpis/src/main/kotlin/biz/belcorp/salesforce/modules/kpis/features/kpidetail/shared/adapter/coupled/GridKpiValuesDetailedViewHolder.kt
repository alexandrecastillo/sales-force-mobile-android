package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.coupled

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.base.features.utils.decorations.SpaceGridInsideDecoration
import biz.belcorp.salesforce.components.utils.LineDividerDecoration
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.utils.removeDecorations
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.grid.KpiGridAdapter
import biz.belcorp.salesforce.modules.kpis.utils.resetLayoutParams
import biz.belcorp.salesforce.modules.kpis.utils.setAttrs
import kotlinx.android.synthetic.main.item_grid_kpi_with_values.view.*
import kotlinx.android.synthetic.main.item_simple_grid.view.*
import kotlinx.android.synthetic.main.view_header_kpi.view.*
import biz.belcorp.salesforce.components.R as ComponentR


sealed class KpiViewHolder<T : CoupledModel>(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    open fun bind(model: T) = Unit

    protected fun RecyclerView.setup(
        viewPool: RecyclerView.RecycledViewPool,
        spanItems: Int
    ) {
        setHasFixedSize(true)
        removeDecorations()
        layoutManager = GridLayoutManager(
            itemView.context,
            spanItems,
            GridLayoutManager.VERTICAL,
            false
        )
        addItemDecoration(
            SpaceGridInsideDecoration(
                context,
                spanItems,
                ComponentR.dimen.zero,
                ComponentR.dimen.content_inset_normal
            )
        )
        setRecycledViewPool(viewPool)
    }

    class SingleItem(itemView: View) :
        KpiViewHolder<CoupledModel.SingleItem>(itemView) {
        override fun bind(model: CoupledModel.SingleItem) {
            with(itemView) {
                model.apply {
                    tvSingleItemHeaderLabel?.apply {
                        setAttrs(label)
                        resetLayoutParams(label.textAppearanceInt)
                    }
                    tvHeaderAmount?.apply {
                        setAttrs(value)
                        resetLayoutParams(value.textAppearanceInt)
                    }
                }
            }
        }
    }

    class SingleItemWithGridItems(
        itemView: View,
        private val viewPool: RecyclerView.RecycledViewPool
    ) : KpiViewHolder<CoupledModel.GridWithHeaderItemModel>(itemView) {

        private val dividerDecoration by lazy {
            LineDividerDecoration(
                itemView.context,
                ComponentR.drawable.divider_newcycle,
                ComponentR.dimen.content_inset_small
            )
        }

        override fun bind(model: CoupledModel.GridWithHeaderItemModel) {
            with(itemView) {
                background = null
                model.apply {
                    tvSingleItemHeaderLabel?.setAttrs(header.label)
                    tvHeaderAmount?.setAttrs(header.value)
                    rvGridKpiValues?.apply {
                        visible(kpiValues.isNotEmpty())
                        val kpiGridAdapter = KpiGridAdapter()
                        kpiGridAdapter.update(kpiValues)
                        setup(viewPool, spanItems)
                        if (hasDividerDecoration) addItemDecoration(dividerDecoration)
                        adapter = kpiGridAdapter
                    }
                }
            }
        }
    }

    class GridItems(itemView: View, private val viewPool: RecyclerView.RecycledViewPool) :
        KpiViewHolder<CoupledModel.GridsItemModel>(itemView) {

        override fun bind(model: CoupledModel.GridsItemModel) {
            with(itemView) {
                background = null
                model.apply {
                    rvGridSimpleKpi?.apply {
                        val kpiGridAdapter = KpiGridAdapter()
                        kpiGridAdapter.update(kpiValues)
                        setup(viewPool, NUMBER_TWO)
                        adapter = kpiGridAdapter
                    }
                }
            }
        }
    }

}

