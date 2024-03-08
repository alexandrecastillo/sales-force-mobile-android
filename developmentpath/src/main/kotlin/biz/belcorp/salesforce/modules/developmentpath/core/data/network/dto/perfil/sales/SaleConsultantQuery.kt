package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.sales

import biz.belcorp.salesforce.core.constants.KEY_CAMPAIGN
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

class SaleConsultantQuery(val params: SaleConsultantParams) : BaseQuery() {

    override val keyFunctionName = FUNCTION_SALE_CONSULTANT_NAME_KEY
    override val keyFilter = FILTER_SALE_CONSULTANT_KEY
    override val keyFilterType = FILTER_SALE_CONSULTANT_TYPE_KEY
    override val keyCollection = COLLECTION_SALE_CONSULTANT_KEY
    override val keyInput = INPUT_SALE_CONSULTANT_KEY


    override fun getJson() = params.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(KEY_CONSULTANT_CODE)
                field(KEY_CAMPAIGN)
                field(KEY_REGION)
                field(KEY_NET_SALE)
                field(KEY_CATALOG_SALE)
                field(KEY_BILLED_ORDER_AMOUNT)
                field(KEY_IS_HIGH_VALUE_ORDER)

                fieldObject(KEY_BRIGHT_PATH_PERIOD) {
                    field(KEY_ORDERS)
                    field(KEY_SALE)
                }

                field(KEY_AVERAGE_SALE_U6C)
                field(KEY_GAIN_AMOUNT)


                field(KEY_CONSTANT_U6C)
                fieldObject(KEY_CURRENT_PACK) {
                    field(KEY_CAMPAING)
                    field(KEY_BRAND_CODE)
                    field(KEY_BRAND)
                    field(KEY_AVERAGE)
                    field(KEY_AMOUNT)
                }
                field(KEY_BRIGHT_PATH_PERIOD_)
                fieldObject(KEY_BRIGHT_PATH_LEVEL) {
                    field(KEY_CONSTANT_NAME)
                    field(KEY_ACCUMULATIVE_SALES)
                    field(KEY_ACCUMULATIVE_ORDERS)
                    field(KEY_HAS_ORDER)
                    field(KEY_SALES_REAL)
                    field(KEY_SALES_AVERAGE)
                    field(KEY_SALES_TO_RETAIN_LEVEL)
                    field(KEY_CURRENT_LEVEL_SALES_REQUIREMENT)
                    field(KEY_CURRENT_LEVEL_ORDER_REQUIREMENT)
                    field(KEY_CAMPAIGN_PROGRESS)
                    field(KEY_AVERAGE_CURRENT_LEVEL)
                }
                fieldObject(KEY_BRIGHT_PATH_NEXT_LEVEL) {
                    field(KEY_CONSTANT_NAME)
                    fieldObject(KEY_SALES) {
                        field(KEY_SALES_REQUIREMENT)
                        field(KEY_AVERAGE)
                    }
                    fieldObject(KEY_ORDERS) {
                        field(KEY_REQUIREMENT)
                    }
                }
                field(KEY_ZONE)
                field(KEY_SECTION)


            }
        }
    }
}
