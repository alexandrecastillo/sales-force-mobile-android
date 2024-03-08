package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.Logger.loge
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.SolicitudPostulanteEstadosResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Localidad
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.features.entities.*
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso2
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.UnetePaso2Factory
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.BaseUneteFragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.entities.PlaceModel
import biz.belcorp.salesforce.modules.postulants.utils.*
import biz.belcorp.salesforce.modules.postulants.utils.Constant.SOLICITUD_NO_CREADA
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.dialog_material.*
import kotlinx.android.synthetic.main.dialog_material.btnOk
import kotlinx.android.synthetic.main.dialog_material.tvContent
import kotlinx.android.synthetic.main.dialog_sms.*
import kotlinx.android.synthetic.main.fragment_unete_paso_2.*

class UnetePaso2Fragment : BaseUneteFragment(), Step, UnetePaso2View {

    private val presenter: UnetePaso2Presenter by injectFragment()

    private var content: IUnetePaso2? = null

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocation: Location? = null
    private var validacionPIN: Int = Constant.PIN_NO_VALIDADO
    private var primerEnvio: Boolean = true
    private var pasoBloqueado: Int = Constant.SIN_ZONA
    private var verificacionBuroMX: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateDisable()
        inicializarPasoBloqueado()

        btnContinuar?.setOnClickListener {
            KeyboardUtil.dismissKeyboard(context, view)
            verificarDatos()
        }

