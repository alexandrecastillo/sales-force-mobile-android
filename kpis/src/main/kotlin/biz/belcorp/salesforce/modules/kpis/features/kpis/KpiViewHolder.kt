package biz.belcorp.salesforce.modules.kpis.features.kpis

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.*
import kotlinx.android.synthetic.main.include_kpi_collection.view.*
import kotlinx.android.synthetic.main.include_kpi_collection.view.description
import kotlinx.android.synthetic.main.include_kpi_collection.view.indicatorHeader
import kotlinx.android.synthetic.main.include_kpi_collection.view.title
import kotlinx.android.synthetic.main.include_kpi_new_cycles.view.*
import kotlinx.android.synthetic.main.include_kpi_retention_capi_billing.view.*
import kotlinx.android.synthetic.main.include_kpi_sale_orders_billing.view.descriptionFirst
import kotlinx.android.synthetic.main.include_kpi_sale_orders_billing.view.indicatorGoalOrders
import kotlinx.android.synthetic.main.include_kpi_sale_orders_billing.view.subtitleFirst
import kotlinx.android.synthetic.main.include_kpi_sale_orders_multiprofile_billing.view.*
import kotlinx.android.synthetic.main.include_kpi_sale_orders_sale.view.*

internal sealed class KpiViewHolder<T : KpiModel>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    open fun bind(model: T) = with(itemView) {
        indicatorHeader?.apply {
            setText(model.header)
            setIcon(model.iconRes)
            setIconColor(getColor(model.iconColor))
            setBackgroundIconColor(getColor(model.backgroundColor))
        }
        return@with
    }

    class KpiCollectionViewHolder(itemView: View) : KpiViewHolder<KpiCollectionModel>(itemView) {
        override fun bind(model: KpiCollectionModel) = with(itemView) {
            super.bind(model)
            title?.text = model.title
            description?.text = model.description
            descriptionChargedOrders?.text = model.descriptionSecond
        }
    }

    class KpiNewCyclesViewHolder(itemView: View) : KpiViewHolder<KpiNewCyclesModel>(itemView) {
        override fun bind(model: KpiNewCyclesModel) = with(itemView) {
            super.bind(model)
            title?.text = model.title
            description?.text = model.description
            descriptionHighValue?.text = model.descriptionSecond
        }
    }

    class KpiSaleOrdersSaleViewHolder(itemView: View) :
        KpiViewHolder<KpiSaleOrdersSaleModel>(itemView) {
        override fun bind(model: KpiSaleOrdersSaleModel) = with(itemView) {
            super.bind(model)
            title?.text = model.title
            descriptionOrderSale?.text = model.description
        }
    }

    class KpiSaleOrdersBillingViewHolder(itemView: View) :
        KpiViewHolder<KpiSaleOrdersBillingModel>(itemView) {
        override fun bind(model: KpiSaleOrdersBillingModel) = with(itemView) {
            super.bind(model)
            title?.text = model.title
            indicatorGoalOrders?.update(model.goalBar)
            subtitleFirst?.text = model.subtitleFirst
            descriptionFirst?.text = model.descriptionFirst
        }
    }

    class KpiSaleOrdersMultiProfileSaleViewHolder(itemView: View) :
        KpiViewHolder<KpiSaleOrdersMultiProfileSaleModel>(itemView) {
        override fun bind(model: KpiSaleOrdersMultiProfileSaleModel) = with(itemView) {
            super.bind(model)
            title?.text = model.title
            descriptionFirst?.text = model.descriptionFirst
            descriptionSecond?.text = model.descriptionSecond
        }
    }

    class KpiSaleOrdersMultiProfileBillingViewHolder(itemView: View) :
        KpiViewHolder<KpiSaleOrdersMultiProfileBillingModel>(itemView) {
        override fun bind(model: KpiSaleOrdersMultiProfileBillingModel) = with(itemView) {
            super.bind(model)
            title?.text = model.title
            indicatorGoalSale?.update(model.goalSale)
            indicatorGoalOrders?.update(model.goalOrders)
            return@with
        }
    }

    class KpiCapitalizationSaleViewHolder(itemView: View) :
        KpiViewHolder<KpiCapitalizationSaleModel>(itemView) {
        override fun bind(model: KpiCapitalizationSaleModel) = with(itemView) {
            super.bind(model)
            title?.text = model.title
            subtitleFirst?.apply {
                visible(model.subtitleFirst.isNotEmpty())
                text = model.subtitleFirst
            }
            descriptionFirst?.text = model.descriptionFirst
            subtitleSecond?.text = model.subtitleSecond
            descriptionSecond?.text = model.descriptionSecond
            return@with
        }
    }

    class KpiCapitalizationBillingViewHolder(itemView: View) :
        KpiViewHolder<KpiCapitalizationBillingModel>(itemView) {
        override fun bind(model: KpiCapitalizationBillingModel) = with(itemView) {
            super.bind(model)
            subtitleFirst?.text = model.subtitleFirst
            descriptionFirst?.text = model.descriptionFirst
            subtitleSecond?.text = model.subtitleSecond
            descriptionSecond?.text = model.descriptionSecond
            return@with
        }
    }
}
