package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.co

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CO
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
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.CALLABLE
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMAIL
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.STARTIN2
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.Constant.providersEmail
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.calcularEdad
import biz.belcorp.salesforce.modules.postulants.utils.minusYears
import biz.belcorp.salesforce.modules.postulants.utils.onEnterKeyListener
import kotlinx.android.synthetic.main.unete_co_paso_1.view.*
import java.util.*

@SuppressLint("ViewConstructor")
class UnetePaso1(mContext: Context, view: UnetePaso1View) :
        BaseUnetePaso<UnetePaso1View>(mContext, view), IUnetePaso1 {

    var isDocumentValid: Boolean = false

    override fun initEvents() {

        spnTipoDocumento?.onItemSelected {
            if (view.isStepZero() && !isDocumentValid) {
                txtDocumento?.setText(Constant.EMPTY_STRING)
            }
            documentValidation()
        }

        txtDocumento?.onEnterKeyListener {
            if (txtDocumento?.validate()!!) {

                val p = createModelDocument()
                view.setModel(p)
                view.validateBloqueos(
                    p.pais.orEmpty(),
                    txtDocumento?.getText().orEmpty(),
                    p.tipoDocumento.orEmpty()
                )
                true
            } else {
                false
            }
        }

        txtCorreo?.setListFiltrer(providersEmail)
        txtCorreoRepeat?.setListFiltrer(providersEmail)
    }

    override fun getLayout() = R.layout.unete_co_paso_1

    override fun showModal(isValid: Boolean) {
        if (isValid) {
            spnTipoDocumento?.isEnabled = false
            txtDocumento?.setEnable(false)
            lyBody?.visible()
            isDocumentValid = true
        } else {
            spnTipoDocumento?.isEnabled = true
            txtDocumento?.setEnable(true)
            isDocumentValid = false
        }
    }

    override fun showTipoDocumento(model: List<ParametroUneteModel>) {
        spnTipoDocumento?.load(model)
        val selected = view.getModel().tipoDocumento
        spnTipoDocumento?.selection<ParametroUneteModel> { it?.valor == selected }
    }

    override fun showRegimenContable(model: List<ParametroUneteModel>) {
        spnRegimenContable.load(model)
        val selected = view.getModel().regimenContable
        spnRegimenContable?.selection<ParametroUneteModel> { it?.valor == selected }
    }

    override fun showGeneros(model: List<SexoModel>) {
        spnGenero?.load(model)
        val selected = view.getModel().sexo
        spnGenero?.selection<SexoModel> { it?.codigo == selected }

    }

    override fun createModelDocument(): PostulanteModel {

        val model = view.getModel()

        model.pais = Pais.COLOMBIA.codigoIso
        model.paisID = Pais.COLOMBIA.id

        model.tipoDocumento = spnTipoDocumento?.selected<ParametroUneteModel>()?.valor
        model.numeroDocumento = txtDocumento?.getText()?.trim()

        return model
    }

    override fun createModel(): PostulanteModel {

        val model = view.getModel()

        if (model.solicitudPostulanteID <= Constant.NUMERO_CERO) {

            model.pais = Pais.COLOMBIA.codigoIso
            model.paisID = Pais.COLOMBIA.id

            model.codigoZona = view.getZona()
            model.codigoSeccion = view.getSeccion()

            model.fuenteIngreso = UneteAlgorithms.buildFuenteIngreso(view.getCodigoRol())

            model.tipoContacto = UneteTipoContacto.MOVIL.value.toString()
            model.estadoGEO = UneteEstadoGEO.SIN_CONSULTAR.value.toString()
            model.estadoBurocrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()

            model.estadoTelefonico = UneteEstadoTelefonico.NO_REQUERIDO.value
            model.indicadorActivo = "true"
        }

        model.tipoDocumento = spnTipoDocumento?.selected<ParametroUneteModel>()?.valor
        model.numeroDocumento = txtDocumento?.getText()?.trim()
        model.primerNombre = txtPrimerNombre?.getText()
        model.segundoNombre = txtSegundoNombre?.getText()
        model.apellidoPaterno = txtApellidoPaterno?.getText()
        model.apellidoMaterno = txtApellidoMaterno?.getText()
        model.regimenContable = spnRegimenContable?.selected<ParametroUneteModel>()?.valor?.replace("3", "1")

        val fechaNacimiento = txtFechaNacimiento?.getText()?.toDate(formatShort2)
        model.fechaNacimiento = fechaNacimiento?.string(formatLongT)
        model.edad = fechaNacimiento?.calcularEdad() ?: Constant.NUMERO_CERO

        model.correoElectronico = txtCorreo?.getText()
        model.sexo = spnGenero?.selected<SexoModel>()?.codigo
        model.estadoPostulante = UneteEstado.REGISTRADO.estado.toString()
        model.paso = 1

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
        if (isBodyVisible) {
            lyBody?.visible()
            isDocumentValid = true
        } else {
            lyBody?.gone()
            isDocumentValid = false
        }
    }

    override fun validateDocument() = txtDocumento?.validate() ?: false

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

    override fun initRequired() {
        txtDocumento?.setRequired(true)
        txtPrimerNombre?.setRequired(true)
        txtApellidoPaterno?.setRequired(true)
        txtApellidoMaterno?.setRequired(false)
        txtFechaNacimiento?.setRequired(true)
        txtCorreo?.setIsRequired(true)
        txtCorreoRepeat?.setIsRequired(true)
    }

    override fun initRestriction() {
        txtPrimerNombre?.setR(Filters.filterLetters(CO.PRIMER_NOMBRE_MAX_LENGHT))
        txtSegundoNombre?.setR(Filters.filterLetters(CO.SEGUNDO_NOMBRE_MAX_LENGHT))
        txtApellidoPaterno?.setR(Filters.filterLetters(CO.APELLIDO_PATERNO_MAX_LENGHT))
        txtApellidoMaterno?.setR(Filters.filterLetters(CO.APELLIDO_MATERNO_MAX_LENGHT))
        txtCorreo?.setR(Filters.filterMaxLength(CO.CORREO_MAX_LENGHT))
        txtCorreoRepeat?.setR(Filters.filterMaxLength(CO.CORREO_MAX_LENGHT))
    }

    override fun initValidation() {

        txtFechaNacimiento?.minDate = Calendar.getInstance().minusYears(CO.EDAD_MAX)
        txtFechaNacimiento?.maxDate = Calendar.getInstance().minusYears(CO.EDAD_MIN)
        txtPrimerNombre?.addV(V(context, EMPTY, R.string.unete_paso1_valid_primer_nombre_empty))
        txtApellidoPaterno?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_apellido_paterno_empty)
        )
        txtApellidoMaterno?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_apellido_materno_empty)
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

    }

    private fun documentValidation() {
        when (spnTipoDocumento?.selected<ParametroUneteModel>()?.valor) {
            UneteTipoDocumento.CO_CC.codigo -> {
                txtDocumento?.setRestriction(Filters.filterNumber(CO.CEDULA_MAX_LENGHT))
                txtDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    fieldCedulaValid(UneteTipoDocumento.CO_CC),
                    V(context, STARTIN2, R.string.unete_paso1_valid_documento_format)
                )
            }
            UneteTipoDocumento.CO_CE.codigo -> {
                txtDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)
                txtDocumento?.setRestriction(Filters.filterMaxLength(7))
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(context, MIN_LENGTH, 4, R.string.unete_paso1_valid_documento_min_length)
                )
            }
            UneteTipoDocumento.CO_CONTRASENA.codigo -> {
                txtDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)
                txtDocumento?.setRestriction(arrayOf(Filters.filterMaxLength(CO.CEDULA_MAX_LENGHT), InputFilter.AllCaps()))
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                        fieldCedulaValid(UneteTipoDocumento.CO_CONTRASENA))
            }
            UneteTipoDocumento.CO_PPT.codigo -> {
                txtDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)
                txtDocumento?.setRestriction(arrayOf(Filters.filterMaxLength(CO.PPT_MAX_LENGTH), InputFilter.AllCaps()))
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(context, MIN_LENGTH, CO.PPT_MIN_LENGTH, R.string.unete_paso1_valid_documento_min_length)
                )
            }
        }
    }

    override fun showDuplicateMailError(message: String) {
        txtCorreo?.setEstado(BaseInput.ERROR, message)
        txtCorreoRepeat?.setEstado(BaseInput.ERROR, message)
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {
        val lst = mutableListOf<View>(txtDocumento!!, spnGenero!!, spnTipoDocumento!!, spnRegimenContable)

        if (!enEdicion) {
            lst.addAll(mutableListOf(
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

    private fun fieldCedulaValid(tipoDocumento: UneteTipoDocumento): V {
        val a = object : Validation.Companion.ValidCommand {
            override fun execute(): Boolean {
                return UneteAlgorithms.validarCedulaCO(txtDocumento?.getText()?.trim().orEmpty(), tipoDocumento)
            }
        }
        return V(context, CALLABLE, a, R.string.unete_paso1_valid_documento_format)
    }

}
