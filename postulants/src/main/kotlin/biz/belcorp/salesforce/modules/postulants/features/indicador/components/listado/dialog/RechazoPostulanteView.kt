package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.dialog

import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.indicador.base.LoadDataView

/**
 * Created by garyfimo on 6/6/18.
 */
interface RechazoPostulanteView : LoadDataView {

    fun showMotivoRechazo(model: List<ParametroUneteModel>)
    fun showPostulanteRechaza()
}
