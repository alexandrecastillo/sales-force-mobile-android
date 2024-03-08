package biz.belcorp.salesforce.base.features.home.menu.sidemenu

import biz.belcorp.salesforce.base.features.home.menu.model.MenuOptionModel
import biz.belcorp.salesforce.core.domain.entities.terms.LinkSE


sealed class SideMenuViewState {
    class Populate(val options: List<MenuOptionModel>) : SideMenuViewState()
    object LogoutSuccess : SideMenuViewState()
    class TermValidation(val validation: LinkSE) : SideMenuViewState()
    class LinkUneteSuccess(val linkUnete: String) : SideMenuViewState()
    class Failed(val message: String) : SideMenuViewState()
}
