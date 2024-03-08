package biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.saveprojection

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.COLLECTION_KEY
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.FILTER_CREATE_KEY
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.FILTER_TYPE_CREATE
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.FUNCTION_KEY
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.INPUT_KEY
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ACTIVE_CONSULTANTS_ACTIVES_EXPECTED
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ACTIVE_CONSULTANTS_CURRENT_ACTIVES
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ACTIVE_CONSULTANTS_EXPECTED
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ACTIVITY_PROJECTED_PERCENTAGE
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_CAMPAIGN
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_CAMPAIGN_PROJECTION_ACTIVE_CONSULTANTS
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_CAPI
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_CAPITALIZATION
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_CAPITALIZATION_GAIN
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_CAPITALIZATION_GAIN_CURRENT
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_CAPITALIZATION_GAIN_REAL
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_CAPI_CURRENT
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_CAPI_REAL
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ENTRIES
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ENTRIES_PROJECTED
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ENTRIES_REAL
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_FINALS_LAST_YEAR
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_GAIN
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_GAIN_BY_POINTS
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_GAIN_BY_POINTS_CURRENT
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_GAIN_BY_POINTS_REAL
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_GAIN_CURRENT
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_GAIN_REAL
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_INSURED_AMOUNT
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_INSURED_AMOUNT_CURRENT
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_INSURED_AMOUNT_REAL
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ORDERS
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ORDERS_CURRENT_TOTAL
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ORDERS_EXPECTED_TOTAL
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ORDERS_LIST
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ORDERS_TOTAL_GAIN
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ORDER_GAIN_PER_ORDER
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ORDER_GAIN_PER_ORDER_EXPECTED
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ORDER_GAIN_PER_ORDER_NOT_SUCCESS
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ORDER_TITLE
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ORDER_UNITS_EXPECTED
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_PEGS
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_PEGS_CURRENT
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_PEGS_LEAVE_EXPECTED
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_PEGS_PROJECTED_NEXT_CAMPAIGN
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_PEGS_RETENTION_EXPECTED
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_PEGS_RETENTION_REAL
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_PROJECT_PROFIT_SUMMARY
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_REGION
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_RETENTION_6D6
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_RETENTION_6D6_GAIN_6D6_HIGH
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_RETENTION_6D6_GAIN_6D6_HIGH_MULTIBRAND
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_RETENTION_6D6_GAIN_6D6_LOW
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_RETENTION_6D6_LIST
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_RETENTION_6D6_LIST_ACTIVE_CONSULTANTS
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_RETENTION_6D6_LIST_RETENTION_EXPECTED
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_RETENTION_6D6_LIST_TITLE
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_RETENTION_RE_ENTRIES_LAST_5C
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_RE_ENTRIES
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_RE_ENTRIES_PROJECTED
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_RE_ENTRIES_REAL
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_SECTION
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.KEY_ZONE


class SaveCampaignProjectionMutation(private val request: SaveCampaignProjectionParams) :
    BaseQuery() {

    override val keyFunctionName = FUNCTION_KEY
    override val keyCollection = COLLECTION_KEY
    override val keyFilter = FILTER_CREATE_KEY
    override val keyFilterType = FILTER_TYPE_CREATE
    override val keyInput = INPUT_KEY

    override fun getJson() = request.toJson()

    override fun get(): Kraph {

        val tempKraph = Kraph {
            mutation(keyFunctionName) {
                fieldObject(
                    keyCollection, mapOf(keyInput to getInput())
                ) {
                    field(KEY_REGION)
                    field(KEY_ZONE)
                    field(KEY_SECTION)
                    field(KEY_CAMPAIGN)
                    fieldObject(KEY_CAPITALIZATION) {
                        field(KEY_CAPI) {
                            field(KEY_CAPI_CURRENT)
                            field(KEY_CAPI_REAL)
                        }
                        field(KEY_GAIN_BY_POINTS) {
                            field(KEY_GAIN_BY_POINTS_CURRENT)
                            field(KEY_GAIN_BY_POINTS_REAL)
                        }
                        field(KEY_GAIN) {
                            field(KEY_GAIN_CURRENT)
                            field(KEY_GAIN_REAL)
                        }
                        field(KEY_ENTRIES) {
                            field(KEY_ENTRIES_PROJECTED)
                            field(KEY_ENTRIES_REAL)
                        }
                        field(KEY_RE_ENTRIES) {
                            field(KEY_RE_ENTRIES_PROJECTED)
                            field(KEY_RE_ENTRIES_REAL)
                        }
                        field(KEY_PEGS) {
                            field(KEY_PEGS_CURRENT)
                            field(KEY_PEGS_RETENTION_EXPECTED)
                            field(KEY_PEGS_LEAVE_EXPECTED)
                            field(KEY_PEGS_RETENTION_REAL)
                        }
                        field(KEY_CAMPAIGN_PROJECTION_ACTIVE_CONSULTANTS) {
                            field(KEY_ACTIVE_CONSULTANTS_EXPECTED)
                            field(KEY_ACTIVE_CONSULTANTS_CURRENT_ACTIVES)
                            field(KEY_ACTIVE_CONSULTANTS_ACTIVES_EXPECTED)
                            field(KEY_FINALS_LAST_YEAR)
                        }
                    }
                    fieldObject(KEY_RETENTION_6D6) {
                        field(KEY_RETENTION_RE_ENTRIES_LAST_5C)
                        field(KEY_RETENTION_6D6_GAIN_6D6_LOW)
                        field(KEY_RETENTION_6D6_GAIN_6D6_HIGH)
                        field(KEY_RETENTION_6D6_GAIN_6D6_HIGH_MULTIBRAND)
                        field(KEY_RETENTION_6D6_LIST) {
                            field(KEY_RETENTION_6D6_LIST_TITLE)
                            field(KEY_RETENTION_6D6_LIST_ACTIVE_CONSULTANTS)
                            field(KEY_RETENTION_6D6_LIST_RETENTION_EXPECTED)
                        }
                    }
                    fieldObject(KEY_ORDERS) {
                        field(KEY_ORDERS_CURRENT_TOTAL)
                        field(KEY_ORDERS_EXPECTED_TOTAL)
                        field(KEY_ORDERS_TOTAL_GAIN)
                        field(KEY_ORDERS_LIST) {
                            field(KEY_ORDER_TITLE)
                            field(KEY_ORDER_UNITS_EXPECTED)
                            field(KEY_ORDER_GAIN_PER_ORDER)
                            field(KEY_ORDER_GAIN_PER_ORDER_NOT_SUCCESS)
                            field(KEY_ORDER_GAIN_PER_ORDER_EXPECTED)
                        }
                        field(KEY_ORDERS_EXPECTED_TOTAL)
                        field(KEY_ORDERS_TOTAL_GAIN)
                    }
                    fieldObject(KEY_PROJECT_PROFIT_SUMMARY) {
                        field(KEY_CAPITALIZATION_GAIN) {
                            field(KEY_CAPITALIZATION_GAIN_CURRENT)
                            field(KEY_CAPITALIZATION_GAIN_REAL)
                        }
                        field(KEY_INSURED_AMOUNT) {
                            field(KEY_INSURED_AMOUNT_CURRENT)
                            field(KEY_INSURED_AMOUNT_REAL)
                        }
                    }
                    field(KEY_ACTIVITY_PROJECTED_PERCENTAGE)
                    field(KEY_PEGS_PROJECTED_NEXT_CAMPAIGN)
                }
            }
        }
        return tempKraph
    }
}
