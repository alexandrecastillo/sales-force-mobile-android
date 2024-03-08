package biz.belcorp.salesforce.base.features.home.di

import androidx.navigation.NavController
import biz.belcorp.salesforce.base.features.home.HomeMapper
import biz.belcorp.salesforce.base.features.home.HomeViewModel
import biz.belcorp.salesforce.base.features.home.cookies.TermsCookiesViewModel
import biz.belcorp.salesforce.base.features.home.menu.bottommenu.BottomMenuViewModel
import biz.belcorp.salesforce.base.features.home.menu.mappers.MenuOptionMapper
import biz.belcorp.salesforce.base.features.home.menu.navigation.MenuNavigation
import biz.belcorp.salesforce.base.features.home.menu.sidemenu.SideMenuViewModel
import biz.belcorp.salesforce.base.features.home.shortcuts.ShortcutMapper
import biz.belcorp.salesforce.base.features.home.shortcuts.ShortcutsViewModel
import biz.belcorp.salesforce.base.features.home.user.UserInfoMapper
import biz.belcorp.salesforce.base.features.home.user.UserViewModel
import biz.belcorp.salesforce.base.features.utils.AppTextResolver
import biz.belcorp.salesforce.components.commons.UaInfoDataState
import biz.belcorp.salesforce.components.commons.UaStateObserver
import biz.belcorp.salesforce.core.base.BaseTextResolver
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val APP_TEXT_RESOLVER = "app_text_resolver"

val homeModule = module {

    single<BaseTextResolver>(named(APP_TEXT_RESOLVER)) { AppTextResolver(get()) }

    factory { HomeMapper(get(named(APP_TEXT_RESOLVER))) }

    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get()) }

    factory { MenuOptionMapper() }

    viewModel { BottomMenuViewModel(get(), get()) }
    viewModel { SideMenuViewModel(get(), get(), get(), get()) }

    factory { ShortcutMapper() }
    viewModel { ShortcutsViewModel(get(), get(), get()) }

    factory { (navController: NavController) -> MenuNavigation(navController) }

    viewModel { UserViewModel(get(), get()) }
    factory { UserInfoMapper(get(named(APP_TEXT_RESOLVER))) }

    viewModel { TermsCookiesViewModel(get()) }

    single { UaInfoDataState() }
    single { UaStateObserver(get()) }
}
