package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import biz.belcorp.mobile.components.dialogs.filter.model.group.GroupModel
import biz.belcorp.mobile.components.dialogs.filter.model.item.CheckItemModel
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.constants.FilterKey.KEY_BILLED_ORDERS
import biz.belcorp.salesforce.core.constants.FilterKey.KEY_NO_BILLED_ORDERS
import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantFilter
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.toHashMap
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.provider.FilterContentProvider

class FilterDialogViewModel(
    private val filterContentProvider: FilterContentProvider
) : ViewModel() {

    private var defaultFilter: List<String> = arrayListOf()

    private val isoCountryCode = UserProperties.session?.countryIso

    val viewState: LiveData<FilterDialogViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<FilterDialogViewState>()

    fun updateFilters(@SourceType sourceType: Int, uaKey: LlaveUA, filtered: List<GroupModel>) {
        val filters = filtered.map { group ->
            group.data.filter { (it as CheckItemModel).isChecked }.map { it.key }
        }.flatten()
        val consultantFilter = ConsultantFilter(sourceType, uaKey, defaultFilter + filters)
        _viewState.postValue(FilterDialogViewState.Request(consultantFilter))
    }

    fun updateDefaultFilter(filters: List<String>) {
        val filter = filters.firstOrNull { it == KEY_NO_BILLED_ORDERS || it == KEY_BILLED_ORDERS }
        filter?.let { defaultFilter = listOf(it) }
        val (title, dialogFilters) = filterContentProvider.getFilters(filters.toHashMap(), isoCountryCode)
        _viewState.postValue(FilterDialogViewState.Success(title, dialogFilters))
    }
}
