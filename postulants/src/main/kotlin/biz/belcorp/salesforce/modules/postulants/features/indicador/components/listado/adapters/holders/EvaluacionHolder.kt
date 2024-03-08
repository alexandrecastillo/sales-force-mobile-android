package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.holders

import android.view.View
import android.widget.ImageView
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.invisible
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.features.entities.BasePostulante
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.UneteActivity
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.EvaluacionItemAdapter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters.PostulantAdapter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.enums.EstadoValidacion
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import kotlinx.android.synthetic.main.item_unete_evaluacion.view.*
import kotlinx.android.synthetic.main.section_unete_action.view.*

class EvaluacionHolder(view: View) : BaseHolder(view) {

    var buttonTitle: String? = Constant.EMPTY_STRING
    var listener: PostulantAdapter.EvaluacionAdapterListener? = null
    var rol: String? = Constant.EMPTY_STRING
    var isRegularizarDocumento: Boolean = false

    override fun bind(model: BasePostulante, focus: Boolean) = with(itemView) {
        focusEnabled = focus
        if (model is PostulanteModel) {
            val paisConfig = UneteConfig.find(model.pais.orEmpty())

            txtNombres?.text = getNombres(model)
            txtDiasEspera?.text = getDiasEspera(model)

            lyDireccion?.visibility =
                if (model.direccion.isNullOrEmpty()) View.GONE else View.VISIBLE

            txtDireccion?.text = "${itemView.context.resources.getString(R.string.direccion)}: ${UneteAlgorithms.getDireccion(model.direccion.orEmpty())}"

            txtFuente?.text = getFuente(model)
            txtSeccion?.text = getSeccion(model)
            txtNroDocumento?.text = getDocumento(model)

            showValidationStatus(model)
            showSMSAprobation(model)

            txtCondiciones?.text = getCondiciones()
            stpPasos?.isEnabled = false
            btnAprobar?.gone()
            btnEditar?.text = buttonTitle

            showConsultantId(model)

            btnEditar?.setOnClickListener {
                UneteActivity.getCallingIntent(
                    itemView.context, model.UUID,
                    model.solicitudPostulanteID, model.codigoZona, isRegularizarDocumento
                )
            }
            btnRechazar?.setOnClickListener {
                listener?.updateEstado(UneteAccionFFVV.RECHAZAR, model)
            }
            btnValidar?.setOnClickListener {
                listener?.updateEstado(UneteAccionFFVV.VALIDAR, model)
            }

            itemView.context?.also {
                stpPasos?.adapter = EvaluacionItemAdapter(it, paisConfig?.pasos.zeroIfNull())
            }
            stpPasos?.currentStepPosition = model.paso
            stpPasos?.isTabNavigationEnabled = false

            focusIfNeeded {
                cv_postulante.setCardBackgroundColor(it)
            }

            visibilityControls(model, listener)

            if (model.fuenteIngreso == FuenteIngreso.UB
                && model.estadoPostulante.equals(UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString())
                && !model.codigoConsultora.isNullOrEmpty()
            ) {
                btnRechazar.invisible()
                btnEditar.invisible()
            }
        }
    }

    private fun showConsultantId(model: PostulanteModel) = with(itemView) {
        if (isRegularizarDocumento) {
            txtId?.visible()
            txtId?.text = resources
                .getString(R.string.unete_listado_codigo, model.codigoConsultora.orEmpty())
            tvUserType?.text = getString(R.string.regularizarDoc)
            btnEditar?.text = getString(R.string.ver_informacion)
        }
    }


    private fun showValidationStatus(model: PostulanteModel) {
        if (model.pais.orEmpty() == Pais.PERU.codigoIso && !isRegularizarDocumento) {

            itemView.lyCondiciones?.visible()
            itemView.txtCondiciones?.text = getCondiciones()
            validarBuroCrediticio(model)
            validarEstadoTelefonico(model)
            validarGEO(model)

        }
    }

