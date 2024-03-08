package biz.belcorp.salesforce.modules.brightpath.core.domain.entities

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING


class UASegment(
    val segmentID: String,
    val fullName: String,
    val level: String = EMPTY_STRING
)
