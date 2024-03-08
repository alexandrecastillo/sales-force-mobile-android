package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pr

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.PE
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.PR
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.FuenteIngreso
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoTelefonico
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.ValidarPinModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso2
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.UnetePaso2Fragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.UnetePaso2View
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.paso2.UnetePrePaso2Fragment
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.BaseInput
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.NOTJUST0
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.cambiarVisibilidad
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import biz.belcorp.salesforce.modules.postulants.utils.toInt
import kotlinx.android.synthetic.main.layout_validate_mobile_number.view.*
import kotlinx.android.synthetic.main.unete_pe_paso_2.view.*
import kotlinx.android.synthetic.main.unete_pr_paso_2.view.*
import kotlinx.android.synthetic.main.unete_pr_paso_2.view.llConsultoraRecomienda
import kotlinx.android.synthetic.main.unete_pr_paso_2.view.swtConsultoraAnteriormente
import kotlinx.android.synthetic.main.unete_pr_paso_2.view.swtRecomiendaConsultora
import kotlinx.android.synthetic.main.unete_pr_paso_2.view.txtCodigoConsultoraRecomienda
import kotlinx.android.synthetic.main.unete_pr_paso_2.view.txtDireccion
import kotlinx.android.synthetic.main.unete_pr_paso_2.view.txtNombreConsultoraRecomienda
import kotlinx.android.synthetic.main.unete_pr_paso_2.view.txtReferencia
import kotlinx.android.synthetic.main.unete_pr_paso_2.view.txtTelefonoCelular
import kotlinx.android.synthetic.main.unete_pr_paso_2.view.txtTelefonoFijo