    private fun showSMSAprobation(model: PostulanteModel) {
        if (isCountrySMSAprobation(model)) {
            when (model.estadoTelefonico) {
                UneteEstadoTelefonico.REQUIERE_VALIDACION_GZ.value -> setGZSMSValidation(model)
                UneteEstadoTelefonico.VALIDADO_POR_GZ.value -> setGZSMSValidated(model)
            }
        }
    }


    private fun isCountrySMSAprobation(model: PostulanteModel) =
        model.pais in PaisUnete.paisesGZSMSAprobation && rol == Rol.GERENTE_ZONA.codigoRol

    private fun setGZSMSValidated(model: PostulanteModel) = with(itemView) {
        lltValidacionGZ?.visible()
        chkBoxValidacionGZ?.text = getValidacionGZ(model)
        chkBoxValidacionGZ?.isChecked = true
        chkBoxValidacionGZ?.isEnabled = false
    }

    private fun setGZSMSValidation(model: PostulanteModel) = with(itemView) {
        lltValidacionGZ?.visible()
        chkBoxValidacionGZ?.text = getValidacionGZ(model)
        chkBoxValidacionGZ?.setOnCheckedChangeListener { checkView, _ ->
            checkView.isChecked = false
            checkView.isEnabled = true
            listener?.showValidacionGZ(model)
        }
    }

    private fun validarBuroCrediticio(model: PostulanteModel) {

        configurarDialogCondicion(
            itemView.ivCondicionCrediticia,
            EstadoValidacion.VALIDACION_CREDITICIA.estado,
            model.estadoBurocrediticio.orEmpty() == UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()
        )
    }

    private fun validarEstadoTelefonico(model: PostulanteModel) {
        configurarDialogCondicion(
            itemView.ivCondicionTelefono,
            EstadoValidacion.VALIDACION_TELEFONICA.estado,
            model.estadoTelefonico == UneteEstadoTelefonico.VALIDADO_POR_GZ.value
        )
    }

    private fun validarGEO(model: PostulanteModel) {
        configurarDialogCondicion(
            itemView.ivCondicionUbicacion,
            EstadoValidacion.VALIDACION_UBICACION.estado,
            model.estadoGEO.orEmpty() == UneteEstadoGEO.OK.value.toString()
        )
    }


    private fun configurarDialogCondicion(
        seccion: ImageView?, tipoCondicion: Int, valido: Boolean
    ) {

        val icono = when (tipoCondicion) {
            EstadoValidacion.VALIDACION_TELEFONICA.estado -> {
                if (valido) R.drawable.icon_telefono_valido else R.drawable.icon_telefono_invalido
            }
            EstadoValidacion.VALIDACION_UBICACION.estado -> {
                if (valido) R.drawable.icon_ubicacion_valido else R.drawable.icon_ubicacion_invalido
            }
            else -> if (valido) R.drawable.icon_crediticia_valido else R.drawable.icon_crediticia_invalido
        }

        seccion?.setBackgroundResource(icono)
        seccion?.setOnClickListener {
            listener?.showRespuestaCondicion(tipoCondicion, valido)
        }

    }

    private fun visibilityControls(
        model: PostulanteModel, listener: PostulantAdapter.EvaluacionAdapterListener?
    ) {


        itemView.btnValidar?.visibility = if (UneteAlgorithms.isValidarBuroEnabled(
                listener?.uneteConfiguracion()?.validarburo == true,
                model.estadoBurocrediticio.orEmpty()
            )
        ) View.VISIBLE else View.GONE

        itemView.btnRechazar?.visibility =
            if (listener?.uneteConfiguracion()?.rechazarEnEvaluacion == true
                && !isRegularizarDocumento
            ) View.VISIBLE else View.GONE

        itemView.txtOffline?.visibility = if (model.sincronizado) View.GONE else View.VISIBLE


    }
}
