package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.holders

import android.view.View
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteProactivaPreAprobadaStatus
import biz.belcorp.salesforce.modules.postulants.features.entities.BasePostulante
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.PostulantAdapter
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import kotlinx.android.synthetic.main.item_unete_proactiva_preaprobado.view.*

class ProactivaPreAprobadoHolder(private val view: View, private val isGzOrGr: Boolean = false) :
    BaseHolder(view) {

    var listener: PostulantAdapter.EvaluacionAdapterListener? = null

    override fun bind(model: BasePostulante, focus: Boolean) = with(itemView) {
        focusEnabled = focus
        if (model is PostulanteModel) {
            txtNombres?.text = getNombres(model)
            txtNroDocumento?.text = getDocumento(model)
            txtTelefonoCelular?.text = getTelfCelular(model)
            txtFuente?.text = getFuente(model)
            txtIngreso?.text = getIngreso(model)
            txtTipoPago?.text = getTipoPago(model)
            txtCorreo?.text = getCorreoElectronico(model)
            txtDireccion?.text = "${itemView.context.resources.getString(R.string.direccion)}: ${
                UneteAlgorithms.getDireccion(model.direccion.orEmpty())
            }"

            if (model.tipoPagoNombre.isNullOrEmpty()) txtTipoPago?.gone()

            btnRechazar?.visibility = View.GONE
            btnAprobar?.visibility = View.GONE

            if (isGzOrGr) {
                btnRechazar?.visibility = View.VISIBLE
                btnAprobar?.visibility = View.VISIBLE

                btnRechazar?.setOnClickListener {
                    listener?.onProactivaPreAprobadaUpdateEstado(
                        UneteProactivaPreAprobadaStatus.RECHAZAR.value,
                        model
                    )
                }

                btnAprobar?.setOnClickListener {
                    listener?.onProactivaPreAprobadaUpdateEstado(
                        UneteProactivaPreAprobadaStatus.APROBAR.value,
                        model
                    )
                }
            }

            btnWhatsapp?.setOnClickListener {
                listener?.openWhatsapp(model)
            }
        }
    }
}
