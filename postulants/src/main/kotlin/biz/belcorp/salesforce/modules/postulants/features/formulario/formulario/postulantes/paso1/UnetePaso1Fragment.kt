package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso1

import android.content.Context
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CR
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.PaisUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.TipoPago
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoCrediticio
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoTelefonico
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.SexoModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso1
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.UnetePaso1Factory
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.BaseUneteFragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.BaseUneteFragment.Estado.Nuevo
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.KeyboardUtil
import biz.belcorp.salesforce.modules.postulants.utils.customDialog
import biz.belcorp.salesforce.modules.postulants.utils.solicitudRechazadaDialog
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.dialog_material.*
import kotlinx.android.synthetic.main.dialog_sms.*
import kotlinx.android.synthetic.main.dialog_sms.btnOk
import kotlinx.android.synthetic.main.dialog_sms.tvContent
import kotlinx.android.synthetic.main.fragment_unete_paso_1.*


class UnetePaso1Fragment : BaseUneteFragment(), Step, UnetePaso1View {

    private val presenter: UnetePaso1Presenter by injectFragment()

    private var content: IUnetePaso1? = null
    private var pasoBloqueado: Int = Constant.SIN_ZONA
    private var primerEnvio: Boolean = true


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateDisable()

        btnContinuar?.setOnClickListener {
            KeyboardUtil.dismissKeyboard(context, it)
            continuar()
        }

    }

    private fun validateDisable() {
        val validate = formularioView?.disableSteps() ?: false
        if (validate) content?.disable()
    }

    override fun getLayout() = R.layout.fragment_unete_paso_1

    private fun continuar() {
        if (content?.validateDocument() == true) {

            pasoBloqueado = formularioView?.getPasoBloqueado() ?: Constant.SIN_ZONA

            if (mEstado == Nuevo) {
                if (!isValid) {

                    val model = content?.createModelDocument()
                    model?.tipoPago = TipoPago.CREDITO.id
                    model?.let { formularioView?.postulante(it) }

                    validateBloqueos(
                        model?.pais.orEmpty(), model?.numeroDocumento.orEmpty(),
                        model?.tipoDocumento.orEmpty(), model?.codigoZona.orEmpty()
                    )
                } else if (content?.validate() == true) {
                    val model = evaluarRespuestaBloqueo()
                    presenter.listValidarRangoEdad(model)
                }
            } else {
                content?.createModel()?.let {
                    presenter.updatePostulante(it)
                }
            }


        }
    }

    override fun validarDatos() {
        val model = evaluarRespuestaBloqueo()
        if (elCorreoEsValido()) {
            presenter.validarMail(
                model.pais.orEmpty(),
                model.correoElectronico.orEmpty(),
                model.numeroDocumento.orEmpty()
            )
        } else {
            presenter.validarDocumento(model.numeroDocumento)
        }
    }

    override fun getCodigoRol() = presenter.sesion.codigoRol

    override fun getZona() = presenter.sesion.zona

    override fun getSeccion() = presenter.sesion.seccion

    override fun documentoNoDuplicado() {
        decidirSiGuardarOContinuar()
    }

    override fun mostrarPrimerNombre(primerNombre: String) {
        content?.showPrimerNombre(primerNombre)
    }

    override fun mostrarSegundoNombre(segundoNombre: String) {
        content?.showSegundoNombre(segundoNombre)
    }

    override fun mostrarPrimerApellido(primerApellido: String) {
        content?.showPrimerApellido(primerApellido)
    }

    override fun mostrarSegundoApellido(segundoApellido: String) {
        content?.showSegundoApellido(segundoApellido)
    }

    override fun mostrarfechaNacimiento(fechaNacimiento: String) {
        content?.mostrarFechaNacimiento(fechaNacimiento)
    }

    override fun showValidMail() {
        val model = evaluarRespuestaBloqueo()
        if (model.celular != null && validarCelular()) {
            presenter.validarCelular(
                model.pais.orEmpty(),
                model.celular.orEmpty(),
                model.tipoDocumento ?: Constant.TIPO_DOCUMENTO_DEFAULT,
                model.numeroDocumento.orEmpty()
            )
        } else {
            decidirSiGuardarOContinuar()
        }
    }

    override fun showValidCelular() {
        decidirSiGuardarOContinuar()
    }

    override fun showDuplicateDocumentNumber(numeroDocumento: String?) {
        context?.customDialog(R.layout.dialog_material) {
            tvTitle.setText(R.string.documento_duplicado)
            tvContent.text = getString(R.string.documento_duplicado_mensaje, numeroDocumento)
            ivIcon.setImageResource(R.drawable.ic_warning_alert)
            btnOk.setText(R.string.actualizacion_button_aceptar)
            btnOk.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    override fun showDuplicateMail() {
        content?.showDuplicateMailError(getString(R.string.duplicate_mail))
    }

    override fun showDuplicateCelular() {
        content?.showDuplicateCelularError(getString(R.string.duplicate_celular))
    }

    private fun decidirSiGuardarOContinuar() {
        val model = evaluarRespuestaBloqueo()
        if (model.pais in PaisUnete.paisesCam || model.pais == Pais.COLOMBIA.codigoIso) {
            nextStep()
        } else {
            crearPostulante()
        }
    }

    private fun crearPostulante() {
        presenter.createPostulante(evaluarRespuestaBloqueo())
    }

    private fun evaluarRespuestaBloqueo(): PostulanteModel {

        content?.createModel()?.let { mModel = it }
        mModel?.usuarioCreacion = presenter.sesion.username

        mModel?.tipoSolicitud = mModel?.bloqueos?.tipoSolicitud
        mModel?.requiereAprobacionSAC = mModel?.bloqueos?.requiereAprobacionSAC ?: false

        mModel?.estadoBurocrediticio = mModel?.bloqueos?.enumEstadoCrediticioID.toString()

        if (mModel?.pais == Pais.PERU.codigoIso) {
            if ((mModel?.edad ?: 0) in 18..25) {
                mModel?.requiereAprobacionSAC = true
            }
        } else if (mModel?.pais in listOf(Pais.ECUADOR.codigoIso, Pais.PUERTORICO.codigoIso)) {
            mModel?.estadoBurocrediticio =
                UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()
        }

        when (mModel?.pais) {
            Pais.COSTARICA.codigoIso -> if (presenter.validacionEdadSac) {
                mModel?.requiereAprobacionSAC = true
            }
            Pais.GUATEMALA.codigoIso -> {
                mModel?.requiereAprobacionSAC = true
            }
        }

        return mModel!!
    }

    override fun onResume() {
        super.onResume()
        content?.showBody(!isStepZero())
        content?.showContactPostulant()
    }

    override fun paso() = Constant.NUMERO_UNO

    override fun initView() {

        presenter.setView(this)
        val vw =
            UnetePaso1Factory.getView(activity as Context, formularioView?.pais(), this, mEstado)
        content = vw as IUnetePaso1
        lyContainer?.removeAllViews()
        lyContainer?.addView(vw)

    }

    override fun onSelected() = Unit

    override fun verifyStep(): VerificationError? {
        return if (!content?.validate()!!) {
            VerificationError("Complete los datos")
        } else {
            if (getEstado() == Nuevo) {
                toast(R.string.unete_step_no_guardado)
                VerificationError("Complete los datos")
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

    override fun created(postulanteModel: PostulanteModel) {
        mModel = postulanteModel
        formularioView?.setPostulante(postulanteModel)
        if (postulanteModel.pais in PaisUnete.paisesCam &&
            postulanteModel.edad >= CR.EDAD_MAX
        ) {
            successCreation(R.string.unete_postulante_supera_edad)
        } else {
            successCreation(R.string.unete_postulante_registrada_text)
        }
        content?.disable()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    private fun successCreation(bodyMessage: Int) {
        context?.customDialog(R.layout.dialog_material) {
            tvTitle.setText(R.string.unete_postulante_registrada_title)
            tvContent.text = getString(bodyMessage)
            ivIcon.setImageResource(R.drawable.ic_checkmark_alert)
            btnOk.setText(R.string.actualizacion_button_continuar)
            btnOk.setOnClickListener {
                dismiss()
                decidirEnviarSMS()
            }
        }?.show()
    }

    private fun decidirEnviarSMS() {
        when (mModel?.pais) {
            Pais.MEXICO.codigoIso -> enviarMensajeValidacionSMS()
            else -> nextStep()
        }
    }

    private fun enviarMensajeValidacionSMS() {
        if (primerEnvio && pasoBloqueado != Constant.SIN_ZONA) {
            primerEnvio = false
            actualizarEstadoTelefonico(UneteEstadoTelefonico.POR_VALIDAR.value)
        } else {
            nextStep()
        }
    }

    override fun actualizarEstadoTelefonico(status: Int) {
        mModel?.estadoTelefonico = status
        mModel?.let { presenter.actualizarEstadoTelefonico(it) }
    }

    override fun nextStep() {
        mModel?.let { formularioView?.postulante(it) }
        formularioView?.continuar(Constant.NUMERO_DOS)
    }

    override fun showErrorConnectionMessageSV() {
        context?.customDialog(R.layout.dialog_material) {
            ivIcon.visibility = View.GONE
            tvTitle.setText(R.string.documento_duplicado)
            tvContent.text = context.getString(R.string.error_conexion_reintentar)
            btnOk.setText(R.string.accept)
            btnOk.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    override fun validarZonaPagoDeContado(bloqueos: BuroResponse) {
        if (getModel().pais in Pais.paisesConValidacionExternaOtroTipoRechazo
            && bloqueos.respuestaServicio in UneteEstadoCrediticio.UneteRechazoCrediticio
        ) {
            presenter.showBloqueoExternoDialog()
        } else {
            if (formularioView?.esPagoContado() == true) showPagoContadoModal(bloqueos) else presenter.showBloqueoExternoDialog()
        }

    }

    override fun createModel(buro: BuroResponse): PostulanteModel {

        val p = getModel()

        p.bloqueos = buro
        p.paso = Constant.NUMERO_UNO
        p.tipoSolicitud = buro.tipoSolicitud
        p.offline = buro.offline
        p.estadoBurocrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.toString()
        formularioView?.postulante(p)

        return p
    }

    override fun showBody(isValid: Boolean) {
        this.isValid = isValid

        if (formularioView?.pais() == Pais.MEXICO.codigoIso || formularioView?.pais() == Pais.SALVADOR.codigoIso) {
            if (isValid) {

                content?.createModel()?.let {
                    mModel = it
                    if (elCorreoEsValido()) {
                        presenter.validarMail(
                            mModel?.pais.orEmpty(),
                            mModel?.correoElectronico.orEmpty(),
                            mModel?.numeroDocumento.orEmpty()
                        )
                    } else {
                        crearPostulante()
                    }
                }

            }
        } else {
            content?.showModal(isValid)
        }
    }

    override fun showBloqueos(bloqueos: BuroResponse?) {
        content?.showBloqueos(bloqueos)
    }

    private fun elCorreoEsValido() = validarMail()

    override fun resetView() {
        val p = getModel()
        p.bloqueos = null
        p.paso = Constant.NUMERO_CERO
        formularioView?.postulante(p)
        showBody(false)
    }

    override fun initModel() {
        presenter.listSexo()
        presenter.listTipoDocumento()
        presenter.listRegimenContable()
        presenter.listNacionalidad()

        presenter.validarCorreoObligatorio(
            presenter.sesion.zona?.trim() + (presenter.sesion.seccion
                ?: Constant.EMPTY_STRING).trim()
        )
    }

    override fun getModel(): PostulanteModel {
        return formularioView?.postulante() ?: PostulanteModel()
    }

    override fun showTipoDocumento(model: List<ParametroUneteModel>) {
        content?.showTipoDocumento(model)
    }

    override fun showRegimenContable(model: List<ParametroUneteModel>) {
        content?.showRegimenContable(model)
    }

    override fun showGeneros(model: List<SexoModel>) {
        content?.showGeneros(model)
    }

    override fun showModal(title: String, msg: String) {
        context?.customDialog(R.layout.dialog_material) {
            tvTitle?.text = title
            tvContent?.text = msg
            ivIcon?.setImageResource(R.drawable.ic_warning_alert)
            btnOk?.setText(R.string.actualizacion_button_aceptar)
            btnOk?.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    private fun showPagoContadoModal(bloqueos: BuroResponse) {
        var resourceMessage = R.string.unete_paso1_consultora_contado

        if (Constant.NEW_MESSAGE_CREDIT_EVALUATION.contains(presenter.sesion.countryIso.toUpperCase())) {

            resourceMessage = if (content?.createModelDocument()?.pais == "PE") {
                R.string.unete_paso1_consultora_contado_new_only_pe
            } else {
                R.string.unete_paso1_consultora_contado_new
            }
        }

        context?.customDialog(R.layout.dialog_pago_contado) {
            tvTitle.text = getString(R.string.unete_paso1_valid_consultora_title)
            tvContent.text = getString(resourceMessage)
            ivIcon.setImageResource(R.drawable.ic_warning_alert)
            btnOk.setText(R.string.actualizacion_button_incorporar)
            btnCancel.visibility = View.VISIBLE
            btnCancel.setText(R.string.actualizacion_button_entendido)
            btnOk.setOnClickListener {
                dismiss()
                createPagoContadoModel(bloqueos)
            }
            btnCancel.setOnClickListener {
                formularioView?.finalizar()
            }
        }?.show()
    }

    private fun createPagoContadoModel(bloqueos: BuroResponse) {
        val model = getModel()
        model.tipoPago = TipoPago.CONTADO.id
        model.estadoBurocrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.toString()
        bloqueos.enumEstadoCrediticioID = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value
        formularioView?.postulante(model)
        createModel(bloqueos)
        showBody(true)
    }


    override fun showSMSExitosoDialog() {

        context?.customDialog(R.layout.dialog_sms) {

            tvContent.text = getString(R.string.message_sms_title)
            tvSubContent.text = getString(R.string.message_sms_subtitle)

            btnOk.setText(R.string.actualizacion_button_continuar)
            btnOk.setOnClickListener {
                dismiss()
                nextStep()
            }
        }?.show()
    }

    override fun setModel(p: PostulanteModel) {
        formularioView?.postulante(p)
    }

    override fun showLoading(msg: String) {
        formularioView?.showLoading()
    }

    override fun showNacionalidad(model: List<TablaLogicaModel>) {
        content?.showNacionalidad(model)
    }

    override fun initEstado() {
        when (getEstado()) {
            Estado.Edicion -> {
                content?.disable(true)
                btnContinuar?.isEnabled = true
            }
            Estado.Visualizacion -> {
                content?.disable()
                btnContinuar?.isEnabled = false
            }
        }
    }

    override fun validateBloqueos(
        pais: String, documento: String, tipoDocumento: String, zona: String
    ) {
        content?.createModel().also { m ->
            if (m != null) {
                m.usuarioCreacion = presenter.sesion.username
                presenter.validarBloqueos(pais, documento, tipoDocumento, zona, m)
            }
        }
    }

    override fun isStepZero() = getModel().paso == Constant.NUMERO_CERO

    override fun setCorreoObligatorio(esCorreoObligatorio: Boolean) {
        content?.setCorreoObligatorio(esCorreoObligatorio)
    }
}
