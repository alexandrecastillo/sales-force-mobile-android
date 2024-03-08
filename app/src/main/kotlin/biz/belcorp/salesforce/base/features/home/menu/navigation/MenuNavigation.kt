package biz.belcorp.salesforce.base.features.home.menu.navigation

import androidx.navigation.NavController
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier
import biz.belcorp.salesforce.base.utils.navigateSafe
import biz.belcorp.salesforce.core.utils.consume

class MenuNavigation(private val navController: NavController) {

    fun handleMenuOptions(code: Int, childCode: Int? = null): Boolean {
        return when {
            code == OptionsIdentifier.MORE && childCode == null -> consume { openSideMenu() }
            code == OptionsIdentifier.MORE && childCode == OptionsIdentifier.MORE_CREDIT_INQUIRY -> consume { goToCreditInquiry() }
            code == OptionsIdentifier.MORE && childCode == OptionsIdentifier.MORE_VIRTUAL_METHODOLOGY -> consume { openVirtualMethodology() }
            code == OptionsIdentifier.MORE && childCode == OptionsIdentifier.MORE_DEVELOPMENT_MATERIAL -> consume { openDevelopmentMaterial() }
            code == OptionsIdentifier.MORE && childCode == OptionsIdentifier.MORE_TERMS_CONDITIONS -> consume { openPoliticsTermsConditions() }
            else -> false
        }
    }

    private fun openSideMenu() {
        navController.navigateSafe(R.id.globalToSideMenuFragment)
    }

    private fun openVirtualMethodology() {
        navController.navigateSafe(R.id.menuToVirtualMethodologyFragment)
    }

    private fun openDevelopmentMaterial() {
        navController.navigateSafe(R.id.menuToDevelopmentMaterialFragment)
    }

    private fun openPoliticsTermsConditions() {
        navController.navigateSafe(R.id.menuToTermsConditionsFragment)
    }

    private fun goToCreditInquiry() {
        navController.navigateSafe(R.id.globalToCreditInquiry)
    }
}
