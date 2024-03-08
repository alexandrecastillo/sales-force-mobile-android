package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.bo

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.BO
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.FuenteIngreso
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
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
import kotlinx.android.synthetic.main.unete_bo_paso_2.view.*

@SuppressLint("ViewConstructor")
class UnetePaso2(mContext: Context, view: UnetePaso2View) :
    BaseUnetePaso<UnetePaso2View>(mContext, view), IUnetePaso2 {

    override fun initEvents() {

        swtRecomiendaConsultora?.setOnCheckedChangeListener { _, _ ->
            llConsultoraRecomienda?.cambiarVisibilidad()
        }

        txtCodigoConsultoraRecomienda?.onTextChanged {
            val value = it.toString().trim()
            if (value.length == BO.CODIGO_CONSULTORA_MAX_DIGITOS)
                view.getConsultoraRecomienda(value)
            else
                txtNombreConsultoraRecomienda?.setText(Constant.EMPTY_STRING)
        }

    }

    override fun getLayout() = R.layout.unete_bo_paso_2

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
        txtTelefonoFijo?.setRequired(true)
        txtDireccion?.setRequired(true)
        txtReferencia?.setRequired(true)
        txtCodigoConsultoraRecomienda?.setRequired(true)
        txtNombreConsultoraRecomienda?.setRequired(true)

    }

    override fun initRestriction() {

        txtTelefonoCelular?.setRestriction(Filters.filterNumber(BO.CELULAR_MAX_DIGITOS))
        txtTelefonoCelular?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoFijo?.setRestriction(Filters.filterNumber(BO.TLF_MAX_DIGITOS))
        txtTelefonoFijo?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtDireccion?.setRestriction(
            arrayOf(
                Filters.filterDireccion(),
                Filters.filterMaxLength(BO.DIR_REF_MAX_DIGITOS)
            )
        )
        txtReferencia?.setRestriction(Filters.filterMaxLength(BO.DIR_REF_MAX_DIGITOS))
        txtCodigoConsultoraRecomienda?.setRestriction(Filters.filterNumber(BO.CODIGO_CONSULTORA_MAX_DIGITOS))
        txtCodigoConsultoraRecomienda?.inputType(InputType.TYPE_CLASS_NUMBER)
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
            V(context, MIN_LENGTH, BO.CELULAR_MAX_DIGITOS, R.string.unete_valid_celular_format)
        )
        txtTelefonoFijo?.addV(
            V(context, EMPTY, R.string.unete_valid_telefono_empty),
            V(context, MIN_LENGTH, BO.TLF_MAX_DIGITOS, R.string.unete_valid_telefono_format)
        )
        txtDireccion?.addV(
            V(context, EMPTY, R.string.unete_valid_direccion_empty)
        )
        txtReferencia?.addV(
            V(context, EMPTY, R.string.unete_valid_referencia_empty)
        )

        txtCodigoConsultoraRecomienda?.addV(
            V(context, EMPTY),
            V(
                context, MIN_LENGTH, BO.CODIGO_CONSULTORA_MAX_DIGITOS,
                R.string.unete_valid_telefono_format
            )
        )
        txtNombreConsultoraRecomienda?.addV(V(context, EMPTY))
        txtNombreConsultoraRecomienda?.setEnable(false)

    }

    override fun showDuplicateCelularError(message: String) {
        txtTelefonoCelular?.setEstado(BaseInput.ERROR, message)
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

}
