package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pr

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.DM
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.PR
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.features.entities.BasePostulante
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.SexoModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso1
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso1.UnetePaso1Fragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso1.UnetePaso1View
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.paso1.UnetePrePaso1Fragment
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.BaseInput
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMAIL
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.Constant.providersEmail
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.calcularEdad
import biz.belcorp.salesforce.modules.postulants.utils.onEnterKeyListener
import kotlinx.android.synthetic.main.unete_pr_paso_1.view.*
import java.util.*

@SuppressLint("ViewConstructor")
class UnetePaso1(mContext: Context, view: UnetePaso1View) :
    BaseUnetePaso<UnetePaso1View>(mContext, view), IUnetePaso1 {

    override fun initEvents() {

        txtSeguroSocial?.onEnterKeyListener {

            if (txtSeguroSocial.validate()) {

                if (view is UnetePaso1Fragment) {
                    val p = createModelDocument()
                    view.setModel(p)
                } else if (isPreInscrita()) {
                    val p = createModelPreDocument()
                    view.setModel(p)
                }

                true
            } else {
                false
            }

        }

        txtCorreo?.setListFiltrer(providersEmail)
        txtCorreoRepeat?.setListFiltrer(providersEmail)

        enabledWhatsAppAndCall()

    }

    private fun enabledWhatsAppAndCall() {
        val model = getPRModel()

        ivWhatsApp.invisible()
        ivPhoneNumber.invisible()
        ivWhatsApp?.apply {
            model.celular?.let { phone ->
                if (phone.isNotEmpty()) visible() else gone()
                setOnClickListener { context.enviarAWhatsapp(phone) }
            }
        }
        ivPhoneNumber?.apply {
            model.celular?.let { phone ->
                if (phone.isNotEmpty()) visible() else gone()
                setOnClickListener { context.llamarATelefono(phone) }
            }
        }
    }

    override fun getLayout() = R.layout.unete_pr_paso_1


    override fun showModal(isValid: Boolean) {
        if (isValid) {
            txtSeguroSocial?.setEnable(false)
            lyBody?.visible()
        }
    }

    override fun createModelDocument(): PostulanteModel {

        val model = view.getModel()

        model.pais = Pais.PUERTORICO.codigoIso
        model.paisID = Pais.PUERTORICO.id

        model.tipoDocumento = DM.TIPO_CEDULA
        model.numeroDocumento = txtSeguroSocial?.getText()?.trim()

        return model
    }

    override fun createModelPreDocument(): PrePostulanteModel {
        val model = view.getPreModel()

        model.pais = Pais.PUERTORICO.codigoIso
        model.paisID = Pais.PUERTORICO.id

        model.tipoDocumento = DM.TIPO_CEDULA
        model.numeroDocumento = txtSeguroSocial?.getText()?.trim()

        return model
    }


    override fun showGeneros(model: List<SexoModel>) {
        model.forEach {
            when (it.codigo) {
                "F" -> it.descripcion = "Mujer"
                "M" -> it.descripcion = "Hombre"
            }
        }
        spnGenero?.load(model)
        var selected = "M"
        if (view is UnetePaso1Fragment) {
            view.getModel().sexo?.let { selected = it }
        }
        spnGenero?.selection<SexoModel> { it?.codigo == selected }
    }

    override fun createModel(): PostulanteModel {

        val model = createModelDocument()

        if (model.solicitudPostulanteID <= Constant.NUMERO_CERO) {

            model.codigoZona = view.getZona()
            model.codigoSeccion = view.getSeccion()

            model.pais = Pais.PUERTORICO.codigoIso
            model.paisID = Pais.PUERTORICO.id

            Pais.find(model.paisID)?.codigoIso
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

        if (view is UnetePaso1Fragment) {
            model.estadoPostulante = UneteEstado.REGISTRADO.estado.toString()
        } else if (isPreInscrita()) {
            model.estadoPostulante = UneteEstado.PRE_INSCRITO.estado.toString()
        }

        model.paso = Constant.NUMERO_UNO
        model.sincronizado = false

        return model
    }

    override fun createPreModel(): PrePostulanteModel? {

        val model = createModelPreDocument()

        if (model.solicitudPrePostulanteID <= Constant.NUMERO_CERO) {

            model.codigoZona = view.getZona()
            model.codigoSeccion = view.getSeccion()
            model.pais = Pais.PUERTORICO.codigoIso
            model.paisID = Pais.PUERTORICO.id
            model.fuenteIngreso = UneteAlgorithms.buildFuenteIngreso(view.getCodigoRol())
            model.estadoGEO = UneteEstadoGEO.SIN_CONSULTAR.value.toString()
            model.estadoTelefonico = UneteEstadoTelefonico.NO_REQUERIDO.value
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

        if (view is UnetePaso1Fragment) {
            model.estadoPostulante = UneteEstado.REGISTRADO.estado.toString()
        } else if (isPreInscrita()) {
            model.estadoPrePostulante = UneteEstado.PRE_INSCRITO.estado.toString()
        }

        model.paso = Constant.NUMERO_UNO
        model.sincronizado = false

        return model
    }

    override fun loadModel() {

        InsertarData()
            .let {
                val model = when (view) {
                    is UnetePaso1Fragment -> it.postulantes(view.getModel())
                    else -> it.prePostulantes(view.getPreModel())
                }
                insertarDataAformulario(model)
            }

    }

    fun insertarDataAformulario(data: InsertarData) {

        txtSeguroSocial?.setText(data.numeroDocumento)
        txtPrimerNombre?.setText(data.primerNombre)
        txtSegundoNombre?.setText(data.segundoNombre)
        txtApellidoPaterno?.setText(data.apellidoPaterno)
        txtApellidoMaterno?.setText(data.apellidoMaterno)
        txtCorreo?.setText(data.correoElectronico)
        txtCorreoRepeat?.setText(data.correoElectronico)

        val fechaNacimiento = data.fechaCreacion
            ?.changeDateFormat(formatLongT, formatShort2)

        txtFechaNacimiento?.setText(fechaNacimiento)
    }

    override fun showBody(isBodyVisible: Boolean) {
        if (isBodyVisible)
            lyBody?.visible()
        else
            lyBody?.gone()
    }

    override fun showContactPostulant() {
        val model = getPRModel()

        if (validateSocialSecurityEmptyEditable(model)
            && UneteEstado.EN_APROBACION_FFVV.estado.toString() == model.estadoPostulante.orEmpty()
        ) {
            clContactPostulant.visible()
            txtSeguroSocial?.setEnable(true)
        }

        if (isPreInscrita() && validateSocialSecurityEmptyEditable(model)) {
            txtSeguroSocial?.setEnable(true)
        }
    }

    private fun validateSocialSecurityEmptyEditable(model: BasePostulante) =
        (model.numeroDocumento.isNullOrEmpty()
                && model.fuenteIngreso.equals(FuenteIngreso.UB))

    private fun getPRModel(): BasePostulante {
        return if (isPreInscrita()) {
            view.getPreModel()
        } else {
            view.getModel()
        }
    }

    private fun isPreInscrita() = view is UnetePrePaso1Fragment

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += txtSeguroSocial?.validate()
        v += txtPrimerNombre?.validate()
        v += txtApellidoPaterno?.validate()
        v += txtApellidoMaterno?.validate()
        v += txtFechaNacimiento?.validate()
        v += txtCorreo?.validate()
        v += txtCorreoRepeat?.validateRepeatEmail(txtCorreo?.getText().toString())

        return v.all { it ?: false }
    }

    override fun validateDocument() = txtSeguroSocial?.validate() ?: false


    override fun initRequired() {
        txtSeguroSocial?.setRequired(true)
        txtPrimerNombre?.setRequired(true)
        txtSegundoNombre?.setRequired(false)
        txtApellidoPaterno?.setRequired(true)
        txtApellidoMaterno?.setRequired(false)
        txtFechaNacimiento?.setRequired(true)
        txtCorreo?.setIsRequired(true)
        txtCorreoRepeat?.setIsRequired(true)
        spnGenero?.required = true
    }

    override fun initRestriction() {

        txtSeguroSocial?.setRestriction(Filters.filterNumber(PR.CEDULA_MAX_DIGITOS))
        txtSeguroSocial?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtPrimerNombre?.setR(Filters.filterLetters(PR.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtSegundoNombre?.setR(Filters.filterLetters(PR.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtApellidoMaterno?.setR(Filters.filterLetters(PR.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtApellidoPaterno?.setR(Filters.filterLetters(PR.NOMBRE_APELLIDO_MAX_DIGITOS))
        txtCorreo?.setR(Filters.filterMaxLength(PR.CORREO_MAX_LENGHT))
        txtCorreoRepeat?.setR(Filters.filterMaxLength(PR.CORREO_MAX_LENGHT))

    }

    override fun initValidation() {
        txtSeguroSocial?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
            V(
                context, MIN_LENGTH, PR.CEDULA_MAX_DIGITOS,
                R.string.unete_paso1_valid_documento_min_length
            )
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

        txtFechaNacimiento?.minDate = Calendar.getInstance().minusYears(PR.MAX_EDAD)
        txtFechaNacimiento?.maxDate = Calendar.getInstance().minusYears(PR.MIN_EDAD)
    }

    override fun showDuplicateMailError(message: String) {
        txtCorreo?.setEstado(BaseInput.ERROR, message)
        txtCorreoRepeat?.setEstado(BaseInput.ERROR, message)
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {
        val lst = mutableListOf<View>(txtSeguroSocial!!, spnGenero!!)

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

    open class InsertarData {
        var numeroDocumento: String? = Constant.EMPTY_STRING
        var primerNombre: String? = Constant.EMPTY_STRING
        var segundoNombre: String? = Constant.EMPTY_STRING
        var apellidoPaterno: String? = Constant.EMPTY_STRING
        var apellidoMaterno: String? = Constant.EMPTY_STRING
        var correoElectronico: String? = Constant.EMPTY_STRING
        var fechaCreacion: String? = Constant.EMPTY_STRING

        fun prePostulantes(model: PrePostulanteModel): InsertarData {
            this.numeroDocumento = model.numeroDocumento
            this.primerNombre = model.nombres
            this.segundoNombre = Constant.EMPTY_STRING
            this.apellidoPaterno = model.apellidoPaterno
            this.apellidoMaterno = model.apellidoMaterno
            this.correoElectronico = model.correoElectronico
            this.fechaCreacion = model.fechaCreacion
            return this
        }

        fun postulantes(model: PostulanteModel): InsertarData {
            this.numeroDocumento = model.numeroDocumento
            this.primerNombre = model.primerNombre
            this.segundoNombre = model.segundoNombre
            this.apellidoPaterno = model.apellidoPaterno
            this.apellidoMaterno = model.apellidoMaterno
            this.correoElectronico = model.correoElectronico
            this.fechaCreacion = model.fechaCreacion
            return this
        }
    }
}
