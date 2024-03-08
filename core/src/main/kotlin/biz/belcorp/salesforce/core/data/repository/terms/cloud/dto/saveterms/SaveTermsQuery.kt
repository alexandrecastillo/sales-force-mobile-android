package biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.saveterms

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.data.repository.terms.cloud.*
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_SAVE_APPROVED_TERMS
import biz.belcorp.salesforce.core.data.repository.terms.cloud.OPERATION_SAVE_APPROVED_NAME
import biz.belcorp.salesforce.core.data.repository.terms.cloud.VAR_KEY_PARAMS
import biz.belcorp.salesforce.core.data.repository.terms.cloud.VAR_SAVE_APPROVED_TYPE_PARAMS
import biz.belcorp.salesforce.core.utils.kraph.Kraph

data class SaveTermsQuery(private val params: SaveTermsParams) : BaseQuery() {

    override val keyFunctionName = OPERATION_SAVE_APPROVED_NAME
    override val keyFilter = VAR_KEY_PARAMS
    override val keyFilterType = VAR_SAVE_APPROVED_TYPE_PARAMS
    override val keyCollection = KEY_SAVE_APPROVED_TERMS
    override val keyInput = ARG_INPUT

    override fun getJson() = params.toJson()

    override fun get(): Kraph {
        return Kraph {
            mutation(keyFunctionName) {
                fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                    field(KEY_REGION)
                    field(KEY_ZONE)
                    field(KEY_SECTION)
                    field(KEY_ROLE)
                    field(KEY_CONSULTANT_CODE)
                    field(KEY_USER_CODE)
                    field(KEY_DOCUMENT_NUMBER)
                    field(KEY_APPLICATION)
                    field(KEY_CREATION_DATE)
                    fieldObject(KEY_TERMS) {
                        field(KEY_CAMPAIGN)
                        field(KEY_CREATION_DATE)
                        field(KEY_TERM_CODE)
                        field(KEY_CHECKED)
                        field(KEY_ACTIVE)
                    }
                }
            }
        }
    }


}
