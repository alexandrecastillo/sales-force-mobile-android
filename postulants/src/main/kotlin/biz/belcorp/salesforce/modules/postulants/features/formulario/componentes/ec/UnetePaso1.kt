package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.ec

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.EC
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.SexoModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel
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
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.NO_SELECTION
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.Constant.providersEmail
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.calcularEdad
import biz.belcorp.salesforce.modules.postulants.utils.onEnterKeyListener
import kotlinx.android.synthetic.main.unete_ec_paso_1.view.*
import java.util.*

@SuppressLint("ViewConstructor")
class UnetePaso1(mContext: Context, view: UnetePaso1View) :
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
                    p.tipoDocumento.orEmpty(),
                    view.getZona().orEmpty()
                )
                true
            } else {
                false
            }
        }

        txtCorreo?.setListFiltrer(providersEmail)
        txtCorreoRepeat?.setListFiltrer(providersEmail)

    }

    override fun getLayout() = R.layout.unete_ec_paso_1

    override fun showModal(isValid: Boolean) {
        if (isValid) {
            txtDocumento?.setEnable(false)
            spnTipoDocumento?.isEnabled = false
            lyBody?.visible()
        } else {
            txtDocumento?.setEnable(true)
            spnTipoDocumento?.isEnabled = true
        }
    }

    override fun showNacionalidad(model: List<TablaLogicaModel>) {
        spnNacionalidad?.load(model)
        val default = Pais.ECUADOR.id.toString().padStart(2, '0')
        val selected = view.getModel().tipoNacionalidad ?: default
        spnNacionalidad?.selection<TablaLogicaModel> { it?.codigo == selected }
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

    override fun createModelDocument(): PostulanteModel {

        val model = view.getModel()

        model.pais = Pais.ECUADOR.codigoIso
        model.paisID = Pais.ECUADOR.id

        model.tipoDocumento = spnTipoDocumento?.selected<ParametroUneteModel>()?.valor
        model.numeroDocumento = txtDocumento?.getText()?.trim()

        return model
    }

    override fun createModel(): PostulanteModel {

        val model = view.getModel()

        if (model.solicitudPostulanteID <= Constant.NUMERO_CERO) {

            model.codigoZona = view.getZona()
            model.pais = Pais.ECUADOR.codigoIso
            model.paisID = Pais.ECUADOR.id
            model.codigoSeccion = view.getSeccion()

            Pais.find(model.paisID)?.codigoIso
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
        model.tipoNacionalidad = spnNacionalidad?.selected<TablaLogicaModel>()?.codigo

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

    override fun showBody(isBodyVisible: Boolean) {
        if (isBodyVisible)
            lyBody?.visible()
        else
            lyBody?.gone()
    }

    override fun validateDocument(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += spnTipoDocumento?.validate()
        v += txtDocumento?.validate()

        return v.all { it ?: false }
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

    override fun initRequired() {
        txtDocumento?.setRequired(true)
        txtPrimerNombre?.setRequired(true)
        txtApellidoMaterno?.setRequired(false)
        txtApellidoPaterno?.setRequired(true)
        txtFechaNacimiento?.setRequired(true)
        txtCorreo?.setIsRequired(true)
        txtCorreoRepeat?.setIsRequired(true)
    }

    override fun initRestriction() {
        txtPrimerNombre?.setR(Filters.filterLetters(EC.PRIMER_NOMBRE_MAX_LENGHT))
        txtSegundoNombre?.setR(Filters.filterLetters(EC.SEGUNDO_NOMBRE_MAX_LENGHT))
        txtApellidoPaterno?.setR(Filters.filterLetters(EC.APELLIDO_PATERNO_MAX_LENGHT))
        txtApellidoMaterno?.setR(Filters.filterLetters(EC.APELLIDO_MATERNO_MAX_LENGHT))
        txtCorreo?.setR(Filters.filterMaxLength(EC.CORREO_MAX_LENGHT))
        txtCorreoRepeat?.setR(Filters.filterMaxLength(EC.CORREO_MAX_LENGHT))
    }

    override fun initValidation() {

        spnTipoDocumento?.addValidations(V(context, NO_SELECTION))

        txtFechaNacimiento?.minDate =
            Calendar.getInstance().minusYears(EC.EDAD_MAX, Pais.ECUADOR.codigoIso)
        txtFechaNacimiento?.maxDate = Calendar.getInstance().minusYears(EC.EDAD_MIN)
        txtPrimerNombre?.addV(V(context, EMPTY, R.string.unete_paso1_valid_primer_nombre_empty))
        txtApellidoPaterno?.addV(
            V(
                context, EMPTY,
                R.string.unete_paso1_valid_apellido_paterno_empty
            )
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

        documentValidation()
    }

    private fun documentValidation() {
        val tipoDocumento = spnTipoDocumento?.selected<ParametroUneteModel>()?.valor
        txtDocumento?.setEnable(true)
        when (tipoDocumento) {
            UneteTipoDocumento.EC_CEDULA.codigo -> {
                txtDocumento?.setRestriction(Filters.filterNumber(EC.CEDULA_MAX_LENGHT))
                txtDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    fieldDocumentoValid()
                )

            }
            UneteTipoDocumento.EC_RUC.codigo -> {
                txtDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)
                txtDocumento?.setRestriction(Filters.filterMaxLength(13))
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(context, MIN_LENGTH, 13, R.string.unete_paso1_valid_documento_min_length)
                )
            }
            UneteTipoDocumento.EC_PASAPORTE.codigo -> {
                txtDocumento?.inputType(InputType.TYPE_CLASS_TEXT)
                txtDocumento?.setRestriction(Filters.filterAlphanumeric(15))
                txtDocumento?.v(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(context, MIN_LENGTH, 6, R.string.unete_paso1_valid_documento_min_length)
                )

            }
            else -> {
                txtDocumento?.setEnable(false)
            }
        }
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {
        val lst = mutableListOf<View>(txtDocumento, spnGenero, spnTipoDocumento)

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    spnNacionalidad,
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

    private fun fieldDocumentoValid(): V {
        val a = object : Validation.Companion.ValidCommand {
            override fun execute(): Boolean {
                return UAlgorithms.validarCedulaEC(txtDocumento?.getText().orEmpty())
            }
        }
        return V(context, CALLABLE, a, R.string.unete_paso1_valid_documento_format)
    }

}
