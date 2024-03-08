package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cl


import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
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
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.Constant.providersEmail
import biz.belcorp.salesforce.core.utils.documents.RUT
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.calcularEdad
import biz.belcorp.salesforce.modules.postulants.utils.onEnterKeyListener
import kotlinx.android.synthetic.main.unete_cl_paso_1.view.*
import java.util.*

@SuppressLint("ViewConstructor")
class UnetePaso1(mContext: Context, view: UnetePaso1View) :
    BaseUnetePaso<UnetePaso1View>(mContext, view), IUnetePaso1 {

    override fun initEvents() {

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

    override fun getLayout() = R.layout.unete_cl_paso_1

    override fun showModal(isValid: Boolean) {
        if (isValid) {
            txtDocumento?.setEnable(false)
            lyBody?.visible()
        } else {
            txtDocumento?.setEnable(true)
        }
    }

    override fun showGeneros(model: List<SexoModel>) {
        spnGenero?.load(model)
        val selected = view.getModel().sexo
        spnGenero?.selection<SexoModel> { it?.codigo == selected }
    }

    override fun createModelDocument(): PostulanteModel {

        val model = view.getModel()
        model.pais = Pais.CHILE.codigoIso
        model.paisID = Pais.CHILE.id

        model.tipoDocumento = Constant.NUMERO_UNO.toString()
        model.numeroDocumento =
            txtDocumento?.getText()?.replace(Constant.HYPHEN, Constant.EMPTY_STRING)

        return model
    }

    override fun createModel(): PostulanteModel {

        val model = view.getModel()

        if (model.solicitudPostulanteID <= 0) {
            model.codigoZona = view.getZona()
            model.pais = Pais.CHILE.codigoIso
            model.paisID = Pais.CHILE.id
            model.codigoSeccion = view.getSeccion()
            model.fuenteIngreso = UneteAlgorithms.buildFuenteIngreso(view.getCodigoRol())
            model.tipoContacto = UneteTipoContacto.MOVIL.value.toString()
            model.estadoGEO = UneteEstadoGEO.SIN_CONSULTAR.value.toString()
            model.estadoBurocrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()
            model.estadoTelefonico = UneteEstadoTelefonico.NO_REQUERIDO.value
            model.indicadorActivo = "true"
        }

        model.tipoDocumento = Constant.NUMERO_UNO.toString()
        model.numeroDocumento =
            txtDocumento?.getText()?.replace(Constant.HYPHEN, Constant.EMPTY_STRING)
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

        if (model.numeroDocumento?.count() ?: Constant.NUMERO_CERO > Constant.NUMERO_OCHO
            && model.numeroDocumento?.contentEquals(Constant.HYPHEN) != true
        ) {
            txtDocumento?.setText(
                (model.numeroDocumento?.substring(Constant.NUMERO_CERO, Constant.NUMERO_OCHO)
                    .orEmpty()) + Constant.HYPHEN +
                    (model.numeroDocumento?.substring(Constant.NUMERO_OCHO, Constant.NUMERO_NUEVE)
                        .orEmpty())
            )
        } else {
            txtDocumento?.setText(model.numeroDocumento)
        }

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
        return txtDocumento?.validate() ?: false
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
        txtApellidoMaterno?.setRequired(true)
        txtApellidoPaterno?.setRequired(true)
        txtFechaNacimiento?.setRequired(true)
        txtCorreo?.setIsRequired(true)
        txtCorreoRepeat?.setIsRequired(true)
        spnGenero?.required = true
    }

    override fun initRestriction() {
        txtDocumento?.setR(
            arrayOf(
                InputFilter.AllCaps(),
                Filters.filterMaxLength(CL.RUT_MAX_LENGHT)
            )
        )
        txtPrimerNombre?.setR(Filters.filterLetters(CL.PRIMER_NOMBRE_MAX_LENGHT))
        txtSegundoNombre?.setR(Filters.filterLetters(CL.SEGUNDO_NOMBRE_MAX_LENGHT))
        txtApellidoPaterno?.setR(Filters.filterLetters(CL.APELLIDO_PATERNO_MAX_LENGHT))
        txtApellidoMaterno?.setR(Filters.filterLetters(CL.APELLIDO_MATERNO_MAX_LENGHT))
        txtCorreo?.setR(Filters.filterMaxLength(CL.CORREO_MAX_LENGHT))
        txtCorreoRepeat?.setR(Filters.filterMaxLength(CL.CORREO_MAX_LENGHT))
    }

    override fun initValidation() {

        txtDocumento?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_rut),
            V(context, MIN_LENGTH, CL.RUT_MIN_LENGHT, R.string.unete_paso1_valid_rut),
            fieldDocumentoValid()
        )

        txtFechaNacimiento?.minDate = Calendar.getInstance().minusYears(CL.EDAD_MAX)
        txtFechaNacimiento?.maxDate = Calendar.getInstance().minusYears(CL.EDAD_MIN)
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

    override fun showDuplicateMailError(message: String) {
        txtCorreo?.setEstado(BaseInput.ERROR, message)
        txtCorreoRepeat?.setEstado(BaseInput.ERROR, message)
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>(txtDocumento!!, spnGenero!!)

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
                return RUT.validate(txtDocumento?.getText().orEmpty())
            }
        }
        return V(context, CALLABLE, a, R.string.unete_paso1_valid_rut)
    }

}
