package biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps

import biz.belcorp.salesforce.modules.postulants.utils.Constant

data class Place(
    val id: String = Constant.EMPTY_STRING,
    val description: String = Constant.EMPTY_STRING
)
