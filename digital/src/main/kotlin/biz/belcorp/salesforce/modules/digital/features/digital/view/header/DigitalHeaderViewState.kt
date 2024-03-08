package biz.belcorp.salesforce.modules.digital.features.digital.view.header

import biz.belcorp.salesforce.modules.digital.features.digital.model.DigitalHeaderModel

sealed class DigitalHeaderViewState {

    class Success(val model: DigitalHeaderModel) : DigitalHeaderViewState()
    object NoDataAvailable : DigitalHeaderViewState()

}
