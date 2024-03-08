package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.paso1

import android.content.Context
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.isZero
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.PaisUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoCrediticio
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.SexoModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso1
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.UnetePaso1Factory
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.BaseUneteFragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.BaseUnetePreFragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso1.UnetePaso1View
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.KeyboardUtil
import biz.belcorp.salesforce.modules.postulants.utils.customDialog
import biz.belcorp.salesforce.modules.postulants.utils.solicitudRechazadaDialog
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.dialog_material.*
import kotlinx.android.synthetic.main.fragment_unete_paso_1.*

class UnetePrePaso1Fragment : BaseUnetePreFragment(), Step, UnetePaso1View {

    private var content: IUnetePaso1? = null
    private val presenter: UnetePrePaso1Presenter by injectFragment()

    override fun getLayout() = R.layout.fragment_unete_paso_1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.obtenerCampanaActual()

        btnContinuar?.setOnClickListener {
            KeyboardUtil.dismissKeyboard(context, it)
            continuar()
        }

    }

    override fun onResume() {
        super.onResume()
        content?.showBody(isPreInscritaCreated())
        content?.showContactPostulant()
    }

    private fun continuar() {
        if (content?.validateDocument() == true) {
            if (!isValid) {

                content?.let {

                    val model = it.createModelPreDocument()

                    formularioPreView?.prePostulante(model)

                    validateBloqueos(
                        model.pais.orEmpty(),
                        model.numeroDocumento.orEmpty(),
                        model.tipoDocumento.orEmpty(),
                        model.codigoZona.orEmpty()
                    )

                }

            } else if (elFormularioEsValido()) {
                presenter.validarDatos()
            }

        }
    }

    private fun elFormularioEsValido() = content?.validate() == true

    override fun validateBloqueos(
        pais: String, documento: String, tipoDocumento: String, zona: String
    ) {
        presenter.validarBloqueos(pais, documento, tipoDocumento, zona)
    }

    override fun initEstado() {
        when (getEstado()) {
            BaseUneteFragment.Estado.Edicion -> {
                content?.disable(true)
                btnContinuar?.isEnabled = true
            }
            BaseUneteFragment.Estado.Visualizacion -> {
                content?.disable()
                btnContinuar?.isEnabled = false
            }
        }
    }

    override fun initView() {
        presenter.setView(this)
        val vw =
            UnetePaso1Factory.getView(activity as Context, formularioPreView?.pais(), this, mEstado)
        content = vw as IUnetePaso1
        lyContainer?.removeAllViews()
        lyContainer?.addView(vw)
    }

    override fun initModel() {
        presenter.listSexo()
        presenter.listTipoDocumento()
        presenter.listRegimenContable()
        presenter.listNacionalidad()
    }

    override fun paso() = Constant.NUMERO_UNO

    override fun onSelected() = Unit

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

    override fun showNacionalidad(model: List<TablaLogicaModel>) {
        content?.showNacionalidad(model)
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
            tvTitle.text = title
            tvContent.text = msg
            ivIcon.setImageResource(R.drawable.ic_warning_alert)
            btnOk.setText(R.string.actualizacion_button_aceptar)
            btnOk.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    override fun showLoading(msg: String) {
        formularioPreView?.showLoading()
    }

    override fun showBody(isValid: Boolean) {
        this.isValid = isValid
        content?.showModal(isValid)
    }

    override fun isStepZero(): Boolean {
        return getPreModel().paso == Constant.NUMERO_CERO
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

    override fun showValidMail() {
        decidirSiGuardarOContinuar()
    }

    override fun showDuplicateCelular() {
        content?.showDuplicateCelularError(getString(R.string.duplicate_celular))
    }

    override fun documentoNoDuplicado() {
        decidirSiGuardarOContinuar()
    }

    override fun showValidCelular() {
        decidirSiGuardarOContinuar()
    }

    private fun decidirSiGuardarOContinuar() {
        content?.createModelPreDocument()?.let {
            if (it.pais in PaisUnete.paisesCam || it.pais == Pais.COLOMBIA.codigoIso) {
                nextStep()
            } else {
                content?.createPreModel()?.let { preModel ->
                    presenter.updatePrePostulante(prePostulante = preModel)
                }
            }
        }
    }

    override fun getCodigoRol(): String {
        return presenter.recuperarRol()
    }

    override fun getZona(): String? {
        return presenter.recuperarZona()
    }

    override fun getSeccion(): String? {
        return presenter.recuperarSeccion()
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

    override fun resetView() {
        val p = getPreModel()
        p.bloqueos = null
        p.paso = Constant.NUMERO_CERO
        formularioPreView?.prePostulante(p)
        showBody(false)
    }

    override fun validarDatos() {
        content?.createPreModel()?.let {
            val model = it
            if (validarMail()) {
                presenter.validarMail(
                    model.pais.orEmpty(),
                    model.correoElectronico.orEmpty(),
                    model.numeroDocumento.orEmpty()
                )
            } else {
                presenter.validarDocumento(model.numeroDocumento)
            }
        }
    }

    override fun nextStep() {
        mPreModel?.let { formularioPreView?.prePostulante(it) }
        formularioPreView?.continuar(Constant.NUMERO_DOS)
    }

    override fun created(prePostulanteModel: PrePostulanteModel) {
        mPreModel = prePostulanteModel
        formularioPreView?.setPrePostulante(prePostulanteModel)
        successCreation()
        content?.disable()
    }


    private fun successCreation() {
        siguientePaso()
    }

    private fun siguientePaso() {
        nextStep()
    }

    override fun getPreModel(): PrePostulanteModel {
        return formularioPreView?.prePostulante() ?: PrePostulanteModel()
    }

    override fun setModel(p: PrePostulanteModel) {
        formularioPreView?.prePostulante(p)
    }

    override fun createPreModel(buro: BuroResponse): PrePostulanteModel {
        val p = getPreModel()

        p.bloqueos = buro
        p.paso = Constant.NUMERO_UNO
        p.offline = buro.offline
        p.tipoSolicitud = buro.tipoSolicitud
        if (buro.bloqueosInternos == false && buro.estadoCrediticio == true) {
            p.estadoBurocrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()
        }
        p.campaniaID = presenter.campanaActual
        formularioPreView?.prePostulante(p)
        return p
    }

    override fun verifyStep(): VerificationError? {
        return if (content?.validate() != true) {
            VerificationError(resources.getString(R.string.tx_complete_los_datos))
        } else {
            if (getEstado() == BaseUneteFragment.Estado.Nuevo) {
                toast(R.string.unete_step_no_guardado)
                VerificationError(resources.getString(R.string.tx_complete_los_datos))
            } else null
        }
    }

    override fun onError(error: VerificationError) = Unit

    private fun isPreInscritaCreated() = !getPreModel().solicitudPrePostulanteID.isZero()
}
