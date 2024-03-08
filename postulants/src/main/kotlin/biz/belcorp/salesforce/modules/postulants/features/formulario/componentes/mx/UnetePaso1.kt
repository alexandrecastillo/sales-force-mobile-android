package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.mx

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.MX
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
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.DATE_FORMAT
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMAIL
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.Constant.providersEmail
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.RFC
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.calcularEdad
import biz.belcorp.salesforce.modules.postulants.utils.onDateChanged
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import kotlinx.android.synthetic.main.unete_mx_paso_1.view.*
import java.util.*

@SuppressLint("ViewConstructor")
class UnetePaso1(context: Context, view: UnetePaso1View) : BaseUnetePaso<UnetePaso1View>(context, view),
    IUnetePaso1 {

    override fun getLayout() = R.layout.unete_mx_paso_1


    override fun initView() {
        super.initView()
        edtDocumento?.setEnable(false)
    }

    override fun initEvents() {

        edtPrimerNombre?.onTextChanged {
            generarRfc()
        }

        edtSegundoNombre?.onTextChanged {
            generarRfc()
        }

        edtApellidoMaterno?.onTextChanged {
            generarRfc()
        }

        edtApellidoPaterno?.onTextChanged {
            generarRfc()
        }

        edtFechaNacimiento?.onDateChanged {
            generarRfc()
        }

        edtCorreo?.setListFiltrer(providersEmail)
        edtCorreoRepeat?.setListFiltrer(providersEmail)

    }

    override fun createModel(): PostulanteModel {

        val model = view.getModel()

        generarRfc()

        if (model.solicitudPostulanteID <= Constant.NUMERO_CERO) {

            model.codigoZona = view.getZona()
            model.codigoSeccion = view.getSeccion()
            model.pais = Pais.MEXICO.codigoIso
            model.paisID = Pais.MEXICO.id
            model.fuenteIngreso = UneteAlgorithms.buildFuenteIngreso(view.getCodigoRol())
            model.tipoContacto = UneteTipoContacto.MOVIL.value.toString()

            model.estadoGEO = UneteEstadoGEO.SIN_CONSULTAR.value.toString()
            model.estadoBurocrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()

            model.estadoTelefonico = UneteEstadoTelefonico.NO_REQUERIDO.value
            model.indicadorActivo = "true"

        }

        model.sexo = spnGenero?.selected<SexoModel>()?.codigo
        model.tipoDocumento = Constant.NUMERO_UNO.toString()
        model.numeroDocumento = edtDocumento?.getText()?.trim()
        model.primerNombre = edtPrimerNombre?.getText()
        model.segundoNombre = edtSegundoNombre?.getText()
        model.apellidoPaterno = edtApellidoPaterno?.getText()
        model.apellidoMaterno = edtApellidoMaterno?.getText()

        val fechaNacimiento = edtFechaNacimiento?.getText()?.toDate(formatShort2)
        model.fechaNacimiento = fechaNacimiento?.string(formatLongT)
        model.edad = fechaNacimiento?.calcularEdad() ?: Constant.NUMERO_CERO

        model.correoElectronico = edtCorreo?.getText()
        model.celular = edtTelefonoCelular.getText()
        model.telefono = edtTelefonoFijo.getText()
        model.estadoPostulante = UneteEstado.REGISTRADO.estado.toString()
        model.paso = Constant.NUMERO_UNO

        model.sincronizado = false

        return model
    }

    override fun createModelDocument(): PostulanteModel {

        val model = view.getModel()

        generarRfc()

        model.pais = Pais.MEXICO.codigoIso
        model.paisID = Pais.MEXICO.id

        model.tipoDocumento = Constant.NUMERO_UNO.toString()
        model.numeroDocumento = edtDocumento?.getText()?.trim()
        model.primerNombre = edtPrimerNombre?.getText()
        model.segundoNombre = edtSegundoNombre?.getText()
        model.apellidoPaterno = edtApellidoPaterno?.getText()
        model.apellidoMaterno = edtApellidoMaterno?.getText()

        createModel()

        model.paso = Constant.NUMERO_CERO

        return model
    }

    override fun loadModel() {

        val model = view.getModel()

        edtPrimerNombre?.setText(model.primerNombre)
        edtSegundoNombre?.setText(model.segundoNombre)
        edtApellidoPaterno?.setText(model.apellidoPaterno)
        edtApellidoMaterno?.setText(model.apellidoMaterno)
        edtCorreo?.setText(model.correoElectronico)
        edtCorreoRepeat?.setText(model.correoElectronico)

        val fechaNacimiento = model.fechaNacimiento
                ?.changeDateFormat(formatLongT, formatShort2)

        edtFechaNacimiento?.setText(fechaNacimiento)

        edtDocumento?.setText(model.numeroDocumento)
        edtTelefonoCelular?.setText(model.celular)
        edtTelefonoFijo?.setText(model.telefono)

    }

    override fun validateDocument() = validate()


    override fun validate(): Boolean {

        generarRfc()

        val v = mutableListOf<Boolean?>()

        v += edtDocumento?.validate()
        v += edtPrimerNombre?.validate()
        v += edtSegundoNombre?.validate()
        v += edtApellidoPaterno?.validate()
        v += edtApellidoMaterno?.validate()
        v += edtFechaNacimiento?.validate()
        v += edtCorreo?.validate()
        v += edtCorreoRepeat?.validateRepeatEmail(edtCorreo.getText().toString())
        v += edtTelefonoCelular?.validate()
        v += edtTelefonoFijo?.validate()
        v += spnGenero?.validate()

        return v.all { it ?: false }
    }

    override fun initRequired() {
        spnGenero?.required = true
        edtPrimerNombre?.setRequired(true)
        edtApellidoPaterno?.setRequired(true)
        edtFechaNacimiento?.setRequired(true)
        edtDocumento?.setRequired(true)
        edtCorreo?.setIsRequired(true)
        edtCorreoRepeat?.setIsRequired(true)
        edtTelefonoCelular?.setRequired(true)
    }

    override fun initRestriction() {
        edtDocumento?.setRestriction(Filters.filterMaxLength(MX.DOCUMENTO_MAX_LENGHT))
        edtPrimerNombre?.setRestriction(Filters.filterLetters(MX.NOMBRES_MAX_LENGHT))
        edtSegundoNombre?.setRestriction(Filters.filterLetters(MX.NOMBRES_MAX_LENGHT))
        edtApellidoMaterno?.setRestriction(Filters.filterLetters(MX.APELLIDO_MAX_LENGHT))
        edtApellidoPaterno?.setRestriction(Filters.filterLetters(MX.APELLIDO_MAX_LENGHT))
        edtCorreo?.setRestriction(Filters.filterMaxLength(MX.CORREO_MAX_LENGHT))
        edtCorreoRepeat?.setRestriction(Filters.filterMaxLength(MX.CORREO_MAX_LENGHT))
        edtTelefonoCelular?.setRestriction(Filters.filterNumber(MX.TLF_CELULAR_MAX_LENGHT))
        edtTelefonoCelular?.inputType(InputType.TYPE_CLASS_NUMBER)
        edtTelefonoFijo?.setRestriction(Filters.filterNumber(MX.TLF_FIJO_MAX_LENGHT))
        edtTelefonoFijo?.inputType(InputType.TYPE_CLASS_NUMBER)
    }

    override fun initValidation() {

        spnGenero?.addValidations(
            V(context, Validation.NO_SELECTION, R.string.unete_paso4_valid_genero_empty)
        )

        edtFechaNacimiento?.minDate = Calendar.getInstance().minusYears(MX.EDAD_MAX)
        edtFechaNacimiento?.maxDate = Calendar.getInstance().minusYears(MX.EDAD_MIN)

        edtDocumento?.addV(V(context, EMPTY))
        edtPrimerNombre?.addV(V(context, EMPTY, R.string.unete_paso1_valid_primer_nombre_empty))
        edtSegundoNombre?.addV(V(context, EMPTY, R.string.unete_paso1_valid_segundo_nombre_empty))
        edtApellidoPaterno?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_apellido_paterno_empty)
        )
        edtApellidoMaterno?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_apellido_materno_empty)
        )
        edtFechaNacimiento?.addV(V(context, EMPTY, R.string.unete_paso1_valid_nacimiento_empty))
        edtFechaNacimiento?.addV(
            V(
                context, DATE_FORMAT, "$formatShort2|$formatLongT",
                R.string.unete_paso1_valid_date_wrong_format
            )
        )
        edtCorreo?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_correo_empty),
            V(context, EMAIL, R.string.unete_paso1_valid_correo_format)
        )
        edtCorreoRepeat?.addV(
            V(context, EMPTY, R.string.unete_paso1_valid_correo_empty),
            V(context, EMAIL, R.string.unete_paso1_valid_correo_format)
        )
        edtTelefonoCelular?.addV(
            V(context, EMPTY, R.string.unete_valid_celular_empty),
            V(
                context, Validation.MIN_LENGTH, MX.TLF_CELULAR_MIN_LENGHT,
                R.string.unete_valid_celular_format
            )
        )
        edtTelefonoFijo?.addV(
            V(context, EMPTY, R.string.unete_valid_telefono_empty),
            V(
                context, Validation.MIN_LENGTH, MX.TLF_FIJO_MIN_LENGHT,
                R.string.unete_valid_telefono_format
            )
        )

    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>(
                edtDocumento,
                edtPrimerNombre,
                edtSegundoNombre,
                edtApellidoPaterno,
                edtApellidoMaterno,
                edtFechaNacimiento)

        if (!enEdicion) {
            lst.addAll(mutableListOf(
                spnGenero,
                edtCorreo,
                edtCorreoRepeat,
                edtTelefonoCelular,
                edtTelefonoFijo
            )
            )
        }

        return lst
    }

    override fun showDuplicateMailError(message: String) {
        edtCorreo?.setEstado(BaseInput.ERROR, message)
        edtCorreoRepeat?.setEstado(BaseInput.ERROR, message)
    }

    override fun showDuplicateCelularError(message: String) {
        edtTelefonoCelular?.setEstado(BaseInput.ERROR, message)
    }

    override fun showGeneros(model: List<SexoModel>) {
        spnGenero?.load(model)
        val selected = view.getModel().sexo
        spnGenero?.selection<SexoModel> { it?.codigo == selected }
    }

    private fun generarRfc() {

        var rfctext = Constant.EMPTY_STRING

        val nombres = String.format("%s %s", edtPrimerNombre?.getText()?.trim(),
                edtSegundoNombre?.getText()?.trim())
        val apellidoPaterno = edtApellidoPaterno?.getText()?.trim().orEmpty()
        val apellidoMaterno = edtApellidoMaterno?.getText()?.trim().orEmpty()
        val fechaNacimiento = edtFechaNacimiento?.getText()?.trim().orEmpty()

        if (nombres.isNotBlank() && apellidoPaterno.isNotBlank()) {
            fechaNacimiento.split("/").takeIf { it.size == 3 }?.also { fecha ->

                val (dia, mes, annio) = fecha

                rfctext = RFC.Builder()
                        .nombres(nombres)
                        .apellidoPaterno(apellidoPaterno)
                        .apellidoMaterno(apellidoMaterno)
                        .fechaNacimiento(dia, mes, annio)
                        .build()
            }
        }

        edtDocumento?.setText(rfctext)

    }

}
