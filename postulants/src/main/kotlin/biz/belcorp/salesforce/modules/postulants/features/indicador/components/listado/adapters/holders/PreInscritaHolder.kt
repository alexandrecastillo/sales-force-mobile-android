package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.holders

import android.view.View
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.entities.BasePostulante
import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.UnetePreActivity
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.EvaluacionItemAdapter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.PostulantAdapter
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import kotlinx.android.synthetic.main.item_unete_pre_inscrito.view.*
import kotlinx.android.synthetic.main.section_unete_action.view.*

class PreInscritaHolder(view: View) : BaseHolder(view) {

    var listener: PostulantAdapter.EvaluacionAdapterListener? = null

    override fun bind(model: BasePostulante, focus: Boolean) = with(itemView) {
        focusEnabled = focus
        if (model is PrePostulanteModel) {
            txtNombres?.text = getNombres(model)

            if (model.direccion.isNullOrEmpty()) {
                lyDireccion?.gone()
            } else {
                lyDireccion?.visible()
            }

            txtDireccion?.text = "${itemView.context.resources.getString(R.string.direccion)}: ${
                UneteAlgorithms.getDireccion(model.direccion.orEmpty())
            }"

            txtFuente?.text = getFuente(model)
            txtSeccion?.text = getSeccion(model)
            txtNroDocumento?.text = getDocumento(model)
            if (model.numeroDocumento.isNullOrEmpty()) txtNroDocumento?.gone()
            txtTelefonoCelular?.text = getTelfCelular(model)

            stpPasos?.isEnabled = false
            btnAprobar?.gone()
            btnValidar?.gone()

            btnEditar?.text = getString(R.string.editar_datos)

            btnEditar?.setOnClickListener {
                UnetePreActivity.getCallingIntent(
                    itemView.context,
                    model.UUID, model.solicitudPrePostulanteID
                )
            }
            btnRechazar?.setOnClickListener {
                listener?.updatePreEstado(model)
            }


            stpPasos?.adapter = EvaluacionItemAdapter(itemView.context, Constant.NUMERO_DOS)
            stpPasos?.currentStepPosition = model.paso
            stpPasos?.isTabNavigationEnabled = false

            focusIfNeeded {
                cv_postulante.setCardBackgroundColor(it)
            }

            visibilityControls(itemView)
        }
    }

    private fun visibilityControls(itemView: View) {
        listener?.uneteConfiguracion()?.let {
            itemView.btnRechazar?.visibility =
                if (it.rechazarEnEvaluacion) View.VISIBLE else View.GONE
        }
        itemView.txtOffline?.gone()
    }
}
