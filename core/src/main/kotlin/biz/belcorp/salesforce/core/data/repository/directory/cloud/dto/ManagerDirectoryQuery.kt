package biz.belcorp.salesforce.core.data.repository.directory.cloud.dto

import biz.belcorp.salesforce.core.constants.KEY_PROFILE
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import biz.belcorp.salesforce.core.data.network.dto.BaseQuery
import biz.belcorp.salesforce.core.utils.kraph.Kraph

class ManagerDirectoryQuery(private val params: DirectoryParams) : BaseQuery() {

    override val keyFunctionName = OPERATION_NAME
    override val keyFilter = VAR_KEY_PARAMS
    override val keyFilterType = VAR_TYPE_PARAMS
    override val keyCollection = KEY_SALES_FORCE_DIRECTORY
    override val keyInput = ARG_INPUT

    override fun getJson() = params.toJson()

    override fun get()= Kraph {
        query(keyFunctionName) {
            fieldObject(keyCollection, mapOf(keyInput to getInput())) {
                field(KEY_PROFILE)
                field(KEY_REGION)
                field(KEY_ZONE)
                field(KEY_CONSULTANT_ID)
                field(KEY_USERNAME)
                fieldObject(KEY_PERSONAL_INFO) {
                    field(KEY_FULL_NAME)
                    field(KEY_FIRST_NAME)
                    field(KEY_SURNAME)
                    field(KEY_SECOND_NAME)
                    field(KEY_SECOND_SURNAME)
                    field(KEY_DOCUMENT)
                    field(KEY_EMAIL)
                    field(KEY_TELEPHONE_NUMBER)
                    field(KEY_CELLPHONE_NUMBER)
                    field(KEY_BIRTHDAY)
                }
                field(KEY_PRODUCTIVITY)
                fieldObject(KEY_STATE) {
                    field(KEY_CODE)
                    field(KEY_DESCRIPTION)
                }
                field(KEY_IS_NEW)
                field(KEY_CAMPAIGNS_AS_NEW)
            }
        }
    }
}
