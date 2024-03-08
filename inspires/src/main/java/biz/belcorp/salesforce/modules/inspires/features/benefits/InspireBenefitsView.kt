package biz.belcorp.salesforce.modules.inspires.features.benefits

import biz.belcorp.salesforce.modules.inspires.model.InspireIndicatorModel

interface InspireBenefitsView {
    fun showValues(model: InspireIndicatorModel)
}
