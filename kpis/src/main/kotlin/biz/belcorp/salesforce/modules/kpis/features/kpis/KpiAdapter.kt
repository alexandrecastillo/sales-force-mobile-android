package biz.belcorp.salesforce.modules.kpis.features.kpis

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ListAdapter
import biz.belcorp.mobile.components.core.extensions.setSafeOnClickListener
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiCapitalizationBillingModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiCapitalizationSaleModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiCollectionModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiNewCyclesModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiSaleOrdersBillingModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiSaleOrdersMultiProfileBillingModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiSaleOrdersMultiProfileSaleModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiSaleOrdersSaleModel

internal class KpiAdapter(
    private val onClick: (KpiModel) -> Unit
) : ListAdapter<KpiModel, KpiViewHolder<*>>(KpiDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KpiViewHolder<*> {
        return when (viewType) {
            KpiViewType.KPI_TYPE_COLLECTION -> {
                val view = createView(parent, R.layout.item_kpis_collection)
                KpiViewHolder.KpiCollectionViewHolder(view)
            }

            KpiViewType.KPI_TYPE_NEW_CYCLES -> {
                val view = createView(parent, R.layout.item_kpis_new_cycles)
                KpiViewHolder.KpiNewCyclesViewHolder(view)
            }

            KpiViewType.KPI_TYPE_SALE_ORDERS_SALE -> {
                val view = createView(parent, R.layout.item_kpis_sale_orders_sale)
                KpiViewHolder.KpiSaleOrdersSaleViewHolder(view)
            }

            KpiViewType.KPI_TYPE_SALE_ORDERS_BILLING -> {
                val view = createView(parent, R.layout.item_kpis_sale_orders_billing)
                KpiViewHolder.KpiSaleOrdersBillingViewHolder(view)
            }

            KpiViewType.KPI_TYPE_SALE_ORDERS_MULTIPROFILE_SALE -> {
                val view = createView(parent, R.layout.item_kpis_sale_orders_multiprofile_sale)
                KpiViewHolder.KpiSaleOrdersMultiProfileSaleViewHolder(view)
            }

            KpiViewType.KPI_TYPE_SALE_ORDERS_MULTIPROFILE_BILLING -> {
                val view = createView(parent, R.layout.item_kpis_sale_orders_multiprofile_billing)
                KpiViewHolder.KpiSaleOrdersMultiProfileBillingViewHolder(view)
            }

            KpiViewType.KPI_TYPE_CAPITALIZATION_SALE,
            KpiViewType.KPI_TYPE_CAPITALIZATION_MULTIPROFILE_SALE -> {
                val view = createView(parent, R.layout.item_kpis_retention_capi_sale)
                KpiViewHolder.KpiCapitalizationSaleViewHolder(view)
            }

            KpiViewType.KPI_TYPE_CAPITALIZATION_BILLING,
            KpiViewType.KPI_TYPE_CAPITALIZATION_MULTIPROFILE_BILLING -> {
                val view = createView(parent, R.layout.item_kpis_retention_capi_billing)
                KpiViewHolder.KpiCapitalizationBillingViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun onBindViewHolder(holder: KpiViewHolder<*>, position: Int) {
        val item = getItem(position)
        when (holder) {
            is KpiViewHolder.KpiCollectionViewHolder -> holder.bind(item as KpiCollectionModel)

            is KpiViewHolder.KpiNewCyclesViewHolder -> holder.bind(item as KpiNewCyclesModel)

            is KpiViewHolder.KpiSaleOrdersSaleViewHolder -> holder.bind(item as KpiSaleOrdersSaleModel)
            is KpiViewHolder.KpiSaleOrdersBillingViewHolder -> holder.bind(item as KpiSaleOrdersBillingModel)

            is KpiViewHolder.KpiSaleOrdersMultiProfileSaleViewHolder -> holder.bind(item as KpiSaleOrdersMultiProfileSaleModel)
            is KpiViewHolder.KpiSaleOrdersMultiProfileBillingViewHolder -> holder.bind(item as KpiSaleOrdersMultiProfileBillingModel)

            is KpiViewHolder.KpiCapitalizationSaleViewHolder -> holder.bind(item as KpiCapitalizationSaleModel)
            is KpiViewHolder.KpiCapitalizationBillingViewHolder -> holder.bind(item as KpiCapitalizationBillingModel)
        }
        holder.itemView.setSafeOnClickListener {
            onClick(getItem(position))
        }
    }

    override fun getItemViewType(position: Int) = getItem(position).type

    private fun createView(parent: ViewGroup, @LayoutRes layout: Int) = parent.inflate(layout)
}
