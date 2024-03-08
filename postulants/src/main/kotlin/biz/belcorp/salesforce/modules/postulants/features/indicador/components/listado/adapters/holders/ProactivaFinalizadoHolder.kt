package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.holders

import android.view.View
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.entities.BasePostulante
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.PostulantAdapter
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import kotlinx.android.synthetic.main.item_unete_proactiva_finalizado.view.*

class ProactivaFinalizadoHolder(view: View) : BaseHolder(view) {

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
            txtDireccion?.text = "${itemView.context.resources.getString(R.string.direccion)}: ${
                UneteAlgorithms.getDireccion(model.direccion.orEmpty())
            }"

            if (model.tipoPagoNombre.isNullOrEmpty()) txtTipoPago?.gone()

            focusIfNeeded {
                itemView.cv_aprobada?.setCardBackgroundColor(it)
            }

            btnNoInteresada?.setOnClickListener {
                listener?.postulanteNoIntersada(model)
            }
        }
    }

}
