package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.adapter.detail

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.getFont
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.mobile.components.design.miniprogressbar.ProgressBar
import biz.belcorp.salesforce.components.utils.LineDividerDecoration
import biz.belcorp.salesforce.core.base.BaseViewHolder
import biz.belcorp.salesforce.core.constants.Constant.NEGATIVE_NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE_HUNDRED
import biz.belcorp.salesforce.core.utils.backgroundColor
import biz.belcorp.salesforce.core.utils.removeDecorations
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.Compact
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.Complex
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.ContentBaseModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.Multiple
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.Range
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.Separator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.Single
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.SingleLeft
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.grid.KpiGridAdapter
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.include_sale_orders_progress.view.progress
import kotlinx.android.synthetic.main.include_sale_orders_progress.view.tvTitleProgress
import kotlinx.android.synthetic.main.include_title_description.view.tvDescription
import kotlinx.android.synthetic.main.include_title_description.view.tvTitle
import kotlinx.android.synthetic.main.include_title_description_bottom.view.tvTitleItem
import kotlinx.android.synthetic.main.item_sale_orders_compact.view.ivIcon
import kotlinx.android.synthetic.main.item_sale_orders_compact.view.rangeGroup
import kotlinx.android.synthetic.main.item_sale_orders_compact.view.rvRanges
import kotlinx.android.synthetic.main.item_sale_orders_compact.view.tvDescriptionIndicator
import kotlinx.android.synthetic.main.item_sale_orders_compact.view.tvTitleIndicator
import kotlinx.android.synthetic.main.item_sale_orders_compact.view.tvTitleRanges
import kotlinx.android.synthetic.main.item_sale_orders_complex.view.indicatorGoalActivesActivity
import kotlinx.android.synthetic.main.item_sale_orders_complex.view.indicatorGoalOrders
import kotlinx.android.synthetic.main.item_sale_orders_complex.view.indicatorGoalPMNP
import kotlinx.android.synthetic.main.item_sale_orders_complex.view.indicatorGoalSales
import kotlinx.android.synthetic.main.item_sale_orders_multiple.view.descriptionLeft
import kotlinx.android.synthetic.main.item_sale_orders_multiple.view.progressRight
import kotlinx.android.synthetic.main.item_sale_orders_multiple.view.titleLeft
import kotlinx.android.synthetic.main.item_sale_orders_multiple.view.titleRight
import kotlinx.android.synthetic.main.item_sale_orders_separator.view.vDivider
import biz.belcorp.mobile.components.design.R as ComponentsR
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.salesforce.components.R as BaseComponentsR

