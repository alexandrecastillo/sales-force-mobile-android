package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model

import androidx.annotation.ColorInt
import biz.belcorp.mobile.components.design.indicatorgoalbar.IndicatorGoalBar
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NEGATIVE_NUMBER_ONE
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.DefaultGridModel

open class ItemModel(val title: String, val description: String)

open class Single(
    title: String,
    description: String,
    @ColorInt val titleColor: Int = NEGATIVE_NUMBER_ONE,
    @ColorInt val descriptionColor: Int = NEGATIVE_NUMBER_ONE
) : ItemModel(title, description), ContentBaseModel {
    override val type: Int
        get() = ItemType.SINGLE
}

class SingleLeft(title: String, description: String, titleColor: Int, descriptionColor: Int) :
    Single(title, description, titleColor, descriptionColor) {
    override val type: Int
        get() = ItemType.SINGLE_LEFT
}

class Compact(
    title: String,
    description: String,
    val goalDescription: String,
    val complianceProgress: Int,
    val compactIcon: Int,
    val range: Range
) : ItemModel(title, description), ContentBaseModel {
    override val type get() = ItemType.COMPACT

    val compliancePercentage get() = "$complianceProgress%"
}

class Progress(val model: IndicatorGoalBar.Model) : ContentBaseModel {
    override val type get() = ItemType.PROGRESS
}

class Complex(
    val progressSale: Progress,
    val progressOrder: Progress,
    val progressPMNP: Progress,
    val progressActivesActivity: Progress,
    val range: Range,
    val isBilling: Boolean
) : ContentBaseModel {
    override val type get() = ItemType.COMPLEX
}

class Range(
    val title: String = EMPTY_STRING,
    val items: List<DefaultGridModel> = emptyList()
) : ContentBaseModel {
    override val type get() = ItemType.RANGES
    val hasRanges get() = items.isNotEmpty()
}

class Multiple(
    val titleLeft: String = EMPTY_STRING,
    @ColorInt val colorTitleLeft: Int = NEGATIVE_NUMBER_ONE,
    val description: String = EMPTY_STRING,
    @ColorInt val colorDescription: Int = NEGATIVE_NUMBER_ONE,
    val titleRight: String = EMPTY_STRING,
    @ColorInt val colorTitleRight: Int = NEGATIVE_NUMBER_ONE,
    val complianceProgress: Int
) : ContentBaseModel {
    override val type get() = ItemType.MULTIPLE

    val compliancePercentage get() = "$complianceProgress%"
}

class Separator(@ColorInt val color: Int = NEGATIVE_NUMBER_ONE) : ContentBaseModel {
    override val type: Int
        get() = ItemType.SEPARATOR
}
