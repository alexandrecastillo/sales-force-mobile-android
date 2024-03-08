package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.holders

import android.view.View
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteProactivaPreAprobadaStatus
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoRechazo
import biz.belcorp.salesforce.modules.postulants.features.entities.BasePostulante
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.PostulantAdapter
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import kotlinx.android.synthetic.main.item_unete_rechazada.view.*

class RechazadaHolder(view: View, private val isGz: Boolean = false) : BaseHolder(view) {

    var listener: PostulantAdapter.EvaluacionAdapterListener? = null

    override fun bind(model: BasePostulante, focus: Boolean) = with(itemView) {
        focusEnabled = focus
        if (model is PostulanteModel) {
            val tipoRechazo = UneteTipoRechazo.find(model.subEstadoPostulante)

            txtNombres?.text = getNombres(model)

            if (model.motivoRechazo.isNullOrEmpty()) {
                txtMotivoRechazo?.gone()
                txtMotivoRechazo?.text = Constant.EMPTY_STRING
            } else {
                txtMotivoRechazo?.visible()
                txtMotivoRechazo?.text = getMotivoRechazo(model)
            }

            llOptions.gone()
            btnAprobar?.setOnClickListener {
                listener?.onProactivaPreAprobadaUpdateEstado(
                    UneteProactivaPreAprobadaStatus.APROBAR.value,
                    model
                )
            }

            if (tipoRechazo != null) {
                txtStatus?.text = tipoRechazo.desc

                if (tipoRechazo.tipo == UneteTipoRechazo.PROACTIVA_RECHAZADA.tipo && isGz) {
                    llOptions.visible()
                }
            } else {
                txtStatus?.text = getString(R.string.unete_listado_rechazada)
            }

            txtSeccion?.text = getSeccion(model)
            txtFuente?.text = getFuente(model)
            txtNroDocumento?.text = getDocumento(model)
            txtDireccion?.text = "${itemView.context.resources.getString(R.string.direccion)}: ${
                UneteAlgorithms.getDireccion(model.direccion.orEmpty())
            }"

            if (model.tipoRechazoExplanation.isNullOrEmpty()) {
                txtTipoRechazo.gone()
                txtTipoRechazo.text = Constant.EMPTY_STRING
            } else {
                txtTipoRechazo.visible()
                txtTipoRechazo.text = model.tipoRechazoExplanation
            }
        }
    }


}