        btnCancelar?.setOnClickListener {
            showConfirmacionCancelar()
        }

    }

    private fun inicializarPasoBloqueado() {
        formularioView?.getPasoBloqueado()?.let {
            this.pasoBloqueado = it
            if (formularioView?.getPais() == Pais.MEXICO.codigoIso && pasoBloqueado != Constant.SIN_ZONA) {
                showValidacionSMSFields()
            }
        }
    }

    private fun validateDisable() {
        val validate = formularioView?.disableSteps() ?: false
        if (validate) content?.disable()
    }

    private fun verificarDatos() {
        content?.createModel()?.let {
            mModel = it
            if (content?.validate() == true) {
                updateXY()

                if (validarCelular() && mModel?.pais != Pais.MEXICO.codigoIso) {
                    validarSiCelularDuplicado()
                } else {
                    presenter.validarDatosDireccionMexico(it)
                }
            }
        }
    }

    private fun validacionBuroMexico() {
        mModel?.let {
            presenter.validarBloqueosMX(
                it,
                content?.getClaveEstado().orEmpty(),
                content?.getCiudad().orEmpty(),
                content?.getDireccion().orEmpty(),
                content?.getTarjetaCredito() ?: false,
                content?.getCreditoHipotecario() ?: false,
                content?.getCreditoAutomotriz() ?: false
            )
        }
    }

    override fun validarSiCelularDuplicado() {
        if (content?.validate() == true) {
            if (mModel?.pais != Pais.MEXICO.codigoIso) {
                content?.createModel()?.let {
                    mModel = it
                    presenter.validarCelular(
                        mModel?.pais.orEmpty(),
                        mModel?.celular.orEmpty(),
                        mModel?.tipoDocumento ?: Constant.TIPO_DOCUMENTO_DEFAULT,
                        mModel?.numeroDocumento.orEmpty(),
                        it
                    )
                }
            } else {
                enviarCodigoValidacionTelefonica()
            }
        }
    }

    override fun decidirActualizarOCrear(b: BuroResponse?) {
        if (b != null) mModel?.estadoBurocrediticio = b.enumEstadoCrediticioID.toString()

        if ((mModel?.pais in PaisUnete.paisesCam || mModel?.pais == Pais.COLOMBIA.codigoIso) && getEstado() == Estado.Nuevo
            && mModel?.solicitudPostulanteID == SOLICITUD_NO_CREADA
        ) {
            crearPostulante()
        } else {
            updatePostulante()
        }
    }

    override fun updateMobileStatus(status: Int) {
        mModel?.let {
            it.estadoTelefonico = status
            presenter.updateEstadoTelefonico(it)
        }
    }

    override fun downloadTerms() {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constant.URL_TERMS_CO))
            context?.startActivity(browserIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(context, R.string.legal_activity_not_found, Toast.LENGTH_SHORT).show()
        }
    }

    override fun updateCoordinates(lat: String, lng: String) {
        mModel?.apply {
            latitud = lat
            longitud = lng
        }

        presenter.onUpdateCoordinates()
    }

    private fun enviarCodigoValidacionTelefonica() {
        content?.createModel()?.let {
            mModel = it
            presenter.enviarCodigoValidacionTelefonica(
                mModel?.pais.orEmpty(),
                mModel?.solicitudPostulanteID ?: Constant.NUMERO_CERO,
                mModel?.celular.orEmpty(),
                mModel?.numeroDocumento.orEmpty(),
                mModel?.nombreCompleto.orEmpty()
            )
        }
    }

    private fun updatePostulante() {
        mModel?.let { presenter.updatePostulante(it) }
    }

    private fun crearPostulante() {
        mModel?.let { presenter.createPostulante(it) }
    }

    override fun paso() = Constant.NUMERO_DOS

    override fun getLayout() = R.layout.fragment_unete_paso_2

    override fun onSelected() = Unit

    override fun verifyStep(): VerificationError? {
        return if (getModel().pais in PaisUnete.paisesBuro
            && getModel().estadoPostulante == UneteEstado.GESTION_SAC.estado.toString()
        ) {
            toast(R.string.unete_postulante_sac)
            VerificationError(getString(R.string.completar_datos))
        } else if (content?.validate() != true) {
            VerificationError(getString(R.string.completar_datos))
        } else {
            if (mEstado == Estado.Nuevo) {
                content?.createModel()?.let { mModel = it }
                toast(R.string.unete_step_no_guardado)
                VerificationError(getString(R.string.completar_datos))
            } else null
        }
    }

    override fun onError(error: VerificationError) = Unit

    override fun showLoading() {
        formularioView?.showLoading()
    }

    override fun hideLoading() {
        formularioView?.hideLoading()
    }

    override fun showError(message: String) = Unit

    override fun showSolicitudRechazada(message: String) {
        context?.solicitudRechazadaDialog(message) {
            activity?.finish()
        }?.show()
    }

    private fun updateXY() {
        if ((formularioView?.uneteConfiguracion()?.capturarxypaso2 == true) && (mModel?.capturarXYPostulante == true)) {
            mModel?.latitud = mLocation?.latitude.toString()
            mModel?.longitud = mLocation?.longitude.toString()
        }
    }

    override fun showDuplicateCelular() {
        content?.showDuplicateCelularError(resources.getString(R.string.duplicate_celular))
    }

    override fun decidirEnvioONoEnvioSMS() {
        if (validacionPaisesSMS() && validacionPin() && validacionTelefonica()) {
            decidirEnviarOContinuar()
        } else {
            validacionBloqueoPaso()
        }
    }

    private fun validacionPaisesSMS() = mModel?.pais in PaisUnete.paisesSMS

    private fun validacionPin() = validacionPIN == Constant.PIN_NO_VALIDADO
        && pasoBloqueado != Constant.SIN_ZONA

    private fun validacionTelefonica() =
        (mModel?.estadoTelefonico == UneteEstadoTelefonico.NO_REQUERIDO.value
            || mModel?.estadoTelefonico == UneteEstadoTelefonico.POR_VALIDAR.value)

    private fun decidirEnviarOContinuar() {
        if (primerEnvio) {
            primerEnvio = false
            updateMobileStatus(UneteEstadoTelefonico.POR_VALIDAR.value)
        } else {
            showValidacionSMSIncompleto()
        }
    }

    override fun showValidacionSMSFields() {
        content?.showPINSMSFields()
    }

    override fun showNextStep() {
        validacionBloqueoPaso()
    }

    private fun getLocation() {
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
            if (ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity as Activity,
                    Array(1) { Manifest.permission.ACCESS_FINE_LOCATION },
                    100
                )
                return

            }
            mFusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                mLocation = location
                if (mLocation == null && context != null)
                    mLocation = LocationUtil.lastKnownLocation(context!!)
            }
        } catch (ex: Exception) {
            loge("getLocation", "getLatLang", ex)
        }
    }

    override fun initModel() {
        presenter.listLugarPadre()
        presenter.lstTipoDireccion()
        presenter.verificarZonasCirculoCredito()
        presenter.lstRangosZonas()
        presenter.listZonasAutomcompletadoDireccion()
        presenter.listLocationByGps()
        if (formularioView?.uneteConfiguracion()?.capturarxypaso2 == true)
            getLocation()

    }

    override fun initView() {

        presenter.setView(this)

        val vw = UnetePaso2Factory.getView(activity as Context, formularioView?.pais(), this)
        content = vw as IUnetePaso2
        lyContainer?.removeAllViews()
        lyContainer?.addView(vw)

        btnCancelar.paintFlags = btnCancelar.paintFlags or Paint.UNDERLINE_TEXT_FLAG

    }

    override fun showConsultoraRecomienda(consultora: String) {
        content?.showConsultoraRecomienda(consultora)
    }

    override fun errorAlObtenerNombreConsultoraRecomendante() {
        content?.errorAlObtenerNombreConsultoraRecomendante()
    }

    override fun showListaPostulante() {
        context?.customDialog(R.layout.dialog_material) {
            tvTitle.text = Constant.EMPTY_STRING
            tvContent.setText(R.string.unete_modal_guadar_description_evaluacion)
            tvContent.gravity = GravityCompat.START
            btnOk.setText(R.string.accept)
            btnOk.setOnClickListener {
                dismiss()
                formularioView?.finalizar()
            }
        }?.show()
    }

    override fun updated(postulanteModel: PostulanteModel) {
        mModel = postulanteModel
        if (mModel?.pais != Pais.MEXICO.codigoIso)
            decidirEnvioONoEnvioSMS()
        else {
            validarMostrarSMSIncompleto()
        }
    }

    private fun validarMostrarSMSIncompleto() {
        if (pasoBloqueado == Constant.SIN_ZONA)
            validacionBloqueoPaso()
        else
            showValidacionSMSIncompleto()
    }

    override fun created(map: PostulanteModel) {
        mModel = map
        mModel?.let { formularioView?.setPostulante(it) }
        decidirEnvioONoEnvioSMS()
    }

    private fun validacionBloqueoPaso() {
        if (paso() == pasoBloqueado &&
            mModel?.estadoTelefonico != UneteEstadoTelefonico.VALIDADO.value
        ) {
            mostrarMensajeBloqueoPaso()
        } else {

            if (mModel?.pais in PaisUnete.paisesBuro) {
                showListaPostulante()
            } else if (mModel?.pais == Pais.MEXICO.codigoIso) {
                if (content?.esCirculoCredito() == true && !verificacionBuroMX) {
                    validacionBuroMexico()
                } else {
                    irPaso3()
                }
            } else {
                irPaso3()
            }
        }

    }

    private fun irPaso3() {
        mModel?.let { formularioView?.postulante(it) }
        formularioView?.continuar(Constant.NUMERO_TRES)
    }

    override fun notInSection() {
        mostrarMensajePorSeccion()
    }

    private fun mostrarMensajeBloqueoPaso() {
        context?.customDialog(R.layout.dialog_material) {
            tvTitle.gone()
            tvContent.text = getString(R.string.alert_paso_bloqueado)
            ivIcon.gone()

            btnOk.text = getString(R.string.actualizacion_button_ok)
            btnCancel.visible()
            btnOk.setOnClickListener {
                dismiss()
            }
        }?.show()
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
                formularioView?.finalizar()
            }
        }?.show()
    }

    override fun getModel(): PostulanteModel {
        return formularioView?.postulante() ?: PostulanteModel()
    }

    override fun getPreModel(): PrePostulanteModel? {
        return null
    }

    override fun actualizarPrimerEnvio(status: Boolean) {
        this.primerEnvio = status
    }

    override fun getConsultoraRecomienda(codigo: String) {
        presenter.obtenerNombreConsultora(codigo)
    }

    override fun expandirConsultoraRecomendante(codigoConsultoraRecomendante: String?): Boolean {
        return presenter.expandirConsultoraRecomendante(codigoConsultoraRecomendante)
    }

    override fun obtenerCodigoODocumentoConsultoraRecomendante(codigoConsultoraRecomendante: String?): String? {
        return presenter.obtenerCodigoODocumentoConsultoraRecomendante(codigoConsultoraRecomendante)
    }


    override fun showLugarNivel1(lst: List<ParametroUneteModel>) {
        content?.showLugarNivel1(lst)
    }

    override fun showLugarNivel2(lst: List<ParametroUneteModel>) {
        content?.showLugarNivel2(lst)
    }

    override fun showLugarNivel3(lst: List<ParametroUneteModel>) {
        content?.showLugarNivel3(lst)
    }

    override fun showLugarNivel4(lst: List<ParametroUneteModel>) {
        content?.showLugarNivel4(lst)
    }

    override fun showLugarNivel5(lst: List<ParametroUneteModel>) {
        content?.showLugarNivel5(lst)
    }

    override fun getLugarNivel2(id: Int) {
        presenter.lstLugarNivel2(id)
    }

    override fun getLugarNivel3(id: Int) {
        presenter.lstLugarNivel3(id)
    }

    override fun getLugarNivel4(id: Int) {
        presenter.lstLugarNivel4(id)
    }

    override fun getLugarNivel5(id: Int) {
        presenter.lstLugarNivel5(id)
    }

    override fun showTipoDireccion(lst: List<ParametroUneteModel>) {
        content?.showTipoDireccion(lst)
    }

    override fun getConfiguracion(): UneteConfiguracionModel? {
        return formularioView?.uneteConfiguracion()
    }

    override fun showValidacionCrediticia() {
        btnCancelar?.visible()
        btnContinuar?.text = getString(R.string.validar_button)
        content?.showValidacionCrediticia()
        initEstado()
    }

    override fun setRangosZonas(lst: List<ParametroUneteModel>) {
        content?.setRangosZonas(lst)
    }

    private fun showConsultoraNoAprobada() {
        val model = content?.createModel()!!
        context?.dialog1 {
            iconResId = R.drawable.ic_consultora_noaprobada
            titleTextResId = R.string.alert_noaprobada_title
            contentText = getString(R.string.alert_noaprobada_content, model.primerNombre)
            confirmButton(R.string.alert_aceptar_button) {
                if (getModel().pais == Pais.SALVADOR.codigoIso) {
                    createRechazoPagoContadoModel()
                } else {
                    presenter.updateEstadoRechazada(model.solicitudPostulanteID)
                }

                dismiss()
            }
        }?.show()
    }

    override fun validarZonaPagoDeContado(bloqueos: BuroResponse?) {
        if (getModel().pais in Pais.paisesConValidacionExternaOtroTipoRechazo
            && bloqueos?.respuestaServicio in UneteEstadoCrediticio.UneteRechazoCrediticio
        ) {
            showConsultoraNoAprobada()
        } else {
            if (formularioView?.esPagoContado() == true) showPagoContadoModal() else showConsultoraNoAprobada()
        }
    }

    private fun showPagoContadoModal() {
        context?.customDialog(R.layout.dialog_pago_contado) {
            tvTitle.text = getString(R.string.unete_paso1_valid_consultora_title)
            tvContent.text = getString(R.string.unete_paso1_consultora_contado)
            ivIcon.setImageResource(R.drawable.ic_warning_alert)
            btnOk.setText(R.string.actualizacion_button_incorporar)
            btnCancel.visible()
            btnCancel.setText(R.string.actualizacion_button_entendido)
            btnOk.setOnClickListener {
                createPagoContadoModel()
                dismiss()
            }
            btnCancel.setOnClickListener {
                createRechazoPagoContadoModel()
                dismiss()
            }
        }?.show()
    }

    private fun createPagoContadoModel() {
        mModel?.tipoPago = TipoPago.CONTADO.id
        mModel?.estadoBurocrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()

        mModel?.let {
            formularioView?.postulante(it)
            presenter.updatePostulante(it)
        }
    }

    private fun createRechazoPagoContadoModel() {
        mModel?.estadoBurocrediticio =
            UneteEstadoCrediticio.NO_PUEDE_SER_CONSULTORA.value.toString()
        mModel?.estadoPostulante = UneteEstado.RECHAZADOS.estado.toString()

        mModel?.let {
            formularioView?.postulante(it)
            presenter.updatePostulante(it)
        }
    }

    override fun showErrorConexion() {
        mModel = content?.createModel()
        if (mModel?.pais != Pais.BOLIVIA.codigoIso) {
            mModel?.estadoBurocrediticio = UneteEstadoCrediticio.SIN_CONSULTAR.value.toString()
        }
        context?.dialog1 {
            iconResId = R.drawable.ic_servicio_inactivo
            titleTextResId = R.string.alert_nointernet_title
            contentTextResId = R.string.alert_nointernet_content
            confirmButton(R.string.alert_aceptar_button) {
                verificacionBuroMX = true
                decidirActualizarOCrear()
                dismiss()
            }
        }?.show()
    }

    override fun showValidacionExitosa() {
        mModel = content?.createModel()

        context?.dialog1 {
            iconResId = R.drawable.ic_validacion_exitosa
            titleTextResId = R.string.alert_exitosa_title
            contentText = getString(R.string.alert_exitosa_content, mModel?.primerNombre)
            confirmButton(R.string.alert_continuar_button) {
                verificacionBuroMX = true
                mModel?.estadoBurocrediticio =
                    UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()
                decidirActualizarOCrear()
                dismiss()
            }
        }?.show()

    }

    override fun showValidacionPendiente() {
        mModel = content?.createModel()!!

        content?.createModel()?.let {
            context?.dialog1 {
                iconResId = R.drawable.ic_validacion_pendiente
                titleTextResId = R.string.alert_pendiente_title
                contentText = getString(R.string.alert_pendiente_content, mModel?.primerNombre)
                confirmButton(R.string.alert_continuar_button) {
                    verificacionBuroMX = true
                    decidirActualizarOCrear()
                    dismiss()
                }
            }?.show()
        }
    }

    override fun showSMSExitosoDialog() {

        context?.customDialog(R.layout.dialog_sms) {

            tvContent.text = getString(R.string.message_sms_title)
            tvSubContent.text = getString(R.string.message_sms_subtitle)

            btnOk.setText(R.string.actualizacion_button_continuar)
            btnOk.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    private fun showValidacionSMSIncompleto() {
        context?.customDialog(R.layout.dialog_material) {
            tvTitle.gone()
            tvContent.text = getString(R.string.alert_sms_not_validate)
            ivIcon.gone()

            btnOk.text = getString(R.string.actualizacion_button_continuar)
            btnOk.setOnClickListener {
                dismiss()
                validacionBloqueoPaso()
            }

            btnCancel.text = getString(R.string.actualizacion_button_cancelar)
            btnCancel.visible()
            btnCancel.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    private fun showConfirmacionCancelar() {
        context?.dialog2 {
            iconResId = R.drawable.ic_wrong
            titleTextResId = R.string.alert_cancelar_registro_title
            cancelButton(R.string.alert_cancelar_button)
            confirmButton(R.string.alert_aceptar_button) {
                dismiss()
                content?.createModel()?.let {
                    presenter.eliminarPostulante(it.solicitudPostulanteID)
                }
            }
        }?.show()
    }


    override fun saveRepuestaBloqueos(respuesta: String) {
        verificacionBuroMX = true
        content?.saveRepuestaCC(respuesta)
    }

    override fun onRegistroCancelado() {
        if (!isAdded) return
        toast(R.string.message_registro_cancelado)
        activity?.onBackPressed()
    }

    override fun onPostulanteRechazada() {
        if (!isAdded) return
        activity?.onBackPressed()
    }

    override fun initEstado() {
        when (getEstado()) {
            Estado.Edicion -> {
                content?.disable(true)
                btnContinuar?.isEnabled = true
                btnContinuar?.text = getString(R.string.save_continue)
                btnCancelar?.gone()
            }
            Estado.Visualizacion -> {
                content?.disable()
                btnContinuar?.isEnabled = false
                btnContinuar?.text = getString(R.string.save_continue)
                btnCancelar?.gone()
            }
        }
    }

    override fun mostrarPlaces(lugares: List<PlaceModel>) {
        content?.mostrarPlaces(lugares)
    }

    override fun buscarPlaces(codigoPais: String, cadenaBusqueda: String, localidad: Localidad?) {
        presenter.buscarLugares(codigoPais, cadenaBusqueda, localidad)
    }

    override fun validarPin(validarPin: ValidarPinModel) {
        if (mModel == null) mModel = content?.createModel()
        presenter.validarPin(validarPin)
    }

    override fun showValidacionPINCompleta(value: Int) {
        validacionPIN = value
        when (validacionPIN) {
            Constant.PIN_VALIDADO -> {
                updateMobileStatus(UneteEstadoTelefonico.VALIDADO.value)
            }
            Constant.PIN_NO_VALIDADO, Constant.PIN_ZERO -> showValidacionErrada()
        }
    }

    override fun deshabilitarValidarPIN() {
        mModel?.let { formularioView?.setPostulante(it) }
        content?.desactivarValidarPIN()
    }

    override fun showValidacionErrada() {
        content?.mostrarErrorPIN()
    }

    override fun showErrorEnLaValidacion() {
        content?.mostrarErrorPIN()
        context?.customDialog(R.layout.dialog_material) {
            tvTitle.gone()
            tvContent.text = getString(R.string.alert_error_validate_pin)
            ivIcon.gone()

            btnOk.text = getString(R.string.actualizacion_button_ok)
            btnCancel.visible()
            btnOk.setOnClickListener {
                dismiss()
            }

            btnCancel.text = getString(R.string.actualizacion_button_cancelar)
            btnCancel.gone()
            btnCancel.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    override fun autocompletadoDireccionNuevaExperiencia() {
        content?.showNewExperienceDireccion()
    }
    
    override fun updateCountdownSMS(time: String) {
        content?.actualizarCuentaAtras(time)
    }

    override fun onFinishCountdownSMS() {
        content?.resetTextoBotonReenviarSMS()
        presenter.validateStatePhone(mModel?.pais.toString(), mModel?.solicitudPostulanteID.toString())
    }

    override fun disableReenviarSMS() {
        content?.desactivarBotonEnviarSMS()
    }

    override fun onNotVerifiedPhoneAfterCountdown() {
        content?.activarBotonEnviarSMS()
    }

    override fun reenviarSMS() {
        presenter.validarCelularDuplicado(
            mModel?.pais.orEmpty(),
            mModel?.celular.orEmpty(),
            mModel?.tipoDocumento ?: Constant.TIPO_DOCUMENTO_DEFAULT,
            mModel?.numeroDocumento.orEmpty()
        )
    }

    override fun enviarCodigo() {
        enviarCodigoValidacionTelefonica()
    }

    override fun onGetSolicitudPostulante(t: SolicitudPostulanteEstadosResponse) {
        mModel?.apply {
            estadoTelefonico = t.estadoTelefonico ?: estadoTelefonico
            presenter.updateEstadosPostulante(this)
        }
    }

}
