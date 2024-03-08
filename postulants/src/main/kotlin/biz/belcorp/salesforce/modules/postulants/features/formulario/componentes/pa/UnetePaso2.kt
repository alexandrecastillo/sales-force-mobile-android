package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pa

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.PA
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.FuenteIngreso
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoGEO
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoTelefonico
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.ValidarPinModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso2
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.UnetePaso2View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.BaseInput
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import biz.belcorp.salesforce.modules.postulants.utils.toInt
import kotlinx.android.synthetic.main.layout_validate_mobile_number.view.*
import kotlinx.android.synthetic.main.unete_pa_paso_2.view.*

@SuppressLint("ViewConstructor")
class UnetePaso2(mContext: Context, view: UnetePaso2View) :
    BaseUnetePaso<UnetePaso2View>(mContext, view), IUnetePaso2 {

    override fun initEvents() {

        spnProvincia?.onItemSelected {
            spnProvincia?.selected<ParametroUneteModel>()?.also {
                view.getLugarNivel2(it.idParametroUnete)
            }
        }

        spnDistrito?.onItemSelected {
            spnDistrito?.selected<ParametroUneteModel>()?.also {
                view.getLugarNivel3(it.idParametroUnete)
            }
        }

        spnCorregimiento?.onItemSelected {
            spnCorregimiento?.selected<ParametroUneteModel>()?.also {
                view.getLugarNivel4(it.idParametroUnete)
            }
        }

        swtRecomiendaConsultora?.setOnCheckedChangeListener { _, _ ->
            toggleRecomiendaConsultora()
        }

        txtCodigoConsultoraRecomienda?.onTextChanged {
            val value = it.toString().trim()
            if (value.length == PA.CODIGO_CONSULTORA_MAX_DIGITOS)
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
            btn_send_sms.isEnabled = value.length == PA.MAX_VALUE_PIN
            when {
                value.length != PA.MAX_VALUE_PIN -> txtPINSMS?.setEstado(BaseInput.NEUTRO)
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

    private fun toggleRecomiendaConsultora() {
        llConsultoraRecomienda?.visibility =
            if (llConsultoraRecomienda?.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    override fun initView() {
        super.initView()
        if (view.getConfiguracion()?.capturarxypaso2 == true) {
            ckbEstoyAqui?.visible()
        } else {
            ckbEstoyAqui?.gone()
        }
    }

    override fun getLayout() = R.layout.unete_pa_paso_2


    override fun showLugarNivel1(lst: List<ParametroUneteModel>) {
        spnProvincia?.load(lst)
        val selected = view.getModel()?.lugarPadre
        spnProvincia?.selection<ParametroUneteModel> { it?.nombre == selected }
    }

    override fun showLugarNivel2(lst: List<ParametroUneteModel>) {
        spnDistrito?.load(lst)
        val selected = view.getModel()?.lugarHijo
        spnDistrito?.selection<ParametroUneteModel> { it?.nombre == selected }
    }

    override fun showLugarNivel3(lst: List<ParametroUneteModel>) {
        spnCorregimiento?.load(lst)
        val selected =
            UneteAlgorithms.getValueFromDireccion(view.getModel()?.direccion, Constant.NUMERO_CERO)
        spnCorregimiento?.selection<ParametroUneteModel> { it?.nombre == selected }
    }

    override fun showLugarNivel4(lst: List<ParametroUneteModel>) {
        spnBarrio?.load(lst)
        val selected =
            UneteAlgorithms.getValueFromDireccion(view.getModel()?.direccion, Constant.NUMERO_UNO)
        spnBarrio?.selection<ParametroUneteModel> { it?.nombre == selected }
    }

    override fun createModel(): PostulanteModel? {

        val model = view.getModel() ?: PostulanteModel()

        val recomienda = swtRecomiendaConsultora?.isChecked?.toInt()

        model.celular = txtTelefonoCelular?.getText()
        model.telefono = txtTelefonoFijo?.getText()
        model.lugarPadre = spnProvincia?.selected<ParametroUneteModel>()?.nombre
        model.lugarHijo = spnDistrito?.selected<ParametroUneteModel>()?.nombre
        model.direccion = UneteAlgorithms.createDireccion(direccionDataList())
        model.teRecomendoOtraConsultora = recomienda ?: Constant.NUMERO_CERO
        model.estadoGEO = UneteEstadoGEO.OK.value.toString()
        model.respuestaGEO = spnBarrio?.selected<ParametroUneteModel>()?.descripcion
        model.codigoZona = UneteAlgorithms.decodeRespuestaGeo(model.respuestaGEO.orEmpty()).getOrNull(1)
        model.codigoSeccion = UneteAlgorithms.decodeRespuestaGeo(model.respuestaGEO.orEmpty()).getOrNull(2)
        model.codigoTerritorio = UneteAlgorithms.decodeRespuestaGeo(model.respuestaGEO.orEmpty()).getOrNull(3)
        model.capturarXYPostulante = ckbEstoyAqui?.isChecked!!

        if (recomienda == Constant.NUMERO_CERO) {
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

    private fun direccionDataList(): List<String> {

        val corregimiento = spnCorregimiento?.selected<ParametroUneteModel>()?.nombre.orEmpty()
        val barrio = spnBarrio?.selected<ParametroUneteModel>()?.nombre.orEmpty()
        val direccion = txtDireccion?.getText().orEmpty()

        return listOf(corregimiento, barrio, direccion)
    }

    override fun loadModel() {

        val model = view.getModel() ?: PostulanteModel()

        txtTelefonoCelular?.setText(model.celular)
        txtTelefonoFijo?.setText(model.telefono)
        txtDireccion?.setText(
            UneteAlgorithms.getValueFromDireccion(
                view.getModel()?.direccion,
                Constant.NUMERO_DOS
            )
        )

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

    private fun agregarDatosDeConsultoraRecomendante(model: PostulanteModel) {
        val codigoConsultora =
            view.obtenerCodigoODocumentoConsultoraRecomendante(model.codigoConsultoraRecomienda)
        txtCodigoConsultoraRecomienda?.setText(codigoConsultora)
        txtNombreConsultoraRecomienda?.setText(model.nombreConsultoraRecomienda)
    }

    private fun removerConsultoraRecomiendaTextWatcher() {
        txtCodigoConsultoraRecomienda?.onTextWatcher = null
    }

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()
        val model = view.getModel() ?: PostulanteModel()

        v += txtTelefonoCelular?.validate()
        v += txtTelefonoFijo?.validate()
        v += spnCorregimiento?.validate()
        v += spnDistrito?.validate()
        v += spnBarrio?.validate()
        v += spnProvincia?.validate()
        v += txtDireccion?.validate()

        if (swtRecomiendaConsultora?.isChecked == true && model.fuenteIngreso != FuenteIngreso.UB) {
            v += txtCodigoConsultoraRecomienda?.validate()
            v += txtNombreConsultoraRecomienda?.validate()
        }

        return v.all { it ?: false }
    }

    override fun initRequired() {

        spnDistrito?.required = true
        spnCorregimiento?.required = true
        spnProvincia?.required = true
        spnBarrio?.required = true
        txtTelefonoCelular?.setRequired(true)
        txtTelefonoFijo?.setRequired(false)
        txtDireccion?.setRequired(true)
        txtCodigoConsultoraRecomienda?.setRequired(true)
        txtNombreConsultoraRecomienda?.setRequired(true)

    }

    override fun initRestriction() {

        txtTelefonoCelular?.setRestriction(Filters.filterNumber(PA.CELULAR_TELEFONO_MAX_DIGITOS))
        txtTelefonoFijo?.setRestriction(Filters.filterNumber(PA.CELULAR_TELEFONO_MAX_DIGITOS))
        txtDireccion?.setRestriction(
            arrayOf(
                Filters.filterDireccion(),
                Filters.filterMaxLength(PA.DIRECCION_MAX_LENGHT)
            )
        )
        txtCodigoConsultoraRecomienda?.setRestriction(Filters.filterNumber(PA.CODIGO_CONSULTORA_MAX_DIGITOS))
        txtCodigoConsultoraRecomienda?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtPINSMS?.setRestriction(Filters.filterNumber(PA.MAX_VALUE_PIN))
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

    override fun initValidation() {

        spnCorregimiento?.addValidations(
            V(context, Validation.NO_SELECTION, R.string.unete_paso2_valid_corregimiento_select)
        )
        spnDistrito?.addValidations(
            V(context, Validation.NO_SELECTION, R.string.unete_paso2_valid_distrito_select)
        )
        spnBarrio?.addValidations(
            V(context, Validation.NO_SELECTION, R.string.unete_paso2_valid_barrio_select)
        )
        txtTelefonoCelular?.addV(
            V(context, EMPTY),
            V(
                context, MIN_LENGTH, PA.CELULAR_TELEFONO_MAX_DIGITOS,
                R.string.unete_valid_celular_format
            )
        )
        txtTelefonoCelular?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoFijo?.addV(
            V(context, EMPTY),
            V(
                context, MIN_LENGTH, PA.CELULAR_TELEFONO_MAX_DIGITOS,
                R.string.unete_valid_telefono_format
            )
        )
        txtTelefonoFijo?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtDireccion?.addV(V(context, EMPTY))
        txtCodigoConsultoraRecomienda?.addV(
            V(context, EMPTY),
            V(
                context, MIN_LENGTH, PA.CODIGO_CONSULTORA_MAX_DIGITOS,
                R.string.consultora_recomendada_format
            )
        )
        txtNombreConsultoraRecomienda?.addV(V(context, EMPTY))
        txtNombreConsultoraRecomienda?.setEnable(false)
    }

    override fun showDuplicateCelularError(message: String) {
        txtTelefonoCelular?.setEstado(BaseInput.ERROR, message)
    }

    override fun showPINSMSFields() {
        layout_pin_sms?.visible()
    }

    override fun validarCelular(model: PostulanteModel) {
        if (model.estadoTelefonico == UneteEstadoTelefonico.VALIDADO.value) {
            desactivarValidarPIN()
        } else if (model.estadoTelefonico == UneteEstadoTelefonico.POR_VALIDAR.value) {
            layout_pin_sms?.visible()
            view.actualizarPrimerEnvio(false)
        }
    }

    override fun mostrarErrorPIN() {
        txtPINSMS?.setEstado(BaseInput.ERROR, context.getString(R.string.pin_invalido))
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>(
            spnDistrito,
            spnCorregimiento,
            spnBarrio,
            spnProvincia
        )

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    spnDistrito,
                    spnCorregimiento,
                    spnBarrio,
                    spnProvincia,
                    txtTelefonoCelular,
                    txtTelefonoFijo,
                    txtDireccion,
                    swtRecomiendaConsultora,
                    txtCodigoConsultoraRecomienda,
                    txtNombreConsultoraRecomienda
                )
            )
        }

        return lst
    }

}
