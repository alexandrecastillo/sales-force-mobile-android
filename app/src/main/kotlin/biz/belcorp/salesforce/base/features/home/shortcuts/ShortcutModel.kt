package biz.belcorp.salesforce.base.features.home.shortcuts

import androidx.annotation.DrawableRes

data class ShortcutModel(
    val code: Int = 0,
    @DrawableRes val iconRes: Int = -1,
    val text: String?
)
