package biz.belcorp.salesforce.base.features.deeplinks.di

import androidx.navigation.NavController
import biz.belcorp.salesforce.base.features.deeplinks.DeepLinkHandler
import org.koin.dsl.module


val deepLinksModule = module {

    factory { (navController: NavController) -> DeepLinkHandler(navController) }

}
