package biz.belcorp.salesforce.base.features.home.menu.mappers

import androidx.annotation.DrawableRes
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier
import biz.belcorp.salesforce.base.features.home.menu.model.MenuOptionModel
import biz.belcorp.salesforce.core.domain.entities.options.Option

class MenuOptionMapper {

    fun map(options: List<Option>): List<MenuOptionModel> {
        return options.map { map(it) }
    }

    private fun map(option: Option): MenuOptionModel {
        return MenuOptionModel(
            code = option.code.toInt(),
            text = option.description,
            iconResId = getIconResId(option.code.toInt())
        )
    }

    fun map(options: List<Option>, parentCode: Int): List<MenuOptionModel> {
        return options.map { map(it, parentCode) }
    }

    private fun map(option: Option, parentCode: Int): MenuOptionModel {
        return MenuOptionModel(
            code = option.code.toInt(),
            text = option.description,
            iconResId = getIconResId(parentCode, option.code.toInt())
        )
    }

    @DrawableRes
    private fun getIconResId(code: Int, childCode: Int? = null): Int {
        return when {
            code == OptionsIdentifier.MORE && childCode == null -> R.drawable.icon_menu
            code == OptionsIdentifier.MORE && childCode == OptionsIdentifier.MORE_CREDIT_INQUIRY -> R.drawable.ic_menu_credit
            code == OptionsIdentifier.MORE && childCode == OptionsIdentifier.MORE_VIRTUAL_METHODOLOGY -> R.drawable.ic_menu_virtual
            code == OptionsIdentifier.MORE && childCode == OptionsIdentifier.MORE_DEVELOPMENT_MATERIAL -> R.drawable.ic_menu_materials
            code == OptionsIdentifier.MORE && childCode == OptionsIdentifier.MORE_LOGOUT -> R.drawable.ic_menu_logout
            code == OptionsIdentifier.MORE && childCode == OptionsIdentifier.MORE_CHAT -> R.drawable.ic_menu_chat
            code == OptionsIdentifier.MORE && childCode == OptionsIdentifier.MORE_TERMS_CONDITIONS -> R.drawable.ic_menu_terms
            code == OptionsIdentifier.MORE && childCode == OptionsIdentifier.MORE_LINK_UNETE -> R.drawable.ic_menu_link

            code == OptionsIdentifier.HOME -> R.drawable.icon_home
            code == OptionsIdentifier.SEARCH_CONSULTANTS -> R.drawable.icon_search_consultant
            code == OptionsIdentifier.CREDIT_INQUIRY -> R.drawable.ic_menu_credit
            code == OptionsIdentifier.ORDERS -> R.drawable.icon_orders

            else -> R.drawable.icon_menu
        }
    }
}
