package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.provider

import android.content.res.Resources
import biz.belcorp.mobile.components.dialogs.filter.frame.FilterChildViewType
import biz.belcorp.mobile.components.dialogs.filter.frame.FilterViewType
import biz.belcorp.mobile.components.dialogs.filter.model.group.ChoiceMode
import biz.belcorp.mobile.components.dialogs.filter.model.group.CollapsedGroupModel
import biz.belcorp.mobile.components.dialogs.filter.model.group.GroupModel
import biz.belcorp.mobile.components.dialogs.filter.model.item.CheckItemModel
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_FIVE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_FOUR
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_THREE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.CountryISO
import biz.belcorp.salesforce.core.constants.FilterKey
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.*

class FilterContentProvider(private val resources: Resources) {

    fun getFilters(hashMap: HashMap<String, String>, isoCountryCode: String?): Pair<String, List<GroupModel>> {
        return when {
            hashMap.containsKey(FilterKey.KEY_NO_BILLED_ORDERS) -> {
                Pair(
                    resources.getString(R.string.filter_pending_orders),
                    getNoBilledOrdersFilters()
                )
            }
            hashMap.containsKey(FilterKey.KEY_BILLED_ORDERS) -> {
                Pair(resources.getString(R.string.filter_billed_orders), getBilledOrdersFilters(isoCountryCode))
            }
            else -> Pair(EMPTY_STRING, emptyList())
        }
    }

    private fun getNoBilledOrdersFilters() = listOf<GroupModel>(
        CollapsedGroupModel(
            KEY_STATUS_ORDERS,
            resources.getString(R.string.filter_title_order_status),
            null,
            NUMBER_ONE,
            true,
            listOf(
                CheckItemModel(
                    FilterKey.KEY_ORDERS_MINIMAL_AMOUNT,
                    KEY_STATUS_ORDERS,
                    resources.getString(R.string.filter_title_min_amount_not_accomplished),
                    null,
                    NUMBER_ZERO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                ),
                CheckItemModel(
                    FilterKey.KEY_ORDERS_NEAR_TP_HIGH_VALUE,
                    KEY_STATUS_ORDERS,
                    resources.getString(R.string.filter_title_near_to_be_high_value),
                    null,
                    NUMBER_ONE,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                ),
                CheckItemModel(
                    FilterKey.KEY_ORDERS_WITH_DEBT,
                    KEY_STATUS_ORDERS,
                    resources.getString(R.string.filter_title_has_pending_debt),
                    null,
                    NUMBER_TWO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                )
            ),
            null,
            FilterViewType.TYPE_COLLAPSED,
            null,
            ChoiceMode.MULTIPLE
        ),
        CollapsedGroupModel(
            KEY_CONSTANCY,
            resources.getString(R.string.filter_title_new_cycles),
            null,
            NUMBER_TWO,
            true,
            listOf(
                CheckItemModel(
                    FilterKey.KEY_5D5,
                    KEY_CONSTANCY,
                    resources.getString(R.string.filter_title_new_cycles_6d6),
                    null,
                    NUMBER_ZERO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                ),
                CheckItemModel(
                    FilterKey.KEY_4D4,
                    KEY_CONSTANCY,
                    resources.getString(R.string.filter_title_new_cycles_5d5),
                    null,
                    NUMBER_ONE,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                ),
                CheckItemModel(
                    FilterKey.KEY_3D3,
                    KEY_CONSTANCY,
                    resources.getString(R.string.filter_title_new_cycles_4d4),
                    null,
                    NUMBER_TWO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                ),
                CheckItemModel(
                    FilterKey.KEY_2D2,
                    KEY_CONSTANCY,
                    resources.getString(R.string.filter_title_new_cycles_3d3),
                    null,
                    NUMBER_THREE,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                ),
                CheckItemModel(
                    FilterKey.KEY_1D1,
                    KEY_CONSTANCY,
                    resources.getString(R.string.filter_title_new_cycles_2d2),
                    null,
                    NUMBER_FOUR,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                )
            ),
            null,
            FilterViewType.TYPE_COLLAPSED,
            null,
            ChoiceMode.MULTIPLE
        ),
        CollapsedGroupModel(
            KEY_PEGS,
            resources.getString(R.string.filter_title_segment),
            null,
            NUMBER_THREE,
            true,
            listOf(
                CheckItemModel(
                    FilterKey.KEY_PEGS,
                    KEY_PEGS,
                    resources.getString(R.string.filter_title_segment_pegs),
                    null,
                    NUMBER_ZERO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                ),
                CheckItemModel(
                    FilterKey.KEY_POSSIBLE_PEGS,
                    KEY_PEGS,
                    resources.getString(R.string.filter_title_segment_possible_pegs),
                    null,
                    NUMBER_ZERO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                )
            ),
            null,
            FilterViewType.TYPE_COLLAPSED,
            null,
            ChoiceMode.MULTIPLE
        ),
        getConsultantTypeFilters(),
        getConsultantMultibrandFilters()
    )

