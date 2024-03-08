package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.backgroundColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model.LevelRequestModel
import kotlinx.android.synthetic.main.item_level_request.view.consultant
import kotlinx.android.synthetic.main.item_level_request.view.containerLevel
import kotlinx.android.synthetic.main.item_level_request.view.orders
import kotlinx.android.synthetic.main.item_level_request.view.sales

class LevelRequestAdapter :
    RecyclerView.Adapter<LevelRequestAdapter.LevelRequestViewHolder>() {

    private val data: MutableList<LevelRequestModel> = emptyList<LevelRequestModel>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelRequestViewHolder {
        val view = parent.inflate(R.layout.item_level_request)
        return LevelRequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: LevelRequestViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount() = data.size

    fun submitList(data: List<LevelRequestModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class LevelRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: LevelRequestModel, position: Int): Unit = with(itemView) {

            consultant.text = data.path
            orders.text = data.currentLevelOrderRequirement
            sales.text = data.saleAccumulative

            if ((position / 2) == 0 && position != 0) {
                containerLevel.backgroundColor = getColor(R.color.background_request_level)
            } else {
                containerLevel.backgroundColor = getColor(R.color.rdd_white)
            }

        }
    }
}
