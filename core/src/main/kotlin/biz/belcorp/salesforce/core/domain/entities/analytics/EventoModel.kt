package biz.belcorp.salesforce.core.domain.entities.analytics

import biz.belcorp.salesforce.core.domain.entities.hardware.BuildVariant

class EventoModel(
    var category: String,
    var action: String,
    var label: String,
    var screenName: String
) {
    var ambiente: BuildVariant = BuildVariant.DEVELOP
}
