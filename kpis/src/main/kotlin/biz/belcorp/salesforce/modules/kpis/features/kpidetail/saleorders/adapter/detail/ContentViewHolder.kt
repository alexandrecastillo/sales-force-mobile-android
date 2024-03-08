package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.adapter.detail

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.components.R
import biz.belcorp.salesforce.components.utils.decoration.BoxOffsetDecoration
import biz.belcorp.salesforce.core.base.BaseViewHolder
import biz.belcorp.salesforce.core.utils.removeDecorations
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.ContentBaseModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.ContentModel
import kotlinx.android.synthetic.main.item_simple_card.view.rvResultItems
import kotlinx.android.synthetic.main.item_tip_card.view.*

sealed class ContentViewHolder(itemView: View) : BaseViewHolder<ContentModel>(itemView) {

    protected fun createAdapter(
        data: List<ContentBaseModel>,
        viewPool: RecyclerView.RecycledViewPool
    ) = with(itemView) {
        val itemAdapter = SaleOrdersItemAdapter(viewPool)
        rvResultItems?.apply {
            setHasFixedSize(true)
            removeDecorations()
            addItemDecoration(
                BoxOffsetDecoration(
                    context,
                    R.dimen.content_inset_normal,
                    R.dimen.content_inset_normal,
                    enableTop = true,
                    enableBottom = true
                )
            )
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setRecycledViewPool(viewPool)
            adapter = itemAdapter
        }
        itemAdapter.addData(data)
    }

    class SimpleViewHolder(itemView: View, private val viewPool: RecyclerView.RecycledViewPool) :
        ContentViewHolder(itemView) {
        override fun bind(model: ContentModel) {
            createAdapter(model.items, viewPool)
        }
    }

    class TipViewHolder(itemView: View, private val viewPool: RecyclerView.RecycledViewPool) :
        ContentViewHolder(itemView) {
        override fun bind(model: ContentModel) = with(itemView) {
            if (model.tip.isNotEmpty()) tvTipDescription?.apply {
                text = model.tip
            }
            clTipDescription.visible()
            createAdapter(model.items, viewPool)
        }
    }

}
