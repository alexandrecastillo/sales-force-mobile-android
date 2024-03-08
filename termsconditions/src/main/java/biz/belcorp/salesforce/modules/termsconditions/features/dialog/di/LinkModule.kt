package biz.belcorp.salesforce.modules.termsconditions.features.dialog.di

import biz.belcorp.salesforce.modules.termsconditions.features.dialog.TermsDialogViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val linkModule = module {
    viewModel { TermsDialogViewModel(get(),get()) }
}
