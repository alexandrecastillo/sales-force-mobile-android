package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pa

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.text.InputType.TYPE_CLASS_TEXT
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.PA
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.SexoModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso1
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso1.UnetePaso1View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.BaseInput
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMAIL
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.Constant.providersEmail
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.calcularEdad
import biz.belcorp.salesforce.modules.postulants.utils.onEnterKeyListener
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import kotlinx.android.synthetic.main.unete_pa_paso_1.view.*
import java.util.*

@SuppressLint("ViewConstructor")
class UnetePaso1(mContext: Context, view: UnetePaso1View) :
    BaseUnetePaso<UnetePaso1View>(mContext, view), IUnetePaso1 {

    private var isDocumentValid: Boolean = false
    private var ultimoDocumento: String? = null

    override fun initEvents() {

        spnTipoDocumento?.onItemSelected {
            if (view.isStepZero()) {
                txtDocumento?.setText(Constant.EMPTY_STRING)
            }
            documentValidation()
        }

        txtDocumento?.onEnterKeyListener {
            if (txtDocumento.validate()) {

                val p = createModelDocument()

                view.setModel(p)

                view.validateBloqueos(
                    p.pais.orEmpty(), p.numeroDocumento.orEmpty(), p.tipoDocumento.orEmpty()
                )

                true
            } else {
                false
            }
        }

        txtDocumento?.onTextChanged {
            if (ultimoDocumento != null
                && it.toString() != ultimoDocumento
            ) {
                ultimoDocumento = null
                view.resetView()
                resetInputs()
            }
        }

        txtCorreo?.setListFiltrer(providersEmail)
        txtCorreoRepeat?.setListFiltrer(providersEmail)

    }

    override fun getLayout() = R.layout.unete_pa_paso_1


    override fun createModelDocument(): PostulanteModel {

        val model = view.getModel()

        model.pais = Pais.PANAMA.codigoIso
        model.paisID = Pais.PANAMA.id

        val tipoDocumentoParametro = spnTipoDocumento?.selected<ParametroUneteModel>()

        model.tipoDocumento = tipoDocumentoParametro?.valor
        model.numeroDocumento = txtDocumento?.getText()?.trim()

        ultimoDocumento = txtDocumento.getText()

        return model
    }

    override fun showTipoDocumento(model: List<ParametroUneteModel>) {
        spnTipoDocumento?.load(model)
        val selected = view.getModel().tipoDocumento
        spnTipoDocumento?.selection<ParametroUneteModel> { it?.valor == selected }
    }

    override fun showGeneros(model: List<SexoModel>) {
        spnGenero?.load(model)
        val selected = view.getModel().sexo
        spnGenero?.selection<SexoModel> { it?.codigo == selected }
    }

    override fun createModel(): PostulanteModel {

        val model = createModelDocument()

        if (model.solicitudPostulanteID <= Constant.NUMERO_CERO) {

            model.codigoZona = view.getZona()
            model.codigoSeccion = view.getSeccion()

            model.pais = Pais.PANAMA.codigoIso
            model.paisID = Pais.PANAMA.id

            model.fuenteIngreso = UneteAlgorithms.buildFuenteIngreso(view.getCodigoRol())

            model.tipoContacto = UneteTipoContacto.MOVIL.value.toString()
            model.estadoGEO = UneteEstadoGEO.SIN_CONSULTAR.value.toString()
            model.estadoBurocrediticio = UneteEstadoCrediticio.SIN_CONSULTAR.value.toString()

            model.estadoTelefonico = UneteEstadoTelefonico.NO_REQUERIDO.value
            model.indicadorActivo = "true"

        }

        model.primerNombre = txtPrimerNombre?.getText()
        model.segundoNombre = txtSegundoNombre?.getText()
        model.apellidoPaterno = txtApellidoPaterno?.getText()
        model.apellidoMaterno = txtApellidoMaterno?.getText()

        val fechaNacimiento = txtFechaNacimiento?.getText()?.toDate(formatShort2)
        model.fechaNacimiento = fechaNacimiento?.string(formatLongT)
        model.edad = fechaNacimiento?.calcularEdad() ?: 0

        model.correoElectronico = txtCorreo?.getText()
        model.sexo = spnGenero?.selected<SexoModel>()?.codigo
        model.estadoPostulante = UneteEstado.GESTION_SAC.estado.toString()
        model.paso = Constant.NUMERO_UNO

        model.sincronizado = false

        return model
    }

    override fun loadModel() {

        val model = view.getModel()

        txtDocumento?.setText(model.numeroDocumento)
        txtPrimerNombre?.setText(model.primerNombre)
        txtSegundoNombre?.setText(model.segundoNombre)
        txtApellidoPaterno?.setText(model.apellidoPaterno)
        txtApellidoMaterno?.setText(model.apellidoMaterno)
        txtCorreo?.setText(model.correoElectronico)
        txtCorreoRepeat?.setText(model.correoElectronico)

        val fechaNacimiento = model.fechaNacimiento
            ?.changeDateFormat(formatLongT, formatShort2)

        txtFechaNacimiento?.setText(fechaNacimiento)

    }

    override fun showModal(isValid: Boolean) {
        if (isValid) {
            txtDocumento?.setEnable(false)
            spnTipoDocumento?.isEnabled = false
            tvDocumentLabel.gone()
            lyBody?.visible()
            isDocumentValid = true
        } else {
            spnTipoDocumento?.isEnabled = true
            txtDocumento?.setEnable(true)
            lyBody?.gone()
            isDocumentValid = false
        }
    }

    override fun showBody(isBodyVisible: Boolean) {
        if (isBodyVisible) {
            txtDocumento?.setEnable(false)
            lyBody?.visible()
            isDocumentValid = true
        } else {
            txtDocumento?.setEnable(true)
            lyBody?.gone()
            isDocumentValid = false
        }
    }

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += txtDocumento?.validate()
        v += txtPrimerNombre?.validate()
        v += txtSegundoNombre?.validate()
        v += txtApellidoPaterno?.validate()
        v += txtApellidoMaterno?.validate()
        v += txtFechaNacimiento?.validate()
        v += txtCorreo?.validate()
        v += txtCorreoRepeat?.validateRepeatEmail(txtCorreo?.getText().toString())

        return v.all { it ?: false }
    }

    override fun validateDocument(): Boolean {
        return txtDocumento?.validate() ?: false
    }

    override fun initRequired() {
        txtPrimerNombre?.setRequired(true)
        txtSegundoNombre?.setRequired(false)
        txtApellidoPaterno?.setRequired(true)
        txtApellidoMaterno?.setRequired(false)
        txtDocumento?.setRequired(true)
        txtFechaNacimiento?.setRequired(true)
        txtCorreo?.setIsRequired(true)
        txtCorreoRepeat?.setIsRequired(true)
        spnGenero?.required = true
    }

    override fun initRestriction() {

        txtPrimerNombre?.setR(Filters.filterLetters(PA.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtSegundoNombre?.setR(Filters.filterLetters(PA.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtApellidoMaterno?.setR(Filters.filterLetters(PA.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtApellidoPaterno?.setR(Filters.filterLetters(PA.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtDocumento?.setR(
            arrayOf(
                InputFilter.AllCaps(),
                Filters.filterMaxLength(PA.DOCUMENTO_IDENTIDAD_MAX)
            )
        )
        txtCorreo?.setR(Filters.filterMaxLength(PA.CORREO_MAX_LENGHT))
        txtCorreoRepeat?.setR(Filters.filterMaxLength(PA.CORREO_MAX_LENGHT))

    }

    override fun initValidation() {
        txtPrimerNombre?.addV(V(context, EMPTY))
        txtApellidoPaterno?.addV(V(context, EMPTY))
        txtApellidoMaterno?.addV(V(context, EMPTY))
        txtFechaNacimiento?.addV(V(context, EMPTY))

        txtDocumento?.v(
            V(context, EMPTY, R.string.unete_valid_obligatorio),
            V(
                context,
                MIN_LENGTH,
                PA.DOCUMENTO_IDENTIDAD_MIN,
                R.string.unete_paso1_valid_documento_min_length
            ),
            fieldDocumentoValid()
        )
        txtDocumento?.inputType(TYPE_CLASS_TEXT)

        txtCorreo?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_correo_empty),
            V(context, EMAIL, R.string.unete_paso1_valid_correo_format)
        )
        txtCorreoRepeat?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_correo_empty),
            V(context, EMAIL, R.string.unete_paso1_valid_correo_format)
        )

        txtFechaNacimiento?.minDate = Calendar.getInstance().minusYears(PA.EDAD_MAX)
        txtFechaNacimiento?.maxDate = Calendar.getInstance().minusYears(PA.EDAD_MIN)

        documentValidation()
    }

    override fun showDuplicateMailError(message: String) {
        txtCorreo?.setEstado(BaseInput.ERROR, message)
        txtCorreoRepeat?.setEstado(BaseInput.ERROR, message)
    }

    private fun documentValidation() {

        val tipoDocumento = spnTipoDocumento?.selected<ParametroUneteModel>()?.idParametroUnete
        tvDocumentLabel?.visibility = View.GONE

        when (tipoDocumento) {
            UneteTipoDocumento.PA_CEDULA_IDENTIDAD.tipo -> {
                txtDocumento?.setRestriction(Filters.filterAlphanumericHyphen(12))
                txtDocumento?.inputType(TYPE_CLASS_TEXT)
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_valid_obligatorio),
                    V(context, MIN_LENGTH, 5, R.string.unete_paso1_valid_documento_min_length)
                )
                tvDocumentLabel?.visibility = View.VISIBLE
            }
            UneteTipoDocumento.PA_CARNE_PERMISO_TEMPORAL.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_TEXT)
                txtDocumento?.setRestriction(Filters.filterAlphanumericHyphen(13))
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(context, MIN_LENGTH, 5, R.string.unete_paso1_valid_documento_min_length)
                )
            }
            UneteTipoDocumento.PA_CARNE_RESIDENTE_PROVISIONAL.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_TEXT)
                txtDocumento?.setRestriction(Filters.filterAlphanumericHyphen(15))
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(context, MIN_LENGTH, 5, R.string.unete_paso1_valid_documento_min_length)
                )

            }
            UneteTipoDocumento.PA_CARNE_RESIDENTE_PERMANENTE.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_TEXT)
                txtDocumento?.setRestriction(Filters.filterAlphanumericHyphen(9))
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(context, MIN_LENGTH, 5, R.string.unete_paso1_valid_documento_min_length),
                    fieldDocumentoValid()
                )

            }
            UneteTipoDocumento.PA_PASAPORTE.tipo,
            UneteTipoDocumento.PA_DOC_VARIOS_OTROS.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_TEXT)
                txtDocumento?.setRestriction(Filters.filterAlphanumericHyphen(13))
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(context, MIN_LENGTH, 5, R.string.unete_paso1_valid_documento_min_length),
                    fieldDocumentoValid()
                )

            }
        }

    }


    override fun getDisables(enEdicion: Boolean): MutableList<View> {
        val lst = mutableListOf<View>(txtDocumento, spnGenero)

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    txtPrimerNombre,
                    txtSegundoNombre,
                    txtApellidoPaterno,
                    txtApellidoMaterno,
                    txtFechaNacimiento,
                    txtCorreo,
                    txtCorreoRepeat
                )
            )
        }

        return lst
    }

    private fun fieldDocumentoValid(): V {
        val a = object : Validation.Companion.ValidCommand {
            override fun execute(): Boolean {
                return UAlgorithms.validaDocumentoPA(txtDocumento?.getText().orEmpty())
            }
        }
        return V(context, Validation.CALLABLE, a, R.string.unete_valid_formato_invalido)
    }

    private fun resetInputs() {
        resetValidations()
        txtPrimerNombre?.setText(Constant.EMPTY_STRING)
        txtSegundoNombre?.setText(Constant.EMPTY_STRING)
        txtApellidoPaterno?.setText(Constant.EMPTY_STRING)
        txtApellidoMaterno?.setText(Constant.EMPTY_STRING)
        txtFechaNacimiento?.setText(Constant.EMPTY_STRING)
        txtCorreo?.setText(Constant.EMPTY_STRING)
        txtCorreoRepeat?.setText(Constant.EMPTY_STRING)
        initValidation()
        enableInputs()
    }

    private fun enableInputs() {
        txtPrimerNombre?.setEnable(true)
        txtSegundoNombre?.setEnable(true)
        txtApellidoPaterno?.setEnable(true)
        txtApellidoMaterno?.setEnable(true)
    }

    private fun resetValidations() {
        txtPrimerNombre?.resetV()
        txtSegundoNombre?.resetV()
        txtApellidoPaterno?.resetV()
        txtApellidoMaterno?.resetV()
        txtFechaNacimiento?.resetV()
        txtCorreo?.resetV()
        txtCorreoRepeat?.resetV()
    }

}
