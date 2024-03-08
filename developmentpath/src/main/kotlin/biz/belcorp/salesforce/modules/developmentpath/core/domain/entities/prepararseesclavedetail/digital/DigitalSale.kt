package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING

open class DigitalSale(
    var campaign: String = EMPTY_STRING,
    var region: String = EMPTY_STRING,
    var zone: String = EMPTY_STRING,
    var section: String = EMPTY_STRING
)
