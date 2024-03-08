package biz.belcorp.salesforce.modules.digital.features.digital.model

import androidx.annotation.DrawableRes

data class DigitalHeaderItemModel(
    val title: String,
    @DrawableRes val iconResId: Int,
    val percentageNumber: Float,
    val percentageValue: String,
    val percentageDescription: String,
    val progressValue: String,
    val progressDescription: String
)
