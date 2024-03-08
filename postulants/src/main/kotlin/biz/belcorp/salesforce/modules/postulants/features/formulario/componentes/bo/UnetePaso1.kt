package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.bo

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType.TYPE_CLASS_NUMBER
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.BO
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
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMAIL
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.Constant.providersEmail
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.CIBolivia
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.calcularEdad
import biz.belcorp.salesforce.modules.postulants.utils.onEnterKeyListener
import kotlinx.android.synthetic.main.unete_bo_paso_1.view.*
import java.util.*

@SuppressLint("ViewConstructor")
class UnetePaso1(mContext: Context, view: UnetePaso1View) :
    BaseUnetePaso<UnetePaso1View>(mContext, view), IUnetePaso1 {

    private var isDocumentValid: Boolean = false

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

    override fun getLayout() = R.layout.unete_bo_paso_1

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

        model.pais = Pais.BOLIVIA.codigoIso
        model.paisID = Pais.BOLIVIA.id

        model.tipoDocumento = spnTipoDocumento?.selected<ParametroUneteModel>()?.valor
        model.numeroDocumento =
            CIBolivia.encode(model.tipoDocumento.toString(), txtDocumento?.getText().orEmpty())

        return model
    }

    override fun createModel(): PostulanteModel {

        val model = view.getModel()

        if (model.solicitudPostulanteID <= 0) {
            model.codigoZona = view.getZona()

            model.pais = Pais.BOLIVIA.codigoIso
            model.paisID = Pais.BOLIVIA.id
            model.fuenteIngreso = UneteAlgorithms.buildFuenteIngreso(view.getCodigoRol())

            model.tipoContacto = UneteTipoContacto.MOVIL.value.toString()
            model.codigoSeccion = view.getSeccion()
            model.estadoGEO = UneteEstadoGEO.SIN_CONSULTAR.value.toString()
            model.estadoBurocrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()

            model.estadoTelefonico = UneteEstadoTelefonico.NO_REQUERIDO.value
            model.indicadorActivo = "true"

        }

        model.tipoDocumento = spnTipoDocumento?.selected<ParametroUneteModel>()?.valor
        model.numeroDocumento =
            CIBolivia.encode(model.tipoDocumento.toString(), txtDocumento?.getText().orEmpty())
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

        val numeroDocumento = model.numeroDocumento
        txtDocumento?.setText(CIBolivia.decode(numeroDocumento))
        txtPrimerNombre?.setText(model.primerNombre)
        txtSegundoNombre?.setText(model.segundoNombre)
        txtApellidoPaterno?.setText(model.apellidoPaterno)
        txtApellidoMaterno?.setText(model.apellidoMaterno)
        txtCorreo?.setText(model.correoElectronico)
        txtCorreoRepeat?.setText(model.correoElectronico)

        val fechaNacimiento = model.fechaNacimiento?.changeDateFormat(formatLongT, formatShort2)

        txtFechaNacimiento?.setText(fechaNacimiento)

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

        txtDocumento?.setRestriction(Filters.filterMaxLength(BO.CI_MAX_DIGITOS))
        txtPrimerNombre?.setRestriction(Filters.filterLetters(BO.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtSegundoNombre?.setRestriction(Filters.filterLetters(BO.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtApellidoMaterno?.setRestriction(Filters.filterLetters(BO.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtApellidoPaterno?.setRestriction(Filters.filterLetters(BO.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtCorreo?.setR(Filters.filterMaxLength(BO.CORREO_MAX_LENGHT))
        txtCorreoRepeat?.setR(Filters.filterMaxLength(BO.CORREO_MAX_LENGHT))

    }

    override fun initValidation() {
        txtDocumento?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
            V(context, MIN_LENGTH, 6, R.string.unete_paso1_valid_documento_min_length)
        )
        txtPrimerNombre?.addV(V(context, EMPTY, R.string.unete_paso1_valid_primer_nombre_empty))
        txtApellidoPaterno?.addV(
            V(
                context,
                EMPTY,
                R.string.unete_paso1_valid_apellido_paterno_empty
            )
        )
        txtApellidoMaterno?.addV(
            V(
                context,
                EMPTY,
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
        txtFechaNacimiento?.minDate = Calendar.getInstance().minusYears(BO.MAX_EDAD)
        txtFechaNacimiento?.maxDate = Calendar.getInstance().minusYears(BO.MIN_EDAD)

        documentValidation()
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>(
            spnTipoDocumento, txtDocumento, spnGenero
        )

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

    private fun documentValidation() {

        when (spnTipoDocumento?.selected<ParametroUneteModel>()?.idParametroUnete) {
            UneteTipoDocumento.CI.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_NUMBER)
                txtDocumento?.addV(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(context, MIN_LENGTH, 6, R.string.unete_paso1_valid_documento_min_length)
                )
                txtDocumento?.setRestriction(Filters.filterMaxLength(BO.CI_MAX_DIGITOS))
            }
            UneteTipoDocumento.EXTRANJERO.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_NUMBER)
                txtDocumento?.addV(
                    V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(context, MIN_LENGTH, BO.EXTRANJERO_MIN_DIGITOS, R.string.unete_paso1_valid_documento_min_length)
                )
                txtDocumento?.setRestriction(Filters.filterMaxLength(BO.EXTRANJERO_MAX_DIGITOS))
            }
        }
    }

    override fun showDuplicateMailError(message: String) {
        txtCorreo?.setEstado(BaseInput.ERROR, message)
        txtCorreoRepeat?.setEstado(BaseInput.ERROR, message)
    }

    override fun validateDocument(): Boolean {
        return txtDocumento?.validate() ?: false
    }

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

    override fun showBody(isBodyVisible: Boolean) {
        isDocumentValid = if (isBodyVisible) {
            lyBody?.visible()
            true
        } else {
            lyBody?.gone()
            false
        }
    }

}
