package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.ec

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.EC
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.PE
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.FuenteIngreso
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoTelefonico
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.ValidarPinModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso2
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.UnetePaso2View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.BaseInput
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.cambiarVisibilidad
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import biz.belcorp.salesforce.modules.postulants.utils.toInt
import kotlinx.android.synthetic.main.layout_validate_mobile_number.view.*
import kotlinx.android.synthetic.main.unete_ec_paso_2.view.*
import kotlinx.android.synthetic.main.unete_ec_paso_2.view.layout_pin_sms
import kotlinx.android.synthetic.main.unete_ec_paso_2.view.llConsultoraRecomienda
import kotlinx.android.synthetic.main.unete_ec_paso_2.view.swtConsultoraAnteriormente
import kotlinx.android.synthetic.main.unete_ec_paso_2.view.swtRecomiendaConsultora
import kotlinx.android.synthetic.main.unete_ec_paso_2.view.txtCodigoConsultoraRecomienda
import kotlinx.android.synthetic.main.unete_ec_paso_2.view.txtDireccion
import kotlinx.android.synthetic.main.unete_ec_paso_2.view.txtNombreConsultoraRecomienda
import kotlinx.android.synthetic.main.unete_ec_paso_2.view.txtReferencia
import kotlinx.android.synthetic.main.unete_ec_paso_2.view.txtTelefonoCelular
import kotlinx.android.synthetic.main.unete_ec_paso_2.view.txtTelefonoFijo
import kotlinx.android.synthetic.main.unete_pe_paso_2.view.*

