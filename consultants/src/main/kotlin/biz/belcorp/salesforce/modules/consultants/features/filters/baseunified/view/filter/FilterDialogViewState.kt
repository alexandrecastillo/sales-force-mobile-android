package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view.filter

import biz.belcorp.mobile.components.dialogs.filter.model.group.GroupModel
import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantFilter

sealed class FilterDialogViewState {
    class Success(val title: String, val filters: List<GroupModel>) : FilterDialogViewState()
    class Request(val consultantFilter: ConsultantFilter) : FilterDialogViewState()
}
