package biz.belcorp.salesforce.modules.virtualmethodology.features.contacts.di

import biz.belcorp.salesforce.modules.virtualmethodology.features.contacts.ContactsContentResolver
import biz.belcorp.salesforce.modules.virtualmethodology.features.contacts.ListContactsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val contactsModule = module {
    factory { ContactsContentResolver(get()) }
    viewModel { ListContactsViewModel(get()) }
}
