package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.holders

import android.content.Context
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.backgroundResource
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.features.entities.BasePostulante
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.UneteActivity
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.EvaluacionItemAdapter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.PostulantAdapter
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import kotlinx.android.synthetic.main.item_unete_preaprobada.view.*
import kotlinx.android.synthetic.main.section_unete_action.view.*

class PreAprobadaHolder(view: View) : BaseHolder(view) {

    var buttonTitle: String = Constant.EMPTY_STRING
    var listener: PostulantAdapter.EvaluacionAdapterListener? = null
    var rol: String = Constant.EMPTY_STRING

    override fun bind(model: BasePostulante, focus: Boolean) = with(itemView) {
        focusEnabled = focus
        if (model is PostulanteModel) {
            val mContext: Context = itemView.context

            val paisConfig = model.pais?.let { UneteConfig.find(it) }
            paisConfig?.also {
                stpPasos?.adapter = EvaluacionItemAdapter(mContext, it.pasos)
                stpPasos?.currentStepPosition = model.paso
                stpPasos?.isTabNavigationEnabled = false
            }

            txtNombres?.text = getNombres(model)
            txtDiasEspera?.text = getDiasEspera(model)
            txtFuente?.text = getFuente(model)
            txtSeccion?.text = getSeccion(model)

            showSMSValidation(model)
            showPendienteCambio(model)

            txtTipoPago?.text = getTipoPago(model)
            if (model.tipoPagoNombre.isNullOrEmpty()) txtTipoPago?.gone()

            if (model.numeroDocumento.isNullOrEmpty()) txtNroDocumento?.gone()
            txtNroDocumento?.text = getDocumento(model)
            txtDireccion?.text = "${itemView.context.resources.getString(R.string.direccion)}: ${
                UneteAlgorithms.getDireccion(model.direccion.orEmpty())
            }"
            btnEditar?.text = buttonTitle
            btnEditar?.setOnClickListener {
                UneteActivity.getCallingIntent(mContext, model.UUID, model.solicitudPostulanteID, model.codigoZona)
            }

            btnRechazar?.setOnClickListener {
                listener?.updateEstado(UneteAccionFFVV.RECHAZAR, model)
            }

            btnAprobar?.setOnClickListener {
                listener?.let { aprobar(model, it) }
            }

            lltCabecera?.setOnClickListener {
                if (model.devueltoPorSAC) {
                    listener?.devueltoSac(model.solicitudPostulanteID)
                }
            }

            focusIfNeeded { itemView.cv_pre_aprobada.setCardBackgroundColor(it) }
            listener?.let { visibilityControls(model, it) }

        }
    }

    private fun showSMSValidation(model: PostulanteModel) = with(itemView) {
        if (isCountrySMSAprobation(model)) {
            lltValidacionGZ?.visible()
            chkBoxValidacionGZ?.text = getValidacionGZ(model)
            chkBoxValidacionGZ?.isChecked = true
            chkBoxValidacionGZ?.isEnabled = false
        }
    }

    private fun showPendienteCambio(model: PostulanteModel) = with(itemView) {
        if (model.estadoPostulante.equals(UneteEstado.PENDIENTE_CAMBIO_MODELO.estado.toString())) {
            lyPendienteCambio.visible()
            txtDescPreparobada.text = context.resources.getString(R.string.cambiomodelo)
        } else {
            lyPendienteCambio.gone()
            txtDescPreparobada.text = context.resources.getString(R.string.preaprobada)
        }
    }

    private fun isCountrySMSAprobation(model: PostulanteModel) =
        (model.pais in PaisUnete.paisesGZSMSAprobation && rol == Rol.GERENTE_ZONA.codigoRol
            && model.estadoTelefonico == UneteEstadoTelefonico.VALIDADO_POR_GZ.value)

    private fun aprobar(
        model: PostulanteModel, listener: PostulantAdapter.EvaluacionAdapterListener
    ) = listener.updateEstado(UneteAccionFFVV.APROBAR, model)


    private fun visibilityControls(
        model: PostulanteModel, listener: PostulantAdapter.EvaluacionAdapterListener
    ) {
        val aprobarEnabled = UneteAlgorithms.isAprobarEnabled(
            model.pais.orEmpty(), model.paso, rol
        )

        itemView.btnAprobar?.visibility = if (aprobarEnabled) View.VISIBLE else View.GONE
        itemView.btnRechazar?.visibility =
            if (listener.uneteConfiguracion()?.rechazarPreAprobada == true) View.VISIBLE else View.GONE
        itemView.etiquetaObservada?.visibility = if ((model.devueltoPorSAC)
            && (listener.uneteConfiguracion()?.rol in arrayOf(Rol.GERENTE_ZONA.codigoRol, Rol.GERENTE_REGION.codigoRol))
        ) View.VISIBLE else View.GONE
        itemView.btnValidar?.gone()
        itemView.txtOffline?.visibility = if (model.sincronizado) View.GONE else View.VISIBLE
        itemView.lySeguroSocial?.gone()
        if (isSeguroSocialPR(model)) {
            itemView.lySeguroSocial?.visible()
            val background =
                if (model.numeroDocumento.isNullOrEmpty()) R.drawable.background_seguro_social
                else R.drawable.background_seguro_social_enabled

            val icon =
                if (model.numeroDocumento.isNullOrEmpty()) R.drawable.ic_close_white else R.drawable.ic_check_white
            itemView.lySeguroSocial?.backgroundResource = background
            itemView.ivSocialIcon?.imageResource = icon

        }
    }

    private fun isSeguroSocialPR(model: PostulanteModel): Boolean {
        return (model.pais.equals(Pais.PUERTORICO.codigoIso)
            && model.fuenteIngreso.equals(FuenteIngreso.UB))
    }
}
