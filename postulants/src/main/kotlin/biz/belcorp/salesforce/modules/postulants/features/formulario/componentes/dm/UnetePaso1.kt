package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.dm

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.DM
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
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
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.calcularEdad
import biz.belcorp.salesforce.modules.postulants.utils.onEnterKeyListener
import kotlinx.android.synthetic.main.unete_dm_paso_1.view.*
import java.util.*

@SuppressLint("ViewConstructor")
class UnetePaso1(context: Context, view: UnetePaso1View) :
    BaseUnetePaso<UnetePaso1View>(context, view), IUnetePaso1 {

    override fun initEvents() {

        txtDocumento?.onEnterKeyListener {
            val doc = txtDocumento?.getText().orEmpty()
            if (doc.isEmpty()) {
                false
            } else {
                val postulant = view.getModel()
                postulant.tipoDocumento = DM.TIPO_CEDULA
                postulant.numeroDocumento = txtDocumento?.getText()?.trim()
                view.setModel(postulant)
                view.validateBloqueos(postulant.pais.orEmpty(), doc, Constant.NUMERO_UNO.toString())
                true
            }
        }

        txtCorreo?.setListFiltrer(providersEmail)
        txtCorreoRepeat?.setListFiltrer(providersEmail)
    }

    override fun getLayout() = R.layout.unete_dm_paso_1

    override fun showModal(isValid: Boolean) {
        if (isValid) {
            txtDocumento?.setEnable(false)
            lyBody?.visible()
        }
    }

    override fun showBloqueos(bloqueos: BuroResponse?) {
        txtPrimerNombre.setEnable(true)
        txtSegundoNombre.setEnable(true)
        txtApellidoPaterno.setEnable(true)
        txtApellidoMaterno.setEnable(true)
        txtFechaNacimiento.setEnable(true)
    }

    override fun createModelDocument(): PostulanteModel {

        val model = view.getModel()

        model.pais = Pais.DOMINICANA.codigoIso
        model.paisID = Pais.DOMINICANA.id

        model.tipoDocumento = DM.TIPO_CEDULA
        model.numeroDocumento = txtDocumento?.getText()?.trim()

        return model
    }

    override fun showGeneros(model: List<SexoModel>) {
        val lista = arrayListOf<SexoModel>()

        var p = SexoModel()
        p.codigo = "F"
        p.descripcion = "Mujer"
        lista.add(p)

        p = SexoModel()
        p.codigo = "M"
        p.descripcion = "Hombre"
        lista.add(p)

        spnGenero?.load(lista)
        val selected = view.getModel().sexo
        spnGenero?.selection<SexoModel> { it?.codigo == selected }
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

    override fun mostrarFechaNacimiento(fechaNacimiento: String) {
        txtFechaNacimiento?.setText(fechaNacimiento)
        txtFechaNacimiento?.setEnable(false)
    }

    override fun createModel(): PostulanteModel {

        val model = createModelDocument()

        if (model.solicitudPostulanteID <= Constant.NUMERO_CERO) {
            model.codigoZona = view.getZona()
            model.codigoSeccion = view.getSeccion()

            model.pais = Pais.DOMINICANA.codigoIso
            model.paisID = Pais.DOMINICANA.id

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

    override fun initRequired() {
        txtDocumento?.setRequired(true)
        txtPrimerNombre?.setRequired(true)
        txtSegundoNombre?.setRequired(false)
        txtApellidoPaterno?.setRequired(true)
        txtApellidoMaterno?.setRequired(false)
        txtFechaNacimiento?.setRequired(true)
        txtCorreo?.setIsRequired(true)
        txtCorreoRepeat?.setIsRequired(true)
    }

    override fun initRestriction() {

        txtDocumento?.setR(
            arrayOf(
                Filters.filterMaxLength(DM.DOCUMENTO_MAX_DIGITOS),
                InputFilter.AllCaps()
            )
        )
        txtPrimerNombre?.setR(Filters.filterLetters(DM.NOMBRES_MAX_DIGITOS))
        txtSegundoNombre?.setR(Filters.filterLetters(DM.NOMBRES_MAX_DIGITOS))
        txtApellidoPaterno?.setR(Filters.filterLetters(DM.APELLIDOS_MAX_DIGITOS))
        txtApellidoMaterno?.setR(Filters.filterLetters(DM.APELLIDOS_MAX_DIGITOS))
        txtCorreo?.setR(Filters.filterMaxLength(DM.CORREO_MAX_LENGHT))
        txtCorreoRepeat?.setR(Filters.filterMaxLength(DM.CORREO_MAX_LENGHT))

    }

    override fun initValidation() {
        txtDocumento?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_documento_empty),
            V(
                context, MIN_LENGTH, DM.DOCUMENTO_MAX_DIGITOS,
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

        txtFechaNacimiento?.minDate = Calendar.getInstance().minusYears(DM.MAX_EDAD)
        txtFechaNacimiento?.maxDate = Calendar.getInstance().minusYears(DM.MIN_EDAD)
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

    override fun showDuplicateMailError(message: String) {
        txtCorreo?.setEstado(BaseInput.ERROR, message)
        txtCorreoRepeat?.setEstado(BaseInput.ERROR, message)
    }

}
