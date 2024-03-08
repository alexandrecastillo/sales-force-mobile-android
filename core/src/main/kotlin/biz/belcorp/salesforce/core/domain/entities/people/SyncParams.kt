package biz.belcorp.salesforce.core.domain.entities.people

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA


class SyncParams(
    val ua: LlaveUA,
    val countryIso: String,
    val campaign: String,
    val phase: String = Constant.EMPTY_STRING,
    val isForced: Boolean = false,
    val onlyModified: Boolean = false
)
