package biz.belcorp.salesforce.modules.brightpath.features.drilldown

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.modules.brightpath.R
import biz.belcorp.salesforce.modules.brightpath.features.drilldown.model.ConsultantHeaderDetailedKpiModel
import biz.belcorp.salesforce.modules.brightpath.features.drilldown.utils.GridBuilder
import kotlinx.android.synthetic.main.item_view_drilldown_grid.view.*

class GridDrillDownAdapter : RecyclerView.Adapter<GridDrillDownAdapter.GridViewHolder>() {

    private val items = mutableListOf<ConsultantHeaderDetailedKpiModel>()

    private var uaSegmentSelected: String = EMPTY_STRING

    var onClickListener: ((ConsultantHeaderDetailedKpiModel) -> Unit)? = null

    var onClickLevelListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): GridViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view_drilldown_grid, parent, false)
        return GridViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: GridViewHolder, pos: Int) {
        holder.bind(items[pos])
    }

    fun addItems(items: List<ConsultantHeaderDetailedKpiModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setUaSegmentId(prevUaSelected: String) {
        this.uaSegmentSelected = prevUaSelected
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(consultantListSetUpModel: ConsultantHeaderDetailedKpiModel) {

            itemView.setOnClickListener {
                if (consultantListSetUpModel.id
                    == GridBuilder(itemView.context).changeLevelId || consultantListSetUpModel.id
                    == GridBuilder(itemView.context).endPeriodLevelId
                ) {
                    onClickLevelListener?.invoke(consultantListSetUpModel.id)
                } else {
                    onClickListener?.invoke(consultantListSetUpModel)
                }
            }

            consultantListSetUpModel.apply {
                itemView.gridItemViewTitle?.text = title
                itemView.gridItemViewNoConsultoras?.text =
                    itemView.context.getString(R.string.non_consultant)
            }
        }

    }
}
