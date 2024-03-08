package biz.belcorp.salesforce.modules.virtualmethodology.features.contacts

import biz.belcorp.salesforce.modules.virtualmethodology.features.contacts.model.ListContacts

sealed class ListContactsViewState {
    class MyContacts(val contacts: List<ListContacts>) : ListContactsViewState()
}
