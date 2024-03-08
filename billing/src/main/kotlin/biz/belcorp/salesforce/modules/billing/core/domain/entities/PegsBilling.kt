package biz.belcorp.salesforce.modules.billing.core.domain.entities

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO

class PegsBilling(val retainedPegs: Int = NUMBER_ZERO, val totalPegs: Int = NUMBER_ZERO)
