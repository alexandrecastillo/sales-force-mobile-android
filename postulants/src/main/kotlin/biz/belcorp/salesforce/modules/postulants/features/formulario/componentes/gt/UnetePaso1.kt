package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.gt

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.GT
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
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMAIL
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.Constant.providersEmail
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.calcularEdad
import biz.belcorp.salesforce.modules.postulants.utils.onEnterKeyListener
import kotlinx.android.synthetic.main.unete_gt_paso_1.view.*
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

    override fun getLayout() = R.layout.unete_gt_paso_1


    override fun createModelDocument(): PostulanteModel {

        val model = view.getModel()

        model.pais = Pais.GUATEMALA.codigoIso
        model.paisID = Pais.GUATEMALA.id

        model.tipoDocumento = GT.TIPO_DOCUMENTO
        model.numeroDocumento = txtDocumento?.getText()?.trim()

        return model
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

            model.pais = Pais.GUATEMALA.codigoIso
            model.paisID = Pais.GUATEMALA.id

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
        model.edad = fechaNacimiento?.calcularEdad() ?: Constant.NUMERO_CERO

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
            lyBody?.visible()
        } else {
            txtDocumento?.setEnable(true)
        }
    }

    override fun showBody(isBodyVisible: Boolean) {
        if (isBodyVisible) {
            lyBody?.visible()
        } else {
            lyBody?.gone()
        }
    }

    override fun showDuplicateMailError(message: String) {
        txtCorreo?.setEstado(BaseInput.ERROR, message)
        txtCorreoRepeat?.setEstado(BaseInput.ERROR, message)
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

    override fun validateDocument() = txtDocumento?.validate() ?: false

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

        txtPrimerNombre?.setR(Filters.filterLetters(GT.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtSegundoNombre?.setR(Filters.filterLetters(GT.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtApellidoMaterno?.setR(Filters.filterLetters(GT.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtApellidoPaterno?.setR(Filters.filterLetters(GT.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtDocumento?.setR(Filters.filterNumber(GT.DPI_MAX_DIGITOS))
        txtCorreo?.setR(Filters.filterMaxLength(GT.CORREO_MAX_LENGHT))
        txtCorreoRepeat?.setR(Filters.filterMaxLength(GT.CORREO_MAX_LENGHT))

    }

    override fun initValidation() {
        txtPrimerNombre?.addV(V(context, EMPTY))
        txtApellidoPaterno?.addV(V(context, EMPTY))
        txtApellidoMaterno?.addV(V(context, EMPTY))
        txtFechaNacimiento?.addV(V(context, EMPTY))

        txtDocumento?.v(
            V(context, EMPTY, R.string.unete_valid_obligatorio),
            V(
                context, MIN_LENGTH, GT.DPI_MIN_DIGITOS,
                R.string.unete_paso1_valid_documento_min_length
            ),
            fieldDocumentoValid()
        )
        txtDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)


        txtCorreo?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_correo_empty),
            V(context, EMAIL, R.string.unete_paso1_valid_correo_format)
        )
        txtCorreoRepeat?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_correo_empty),
            V(context, EMAIL, R.string.unete_paso1_valid_correo_format)
        )

        txtFechaNacimiento?.minDate = Calendar.getInstance().minusYears(GT.EDAD_MAX)
        txtFechaNacimiento?.maxDate = Calendar.getInstance().minusYears(GT.EDAD_MIN)

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
                return UAlgorithms.validaDocumentoPA(txtDocumento?.getText().orEmpty())
            }
        }
        return V(context, Validation.CALLABLE, a, R.string.unete_valid_formato_invalido)
    }
}