@SuppressLint("ViewConstructor")
class UnetePaso2(mContext: Context, view: UnetePaso2View) :
    BaseUnetePaso<UnetePaso2View>(mContext, view), IUnetePaso2 {

    override fun initEvents() {

        swtRecomiendaConsultora?.setOnCheckedChangeListener { _, _ ->
            llConsultoraRecomienda?.cambiarVisibilidad()
        }

        txtCodigoConsultoraRecomienda?.onTextChanged {
            val value = it.toString().trim()
            if (value.length == EC.COD_CONSULTORA_MAX_LENGHT)
                view.getConsultoraRecomienda(value)
            else
                txtNombreConsultoraRecomienda?.setText(Constant.EMPTY_STRING)
        }

        txtNombreConsultoraRecomienda?.setEnable(false)

        initSMSEvents()
    }

    private fun initSMSEvents() {
        txtPINSMS?.onTextChanged {
            val value = it.toString().trim()
            btn_send_sms.isEnabled = value.length == EC.MAX_VALUE_PIN
            when {
                value.length != EC.MAX_VALUE_PIN -> txtPINSMS?.setEstado(BaseInput.NEUTRO)
                else -> txtPINSMS?.setEstado(BaseInput.OK)
            }
        }

        btn_retry_sms?.setOnClickListener {
            view.actualizarPrimerEnvio(true)
            view.validarSiCelularDuplicado()
        }

        btn_send_sms?.setOnClickListener {
            view.validarPin(createValidarPinModel())
        }
    }

    override fun getLayout() = R.layout.unete_ec_paso_2


    override fun createModel(): PostulanteModel? {

        val model = view.getModel() ?: PostulanteModel()

        val experiencia = swtConsultoraAnteriormente?.isChecked?.toInt()
        val recomienda = swtRecomiendaConsultora?.isChecked?.toInt()

        model.celular = txtTelefonoCelular?.getText()
        model.telefono = txtTelefonoFijo?.getText()
        model.direccion = txtDireccion?.getText()
        model.referencia = txtReferencia?.getText()
        model.tieneExperiencia = experiencia
        model.teRecomendoOtraConsultora = recomienda ?: Constant.NUMERO_CERO

        if (recomienda == 0) {
            model.codigoConsultoraRecomienda = Constant.EMPTY_STRING
            model.nombreConsultoraRecomienda = Constant.EMPTY_STRING
        } else {
            model.codigoConsultoraRecomienda = txtCodigoConsultoraRecomienda?.getText()
            model.nombreConsultoraRecomienda = txtNombreConsultoraRecomienda?.getText()
        }

        model.paso = Constant.NUMERO_DOS
        model.sincronizado = false

        return model
    }

    override fun loadModel() {

        val model = view.getModel() ?: PostulanteModel()

        txtTelefonoCelular?.setText(model.celular)
        txtTelefonoFijo?.setText(model.telefono)
        txtDireccion?.setText(model.direccion)
        txtReferencia?.setText(model.referencia)

        swtConsultoraAnteriormente?.isChecked = (model.tieneExperiencia == Constant.NUMERO_UNO)
        swtRecomiendaConsultora?.isChecked =
            view.expandirConsultoraRecomendante(model.codigoConsultoraRecomienda)

        validarCelular(model)

        agregarDatosDeConsultoraRecomendante(model)
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

    override fun showDuplicateCelularError(message: String) {
        txtTelefonoCelular?.setEstado(BaseInput.ERROR, message)
    }

    override fun showPINSMSFields() {
        layout_pin_sms.visible()
    }

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()
        val model = view.getModel() ?: PostulanteModel()

        v += txtTelefonoCelular?.validate()
        v += txtTelefonoFijo?.validate()
        v += txtDireccion?.validate()
        v += txtReferencia?.validate()

        if (swtRecomiendaConsultora?.isChecked == true && model.fuenteIngreso != FuenteIngreso.UB) {
            v += txtCodigoConsultoraRecomienda?.validate()
            v += txtNombreConsultoraRecomienda?.validate()
        }

        return v.all { it ?: false }
    }

    override fun initRequired() {
        txtTelefonoCelular?.setRequired(true)
        txtDireccion?.setRequired(true)
        txtReferencia?.setRequired(true)
        txtCodigoConsultoraRecomienda?.setRequired(true)
        txtNombreConsultoraRecomienda?.setRequired(true)
    }

    override fun initRestriction() {
        txtTelefonoCelular?.setRestriction(Filters.filterNumber(EC.TLF_CELULAR_MAX_LENGHT))
        txtTelefonoCelular?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoFijo?.setRestriction(Filters.filterNumber(EC.TLF_FIJO_MAX_LENGHT))
        txtTelefonoFijo?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtDireccion?.setRestriction(
            arrayOf(
                Filters.filterDireccion(),
                Filters.filterMaxLength(EC.DIRECCION_MAX_LENGHT)
            )
        )
        txtReferencia?.setRestriction(Filters.filterMaxLength(EC.DIRECCION_REFERENCIA_MAX_LENGHT))
        txtCodigoConsultoraRecomienda?.setRestriction(Filters.filterNumber(EC.COD_CONSULTORA_MAX_LENGHT))
        txtCodigoConsultoraRecomienda?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtPINSMS.setRestriction(Filters.filterNumber(EC.MAX_VALUE_PIN))
        txtPINSMS.inputType(InputType.TYPE_CLASS_NUMBER)
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

    override fun initValidation() {
        txtTelefonoCelular?.addV(
            V(context, EMPTY, R.string.unete_valid_celular_empty),
            V(context, MIN_LENGTH, EC.TLF_CELULAR_MIN_LENGHT, R.string.unete_valid_celular_format)
        )
        txtTelefonoFijo?.addV(
            V(context, EMPTY, R.string.unete_valid_obligatorio),
            V(context, MIN_LENGTH, EC.TLF_FIJO_MIN_LENGHT, R.string.unete_valid_celular_format)
        )
        txtDireccion?.addV(V(context, EMPTY, R.string.unete_valid_obligatorio))
        txtReferencia?.addV(V(context, EMPTY, R.string.unete_valid_referencia_empty))
        txtCodigoConsultoraRecomienda?.addV(
            V(context, EMPTY),
            V(
                context, MIN_LENGTH, EC.COD_CONSULTORA_MAX_LENGHT,
                R.string.consultora_recomendada_format
            )
        )
        txtNombreConsultoraRecomienda?.addV(V(context, EMPTY))
        txtNombreConsultoraRecomienda?.setEnable(false)
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {
        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    txtTelefonoCelular,
                    txtTelefonoFijo,
                    txtDireccion,
                    txtReferencia,
                    swtConsultoraAnteriormente,
                    swtRecomiendaConsultora,
                    txtCodigoConsultoraRecomienda,
                    txtNombreConsultoraRecomienda
                )
            )
        }

        return lst
    }

    private fun createValidarPinModel(): ValidarPinModel {

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
        txtTelefonoCelular?.setEstado(BaseInput.OK)
        txtPINSMS?.setCustomHint(context.getString(R.string.celular_validado))
        txtPINSMS?.clear()
        txtPINSMS?.setEnable(false)
        txtPINSMS?.setEstado(BaseInput.OK, context.getString(R.string.pin_correcto))
        view.actualizarPrimerEnvio(false)
    }

    override fun mostrarErrorPIN() {
        txtPINSMS.setEstado(BaseInput.ERROR, context.getString(R.string.pin_invalido))
    }
}
