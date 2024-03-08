package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cr

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.text.InputType.TYPE_CLASS_NUMBER
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CR
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.SexoModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso1
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.BaseUneteFragment.Estado.Nuevo
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
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.calcularEdad
import biz.belcorp.salesforce.modules.postulants.utils.onEnterKeyListener
import kotlinx.android.synthetic.main.unete_cr_paso_1.view.*
import java.util.*

@SuppressLint("ViewConstructor")
class UnetePaso1(mContext: Context, view: UnetePaso1View, val mEstado: Int) :
    BaseUnetePaso<UnetePaso1View>(mContext, view), IUnetePaso1 {

    private var isDocumentValid: Boolean = false

    override fun initEvents() {

        spnTipoDocumento?.onItemSelected {
            documentValidation()
            if (view.isStepZero() && !isDocumentValid) {
                txtDocumento?.setText(Constant.EMPTY_STRING)
            }
        }

        txtDocumento?.onEnterKeyListener {
            if (txtDocumento?.validate()!!) {
                val p = createModelDocument()
                view.setModel(p)
                view.validateBloqueos(
                    p.pais.orEmpty(),
                    p.numeroDocumento.orEmpty(),
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

    override fun getLayout() = R.layout.unete_cr_paso_1

    override fun showModal(isValid: Boolean) {
        if (isValid) {
            spnTipoDocumento?.isEnabled = false
            txtDocumento?.setEnable(false)
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
            spnTipoDocumento?.isEnabled = false
            txtDocumento?.setEnable(false)
            lyBody?.visible()
            isDocumentValid = true
        } else {
            spnTipoDocumento?.isEnabled = true
            txtDocumento?.setEnable(true)
            lyBody?.gone()
            isDocumentValid = false
        }
    }

    override fun createModelDocument(): PostulanteModel {

        val model = view.getModel()

        model.pais = Pais.COSTARICA.codigoIso
        model.paisID = Pais.COSTARICA.id

        model.tipoDocumento = spnTipoDocumento?.selected<ParametroUneteModel>()?.valor.toString()
        model.numeroDocumento = txtDocumento?.getText()?.trim()

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

        if (model.solicitudPostulanteID <= 0) {
            model.codigoZona = view.getZona()
            model.codigoSeccion = view.getSeccion()
            model.pais = Pais.COSTARICA.codigoIso
            model.paisID = Pais.COSTARICA.id

            model.fuenteIngreso = UneteAlgorithms.buildFuenteIngreso(view.getCodigoRol())
            model.tipoContacto = UneteTipoContacto.MOVIL.value.toString()
            model.estadoGEO = UneteEstadoGEO.SIN_CONSULTAR.value.toString()
            model.estadoBurocrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()

            model.estadoTelefonico = UneteEstadoTelefonico.NO_REQUERIDO.value
            model.indicadorActivo = "true"

        }

        model.primerNombre = txtPrimerNombre?.getText()
        model.segundoNombre = txtSegundoNombre?.getText()
        model.apellidoPaterno = txtApellidoPaterno?.getText()
        model.apellidoMaterno = txtApellidoMaterno?.getText()
        val fechaNacimiento = txtFechaNacimiento?.getText()?.toDate(formatShort2)
        model.fechaNacimiento = fechaNacimiento?.string(formatLongT)
        model.edad = fechaNacimiento?.calcularEdad() ?: Constant.NUMERO_CERO
        model.correoElectronico = txtCorreo?.getText()
        model.sexo = spnGenero?.selected<SexoModel>()?.codigo
        model.estadoPostulante = UneteEstado.REGISTRADO.estado.toString()
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

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += spnTipoDocumento?.validate()
        v += txtDocumento?.validate()
        v += txtPrimerNombre?.validate()
        v += txtApellidoPaterno?.validate()
        v += txtApellidoMaterno?.validate()
        v += txtFechaNacimiento?.validate()
        v += txtCorreo?.validate()
        v += txtCorreoRepeat?.validateRepeatEmail(txtCorreo?.getText().toString())

        return v.all { it ?: false }
    }

    override fun validateDocument(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += spnTipoDocumento?.validate()
        v += txtDocumento?.validate()

        return v.all { it ?: false }
    }

    override fun initRequired() {
        spnTipoDocumento?.required = true
        txtDocumento?.setRequired(true)
        txtPrimerNombre?.setRequired(true)
        txtSegundoNombre?.setRequired(false)
        txtApellidoPaterno?.setRequired(true)
        txtApellidoMaterno?.setRequired(false)
        txtFechaNacimiento?.setRequired(true)
        txtCorreo?.setIsRequired(true)
        txtCorreoRepeat?.setIsRequired(true)
        spnGenero.required = true
    }

    override fun initRestriction() {

        txtPrimerNombre?.setR(Filters.filterLetters(CR.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtSegundoNombre?.setR(Filters.filterLetters(CR.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtApellidoMaterno?.setR(Filters.filterLetters(CR.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtApellidoPaterno?.setR(Filters.filterLetters(CR.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtCorreo?.setR(Filters.filterMaxLength(CR.CORREO_MAX_LENGHT))
        txtCorreoRepeat?.setR(Filters.filterMaxLength(CR.CORREO_MAX_LENGHT))

    }

    override fun initValidation() {
        spnTipoDocumento?.addValidations(
            V(context, Validation.NO_SELECTION, R.string.unete_paso1_valid_tipo_documento_select)
        )
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

        txtFechaNacimiento?.minDate = Calendar.getInstance().minusYears(CR.EDAD_MAX)
        txtFechaNacimiento?.maxDate = Calendar.getInstance().minusYears(CR.EDAD_MIN)

        documentValidation()
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {
        val lst = mutableListOf<View>(txtDocumento!!, spnGenero!!, spnTipoDocumento!!)

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

    override fun showDuplicateMailError(message: String) {
        txtCorreo?.setEstado(BaseInput.ERROR, message)
        txtCorreoRepeat?.setEstado(BaseInput.ERROR, message)
    }

    private fun documentValidation() {

        val tipoDocumento = spnTipoDocumento?.selected<ParametroUneteModel>()?.valor

        if (mEstado == Nuevo)
            txtDocumento?.setEnable(true)

        when (tipoDocumento) {
            UneteTipoDocumento.CR_CEDULA_IDENTIDAD.codigo -> {
                txtDocumento?.setRestriction(Filters.filterNumber(CR.DOCUMENTO_CEDULA_IDENTIDAD))
                txtDocumento?.inputType(TYPE_CLASS_NUMBER)
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_valid_obligatorio),
                    V(
                        context, MIN_LENGTH, CR.DOCUMENTO_CEDULA_IDENTIDAD,
                        R.string.unete_paso1_valid_documento_min_length
                    )
                )
            }
            UneteTipoDocumento.CR_PASAPORTE.codigo -> {
                txtDocumento?.setRestriction(Filters.filterAlphanumeric(CR.DOCUMENTO_PASAPORTE_MAX))
                txtDocumento?.inputType(InputType.TYPE_CLASS_TEXT)
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_valid_obligatorio),
                    V(
                        context, MIN_LENGTH, CR.DOCUMENTO_PASAPORTE_MIN,
                        R.string.unete_paso1_valid_documento_min_length
                    )
                )
            }
            UneteTipoDocumento.CR_RESIDENCIA.codigo -> {
                txtDocumento?.setRestriction(Filters.filterNumber(CR.DOCUMENTO_RESIDENCIA_MAX))
                txtDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_valid_obligatorio),
                    V(
                        context, MIN_LENGTH, CR.DOCUMENTO_RESIDENCIA_MIN,
                        R.string.unete_paso1_valid_documento_min_length
                    )
                )
            }
            UneteTipoDocumento.CR_CEDULA_RESIDENCIA_TEMPORAL.codigo -> {
                txtDocumento?.setRestriction(Filters.filterAlphanumeric(CR.DOCUMENTO_CEDULA_RESIDENCIA_TEMPORAL_MAX))
                txtDocumento?.inputType(InputType.TYPE_CLASS_TEXT)
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_valid_obligatorio),
                    V(
                        context, MIN_LENGTH, CR.DOCUMENTO_CEDULA_RESIDENCIA_TEMPORAL_MIN,
                        R.string.unete_paso1_valid_documento_min_length
                    )
                )
            }
            UneteTipoDocumento.CR_OTROS.codigo -> {
                txtDocumento?.setRestriction(Filters.filterAlphanumeric(CR.DOCUMENTO_OTROS_MAX))
                txtDocumento?.inputType(InputType.TYPE_CLASS_TEXT)
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_valid_obligatorio),
                    V(
                        context, MIN_LENGTH, CR.DOCUMENTO_OTROS_MIN,
                        R.string.unete_paso1_valid_documento_min_length
                    )
                )
            }
            else -> {
                txtDocumento?.setEnable(false)
            }
        }
    }

}
