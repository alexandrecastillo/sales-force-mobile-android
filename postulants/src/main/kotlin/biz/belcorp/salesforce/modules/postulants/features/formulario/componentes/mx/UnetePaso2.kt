package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.mx

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.MX
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.MX.APROBADA_CON_CC
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.MX.VALID_SIN_CC_ZONA_NO_CONFIG
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoCrediticio
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoTelefonico
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.ValidarPinModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso2
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.UnetePaso2View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.BaseInput.Companion.ERROR
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.BaseInput.Companion.NEUTRO
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.BaseInput.Companion.OK
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.NO_SELECTION
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import kotlinx.android.synthetic.main.layout_validate_mobile_number.view.*
import kotlinx.android.synthetic.main.unete_mx_paso_2.view.*


@SuppressLint("ViewConstructor")
class UnetePaso2(context: Context, view: UnetePaso2View) : BaseUnetePaso<UnetePaso2View>(context, view),
    IUnetePaso2 {

    private var rangosCodigoPostal: List<ParametroUneteModel> = emptyList()

    private var validacionCC = false
    private var respuestaCC = Constant.EMPTY_STRING

    override fun getLayout() = R.layout.unete_mx_paso_2

    override fun initView() {
        super.initView()

        rangosCodigoPostal = emptyList()
    }

    override fun initEvents() {

        txtCodigoPostal?.onTextChanged {
            validateCodigoPostal()
        }

        spnEstado?.onItemSelected {
            spnEstado?.selected<ParametroUneteModel>()?.also {
                view.getLugarNivel2(it.idParametroUnete)
                validateCodigoPostal()
            }
        }

        edtDelegacion?.onTextChanged {
            val nombre = edtDelegacion.getValue<ParametroUneteModel>()?.nombre
            edtDelegacion?.getItems<ParametroUneteModel>()?.find { it.nombre == nombre }?.also {
                view.getLugarNivel3(it.idParametroUnete)
            }
            edtDelegacion?.validate()
        }

        edtCiudad?.onTextChanged {
            edtCiudad?.validate()
        }

        txtPINSMS?.onTextChanged {
            val value = it.toString().trim()
            btn_send_sms.isEnabled = value.length == MX.MAX_VALUE_PIN
            when {
                value.length != MX.MAX_VALUE_PIN -> txtPINSMS?.setEstado(NEUTRO)
                else -> txtPINSMS?.setEstado(OK)
            }
        }

        btn_retry_sms?.setOnClickListener {
            view.validarSiCelularDuplicado()
        }

        btn_send_sms?.setOnClickListener {
            view.validarPin(createValidarPinModel())
        }

    }

    override fun createModel(): PostulanteModel? {

        val model = view.getModel() ?: PostulanteModel()

        val estado = spnEstado?.selected<ParametroUneteModel>()?.nombre

        model.lugarPadre = estado
        model.lugarHijo = edtDelegacion?.getText()
        model.codigoPostal = txtCodigoPostal?.getText()
        model.direccion = createDireccion()
        model.referencia = txtEntreCalles?.getText()

        model.ciudad = getCiudad()

        model.requiereAprobacionSAC = true
        model.estadoBurocrediticio = UneteEstadoCrediticio.SIN_CONSULTAR.value.toString()

        if (esCirculoCredito()) {
            model.referenciaEntrega = getRespuestas()
        } else {
            model.referenciaEntrega = APROBADA_CON_CC
            model.estadoBurocrediticio = UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value.toString()
        }


        model.paso = 2
        model.sincronizado = false

        return model
    }

    override fun loadModel() {

        val model = view.getModel() ?: PostulanteModel()

        edtDelegacion?.setText(model.lugarHijo)

        model.direccion?.split("|")
                ?.takeIf { it.size >= 3 }
                ?.also {
                    edtCiudad?.setText(it[0])
                    edtColonia?.setText(it[1])
                    txtDireccion?.setText(it[2])
                }

        model.referenciaEntrega?.split("|")
                ?.takeIf { it.size >= 4 }
                ?.also {
                    swtTarjetaCredito.isChecked = it[0].toBoolean()
                    swtCreditoHipotecario.isChecked = it[1].toBoolean()
                    swtCreditoAutomotriz.isChecked = it[2].toBoolean()
                    respuestaCC = it[3]
                }

        txtCodigoPostal?.setText(model.codigoPostal)
        txtEntreCalles?.setText(model.referencia)

        validarCelular(model)

    }

    override fun validarCelular(model: PostulanteModel) {
        if (model.estadoTelefonico == UneteEstadoTelefonico.VALIDADO.value) {
            desactivarValidarPIN()
        } else if (model.estadoTelefonico == UneteEstadoTelefonico.POR_VALIDAR.value) {
            layout_pin_sms?.visible()
            view.actualizarPrimerEnvio(false)
        }
    }

    override fun showPINSMSFields() {
        layout_pin_sms.visible()
    }

    private fun getRespuestas(): String {
        return if (respuestaCC == VALID_SIN_CC_ZONA_NO_CONFIG) {
            APROBADA_CON_CC
        } else {
            generateCCAnswerFromArray()
        }
    }

    private fun generateCCAnswerFromArray(): String {
        return listOf(
            swtTarjetaCredito.isChecked.toValue(),
            swtCreditoHipotecario.isChecked.toValue(),
            swtCreditoAutomotriz.isChecked.toValue(),
            respuestaCC
        ).joinToString("|")
    }

    private fun createDireccion(): String {
        return listOf(
                edtCiudad?.getText(),
                edtColonia?.getText(),
                txtDireccion?.getText()
        ).joinToString("|")
    }

    override fun showValidacionCrediticia() {
        validacionCC = true
        lyValidacionCrediticia?.visible()
    }

    override fun saveRepuestaCC(respuesta: String) {
        respuestaCC = respuesta
    }

    override fun esCirculoCredito() = validacionCC


    override fun setRangosZonas(lst: List<ParametroUneteModel>) {
        rangosCodigoPostal = lst
        validateCodigoPostal()
    }

    override fun showLugarNivel1(lst: List<ParametroUneteModel>) {
        spnEstado?.load(lst.filter { it.descripcion != null })
        val selected = view.getModel()?.lugarPadre
        spnEstado?.selection<ParametroUneteModel> { it?.nombre == selected }
    }

    override fun showLugarNivel2(lst: List<ParametroUneteModel>) {
        edtDelegacion?.setItems(lst.filter { it.descripcion == null })
    }

    override fun showLugarNivel3(lst: List<ParametroUneteModel>) {
        edtCiudad?.setItems(lst.filter { it.descripcion == null })
    }

    override fun getClaveEstado(): String {
        return spnEstado?.selected<ParametroUneteModel>()?.descripcion.orEmpty()
    }

    override fun getCiudad(): String {
        return edtCiudad?.getText().orEmpty()
    }

    override fun getDireccion(): String {
        return txtDireccion?.getText().orEmpty()
    }

    override fun getTarjetaCredito() = swtTarjetaCredito?.isChecked ?: false

    override fun getCreditoHipotecario() = swtCreditoHipotecario?.isChecked ?: false

    override fun getCreditoAutomotriz() = swtCreditoAutomotriz?.isChecked ?: false

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += spnEstado?.validate()
        v += edtDelegacion?.validate()
        v += edtCiudad?.validate()
        v += txtCodigoPostal?.validate() ?: false && validateCodigoPostal()
        v += edtColonia?.validate()
        v += txtDireccion?.validate()
        v += txtEntreCalles?.validate()

        return v.all { it ?: false }
    }

    override fun initRequired() {
        spnEstado?.required = true
        edtDelegacion?.required = true
        edtCiudad?.required = true
        txtCodigoPostal?.setRequired(true)
        edtColonia?.setRequired(true)
        txtDireccion?.setRequired(true)
        txtEntreCalles?.setRequired(true)
    }

    override fun initRestriction() {
        txtCodigoPostal?.setRestriction(Filters.filterNumber(MX.CODIGO_POSTAL_MAX_LENGHT))
        txtCodigoPostal?.inputType(InputType.TYPE_CLASS_NUMBER)
        edtColonia?.setR(Filters.filterMaxLength(MX.COLONIA_MAX_LENGHT))
        txtDireccion?.setRestriction(arrayOf(Filters.filterDireccion(),
                Filters.filterMaxLength(MX.DIRECCION_REFERENCIA_MAX_LENGHT)))

        txtPINSMS?.setRestriction(Filters.filterNumber(MX.MAX_VALUE_PIN))
        txtPINSMS?.inputType(InputType.TYPE_CLASS_NUMBER)
    }

    override fun initValidation() {
        spnEstado?.addValidations(V(context, NO_SELECTION))
        edtDelegacion?.addValidations(V(context, EMPTY))
        edtCiudad?.addValidations(V(context, EMPTY))
        edtColonia?.addV(V(context, EMPTY))
        txtEntreCalles?.addV(V(context, EMPTY))
        txtCodigoPostal?.addV(
            V(context, EMPTY),
            V(
                context, MIN_LENGTH, MX.CODIGO_POSTAL_MIN_LENGHT,
                R.string.unete_valid_formato_invalido
            )
        )
        txtDireccion?.addV(V(context, EMPTY))
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>(
                spnEstado,
                edtDelegacion,
                edtCiudad,
                edtColonia,
                txtCodigoPostal,
                txtDireccion,
                txtEntreCalles,
                swtTarjetaCredito,
                swtCreditoHipotecario,
                swtCreditoAutomotriz
        )

        if (!enEdicion) {
            lst.addAll(mutableListOf())
        }

        return lst
    }

    private fun validateCodigoPostal(): Boolean {
        val estado = spnEstado.selected<ParametroUneteModel>()
        return if (rangosCodigoPostal.isEmpty() || estado?.idParametroUnete == -1) {
            txtCodigoPostal?.setEstado(NEUTRO)
            false
        } else if (estado == null || !validateRangoCodigoPostal(estado.descripcion!!)) {
            txtCodigoPostal?.setEstado(ERROR, context.getString(R.string.unete_paso2_codigo_postal_error))
            false
        } else {
            txtCodigoPostal?.setEstado(OK)
            true
        }
    }

    private fun validateRangoCodigoPostal(estadoClave: String): Boolean {

        val codigoPostal = txtCodigoPostal?.getText()
                ?.takeIf { it.length == MX.CODIGO_POSTAL_MAX_LENGHT }
                ?.toIntOrNull() ?: return false

        val rangos = rangosCodigoPostal
                .filter { it.descripcion == estadoClave }
                .mapNotNull { it.nombre?.toIntOrNull() }
                .takeIf { it.size >= 2 } ?: return false

        return codigoPostal in rangos.min()!!..rangos.max()!!
    }

    private fun String.toBoolean(): Boolean {
        return this == MX.TRUE
    }

    private fun Boolean.toValue() = if (this) MX.TRUE else MX.FALSE


    fun createValidarPinModel(): ValidarPinModel {

        val model = view.getModel() ?: PostulanteModel()

        val validarPINmodel = ValidarPinModel()
        validarPINmodel.pais = model.pais
        validarPINmodel.solicitudPostulanteID = model.solicitudPostulanteID
        validarPINmodel.numeroDocumento = model.numeroDocumento
        validarPINmodel.PIN = txtPINSMS?.getText()

        return validarPINmodel
    }

    override fun desactivarValidarPIN() {
        layout_pin_sms?.visible()
        btn_send_sms?.isEnabled = false
        btn_retry_sms?.isEnabled = false
        txtPINSMS?.setCustomHint(context.getString(R.string.celular_validado))
        txtPINSMS?.clear()
        txtPINSMS?.setEnable(false)
        txtPINSMS?.setEstado(OK, context.getString(R.string.pin_correcto))
        view.actualizarPrimerEnvio(false)
    }

    override fun mostrarErrorPIN() {
        txtPINSMS?.setEstado(ERROR, context.getString(R.string.pin_invalido))
    }

}
