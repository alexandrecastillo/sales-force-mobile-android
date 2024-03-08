package biz.belcorp.salesforce.modules.virtualmethodology.features.methodology

import biz.belcorp.salesforce.modules.virtualmethodology.features.contacts.model.ListContacts

interface OnCheckConsultantListener {
    fun consultantCheck(consultoraCheckList: List<ListContacts>)
    fun showSendButton(show: Boolean)
}
