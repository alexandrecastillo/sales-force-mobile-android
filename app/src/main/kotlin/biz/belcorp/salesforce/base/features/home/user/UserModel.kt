package biz.belcorp.salesforce.base.features.home.user

import androidx.annotation.ColorRes
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.core.constants.Constant

data class UserModel(
    val name: String = Constant.EMPTY_STRING,
    val description: String = Constant.EMPTY_STRING,
    val level: String = Constant.EMPTY_STRING,
    @ColorRes val colorSegment: Int = R.color.transparent
) {
    val hasSegment get() = level.isNotEmpty()
}
