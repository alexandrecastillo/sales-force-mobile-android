package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.components.utils.decoration.BackgroundPairTintDecorator
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.helper.DigitalViewTypeIdentifier
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleItemModel
import kotlinx.android.synthetic.main.item_digital_single_item.view.*
import biz.belcorp.salesforce.base.R as BaseR

class DigitalItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataSet = mutableListOf<DigitalSaleItemModel>()
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DigitalViewTypeIdentifier.SINGLE_HORIZONTAL -> {
                val view = parent.inflate(R.layout.item_digital_single_item)
                DigitalItemViewHolder(
                    view,
                    LayoutOrientation.HORIZONTAL,
                    viewPool
                )
            }
            DigitalViewTypeIdentifier.SINGLE_VERTICAL -> {
                val view = parent.inflate(R.layout.item_digital_single_item)
                DigitalItemViewHolder(
                    view,
                    LayoutOrientation.VERTICAL,
                    viewPool
                )
            }
            else -> throw Exception("ViewType incorrecto")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataSet[position]
        (holder as DigitalItemViewHolder).bind(item)
    }

    override fun getItemId(position: Int): Long = dataSet[position].id.toLong()

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].type
    }

    override fun getItemCount() = dataSet.size

    fun actualizar(data: List<DigitalSaleItemModel>) {
        this.dataSet.clear()
        this.dataSet.addAll(data)
        notifyDataSetChanged()
    }
}

abstract class BaseDigitalItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: DigitalSaleItemModel)
}

class DigitalItemViewHolder(
    view: View, @LayoutOrientation val orientation: Int,
    private val viewPool: RecyclerView.RecycledViewPool
) : BaseDigitalItemViewHolder(view) {

    override fun bind(item: DigitalSaleItemModel) = with(itemView) {

        text_titulo?.text = item.description
        text_titulo?.visibility =
            if (!item.description.isNullOrEmpty()) View.VISIBLE else View.GONE

        val childLayoutManager = LinearLayoutManager(context)
        childLayoutManager.orientation =
            if (orientation == LayoutOrientation.HORIZONTAL) LinearLayoutManager.HORIZONTAL else LinearLayoutManager.VERTICAL
        childLayoutManager.initialPrefetchItemCount = 3

        val childAdapter =
            DigitalChildrenAdapter(
                orientation
            )

        recycler?.apply {
            if (orientation == LayoutOrientation.VERTICAL) {
                isNestedScrollingEnabled = false
                addItemDecoration(
                    BackgroundPairTintDecorator(
                        R.color.background_color,
                        BaseR.color.white
                    )
                )
            }
            setHasFixedSize(true)
            layoutManager = childLayoutManager
            adapter = childAdapter
            setRecycledViewPool(viewPool)
        }
        childAdapter.actualizar(item.campaignList)
    }
}
