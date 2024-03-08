package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getDrawable
import biz.belcorp.salesforce.base.features.utils.decorations.SpaceGridInsideDecoration
import biz.belcorp.salesforce.components.utils.DividerItemDecoration
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.SegmentFeedModel
import kotlinx.android.synthetic.main.item_segment_feed.view.*
import biz.belcorp.salesforce.components.R as ComponentR

internal class SegmentFeedListAdapter(
    private val isGridEnabled: Boolean = true,
    private val hasWidthFitGrid: Boolean = false
) : RecyclerView.Adapter<SegmentFeedListAdapter.SegmentFeedViewHolder>() {

    private val items = mutableListOf<SegmentFeedModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SegmentFeedViewHolder {
        val view = parent.inflate(R.layout.item_segment_feed)
        return SegmentFeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: SegmentFeedViewHolder, position: Int) {
        holder.bind(items[position], isGridEnabled, hasWidthFitGrid)
    }

    override fun getItemCount(): Int = items.size

    fun swap(data: List<SegmentFeedModel>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    class SegmentFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: SegmentFeedModel, isGridEnabled: Boolean, hasWidthFitGrid: Boolean) {
            model.apply {
                itemView.tvSegmentTitle?.text = model.title
                initSegmentListRecycler(this, isGridEnabled, hasWidthFitGrid)
            }
        }

        private fun initSegmentListRecycler(
            model: SegmentFeedModel,
            isGridEnabled: Boolean,
            hasWidthFitGrid: Boolean
        ) {
            if (isGridEnabled) {
                val segmentListAdapter = SegmentListAdapter(hasWidthFitGrid)
                itemView.rvSegments?.apply {
                    visible()
                    if (hasWidthFitGrid) layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    layoutManager = GridLayoutManager(context, model.spanItems)
                    isNestedScrollingEnabled = false
                    setHasFixedSize(true)
                    addItemDecoration(getDividerDecoration())
                    addItemDecoration(getSpaceGridDecoration(model.spanItems))
                    adapter = segmentListAdapter
                }
                segmentListAdapter.swap(model.list)
            }
        }

        private fun getSpaceGridDecoration(spanItems: Int): RecyclerView.ItemDecoration {
            return SpaceGridInsideDecoration(
                itemView.context,
                spanItems,
                R.dimen.kpi_no_margin,
                R.dimen.kpi_content_inset_normal
            )
        }

        private fun getDividerDecoration(): RecyclerView.ItemDecoration {
            val dividerDecoration =
                DividerItemDecoration(itemView.context, DividerItemDecoration.HORIZONTAL)
            dividerDecoration.setDrawable(itemView.getDrawable(ComponentR.drawable.divider_newcycle))
            return dividerDecoration
        }
    }

}
