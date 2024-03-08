package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pe

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_CLASS_TEXT
import android.view.View
import android.widget.Toast
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.PE
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
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.RUC
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.calcularEdad
import biz.belcorp.salesforce.modules.postulants.utils.onEnterKeyListener
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import kotlinx.android.synthetic.main.unete_pe_paso_1.view.*
import java.util.*


@SuppressLint("ViewConstructor")
class UnetePaso1(mContext: Context, view: UnetePaso1View) :
    BaseUnetePaso<UnetePaso1View>(mContext, view), IUnetePaso1 {

    private var ultimoDocumento: String? = null
    private var isCorreoObligatorio: Boolean = false

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
                    p.pais.orEmpty(), p.numeroDocumento.orEmpty(), p.tipoDocumento.orEmpty(),
                    view.getZona().orEmpty()
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

    override fun getLayout() = R.layout.unete_pe_paso_1


    override fun showModal(isValid: Boolean) {
        if (isValid) {
            spnTipoDocumento?.isEnabled = false
            lyBody?.visible()
        } else {
            spnTipoDocumento?.isEnabled = true
            lyBody?.gone()
        }
    }

    override fun createModelDocument(): PostulanteModel {

        val model = view.getModel()

        model.pais = Pais.PERU.codigoIso
        model.paisID = Pais.PERU.id

        if (model.codigoZona.isNullOrEmpty()) {
            model.codigoZona = view.getZona()
        }

        val tipoDocumentoParametro = spnTipoDocumento?.selected<ParametroUneteModel>()

        model.tipoDocumento = tipoDocumentoParametro?.valor

        if (tipoDocumentoParametro?.idParametroUnete == UneteTipoDocumento.CE.tipo) {
            model.numeroDocumento = txtDocumento?.getText()?.padStart(10, '0')?.trim()
        } else {
            model.numeroDocumento = txtDocumento?.getText()?.trim()
        }

        ultimoDocumento = txtDocumento.getText()

        return model
    }

    override fun showPrimerNombre(primerNombre: String) {
        txtPrimerNombre?.setText(primerNombre)
        txtPrimerNombre?.setEnable(false)
    }

    override fun showSegundoNombre(segundoNombre: String) {
        txtSegundoNombre?.setText(segundoNombre)
        txtSegundoNombre?.setEnable(false)
    }

    override fun showPrimerApellido(primerApellido: String) {
        txtApellidoPaterno?.setText(primerApellido)
        txtApellidoPaterno?.setEnable(false)
    }

    override fun showSegundoApellido(segundoApellido: String) {
        txtApellidoMaterno?.setText(segundoApellido)
        txtApellidoMaterno?.setEnable(false)
    }

    override fun showTipoDocumento(model: List<ParametroUneteModel>) {
        spnTipoDocumento?.load(model)
        if (view.isStepZero() && view.getModel().tipoDocumento == null) {
            spnTipoDocumento?.selection<ParametroUneteModel> { it?.valor == Constant.NUMERO_UNO.toString() }
        } else {
            val selected = view.getModel().tipoDocumento
            spnTipoDocumento?.selection<ParametroUneteModel> { it?.valor == selected }
        }
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

            model.pais = Pais.PERU.codigoIso
            model.paisID = Pais.PERU.id

            model.fuenteIngreso = UneteAlgorithms.buildFuenteIngreso(view.getCodigoRol())

            model.tipoContacto = UneteTipoContacto.MOVIL.value.toString()
            model.estadoGEO = UneteEstadoGEO.SIN_CONSULTAR.value.toString()
            model.estadoBurocrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()

            model.estadoTelefonico = UneteEstadoTelefonico.NO_REQUERIDO.value
            model.indicadorActivo = "true"

        }

        val sexoCodigo = spnGenero?.selected<SexoModel>()?.codigo

        model.primerNombre = txtPrimerNombre?.getText()
        model.segundoNombre = txtSegundoNombre?.getText()
        model.apellidoPaterno = txtApellidoPaterno?.getText()
        model.apellidoMaterno = txtApellidoMaterno?.getText()

        val fechaNacimiento = txtFechaNacimiento?.getText()?.toDate(formatShort2)
        model.fechaNacimiento = fechaNacimiento?.string(formatLongT)
        model.edad = fechaNacimiento?.calcularEdad() ?: Constant.NUMERO_CERO

        model.correoElectronico = txtCorreo?.getText()
        model.sexo = sexoCodigo
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

    override fun showBody(isBodyVisible: Boolean) {
        if (isBodyVisible)
            lyBody?.visible()
        else
            lyBody?.gone()
    }

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += txtDocumento?.validate()
        v += txtPrimerNombre?.validate()
        v += txtApellidoPaterno?.validate()
        v += txtApellidoMaterno?.validate()
        v += txtFechaNacimiento?.validate()
        v += txtCorreo?.validate()
        v += txtCorreoRepeat?.validateRepeatEmail(txtCorreo?.getText().toString())

        return v.all { it ?: false }
    }

    override fun validateDocument() = txtDocumento?.validate() ?: false

    override fun setCorreoObligatorio(esCorreoObligatorio: Boolean) {
        isCorreoObligatorio = esCorreoObligatorio
        initRequired()
    }

    override fun initRequired() {
        txtDocumento?.setRequired(true)
        txtPrimerNombre?.setRequired(true)
        txtApellidoMaterno?.setRequired(true)
        txtApellidoPaterno?.setRequired(true)
        txtFechaNacimiento?.setRequired(true)
        txtCorreo?.setIsRequired(isCorreoObligatorio)
        txtCorreoRepeat?.setIsRequired(isCorreoObligatorio)
        spnGenero?.required = true
    }

    override fun initRestriction() {

        txtDocumento?.setR(
            arrayOf(
                Filters.filterMaxLength(PE.DOCUMENTO_MAX_LENGHT),
                InputFilter.AllCaps()
            )
        )
        txtPrimerNombre?.setR(Filters.filterLetters(PE.NOMBRES_MAX_LENGHT))
        txtSegundoNombre?.setR(Filters.filterLetters(PE.NOMBRES_MAX_LENGHT))
        txtApellidoMaterno?.setR(Filters.filterLetters(PE.APELLIDO_MAX_LENGHT))
        txtApellidoPaterno?.setR(Filters.filterLetters(PE.APELLIDO_MAX_LENGHT))
        txtCorreo?.setR(Filters.filterMaxLength(PE.CORREO_MAX_LENGHT))
        txtCorreoRepeat?.setR(Filters.filterMaxLength(PE.CORREO_MAX_LENGHT))

    }

    override fun initValidation() {
        txtDocumento?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
            V(
                context, MIN_LENGTH, PE.DOCUMENTO_MIN_LENGHT,
                R.string.unete_paso1_valid_documento_min_length
            )
        )
        txtPrimerNombre?.addV(V(context, EMPTY, R.string.unete_paso1_valid_primer_nombre_empty))
        txtApellidoPaterno?.addV(
            V(
                context, EMPTY,
                R.string.unete_paso1_valid_apellido_paterno_empty
            )
        )
        txtApellidoMaterno?.addV(
            V(
                context, EMPTY,
                R.string.unete_paso1_valid_apellido_materno_empty
            )
        )
        txtFechaNacimiento?.addV(V(context, EMPTY, R.string.unete_paso1_valid_nacimiento_empty))
        txtCorreo?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_correo_empty),
            V(context, EMAIL, R.string.unete_paso1_valid_correo_format)
        )
        txtCorreoRepeat?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_correo_empty),
            V(context, EMAIL, R.string.unete_paso1_valid_correo_format)
        )

        txtFechaNacimiento?.minDate = Calendar.getInstance().minusYears(PE.EDAD_MAX)
        txtFechaNacimiento?.maxDate = Calendar.getInstance().minusYears(PE.EDAD_MIN)

        documentValidation()
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>(spnTipoDocumento, txtDocumento)

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    txtPrimerNombre,
                    txtSegundoNombre,
                    txtApellidoPaterno,
                    txtApellidoMaterno,
                    txtFechaNacimiento,
                    txtCorreo,
                    txtCorreoRepeat,
                    spnGenero
                )
            )
        }

        return lst
    }

    override fun showDuplicateMailError(message: String) {
        txtCorreo?.setEstado(BaseInput.ERROR, message)
        txtCorreoRepeat?.setEstado(BaseInput.ERROR, message)
    }


    private fun documentValidation() {

        when (spnTipoDocumento?.selected<ParametroUneteModel>()?.idParametroUnete) {
            UneteTipoDocumento.DNI.tipo -> {
                txtDocumento?.setRestriction(Filters.filterNumber(8))
                txtDocumento?.inputType(TYPE_CLASS_NUMBER)
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_valid_obligatorio),
                    V(context, MIN_LENGTH, 8, R.string.unete_paso1_valid_documento_min_length)
                )

            }
            UneteTipoDocumento.CE.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_NUMBER)
                txtDocumento?.setRestriction(Filters.filterNumber(10))
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(context, MIN_LENGTH, 7, R.string.unete_paso1_valid_documento_min_length)
                )
            }
            UneteTipoDocumento.PASAPORTE.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_TEXT)
                txtDocumento?.setRestriction(Filters.filterAlphanumeric(10))
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(context, MIN_LENGTH, 7, R.string.unete_paso1_valid_documento_min_length)
                )

            }
            UneteTipoDocumento.RUC.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_NUMBER)
                txtDocumento?.setRestriction(Filters.filterNumber(11))
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(context, MIN_LENGTH, 11, R.string.unete_paso1_valid_documento_min_length),
                    fieldDocumentoValid()
                )

            }
            UneteTipoDocumento.OTROS.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_TEXT)
                txtDocumento?.setRestriction(Filters.filterMaxLength(11))
                txtDocumento?.resetV()

            }
        }

    }

    private fun fieldDocumentoValid(): V {
        val a = object : Validation.Companion.ValidCommand {
            override fun execute(): Boolean {
                return RUC.validate(txtDocumento?.getText().orEmpty())
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
