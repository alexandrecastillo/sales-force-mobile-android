package biz.belcorp.salesforce.modules.postulants.features.evaluation

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.features.adapter.validacionmensaje.ValidacionMensajeModel
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.LoadingView

interface PostulantEvaluationView : LoadingView {

    fun showTipoDocumento(model: List<ParametroUneteModel>)

    fun showErrorConnectionMessage()

    fun showError(message: String)

    fun onSuccess(response: BuroResponse, messages: List<ValidacionMensajeModel>)

    fun onShowName(name: String)

    fun initViews(countryIso: String, rol: String)

}
