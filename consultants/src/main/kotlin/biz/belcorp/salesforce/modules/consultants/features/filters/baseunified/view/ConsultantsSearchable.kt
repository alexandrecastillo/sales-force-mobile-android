package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view

import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view.SearchConsultantViewState.*

class ConsultantsSearchable {

    fun search(consultants: List<ConsultantModel>, text: String): SearchConsultantViewState {
        if (text.isEmpty()) SuccessSearch(consultants)
        val filteredConsultants = consultants.filter {
            it.name.contains(text, ignoreCase = true) || it.segment.contains(text, ignoreCase = true)
        }
        return if (filteredConsultants.isEmpty()) EmptySearch
        else SuccessSearch(filteredConsultants)
    }
}
