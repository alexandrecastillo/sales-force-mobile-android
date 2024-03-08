package biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class DetailPlace(
    val placeId: String = Constant.EMPTY_STRING,
    val lat: Double?,
    val lng: Double?
)
