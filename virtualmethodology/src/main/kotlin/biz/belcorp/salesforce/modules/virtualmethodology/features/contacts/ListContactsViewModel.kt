package biz.belcorp.salesforce.modules.virtualmethodology.features.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.utils.io
import kotlinx.coroutines.launch

class ListContactsViewModel(val contacts: ContactsContentResolver) : ViewModel() {

    val viewState: LiveData<ListContactsViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<ListContactsViewState>()

    fun getListContacts() {
        viewModelScope.launch {
            io {
                val list = contacts.getAllContacts()
                val myContacts = ListContactsViewState.MyContacts(list)
                _viewState.postValue(myContacts)
            }
        }
    }

}
