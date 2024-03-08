package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.components.utils.decoration.BackgroundPairTintDecorator
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.helper.DigitalViewTypeIdentifier
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import biz.belcorp.salesforce.base.R as BaseR
import kotlinx.android.synthetic.main.item_digital_single.view.image_icon as text_image_icon_single
import kotlinx.android.synthetic.main.item_digital_single.view.recycler as recycler_single
import kotlinx.android.synthetic.main.item_digital_single.view.text_header as text_header_single
import kotlinx.android.synthetic.main.item_digital_single.view.text_titulo as text_titulo_single

class DigitalParentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataSet = mutableListOf<DigitalSaleModel>()
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DigitalViewTypeIdentifier.SINGLE_HORIZONTAL -> {
                val view = parent.inflate(R.layout.item_digital_single)
                DigitalSingleViewHolder(
                    view,
                    LayoutOrientation.HORIZONTAL,
                    viewPool
                )
            }

            DigitalViewTypeIdentifier.SINGLE_VERTICAL -> {
                val view = parent.inflate(R.layout.item_digital_single)
                DigitalSingleViewHolder(
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
        (holder as DigitalSingleViewHolder).bind(item)
    }

    override fun getItemId(position: Int): Long = dataSet[position].digitalId.toLong()

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].type
    }

    override fun getItemCount() = dataSet.size

    fun actualizar(data: List<DigitalSaleModel>) {
        this.dataSet.clear()
        this.dataSet.addAll(data)
        notifyDataSetChanged()
    }
}

abstract class BaseDigitalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: DigitalSaleModel)
}

class DigitalSingleViewHolder(
    view: View, @LayoutOrientation val orientation: Int,
    private val viewPool: RecyclerView.RecycledViewPool
) : BaseDigitalViewHolder(view) {

    override fun bind(item: DigitalSaleModel) = with(itemView) {
        text_header_single?.text = item.title
        if (item.subtitle.isNullOrBlank()) {
            text_titulo_single.visibility = View.GONE
        } else {
            text_titulo_single.visibility = View.VISIBLE
            text_titulo_single?.text = item.subtitle
        }
        val requestOptions = RequestOptions()
            .error(R.drawable.ic_upload_image)
        Glide.with(context)
            .load(item.image)
            .apply(requestOptions)
            .into(text_image_icon_single)
        val childLayoutManager = LinearLayoutManager(context)
        childLayoutManager.orientation = LinearLayoutManager.VERTICAL
        childLayoutManager.initialPrefetchItemCount = 3

        val childAdapter =
            DigitalItemAdapter()

        recycler_single?.apply {
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
        childAdapter.actualizar(item.itemList)
    }
}
