package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.paso2

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Paint
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.PaisUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstado
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteConfiguracionModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso2
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.UnetePaso2Factory
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.BaseUneteFragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.BaseUnetePreFragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.UnetePaso2View
import biz.belcorp.salesforce.modules.postulants.utils.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.dialog_material.*
import kotlinx.android.synthetic.main.fragment_unete_paso_2.*

class UnetePrePaso2Fragment : BaseUnetePreFragment(), Step, UnetePaso2View {

    private val presenter: UnetePrePaso2Presenter by injectFragment()

    override fun getLayout(): Int {
        return R.layout.fragment_unete_paso_2
    }

    private var content: IUnetePaso2? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocation: Location? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnContinuar?.setOnClickListener {
            KeyboardUtil.dismissKeyboard(context, view)
            verificarDatos()
        }

        btnCancelar?.setOnClickListener {
            showConfirmacionCancelar()
        }

    }

    private fun verificarDatos() {
        content?.createPreModel()?.let {
            mPreModel = it
            if (elFormularioEsValido()) {
                updateXY()
                if (validarCelular() && mPreModel?.pais != Pais.MEXICO.codigoIso) {
                    validarSiCelularDuplicado()
                }
            }
        }
    }

    private fun updateXY() {
        if ((formularioPreView?.uneteConfiguracion()?.capturarxypaso2 == true) && (mPreModel?.capturarXYPostulante == true)) {
            mPreModel?.latitud = mLocation?.latitude.toString()
            mPreModel?.longitud = mLocation?.longitude.toString()
        }
    }

    private fun showConfirmacionCancelar() {
        context?.dialog2 {
            iconResId = R.drawable.ic_wrong
            titleTextResId = R.string.alert_cancelar_registro_title
            cancelButton(R.string.alert_cancelar_button)
            confirmButton(R.string.alert_aceptar_button) {
                dismiss()
                content?.createPreModel()?.let {
                    presenter.eliminarPrePostulante(it.solicitudPrePostulanteID)
                }
            }
        }?.show()
    }

    override fun onSelected() = Unit

    override fun verifyStep(): VerificationError? {
        return if (getPreModel().pais in PaisUnete.paisesBuro
            && getPreModel().estadoPostulante == UneteEstado.GESTION_SAC.estado.toString()
        ) {
            toast(R.string.unete_postulante_sac)
            VerificationError(getString(R.string.completar_datos))
        } else if (content?.validate() != true) {
            VerificationError(getString(R.string.completar_datos))
        } else {
            if (mEstado == BaseUneteFragment.Estado.Nuevo) {
                content?.createPreModel()?.let { mPreModel = it }
                toast(R.string.unete_step_no_guardado)
                VerificationError(getString(R.string.completar_datos))
            } else null
        }
    }

    override fun onError(error: VerificationError) = Unit

    override fun showLoading() {
        formularioPreView?.showLoading()
    }

    override fun hideLoading() {
        formularioPreView?.hideLoading()
    }

    override fun showError(message: String) = Unit


    override fun showSolicitudRechazada(message: String) {
        context?.solicitudRechazadaDialog(message) {
            activity?.finish()
        }?.show()
    }

    override fun getConsultoraRecomienda(codigo: String) {
        presenter.obtenerNombreConsultora(codigo)
    }

    override fun showConsultoraRecomienda(consultora: String) {
        content?.showConsultoraRecomienda(consultora)
    }

    override fun updated(prePostulanteModel: PrePostulanteModel) {
        pasoFinal()
    }

    override fun getModel(): PostulanteModel? {
        return null
    }

    override fun getPreModel(): PrePostulanteModel {
        return formularioPreView?.prePostulante()?.let { it } ?: PrePostulanteModel()
    }

    override fun getConfiguracion(): UneteConfiguracionModel? {
        return formularioPreView?.uneteConfiguracion()
    }

    override fun notInSection() {
        mostrarMensajePorSeccion()
    }

    private fun mostrarMensajePorSeccion() {
        context?.customDialog(R.layout.dialog_material) {
            tvTitle.setText(R.string.zonificacion_title)
            tvContent.setText(R.string.postulante_pertenece_otra_zona_seccion)
            tvContent.gravity = GravityCompat.START
            ivIcon.setImageResource(R.drawable.ic_location_green)
            btnOk.setText(R.string.unete_aceptar)
            btnOk.setOnClickListener {
                dismiss()
                formularioPreView?.finalizar()
            }
        }?.show()
    }

    override fun showDuplicateCelular() {
        content?.showDuplicateCelularError(resources.getString(R.string.duplicate_celular))
    }

    private fun pasoFinal() {
        mPreModel?.let { formularioPreView?.prePostulante(it) }
        successCreation(R.string.unete_postulante_registrada_text)
    }

    private fun successCreation(bodyMessage: Int) {

        context?.customDialog(R.layout.dialog_material) {
            tvTitle.setText(R.string.unete_postulante_registrada_title)
            tvContent.text = getString(bodyMessage)
            ivIcon.setImageResource(R.drawable.ic_checkmark_alert)
            btnOk.setText(R.string.actualizacion_button_continuar)
            btnOk.setOnClickListener {
                dismiss()
                formularioPreView?.finalizar()
            }
            setCanceledOnTouchOutside(true)
        }?.show()

    }

    override fun decidirActualizarOCrear(b: BuroResponse?) {
        updatePostulante()
    }

    private fun updatePostulante() {
        mPreModel?.let {
            it.fuenteIngreso = getString(R.string.ub_origen)
            it.estadoPostulante = Constant.NUEVA_POSTULANTE_POR_APROBAR
            it.estadoPrePostulante = Constant.FINALIZADO_PREPOSTULANTE
            it.usuarioCreacion = getString(R.string.ub_origen)
            presenter.updatePrePostulanteOnline(it)
        }
    }

    override fun showNextStep() {
        pasoFinal()
    }

    override fun showErrorConexion() {
        context?.dialog1 {
            iconResId = R.drawable.ic_servicio_inactivo
            titleTextResId = R.string.alert_nointernet_title
            contentTextResId = R.string.alert_nointernet_content
            confirmButton(R.string.alert_aceptar_button)
        }?.show()
    }

    override fun showValidacionExitosa() {
        content?.createPreModel()?.let {
            context?.dialog1 {
                iconResId = R.drawable.ic_validacion_exitosa
                titleTextResId = R.string.alert_exitosa_title
                contentText = getString(R.string.alert_exitosa_content, it.nombres)
                confirmButton(R.string.alert_continuar_button) {
                    mPreModel = content?.createPreModel()
                    decidirActualizarOCrear()
                    dismiss()
                }
            }?.show()
        }
    }

    override fun showValidacionPendiente() {
        content?.createPreModel()?.let {
            context?.dialog1 {
                iconResId = R.drawable.ic_validacion_pendiente
                titleTextResId = R.string.alert_pendiente_title
                contentText = getString(R.string.alert_pendiente_content, it.nombres)
                confirmButton(R.string.alert_continuar_button) {
                    mPreModel = content?.createPreModel()
                    decidirActualizarOCrear()
                    dismiss()
                }
            }?.show()
        }
    }

    override fun onRegistroCancelado() {
        toast(R.string.message_registro_cancelado)
        activity?.onBackPressed()
    }

    override fun expandirConsultoraRecomendante(codigoConsultoraRecomendante: String?): Boolean {
        return presenter.expandirConsultoraRecomendante(codigoConsultoraRecomendante)
    }

    override fun obtenerCodigoODocumentoConsultoraRecomendante(codigoConsultoraRecomendante: String?): String? {
        return presenter.obtenerCodigoODocumentoConsultoraRecomendante(codigoConsultoraRecomendante)
    }

    override fun errorAlObtenerNombreConsultoraRecomendante() {
        content?.errorAlObtenerNombreConsultoraRecomendante()
    }

    override fun validarSiCelularDuplicado() {

        if (elFormularioEsValido() && mPreModel?.pais != Pais.MEXICO.codigoIso) {
            mPreModel = content?.createPreModel()

            val pais = mPreModel?.pais.orEmpty()
            val celular = mPreModel?.celular.orEmpty()
            val numeroDocumento = mPreModel?.numeroDocumento.orEmpty()

            presenter.validarCelular(
                pais, celular,
                mPreModel?.tipoDocumento
                    ?: Constant.TIPO_DOCUMENTO_DEFAULT, numeroDocumento
            )
        }
    }

    private fun elFormularioEsValido() = content?.validate() == true


    override fun initEstado() {
        when (getEstado()) {
            BaseUneteFragment.Estado.Edicion -> {
                content?.disable(true)
                btnContinuar?.isEnabled = true
                btnContinuar?.text = getString(R.string.save_continue)
                btnCancelar?.visibility = View.GONE
            }
            BaseUneteFragment.Estado.Visualizacion -> {
                content?.disable()
                btnContinuar?.isEnabled = false
                btnContinuar?.text = getString(R.string.save_continue)
                btnCancelar?.visibility = View.GONE
            }
        }
    }

    override fun initView() {
        presenter.setView(this)

        val vw = UnetePaso2Factory.getView(activity as Context, formularioPreView?.pais(), this)
        content = vw as IUnetePaso2
        lyContainer?.removeAllViews()
        lyContainer?.addView(vw)

        btnCancelar.paintFlags = btnCancelar.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    override fun initModel() {
        if (formularioPreView?.uneteConfiguracion()?.capturarxypaso2 == true)
            getLocation()
    }

    private fun getLocation() {
        context?.let {
            try {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(it)
                if (ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                    ActivityCompat.requestPermissions(activity as Activity, permissions, 100)
                    return

                }
                mFusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                    mLocation = location
                    if (mLocation == null)
                        mLocation = LocationUtil.lastKnownLocation(it)
                }
            } catch (ex: Exception) {
                Logger.loge("getLocation", "getLatLang", ex)
            }
        }
    }

    override fun paso(): Int {
        return Constant.NUMERO_DOS
    }
}
