package biz.belcorp.salesforce.base.features.home.menu.model

import androidx.annotation.DrawableRes


data class MenuOptionModel(
    var code: Int = 0,
    var text: String? = null,
    @DrawableRes var iconResId: Int
)
