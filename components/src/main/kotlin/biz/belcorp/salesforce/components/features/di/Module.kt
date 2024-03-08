package biz.belcorp.salesforce.components.features.di

import biz.belcorp.salesforce.components.commons.*
import org.koin.core.qualifier.named
import org.koin.dsl.module


const val SHARED_SCOPE = "shared_scope"
const val SHARED = "shared"
const val SHARED_FILTER = "filter"
const val SHARED_GAIN_FILTER = "gain_filter"
const val SHARED_CAPITALIZATION_FILTER = "shared_capitalization_ua_filter"
const val SHARED_KPI_CONSOLIDATED_GRID_FILTER = "shared_kpi_consolidated_grid_filter"
const val GRID_FILTER_KEY = "grid_filter_key"
const val CAPITALIZATION_UA_KEY = "capitalization_ua_key"
private const val HEADER = "shared_header"
private const val CONTENT = "shared_content"
private const val FILTER = "shared_filter"

val sharedFeatureModule = module {
    scope(named(SHARED_SCOPE)) {
        scoped(named(HEADER)) { UaInfoLiveData() }
        scoped(named(CONTENT)) { UaInfoLiveData() }
        scoped(named(SHARED)) { UaInfoStateObserver(get(named(HEADER)), get(named(CONTENT))) }
        scoped(named(FILTER)) { FilterStateLiveData() }
        scoped(named(SHARED_FILTER)) { StateFilterObserver(get(named(FILTER))) }
        scoped(named(SHARED_GAIN_FILTER)) { StateFilterObserver(get(named(FILTER))) }
        scoped(named(CAPITALIZATION_UA_KEY)) { UaKeyLiveData() }
        scoped(named(SHARED_CAPITALIZATION_FILTER)) { UaKeyObserver(get(named(CAPITALIZATION_UA_KEY))) }
        scoped(named(GRID_FILTER_KEY)) { GridFilterLiveData() }
        scoped(named(SHARED_KPI_CONSOLIDATED_GRID_FILTER)) {
            GridFilterObserver(get(named(GRID_FILTER_KEY)))
        }
    }
}
