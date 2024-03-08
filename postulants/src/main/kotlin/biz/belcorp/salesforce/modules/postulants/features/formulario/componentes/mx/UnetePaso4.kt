package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.mx

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.MX
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.FuenteIngreso
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.SexoModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso4
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4.UnetePaso4View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import kotlinx.android.synthetic.main.unete_mx_paso_4.view.*

@SuppressLint("ViewConstructor")
class UnetePaso4(mContext: Context, view: UnetePaso4View) :
    BaseUnetePaso<UnetePaso4View>(mContext, view), IUnetePaso4 {

    override fun initEvents() {

        seccionReferencias?.setOnClickListener {
            expReferencia?.toggle()
        }

        swtRecomiendaConsultora?.setOnCheckedChangeListener { _, isChecked ->
            llConsultoraRecomienda.visibility = if (isChecked) View.VISIBLE else View.GONE
            initRequired()
        }

        txtCodigoConsultoraRecomienda?.onTextChanged {
            val value = it.toString().trim()
            if (value.length == MX.CODIGO_CONSULTORA_MAX_DIGITOS) {
                view.getConsultoraRecomienda(value)
            } else {
                txtNombreConsultoraRecomienda?.setText(Constant.EMPTY_STRING)
            }
        }

    }

    override fun getLayout() = R.layout.unete_mx_paso_4

    override fun createModel(): PostulanteModel? {

        val model = view.getModel()

        val otrasMarcas = spnOtrasMarcas?.selected<TablaLogicaModel>()?.codigo
        val referenciaVinculo = spnVinculoReferencia?.selected<TablaLogicaModel>()?.codigo
        val nivelEducativo = spnNivelEducativo?.selected<TablaLogicaModel>()?.codigo
        val recomienda = if (swtRecomiendaConsultora?.isChecked == true) 1 else 0

        model.nivelEducativo = nivelEducativo
        model.codigoOtrasMarcas = otrasMarcas?.toInt()
        model.nombreFamiliar = txtNombreReferencia?.getText()
        model.apellidoFamiliar = txtApellidoReferencia?.getText()
        model.direccionFamiliar = txtDireccionReferencia?.getText()
        model.tipoVinculoFamiliar = referenciaVinculo?.toInt()
        model.celularFamiliar = txtTelefonoCelularReferencia?.getText()
        model.telefonoFamiliar = txtTelefonoFijoReferencia?.getText()
        model.teRecomendoOtraConsultora = recomienda

        if (recomienda == 0) {
            model.codigoConsultoraRecomienda = Constant.EMPTY_STRING
            model.nombreConsultoraRecomienda = Constant.EMPTY_STRING
        } else {
            model.codigoConsultoraRecomienda = txtCodigoConsultoraRecomienda?.getText()
            model.nombreConsultoraRecomienda = txtNombreConsultoraRecomienda?.getText()
        }

        model.paso = Constant.NUMERO_CUATRO
        model.sincronizado = false

        return model
    }

    override fun loadModel() {

        val model = view.getModel()

        txtNombreReferencia?.setText(model.nombreFamiliar)
        txtApellidoReferencia?.setText(model.apellidoFamiliar)
        txtDireccionReferencia?.setText(model.direccionFamiliar)
        txtTelefonoCelularReferencia?.setText(model.celularFamiliar)
        txtTelefonoFijoReferencia?.setText(model.telefonoFamiliar)

        swtRecomiendaConsultora?.isChecked =
            view.expandirConsultoraRecomendante(model.codigoConsultoraRecomienda)

        agregarDatosDeConsultoraRecomendante(model)
    }

    override fun showConsultoraRecomienda(consultora: String) {
        txtNombreConsultoraRecomienda?.setText(consultora)
    }

    override fun errorAlObtenerNombreConsultoraRecomendante() {
        val model = view.getModel()
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

    override fun showGeneros(generos: List<SexoModel>) {
        spnGenero?.load(generos)
        val selected = view.getModel().sexo
        spnGenero?.selection<SexoModel> { it?.codigo == selected }
    }

    override fun showNivelEducativo(nivelEducativo: List<TablaLogicaModel>) {
        spnNivelEducativo?.load(nivelEducativo)
        val selected = view.getModel().nivelEducativo
        spnNivelEducativo?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun showTipoVinculoFamiliar(tipoVinculo: List<TablaLogicaModel>) {
        spnVinculoReferencia?.load(tipoVinculo)
        val selected = view.getModel().tipoVinculoFamiliar?.toString()
        spnVinculoReferencia?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun showOtrasMarcas(tipoVinculo: List<TablaLogicaModel>) {
        spnOtrasMarcas?.load(tipoVinculo)
        val selected = view.getModel().codigoOtrasMarcas?.toString()
        spnOtrasMarcas?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun showCodigoConsultoraRecomienda(codigoConsultora: String) {
        txtCodigoConsultoraRecomienda?.setText(codigoConsultora)
    }

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()
        val model = view.getModel()

        v += txtNombreReferencia?.validate()
        v += txtApellidoReferencia?.validate()
        v += txtDireccionReferencia?.validate()
        v += txtTelefonoCelularReferencia?.validate()
        v += txtTelefonoFijoReferencia?.validate()
        v += spnVinculoReferencia?.validate()
        v += spnOtrasMarcas?.validate()
        v += spnNivelEducativo?.validate()

        if (swtRecomiendaConsultora?.isChecked == true && model.fuenteIngreso != FuenteIngreso.UB) {
            v += txtCodigoConsultoraRecomienda?.validate()
            v += txtNombreConsultoraRecomienda?.validate()
        }

        return v.all { it ?: false }
    }

    override fun initRequired() {

        spnNivelEducativo?.required = true
        spnOtrasMarcas?.required = true
        spnVinculoReferencia?.required = true
        txtNombreReferencia?.setRequired(true)
        txtApellidoReferencia?.setRequired(true)
        txtDireccionReferencia?.setRequired(true)
        txtTelefonoCelularReferencia?.setRequired(true)

        txtCodigoConsultoraRecomienda?.setRequired(swtRecomiendaConsultora?.isChecked!!)
        txtNombreConsultoraRecomienda?.setRequired(swtRecomiendaConsultora?.isChecked!!)

    }

    override fun initRestriction() {

        txtNombreReferencia?.setR(Filters.filterLetters(MX.NOMBRES_MAX_LENGHT))
        txtApellidoReferencia?.setR(Filters.filterLetters(MX.APELLIDO_MAX_LENGHT))
        txtDireccionReferencia?.setR(
            arrayOf(
                Filters.filterDireccion(),
                Filters.filterMaxLength(MX.DIRECCION_REFERENCIA_MAX_LENGHT)
            )
        )

        txtTelefonoFijoReferencia?.setR(Filters.filterNumber(MX.TLF_FIJO_MAX_LENGHT))
        txtTelefonoCelularReferencia?.setR(Filters.filterNumber(MX.TLF_CELULAR_MAX_LENGHT))

        txtCodigoConsultoraRecomienda?.setRestriction(Filters.filterNumber(MX.CODIGO_CONSULTORA_MAX_DIGITOS))
        txtCodigoConsultoraRecomienda?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtNombreConsultoraRecomienda?.setRestriction(Filters.filterLetters(MX.NOMBRES_MAX_LENGHT))
        validarFuenteIngreso()
    }

    private fun validarFuenteIngreso() {
        val model = view.getModel()
        if (model.fuenteIngreso.orEmpty() == FuenteIngreso.UB) {
            swtRecomiendaConsultora?.isEnabled = false
            swtRecomiendaConsultora?.isChecked = true
            txtCodigoConsultoraRecomienda?.setEnable(false)
            txtNombreConsultoraRecomienda?.setEnable(false)
        }
    }

    override fun initValidation() {

        spnNivelEducativo?.addValidations(
            V(context, Validation.NO_SELECTION, R.string.unete_paso4_valid_nivel_educativo_empty)
        )
        spnOtrasMarcas?.addValidations(
            V(context, Validation.NO_SELECTION, R.string.unete_valid_obligatorio)
        )
        spnVinculoReferencia?.addValidations(
            V(context, Validation.NO_SELECTION, R.string.unete_valid_obligatorio)
        )

        txtNombreReferencia?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_familiar_nombres_empty)
        )
        txtApellidoReferencia?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_familiar_apellidos_empty)
        )
        txtDireccionReferencia?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_familiar_direccion_empty)
        )

        txtTelefonoCelularReferencia?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_obligatorio),
            V(
                context, Validation.MIN_LENGTH, MX.TLF_CELULAR_MIN_LENGHT,
                R.string.unete_valid_formato_invalido
            )
        )
        txtTelefonoFijoReferencia?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_obligatorio),
            V(
                context, Validation.MIN_LENGTH, MX.TLF_FIJO_MIN_LENGHT,
                R.string.unete_valid_formato_invalido
            )
        )

        txtCodigoConsultoraRecomienda?.addV(
            V(context, Validation.EMPTY),
            V(
                context, Validation.MIN_LENGTH, MX.CODIGO_CONSULTORA_MAX_DIGITOS,
                R.string.consultora_recomendada_format
            )
        )
        txtNombreConsultoraRecomienda?.addV(V(context, Validation.EMPTY))
        txtNombreConsultoraRecomienda?.setEnable(false)
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    spnNivelEducativo,
                    spnOtrasMarcas,
                    spnVinculoReferencia,
                    txtNombreReferencia,
                    txtApellidoReferencia,
                    txtDireccionReferencia,
                    txtTelefonoCelularReferencia,
                    txtTelefonoFijoReferencia,
                    txtCodigoConsultoraRecomienda,
                    txtNombreConsultoraRecomienda
                )
            )
        }

        return lst
    }

}
