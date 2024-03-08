package biz.belcorp.salesforce.modules.creditinquiry.features

import android.app.Activity
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ConsultaCrediticiaInternaModel
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ConsultaCrediticiaModel

interface ConsultaCrediticiaView {
    fun showConsultaCrediticia(consultaCrediticiaModel: ConsultaCrediticiaInternaModel?) = Unit
    fun showConsultaCrediticia(consultaCrediticiaModel: ConsultaCrediticiaModel?) = Unit
    fun sendResponseValidarRegionZona(response: Int?) = Unit
    fun showMessageError(message: String)
    fun setNewTitle(title: String)
    fun getFragment() : BaseFragment?
}
