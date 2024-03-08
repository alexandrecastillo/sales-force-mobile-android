package biz.belcorp.salesforce.modules.inspires.features.travel.cards.lost

import biz.belcorp.salesforce.modules.inspires.model.InspireIndicatorModel

interface InspireTravelCardLostView {
    fun showValues(model: InspireIndicatorModel, nextYear: Int?)
}
