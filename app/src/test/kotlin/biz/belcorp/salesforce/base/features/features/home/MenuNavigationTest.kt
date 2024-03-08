package biz.belcorp.salesforce.base.features.features.home

import androidx.navigation.NavController
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier
import biz.belcorp.salesforce.base.features.home.menu.navigation.MenuNavigation
import biz.belcorp.salesforce.base.utils.navigateSafe
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class MenuNavigationTest {

    private lateinit var menuNavigation: MenuNavigation
    private lateinit var navController: NavController

    @Before
    fun setup() {
        navController = mockk(relaxed = true)
        menuNavigation = MenuNavigation(navController)
    }

    @Test
    fun `go to option MORE`() {
        menuNavigation.handleMenuOptions(OptionsIdentifier.MORE)
        verify(exactly = 1) { navController.navigateSafe(R.id.globalToSideMenuFragment) }
    }

    @Test
    fun `go to option MORE_DEVELOPMENT_MATERIAL`() {
        menuNavigation.handleMenuOptions(OptionsIdentifier.MORE, OptionsIdentifier.MORE_DEVELOPMENT_MATERIAL)
        verify(exactly = 1) { navController.navigateSafe(R.id.menuToDevelopmentMaterialFragment) }
    }
}