@SuppressLint("ViewConstructor")
class UnetePaso2(mContext: Context, view: UnetePaso2View) :
    BaseUnetePaso<UnetePaso2View>(mContext, view), IUnetePaso2 {

    override fun initEvents() {

        swtRecomiendaConsultora?.setOnCheckedChangeListener { _, _ ->
            llConsultoraRecomienda?.cambiarVisibilidad()
        }

        txtCodigoConsultoraRecomienda?.onTextChanged {
            val value = it.toString().trim()

            if (value.length == PR.CODIGO_CONSULTORA_MAX_DIGITOS)
                view.getConsultoraRecomienda(value)
            else
                txtNombreConsultoraRecomienda?.setText(Constant.EMPTY_STRING)
        }

        txtNombreConsultoraRecomienda?.setEnable(false)
    }

    override fun getLayout() = R.layout.unete_pr_paso_2

    override fun createModel(): PostulanteModel? {

        val model = view.getModel() ?: PostulanteModel()

        val experiencia = swtConsultoraAnteriormente?.isChecked?.toInt()
        val recomienda = swtRecomiendaConsultora?.isChecked?.toInt()
        val vieneDeFacebook =
            if (swtVieneDeFacebook?.isChecked!!) resources.getString(R.string.facebook) else Constant.EMPTY_STRING


        model.celular = txtTelefonoCelular?.getText()
        model.telefono = txtTelefonoFijo?.getText()
        model.direccion = txtDireccion?.getText()
        model.referencia = txtReferencia?.getText()

        var codigoPostal = txtCodigoPostal?.getText() ?: Constant.EMPTY_STRING
        while (codigoPostal.length < Constant.NUMERO_CINCO) codigoPostal = "0$codigoPostal"
        model.codigoPostal = codigoPostal

        model.tieneExperiencia = experiencia
        model.teRecomendoOtraConsultora = recomienda ?: Constant.NUMERO_CERO

        model.vieneDe = vieneDeFacebook

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

    override fun createPreModel(): PrePostulanteModel? {
        val model = view.getPreModel() ?: PrePostulanteModel()

        val experiencia = swtConsultoraAnteriormente?.isChecked?.toInt()

        val recomienda = swtRecomiendaConsultora?.isChecked?.toInt()
        val vieneDeFacebook = if (swtVieneDeFacebook?.isChecked!!) {
            when (view) {
                is UnetePaso2Fragment -> resources.getString(R.string.facebook)
                is UnetePrePaso2Fragment -> resources.getString(R.string.mshfacebook)
                else -> resources.getString(R.string.facebook)
            }
        } else Constant.EMPTY_STRING

        model.celular = txtTelefonoCelular?.getText()
        model.telefono = txtTelefonoFijo?.getText()
        model.direccion = txtDireccion?.getText()
        model.referencia = txtReferencia?.getText()

        var codigoPostal = txtCodigoPostal?.getText() ?: Constant.EMPTY_STRING
        while (codigoPostal.length < Constant.NUMERO_CINCO) codigoPostal = "0$codigoPostal"
        model.codigoPostal = codigoPostal

        model.tieneExperiencia = experiencia
        model.teRecomendoOtraConsultora = recomienda ?: Constant.NUMERO_CERO

        model.vieneDe = vieneDeFacebook

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

    override fun loadModel() {

        if (view is UnetePaso2Fragment) {

            val model = view.getModel()

            txtTelefonoCelular?.setText(model.celular)
            txtTelefonoFijo?.setText(model.telefono)
            txtDireccion?.setText(model.direccion)
            txtCodigoPostal?.setText(model.codigoPostal)
            txtReferencia?.setText(model.referencia)

            swtConsultoraAnteriormente?.isChecked = (model.tieneExperiencia == Constant.NUMERO_UNO)
            swtVieneDeFacebook?.isChecked =
                model.vieneDe.equals(resources.getString(R.string.facebook))
            swtRecomiendaConsultora?.isChecked =
                view.expandirConsultoraRecomendante(model.codigoConsultoraRecomienda)
            validarCelular(model)
            agregarDatosDeConsultoraRecomendante(model)

        } else if (view is UnetePrePaso2Fragment) {

            val model = view.getPreModel()

            txtTelefonoCelular?.setText(model.celular)
            txtTelefonoFijo?.setText(Constant.EMPTY_STRING)
            txtDireccion?.setText(model.direccion)
            txtCodigoPostal?.setText(model.codigoPostal)
            txtReferencia?.setText(Constant.EMPTY_STRING)

            swtConsultoraAnteriormente?.isChecked = (model.tieneExperiencia == Constant.NUMERO_UNO)
            swtVieneDeFacebook?.isChecked =
                (model.vieneDe.equals(resources.getString(R.string.facebook)) ||
                    model.vieneDe.equals(resources.getString(R.string.mshfacebook)))
            swtRecomiendaConsultora?.isChecked =
                view.expandirConsultoraRecomendante(model.codigoConsultoraRecomienda)
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
        v += txtCodigoPostal?.validate()

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
        txtCodigoPostal?.setRequired(true)
        txtReferencia?.setRequired(true)
        txtCodigoConsultoraRecomienda?.setRequired(true)
        txtNombreConsultoraRecomienda?.setRequired(true)

    }

    override fun initRestriction() {

        txtTelefonoCelular?.setRestriction(Filters.filterNumber(PR.TLF_CELULAR_MAX_DIGITOS))
        txtTelefonoCelular?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoFijo?.setRestriction(Filters.filterNumber(PR.TLF_CELULAR_MAX_DIGITOS))
        txtTelefonoFijo?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtDireccion?.setRestriction(arrayOf(Filters.filterDireccion(), Filters.filterMaxLength(PR.DIR_REF_MAX_DIGITOS)))
        txtReferencia?.setRestriction(arrayOf(Filters.filterAlphanumeric(), Filters.filterMaxLength(PR.DIR_REF_MAX_DIGITOS)))
        txtCodigoConsultoraRecomienda?.setRestriction(Filters.filterNumber(PR.CODIGO_CONSULTORA_MAX_DIGITOS))
        txtCodigoConsultoraRecomienda?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtCodigoPostal?.setRestriction(Filters.filterNumber(PR.CODIGO_POSTAL_MAX_DIGITOS))
        txtCodigoPostal?.inputType(InputType.TYPE_CLASS_NUMBER)
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
            V(context, MIN_LENGTH, PR.TLF_CELULAR_MAX_DIGITOS, R.string.unete_valid_celular_format)
        )
        txtTelefonoFijo?.addV(
            V(context, EMPTY, R.string.unete_valid_telefono_empty),
            V(context, MIN_LENGTH, PR.TLF_CELULAR_MAX_DIGITOS, R.string.unete_valid_telefono_format)
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
                context, MIN_LENGTH, PR.CODIGO_CONSULTORA_MAX_DIGITOS,
                R.string.consultora_recomendada_format
            )
        )

        txtCodigoPostal?.addV(
            V(context, EMPTY, R.string.unete_valid_codigo_postal_empty),
            V(context, NOTJUST0, R.string.unete_valid_codigo_postal_not_just_0)
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
                    txtCodigoPostal,
                    swtConsultoraAnteriormente,
                    swtRecomiendaConsultora,
                    swtVieneDeFacebook,
                    txtCodigoConsultoraRecomienda,
                    txtNombreConsultoraRecomienda
                )
            )
        }

        return lst
    }

}