sealed class ItemViewHolder(itemView: View) :
    BaseViewHolder<ContentBaseModel>(itemView) {

    class SingleViewHolder(itemView: View) : ItemViewHolder(itemView) {
        override fun bind(model: ContentBaseModel): Unit = with(itemView) {
            val singleModel = model as Single
            val secondaryColor = BaseComponentsR.color.textColorPrimaryVariant
            tvTitle?.apply {
                text = singleModel.title
                applyColorAndFontStyle(singleModel.titleColor, secondaryColor)
            }
            tvDescription?.apply {
                text = singleModel.description
                applyColorAndFontStyle(singleModel.descriptionColor, secondaryColor)
            }
        }
    }

    class SingleLeftViewHolder(itemView: View) : ItemViewHolder(itemView) {
        override fun bind(model: ContentBaseModel): Unit = with(itemView) {
            val singleModel = model as SingleLeft
            val secondaryColor = BaseComponentsR.color.textColorPrimaryVariant
            tvTitle?.apply {
                text = singleModel.title
                applyColorAndFontStyle(singleModel.titleColor, secondaryColor)
            }
            tvDescription?.apply {
                text = singleModel.description
                applyColorAndFontStyle(singleModel.descriptionColor, secondaryColor)
            }
        }
    }

    class SeparatorViewHolder(itemView: View) : ItemViewHolder(itemView) {
        override fun bind(model: ContentBaseModel) = with(itemView) {
            val separator = model as Separator
            vDivider?.visible()
            if (separator.color != NEGATIVE_NUMBER_ONE) {
                vDivider?.backgroundColor = model.color
            }
        }
    }

    class CompactViewHolder(itemView: View, private val viewPool: RecyclerView.RecycledViewPool?) :
        ItemViewHolder(itemView) {
        override fun bind(model: ContentBaseModel): Unit = with(itemView) {
            val compactModel = model as Compact
            tvTitleIndicator?.text = compactModel.title
            tvDescriptionIndicator?.text = compactModel.description
            tvTitleItem?.text = context.getString(R.string.title_goal)
            tvDescription?.text = compactModel.goalDescription
            progress?.apply {
                currentProgress = compactModel.complianceProgress
                text = compactModel.compliancePercentage
                checkBackground(compactModel.complianceProgress, this)
                progressDrawableHeight =
                    resources.getDimensionPixelSize(R.dimen.mini_progress_height)
            }
            tvTitleProgress?.text = context.getString(R.string.achievement_title)
            ivIcon?.setImageResource(compactModel.compactIcon)
            if(compactModel.range.items.isNotEmpty()) {
                bindRanges(compactModel.range, this, viewPool)
            }
        }
    }

    class ComplexViewHolder(itemView: View, private val viewPool: RecyclerView.RecycledViewPool?) :
        ItemViewHolder(itemView) {
        override fun bind(model: ContentBaseModel): Unit = with(itemView) {
            val complex = model as Complex
            indicatorGoalSales?.update(complex.progressSale.model)
            indicatorGoalSales?.apply {
                setProgressHeight(context.resources.getDimensionPixelSize(ComponentsR.dimen.content_inset_small))
            }
            indicatorGoalOrders?.update(complex.progressOrder.model)
            indicatorGoalOrders?.apply {
                setProgressHeight(context.resources.getDimensionPixelSize(ComponentsR.dimen.content_inset_small))
            }

            indicatorGoalPMNP?.update(complex.progressPMNP.model)
            indicatorGoalPMNP?.apply {
                setProgressHeight(context.resources.getDimensionPixelSize(ComponentsR.dimen.content_inset_small))
            }

            if(complex.isBilling){
                indicatorGoalActivesActivity.visibility = View.VISIBLE
                indicatorGoalActivesActivity?.update(complex.progressActivesActivity.model)
                indicatorGoalActivesActivity?.apply {
                    setProgressHeight(context.resources.getDimensionPixelSize(ComponentsR.dimen.content_inset_small))
                }
            }else{
                indicatorGoalActivesActivity.visibility = View.GONE
            }


            if(complex.range.items.isNotEmpty()) {
                bindRanges(complex.range, this, viewPool)
            }
        }
    }


    class MultipleViewHolder(itemView: View) :
        ItemViewHolder(itemView) {
        override fun bind(model: ContentBaseModel): Unit = with(itemView) {
            val multipleModel = model as Multiple
            titleLeft?.apply {
                text = multipleModel.titleLeft
                if (multipleModel.colorTitleLeft != NEGATIVE_NUMBER_ONE) {
                    setTextColor(multipleModel.colorTitleLeft)
                } else {
                    setTextColor(getColor(R.color.colorRangeLabel))
                }
            }
            descriptionLeft?.apply {
                text = multipleModel.description
                if (multipleModel.colorDescription != NEGATIVE_NUMBER_ONE) {
                    setTextColor(multipleModel.colorDescription)
                } else {
                    setTextColor(getColor(ComponentsR.color.black))
                }
            }
            titleRight?.apply {
                text = multipleModel.titleRight
                if (multipleModel.colorTitleRight != NEGATIVE_NUMBER_ONE) {
                    setTextColor(multipleModel.colorTitleRight)
                } else {
                    setTextColor(getColor(R.color.colorRangeLabel))
                }
            }
            progressRight?.apply {
                currentProgress = multipleModel.complianceProgress
                text = multipleModel.compliancePercentage
                checkBackground(multipleModel.complianceProgress, this)
                progressDrawableHeight =
                    resources.getDimensionPixelSize(ComponentsR.dimen.content_inset_tiny)
            }
        }
    }

    protected fun checkBackground(value: Int, progressBar: ProgressBar) {
        val color = if (value >= NUMBER_ONE_HUNDRED) BaseR.color.green_progress
        else BaseR.color.colorPrimaryDark

        progressBar.progressDrawableTint = getPressedState(progressBar.context, color)
    }

    private fun getPressedState(context: Context, color: Int): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_pressed)
        )
        val colors = intArrayOf(
            ContextCompat.getColor(context, color),
            ContextCompat.getColor(context, color),
            ContextCompat.getColor(context, color),
            ContextCompat.getColor(context, color)
        )
        return ColorStateList(states, colors)
    }

    protected fun bindRanges(
        model: Range,
        itemView: View,
        viewPool: RecyclerView.RecycledViewPool?
    ) = with(itemView) {
        val gridAdapter = KpiGridAdapter()
        val gridLayoutManager = GridLayoutManager(context, model.items.size)

        rangeGroup?.visible(model.hasRanges)
        tvTitleRanges?.text = model.title
        rvRanges?.apply {
            setHasFixedSize(true)
            removeDecorations()
            addItemDecoration(
                LineDividerDecoration(
                    context,
                    BaseR.drawable.divider_newcycle,
                    ComponentsR.dimen.content_inset_tiny
                )
            )
            layoutManager = gridLayoutManager
            viewPool?.let { setRecycledViewPool(it) }
            adapter = gridAdapter
        }
        gridAdapter.update(model.items)
    }

    protected fun MaterialTextView.applyColorAndFontStyle(color: Int, colorSecondary: Int) {
        val fontFamily = getFont(ComponentsR.font.lato_regular)
        val (currentColor, fontStyle) = when (color) {
            NEGATIVE_NUMBER_ONE -> Pair(getColor(colorSecondary), Typeface.NORMAL)
            else -> Pair(color, Typeface.BOLD)
        }
        setTextColor(currentColor)
        setTypeface(fontFamily, fontStyle)
    }
}
