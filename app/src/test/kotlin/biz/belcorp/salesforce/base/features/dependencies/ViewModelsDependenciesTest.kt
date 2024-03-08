package biz.belcorp.salesforce.base.features.dependencies

import biz.belcorp.salesforce.base.base.BaseDependenciesTest
import biz.belcorp.salesforce.base.features.home.HomeViewModel
import biz.belcorp.salesforce.base.features.home.cookies.TermsCookiesViewModel
import biz.belcorp.salesforce.base.features.home.shortcuts.ShortcutsViewModel
import biz.belcorp.salesforce.base.features.home.user.UserViewModel
import biz.belcorp.salesforce.base.features.splash.SplashViewModel
import biz.belcorp.salesforce.core.utils.get

import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class ViewModelsDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolver dependencies for SplashViewModel`() {
        get<SplashViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for HomeViewModel`() {
        get<HomeViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for ShortcutsViewModel`() {
        get<ShortcutsViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for UserViewModel`() {
        get<UserViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for TermsCookiesViewModel`() {
        get<TermsCookiesViewModel>().shouldNotBeNull()
    }
}
