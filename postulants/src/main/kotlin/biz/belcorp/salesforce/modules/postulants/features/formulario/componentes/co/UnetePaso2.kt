package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.co

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import biz.belcorp.mobile.components.core.extensions.getString
import biz.belcorp.salesforce.core.utils.DeviceUtil
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CO
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.FuenteIngreso
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstado
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
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.INICIO
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.LIMIT_LENGTH_7_10
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.NOT_INICIO
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.Constant.DIVIDE_SEPARATOR
import biz.belcorp.salesforce.modules.postulants.utils.cambiarVisibilidad
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import biz.belcorp.salesforce.modules.postulants.utils.toInt
import kotlinx.android.synthetic.main.layout_validate_mobile_number.view.*
import kotlinx.android.synthetic.main.unete_co_paso_2.view.*

@SuppressLint("ViewConstructor")
class UnetePaso2(mContext: Context, view: UnetePaso2View) :
    BaseUnetePaso<UnetePaso2View>(mContext, view), IUnetePaso2 {

    override fun initEvents() {

        swtRecomiendaConsultora?.setOnCheckedChangeListener { _, _ ->
            llConsultoraRecomienda?.cambiarVisibilidad()
        }

        txtCodigoConsultoraRecomienda?.onTextChanged {
            val value = it.toString().trim()
            if (value.length == CO.CEDULA_MAX_LENGHT)
                view.getConsultoraRecomienda(value)
            else
                txtNombreConsultoraRecomienda?.setText(Constant.EMPTY_STRING)
        }

        btn_retry_sms?.setOnClickListener {
            view.actualizarPrimerEnvio(true)
            view.reenviarSMS()
        }

        btn_send_sms?.setOnClickListener {
            view.validarPin(createValidarPinModel())
        }


        spnDepartamento?.onItemSelected {
            spnDepartamento?.selected<ParametroUneteModel>()?.also {
                view.getLugarNivel2(it.idParametroUnete)
            }
        }

        txtPINSMS?.onTextChanged {
            val value = it.toString().trim()
            btn_send_sms.isEnabled = value.length == CO.MAX_VALUE_PIN
            when {
                value.length != CO.MAX_VALUE_PIN -> txtPINSMS?.setEstado(NEUTRO)
                else -> txtPINSMS?.setEstado(OK)
            }
        }

        txtGetTerms?.setOnClickListener {
            view.downloadTerms()
        }

        txtNombreConsultoraRecomienda?.setEnable(false)

        chkAcceptTerms.setOnCheckedChangeListener { _, checked ->
            if (checked && txtErrorTerms.visibility == View.VISIBLE) txtErrorTerms.visibility =
                View.INVISIBLE
        }
    }

    override fun showPINSMSFields() {
        layout_pin_sms.visible()
    }

    override fun getLayout() = R.layout.unete_co_paso_2

    override fun showLugarNivel1(lst: List<ParametroUneteModel>) {
        spnDepartamento?.load(lst)
        val selected = view.getModel()?.lugarPadre
        spnDepartamento?.selection<ParametroUneteModel> { it?.nombre == selected }
    }

    override fun showLugarNivel2(lst: List<ParametroUneteModel>) {
        spnMunicipio?.load(lst)
        val selected = view.getModel()?.lugarHijo
        spnMunicipio?.selection<ParametroUneteModel> { it?.nombre == selected }
    }

    override fun createModel(): PostulanteModel? {

        val model = view.getModel() ?: PostulanteModel()
        val experiencia = swtConsultoraAnteriormente?.isChecked?.toInt()
        val recomienda = swtRecomiendaConsultora?.isChecked?.toInt()
        val lugarpadre = spnDepartamento?.selected<ParametroUneteModel>()?.nombre
        val lugarHijo = spnMunicipio?.selected<ParametroUneteModel>()?.nombre

        model.celular = txtTelefonoCelular?.getText()
        model.telefono = txtTelefonoFijo?.getText()

        model.lugarPadre = lugarpadre
        model.lugarHijo = lugarHijo
        model.direccion = createDireccion()
        model.ciudad = Constant.EMPTY_STRING
        model.tieneExperiencia = experiencia
        model.teRecomendoOtraConsultora = recomienda ?: Constant.NUMERO_CERO

        if (recomienda == Constant.NUMERO_CERO) {
            model.codigoConsultoraRecomienda = Constant.EMPTY_STRING
            model.nombreConsultoraRecomienda = Constant.EMPTY_STRING
        } else {
            model.codigoConsultoraRecomienda = txtCodigoConsultoraRecomienda?.getText()
            model.nombreConsultoraRecomienda = txtNombreConsultoraRecomienda?.getText()
        }

        model.estadoPostulante = UneteEstado.GESTION_SAC.estado.toString()
        model.paso = Constant.NUMERO_DOS
        model.sincronizado = false
        model.termsAceptados = true
        model.ip = DeviceUtil.getIPAddress(true)
        model.deviceId = DeviceUtil.getId(context)
        model.soMobile =
            "Android (Linux; version ${DeviceUtil.getVersionCode()}; sdk ${DeviceUtil.getVersionSDK()}; ${DeviceUtil.getModel()})"

        return model
    }

    override fun loadModel() {

        val model = view.getModel() ?: PostulanteModel()

        val direccion = model.direccion?.split(DIVIDE_SEPARATOR)

        txtTelefonoCelular?.setText(model.celular)
        txtTelefonoFijo?.setText(model.telefono)

        txtDireccion?.setText(
            obtenerDireccion(direccion)
        )
        txtBarrio?.setText(
            if (direccion?.size ?: Constant.NUMERO_CERO > Constant.NUMERO_CERO)
                direccion?.get(Constant.NUMERO_CERO)
            else
                Constant.EMPTY_STRING
        )

        swtConsultoraAnteriormente?.isChecked = (model.tieneExperiencia == Constant.NUMERO_UNO)
        swtRecomiendaConsultora?.isChecked =
            view.expandirConsultoraRecomendante(model.codigoConsultoraRecomienda)

        validarCelular(model)
        agregarDatosDeConsultoraRecomendante(model)
    }

    private fun obtenerDireccion(direccion: List<String>?): String? {
        return when {
            direccion?.size ?: Constant.NUMERO_CERO > Constant.NUMERO_DOS -> direccion?.get(Constant.NUMERO_DOS)
            direccion?.size ?: Constant.NUMERO_CERO > Constant.NUMERO_UNO -> direccion?.get(Constant.NUMERO_UNO)
            else -> direccion?.get(Constant.NUMERO_CERO)
        }
    }

    override fun showConsultoraRecomienda(consultora: String) {
        txtNombreConsultoraRecomienda?.setText(consultora)
    }

    override fun errorAlObtenerNombreConsultoraRecomendante() {
        val model = view.getModel() ?: PostulanteModel()
        removerConsultoraRecomiendaTextWatcher()
        agregarDatosDeConsultoraRecomendante(model)
        initEvents()
    }

    override fun validarCelular(model: PostulanteModel) {
        if (model.estadoTelefonico == UneteEstadoTelefonico.VALIDADO.value) {
            txtTelefonoCelular?.setEnable(false)
            txtTelefonoCelular?.setEstado(OK)
            txtPINSMS.setCustomHint(context.getString(R.string.celular_validado))
            txtPINSMS?.setEstado(OK)
            layout_pin_sms?.visible()
            desactivarValidarPIN()
        } else if (model.estadoTelefonico == UneteEstadoTelefonico.POR_VALIDAR.value) {
            layout_pin_sms?.visible()
            view.actualizarPrimerEnvio(false)
        }
    }

    private fun agregarDatosDeConsultoraRecomendante(model: PostulanteModel) {
        val codigoConsultora =
            view.obtenerCodigoODocumentoConsultoraRecomendante(model.codigoConsultoraRecomienda)
        txtCodigoConsultoraRecomienda?.setText(codigoConsultora)
        txtNombreConsultoraRecomienda?.setText(model.nombreConsultoraRecomienda)
    }

    private fun removerConsultoraRecomiendaTextWatcher() {
        txtCodigoConsultoraRecomienda?.onTextWatcher = null
    }

    private fun createDireccion(): String {
        var direccion = Constant.EMPTY_STRING

        direccion += txtBarrio?.getText()
        direccion += "|"
        direccion += txtDireccion?.getText()

        return direccion
    }

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()
        val model = view.getModel() ?: PostulanteModel()

        v += spnDepartamento?.validate()
        v += spnMunicipio?.validate()

        v += txtTelefonoCelular?.validate()
        v += txtTelefonoFijo?.validate()
        v += txtDireccion?.validate()

        if (swtRecomiendaConsultora?.isChecked == true && model.fuenteIngreso != FuenteIngreso.UB) {
            v += txtCodigoConsultoraRecomienda?.validate()
            v += txtNombreConsultoraRecomienda?.validate()
        }

        v += chkAcceptTerms?.isChecked ?: false

        validarAcceptTerms()

        return v.all { it ?: false }
    }

    override fun initRequired() {
        spnMunicipio?.required = true
        spnDepartamento?.required = true
        txtTelefonoCelular?.setRequired(true)
        txtDireccion?.setRequired(true)
        txtCodigoConsultoraRecomienda?.setRequired(true)
        txtNombreConsultoraRecomienda?.setRequired(true)
    }

    override fun initRestriction() {
        txtTelefonoCelular?.setRestriction(Filters.filterNumber(CO.TLF_CELULAR_MAX_LENGHT))
        txtTelefonoFijo?.setRestriction(Filters.filterNumber(CO.TLF_CELULAR_MAX_LENGHT))
        txtDireccion?.setRestriction(
            arrayOf(
                Filters.filterDireccion(),
                Filters.filterMaxLength(CO.DIRECCION_MAX_LENGHT)
            )
        )
        txtBarrio?.setRestriction(Filters.filterLetters(CO.DIRECCION_MAX_LENGHT))
        txtCodigoConsultoraRecomienda?.setRestriction(Filters.filterNumber(CO.COD_CONSULTORA_MAX_LENGHT))
        txtCodigoConsultoraRecomienda?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtPINSMS?.setRestriction(Filters.filterNumber(CO.MAX_VALUE_PIN))
        txtPINSMS?.inputType(InputType.TYPE_CLASS_NUMBER)
        validarFuenteIngreso()
    }

    private fun validarFuenteIngreso() {
        val model = view.getModel() ?: PostulanteModel()
        if (model.fuenteIngreso.orEmpty() == FuenteIngreso.UB) {
            swtRecomiendaConsultora?.isEnabled = false
            swtRecomiendaConsultora?.isChecked = true
            txtCodigoConsultoraRecomienda?.setEnable(false)
            txtNombreConsultoraRecomienda?.setEnable(false)
        }
    }

    private fun validarAcceptTerms() {
        if (!chkAcceptTerms.isChecked) txtErrorTerms.visibility = View.VISIBLE
    }

    override fun initValidation() {

        spnDepartamento?.addValidations(V(context, Validation.NO_SELECTION))
        spnMunicipio?.addValidations(V(context, Validation.NO_SELECTION))
        txtTelefonoCelular?.addV(
            V(context, EMPTY, R.string.unete_valid_celular_empty),
            V(context, MIN_LENGTH, CO.TLF_CELULAR_MIN_LENGHT, R.string.unete_valid_celular_format),
            V(context, INICIO, CO.TLF_CELULAR_DIGITO_INICIO, R.string.unete_valid_formato_invalido)
        )
        txtTelefonoCelular?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoFijo?.addV(
            V(context, EMPTY, R.string.unete_valid_obligatorio),
            V(context, LIMIT_LENGTH_7_10, R.string.unete_valid_telefono_format),
            V(
                context, NOT_INICIO, CO.TLF_CELULAR_DIGITO_NOT_INICIO,
                R.string.unete_valid_formato_invalido
            )
        )
        txtTelefonoFijo?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtDireccion?.addV(
            V(context, EMPTY, R.string.unete_valid_obligatorio)
        )
        txtCodigoConsultoraRecomienda?.addV(
            V(context, EMPTY),
            V(context, MIN_LENGTH, CO.COD_CONSULTORA_MAX_LENGHT, R.string.unete_valid_cedula_format)
        )
        txtNombreConsultoraRecomienda?.addV(V(context, EMPTY))
        txtNombreConsultoraRecomienda?.setEnable(false)

    }

    override fun showDuplicateCelularError(message: String) {
        txtTelefonoCelular?.setEstado(ERROR, message)
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {
        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    spnMunicipio,
                    spnDepartamento,
                    txtTelefonoCelular,
                    txtTelefonoFijo,
                    txtDireccion,
                    swtConsultoraAnteriormente,
                    swtRecomiendaConsultora,
                    txtCodigoConsultoraRecomienda,
                    txtNombreConsultoraRecomienda
                )
            )
        }

        return lst
    }

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
        txtTelefonoCelular?.setEnable(false)
        txtTelefonoCelular?.setEstado(OK)
        txtPINSMS?.setCustomHint(context.getString(R.string.celular_validado))
        txtPINSMS?.clear()
        txtPINSMS?.setEnable(false)
        txtPINSMS?.setEstado(OK, context.getString(R.string.pin_correcto))
        view.actualizarPrimerEnvio(false)

    }

    override fun mostrarErrorPIN() {
        txtPINSMS?.setEstado(ERROR, context.getString(R.string.pin_invalido))
    }

    override fun activarBotonEnviarSMS() {
        btn_retry_sms?.setText(R.string.reenviar)
        btn_retry_sms?.isEnabled = true
    }

    override fun desactivarBotonEnviarSMS() {
        btn_retry_sms?.isEnabled = false
    }

    override fun actualizarCuentaAtras(tiempo: String) {
        btn_retry_sms?.text = tiempo
    }

    override fun resetTextoBotonReenviarSMS() {
        btn_retry_sms?.setText(R.string.reenviar)
    }

}
