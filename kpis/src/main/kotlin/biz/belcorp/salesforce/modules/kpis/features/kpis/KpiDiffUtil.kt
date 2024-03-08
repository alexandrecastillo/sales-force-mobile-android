package biz.belcorp.salesforce.modules.kpis.features.kpis

import androidx.recyclerview.widget.DiffUtil
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiModel

internal object KpiDiffUtil: DiffUtil.ItemCallback<KpiModel>() {
    override fun areItemsTheSame(oldItem: KpiModel, newItem: KpiModel) =
        oldItem.code == newItem.code

    override fun areContentsTheSame(oldItem: KpiModel, newItem: KpiModel) =
        oldItem == newItem
}
