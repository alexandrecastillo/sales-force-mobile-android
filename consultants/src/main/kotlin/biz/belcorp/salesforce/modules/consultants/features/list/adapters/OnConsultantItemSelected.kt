package biz.belcorp.salesforce.modules.consultants.features.list.adapters

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.modules.consultants.features.list.models.ConsultoraModel

interface OnConsultantItemSelected {

    fun onLocationIconSelected(model: ConsultoraModel) = Unit
    fun onCallIconSelected(section: String?, phoneNumber: String?) = Unit
    fun onConsultantItemSelected(personIdentifier: PersonIdentifier) = Unit
    fun onWhatsAppIconSelected(phoneNumber: String?) = Unit
    fun onGrownConsultantItemSelected(consultant: ConsultoraModel) = Unit

}
