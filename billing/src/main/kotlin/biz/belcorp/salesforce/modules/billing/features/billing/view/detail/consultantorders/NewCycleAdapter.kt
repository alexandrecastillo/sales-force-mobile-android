package biz.belcorp.salesforce.modules.billing.features.billing.view.detail.consultantorders

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.modules.billing.R
import biz.belcorp.salesforce.modules.billing.features.billing.model.detail.BillingNewCycleModel
import kotlinx.android.synthetic.main.item_billing_new_cycle.view.*
import biz.belcorp.salesforce.components.R as ComponentsR

internal class NewCycleAdapter : RecyclerView.Adapter<NewCycleAdapter.NewCycleHolder>() {

    private val models = arrayListOf<BillingNewCycleModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCycleHolder {
        val view = parent.inflate(R.layout.item_billing_new_cycle)
        return NewCycleHolder(view)
    }

    override fun getItemCount() = models.size

    override fun onBindViewHolder(holder: NewCycleHolder, position: Int) {
        holder.bind(models[position])
    }

    fun submitList(models: List<BillingNewCycleModel>) {
        this.models.clear()
        this.models.addAll(models)
        notifyDataSetChanged()
    }

    inner class NewCycleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: BillingNewCycleModel) = with(itemView) {
            title?.text = model.title
            summary?.text = model.description
            progress?.apply {
                enableAnimation = false
                text = model.progressLabel
                currentProgress = model.progress
                maxProgress = model.maxProgress
            }
            progress?.progressDrawableHeight =
                resources.getDimensionPixelSize(ComponentsR.dimen.mini_progress_height)
        }
    }
}
