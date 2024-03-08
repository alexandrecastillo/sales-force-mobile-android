package biz.belcorp.salesforce.base.features.home.menu.bottommenu

import biz.belcorp.salesforce.base.features.home.menu.model.MenuOptionModel


sealed class BottomMenuViewState {

    class Populate(val options: List<MenuOptionModel>) : BottomMenuViewState()

}
