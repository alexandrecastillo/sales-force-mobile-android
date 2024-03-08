package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.ProgressModel
import kotlinx.android.synthetic.main.item_segment_progress.view.*

internal class SegmentProgressListAdapter(private val progressList: MutableList<ProgressModel> = mutableListOf()) :
    RecyclerView.Adapter<SegmentProgressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SegmentProgressViewHolder {
        val view = parent.inflate(R.layout.item_segment_progress)
        return SegmentProgressViewHolder(view)
    }

    override fun onBindViewHolder(holder: SegmentProgressViewHolder, position: Int) {
        holder.bind(progressList[position])
    }

    override fun getItemCount(): Int {
        return progressList.size
    }

    fun swap(newList: List<ProgressModel>) {
        progressList.clear()
        progressList.addAll(newList)
        notifyDataSetChanged()
    }
}

internal class SegmentProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(model: ProgressModel) = with(itemView) {
        title?.text = model.title
        progress?.apply {
            text = model.summary
            enableAnimation = false
            currentProgress = model.progress ?: 0
            maxProgress = model.maxProgress ?: 0
        }
    }
}

