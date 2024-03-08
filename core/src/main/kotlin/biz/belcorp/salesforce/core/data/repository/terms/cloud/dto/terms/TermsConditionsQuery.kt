package biz.belcorp.salesforce.core.data.repository.terms.cloud.dto.terms

import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.data.repository.terms.cloud.*
import biz.belcorp.salesforce.core.data.repository.terms.cloud.KEY_LEGAL_TERMS
import biz.belcorp.salesforce.core.data.repository.terms.cloud.OPERATION_LEGAL_TERMS_NAME
import biz.belcorp.salesforce.core.data.repository.terms.cloud.VAR_KEY_PARAMS
import biz.belcorp.salesforce.core.data.repository.terms.cloud.VAR_TYPE_LEGAL_PARAMS
import biz.belcorp.salesforce.core.utils.kraph.Kraph

class TermsConditionsQuery(private val params: TermsRequestParams) : BaseQuery() {

    override val keyFunctionName = OPERATION_LEGAL_TERMS_NAME
    override val keyFilter = VAR_KEY_PARAMS
    override val keyFilterType = VAR_TYPE_LEGAL_PARAMS
    override val keyCollection = KEY_LEGAL_TERMS
    override val keyInput = ARG_INPUT

    override fun getJson() = params.toJson()

    override fun get(): Kraph {
        return Kraph {
            query(keyFunctionName) {
                fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                    field(KEY_TERM_CODE)
                    field(KEY_DESCRIPTION)
                    field(KEY_URL)
                    field(KEY_POSITION)
                    field(KEY_ACTIVE)
                }
            }
        }
    }
}
