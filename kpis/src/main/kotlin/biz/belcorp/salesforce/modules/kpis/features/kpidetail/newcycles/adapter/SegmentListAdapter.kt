package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.ProgressModel
import kotlinx.android.synthetic.main.item_segment.view.*

internal class SegmentListAdapter(
    private val hasWidthFitGrid: Boolean
) : RecyclerView.Adapter<SegmentViewHolder>() {

    private val items = mutableListOf<ProgressModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SegmentViewHolder {
        val view = parent.inflate(R.layout.item_segment)
        return SegmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: SegmentViewHolder, position: Int) {
        holder.bind(items[position], hasWidthFitGrid)
    }

    override fun getItemCount(): Int = items.size

    fun swap(data: List<ProgressModel>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }
}

internal class SegmentViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    fun bind(model: ProgressModel, hasWidthFitGrid: Boolean) = with(itemView) {
        if (hasWidthFitGrid) clSegmentContainer?.layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
        tvTitle?.text = model.title
        tvValue?.text = model.summary
    }
}
