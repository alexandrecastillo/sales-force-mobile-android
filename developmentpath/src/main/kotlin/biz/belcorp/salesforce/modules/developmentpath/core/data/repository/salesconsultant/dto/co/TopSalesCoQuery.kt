package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.co

import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_AMOUNT
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_AVERAGE_UNITS
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_BRANDS
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_BRAND_TO_PROMOTE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_BUY_CAMPAIGN
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_BUY_CAMPAIGN_1
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_BUY_CAMPAIGN_2
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_BUY_CAMPAIGN_3
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_CAMPAIGN
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_CODSAP
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_CUV
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_HISTORY
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_IMAGES
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_NAME
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_ORDER
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_PHOTO_PRODUCT
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_PRODUCTS
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_QUANTITY
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_SKU
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_TOP
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_TOP_PRODUCTS
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_TOP_SELLING_BRAND
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_UNITS

class TopSalesCoQuery(private val request: TopSalesCoParams) : BaseQuery() {
    override val keyFunctionName = FUNCTION_KEY
    override val keyCollection = COLLECTION_KEY
    override val keyFilter = FILTER_KEY
    override val keyFilterType = FILTER_TYPE
    override val keyInput = INPUT_KEY

    override fun getJson() = request.toJson()

    override fun get() = Kraph {
        query(keyFunctionName) {
            fieldObject(
                keyCollection, mapOf(keyInput to getInput())
            ) {
                field(KEY_CAMPAIGN)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_SECTION)
                field(KEY_CONSULTANT_CODE)
//                field(KEY_TOP_SELLING_BRAND)
//                field(KEY_BRAND_TO_PROMOTE)
                fieldObject(KEY_BRANDS_CATEGORIES) {
                    field(KEY_BRAND)
                    field(KEY_UNITS)
                    fieldObject(KEY_CATEGORY) {
                        field(KEY_ORDER)
                        field(KEY_NAME)
                        field(KEY_UNITS)
                    }
                }
                fieldObject(KEY_BRANDS) {
                    field(KEY_NAME)
                    field(KEY_UNITS)
                }
                fieldObject(KEY_CATEGORIES) {
                    field(KEY_NAME)
                    field(KEY_UNITS)
                    fieldObject(KEY_PRODUCTS) {
                        fieldObject(KEY_BUY_CAMPAIGN) {
                            field(KEY_BUY_CAMPAIGN_1)
                            field(KEY_BUY_CAMPAIGN_2)
                            field(KEY_BUY_CAMPAIGN_3)
                        }
                        field(KEY_ORDER)
                        field(KEY_NAME)
                        field(KEY_SKU)
                        field(KEY_CUV)
                        field(KEY_QUANTITY)
                        field(KEY_AMOUNT)
                        fieldObject(KEY_IMAGES) {
                            field(KEY_CODSAP)
                            field(KEY_PHOTO_PRODUCT)
                        }
                    }
                }
                fieldObject(KEY_TOP_PRODUCTS) {
                    field(KEY_TOP)
                    field(KEY_NAME)
                    field(KEY_AVERAGE_UNITS)
                    fieldObject(KEY_HISTORY) {
                        field(KEY_CAMPAIGN)
                        field(KEY_UNITS)
                    }
                }
                fieldObject(KEY_MULTI_MARK) {
                    field(KEY_ORDER)
                    field(KEY_NAME)
                    fieldObject(KEY_HISTORY) {
                        field(KEY_CAMPAIGN)
                        field(KEY_MULTI_MARK)
                    }
                }
                fieldObject(KEY_MULTI_CATEGORY) {
                    field(KEY_ORDER)
                    field(KEY_CATEGORY)
                    fieldObject(KEY_HISTORY) {
                        field(KEY_CAMPAIGN)
                        field(KEY_BOUGHT)
                    }
                }
                fieldObject(KEY_TRIO_LBEL) {
                    field(KEY_ORDER)
                    field(KEY_NAME)
                    field(KEY_SKU)
                    field(KEY_CUV)
                    fieldObject(KEY_IMAGES) {
                        field(KEY_CODSAP)
                        field(KEY_PHOTO_PRODUCT)
                    }
                    fieldObject(KEY_HISTORY) {
                        field(KEY_CAMPAIGN)
                        field(KEY_BOUGHT)
                    }
                }
                fieldObject(KEY_ESIKA_MORE_BEAUTIFUL) {
                    field(KEY_ORDER)
                    field(KEY_NAME)
                    field(KEY_SKU)
                    field(KEY_CUV)
                    fieldObject(KEY_IMAGES) {
                        field(KEY_CODSAP)
                        field(KEY_PHOTO_PRODUCT)
                    }
                    fieldObject(KEY_HISTORY) {
                        field(KEY_CAMPAIGN)
                        field(KEY_BOUGHT)
                    }
                }
                fieldObject(KEY_TRIO_LBEL_STAY) {
                    field(KEY_ORDER)
                    field(KEY_NAME)
                    field(KEY_SKU)
                    field(KEY_CUV)
                    fieldObject(KEY_IMAGES) {
                        field(KEY_CODSAP)
                        field(KEY_PHOTO_PRODUCT)
                    }
                    fieldObject(KEY_HISTORY) {
                        field(KEY_CAMPAIGN)
                        field(KEY_BOUGHT)
                    }
                }
                field(KEY_TOP_SELLING_BRAND)
                field(KEY_BRAND_TO_PROMOTE)
            }
        }
    }
}