    private fun getBilledOrdersFilters(isoCountryCode: String?) = listOf<GroupModel>(
        CollapsedGroupModel(
            KEY_STATE,
            resources.getString(R.string.filter_title_state),
            null,
            NUMBER_THREE,
            true,
            listOf(
                CheckItemModel(
                    FilterKey.KEY_ENTRIES,
                    KEY_STATE,
                    resources.getString(R.string.filter_title_state_entries),
                    null,
                    NUMBER_ZERO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                ),
                CheckItemModel(
                    FilterKey.KEY_REENTRIES,
                    KEY_STATE,
                    resources.getString(R.string.filter_title_state_reentries),
                    null,
                    NUMBER_ZERO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                )
            ),
            null,
            FilterViewType.TYPE_COLLAPSED,
            null,
            ChoiceMode.MULTIPLE
        ),
        getConsultantTypeFilters(),
        getOrderTypeFilters(isoCountryCode),
        getConsultantMultibrandFilters(),
        getConsultantMulticategoryFilters()
    )


    private fun getConsultantTypeFilters(): CollapsedGroupModel {
        return CollapsedGroupModel(
            KEY_TYPE,
            resources.getString(R.string.filter_title_type),
            null,
            NUMBER_THREE,
            true,
            listOf(
                CheckItemModel(
                    FilterKey.KEY_HAS_CASH_PAYMENT,
                    KEY_TYPE,
                    resources.getString(R.string.filter_title_type_has_cash_payment),
                    null,
                    NUMBER_ZERO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                ),
                CheckItemModel(
                    FilterKey.KEY_HAS_NOT_CASH_PAYMENT,
                    KEY_TYPE,
                    resources.getString(R.string.filter_title_type_has_not_cash_payment),
                    null,
                    NUMBER_ZERO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                )
            ),
            null,
            FilterViewType.TYPE_COLLAPSED,
            null,
            ChoiceMode.MULTIPLE
        )
    }

    private fun getOrderTypeFilters(isoCountryCode: String?): CollapsedGroupModel {
        val dataFilterList = arrayListOf<CheckItemModel>()
        dataFilterList.add(CheckItemModel(
            FilterKey.KEY_LOW_VALUE,
            KEY_TYPE,
            resources.getString(R.string.filter_title_order_type_low_value),
            null,
            NUMBER_ZERO,
            true,
            FilterChildViewType.TYPE_CHECK,
            null,
            false
        ))

        //4/7/2023:  Filter: BAJO_PLUS --> PBVP  not applied by requirement of missing data

        /*if(isoCountryCode.equals(CountryISO.PERU) || isoCountryCode.equals(CountryISO.ECUADOR)){
            dataFilterList.add(CheckItemModel(
                FilterKey.KEY_LOW_VALUE_PLUS,
                KEY_TYPE,
                resources.getString(R.string.filter_title_order_type_low__plus_value),
                null,
                NUMBER_ZERO,
                true,
                FilterChildViewType.TYPE_CHECK,
                null,
                false
            ))
        }*/

        dataFilterList.add(CheckItemModel(
            FilterKey.KEY_HIGH_VALUE,
            KEY_TYPE,
            resources.getString(R.string.filter_title_order_type_high_value),
            null,
            NUMBER_ZERO,
            true,
            FilterChildViewType.TYPE_CHECK,
            null,
            false
        ))

       dataFilterList.add(CheckItemModel(
           FilterKey.KEY_HIGH_VALUE_PLUS,
           KEY_TYPE,
           resources.getString(R.string.filter_title_order_type_high_plus_value),
           null,
           NUMBER_ZERO,
           true,
           FilterChildViewType.TYPE_CHECK,
           null,
           false
       ))


        return CollapsedGroupModel(
            KEY_TYPE,
            resources.getString(R.string.filter_title_order_type),
            null,
            NUMBER_THREE,
            true,
            dataFilterList,
            null,
            FilterViewType.TYPE_COLLAPSED,
            null,
            ChoiceMode.MULTIPLE
        )
    }

    private fun getConsultantMultibrandFilters(): CollapsedGroupModel {
        return CollapsedGroupModel(
            KEY_TYPE,
            resources.getString(R.string.filter_title_multibrand),
            null,
            NUMBER_FOUR,
            true,
            listOf(
                CheckItemModel(
                    FilterKey.KEY_HAS_YES_MULTIBRAND,
                    KEY_MULTIBRAND,
                    resources.getString(R.string.filter_title_multibrand_yes),
                    null,
                    NUMBER_ZERO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                ),
                CheckItemModel(
                    FilterKey.KEY_HAS_NO_MULTIBRAND,
                    KEY_MULTIBRAND,
                    resources.getString(R.string.filter_title_multibrand_no),
                    null,
                    NUMBER_ZERO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                )
            ),
            null,
            FilterViewType.TYPE_COLLAPSED,
            null,
            ChoiceMode.MULTIPLE
        )
    }

    private fun getConsultantMulticategoryFilters(): CollapsedGroupModel {
        return CollapsedGroupModel(
            KEY_TYPE,
            resources.getString(R.string.filter_title_multicategory),
            null,
            NUMBER_FIVE,
            true,
            listOf(
                CheckItemModel(
                    FilterKey.KEY_HAS_YES_MULTICATEGORY,
                    KEY_MULTICATEGORY,
                    resources.getString(R.string.filter_title_multibrand_yes),
                    null,
                    NUMBER_ZERO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                ),
                CheckItemModel(
                    FilterKey.KEY_HAS_NO_MULTICATEGORY,
                    KEY_MULTICATEGORY,
                    resources.getString(R.string.filter_title_multibrand_no),
                    null,
                    NUMBER_ZERO,
                    true,
                    FilterChildViewType.TYPE_CHECK,
                    null,
                    false
                )
            ),
            null,
            FilterViewType.TYPE_COLLAPSED,
            null,
            ChoiceMode.MULTIPLE
        )
    }

}
