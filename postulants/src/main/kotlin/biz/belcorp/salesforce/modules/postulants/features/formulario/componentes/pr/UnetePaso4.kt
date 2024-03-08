package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pr

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.PR
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso4
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4.UnetePaso4View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import kotlinx.android.synthetic.main.unete_pr_paso_4.view.*

@SuppressLint("ViewConstructor")
class UnetePaso4(mContext: Context, view: UnetePaso4View) :
        BaseUnetePaso<UnetePaso4View>(mContext, view), IUnetePaso4 {

    override fun showTipoVinculoFamiliar(tipoVinculo: List<TablaLogicaModel>) {
        spnRefFamiliarTipoVinculo?.load(tipoVinculo)
        val selected = view.getModel().tipoVinculoFamiliar?.toString()
        spnRefFamiliarTipoVinculo?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun getLayout() = R.layout.unete_pr_paso_4


    override fun createModel(): PostulanteModel? {
        val model = view.getModel()

        model.nombreFamiliar = txtRefFamiliarPrimerNombre?.getText()
        model.apellidoFamiliar = txtRefFamiliarApellidoPaterno?.getText()
        model.celularFamiliar = txtCelularRefFamiliar?.getText()
        model.telefonoFamiliar = txtTelefonoRefFamiliar?.getText()
        model.nombreNoFamiliar = txtNoFamiliarPrimerNombre?.getText()
        model.apellidoNoFamiliar = txtNoFamiliarApellidoPaterno?.getText()
        model.celularNoFamiliar = txtCelularNoFamiliar?.getText()
        model.telefonoNoFamiliar = txtTelefonoNoFamiliar?.getText()
        model.montoMeta = txtMetaMonto?.getText()
        model.tipoVinculoFamiliar = spnRefFamiliarTipoVinculo?.selected<TablaLogicaModel>()?.codigo?.toInt()

        model.paso = Constant.NUMERO_CUATRO
        model.sincronizado = false

        return model
    }

    override fun loadModel() {

        val model = view.getModel()

        txtRefFamiliarPrimerNombre?.setText(model.nombreFamiliar)
        txtRefFamiliarApellidoPaterno?.setText(model.apellidoFamiliar)
        txtCelularRefFamiliar?.setText(model.celularFamiliar)
        txtTelefonoRefFamiliar?.setText(model.telefonoFamiliar)
        txtNoFamiliarPrimerNombre?.setText(model.nombreNoFamiliar)
        txtNoFamiliarApellidoPaterno?.setText(model.apellidoNoFamiliar)
        txtCelularNoFamiliar?.setText(model.celularNoFamiliar)
        txtTelefonoNoFamiliar?.setText(model.telefonoNoFamiliar)
        txtMetaMonto?.setText(model.montoMeta)
    }

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += txtRefFamiliarPrimerNombre?.validate()
        v += txtRefFamiliarApellidoPaterno?.validate()
        v += txtCelularRefFamiliar?.validate()
        v += txtTelefonoRefFamiliar?.validate()
        v += txtNoFamiliarPrimerNombre?.validate()
        v += txtNoFamiliarApellidoPaterno?.validate()
        v += txtCelularNoFamiliar?.validate()
        v += txtTelefonoNoFamiliar?.validate()
        v += txtMetaMonto?.validate()
        v += spnRefFamiliarTipoVinculo?.validate()

        return v.all { it ?: false }
    }

    override fun initRequired() {
        txtRefFamiliarPrimerNombre?.setRequired(true)
        txtRefFamiliarApellidoPaterno?.setRequired(true)
        spnRefFamiliarTipoVinculo?.required = true
        txtCelularRefFamiliar?.setRequired(true)
        txtTelefonoRefFamiliar?.setRequired(false)
        txtNoFamiliarPrimerNombre?.setRequired(true)
        txtNoFamiliarApellidoPaterno?.setRequired(true)
        txtCelularNoFamiliar?.setRequired(true)
        txtTelefonoNoFamiliar?.setRequired(false)
        txtMetaMonto?.setRequired(false)
    }

    override fun initRestriction() {

        txtRefFamiliarPrimerNombre?.setRestriction(Filters.filterLetters(PR.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtRefFamiliarPrimerNombre?.inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
        txtRefFamiliarApellidoPaterno?.setRestriction(Filters.filterLetters(PR.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtRefFamiliarApellidoPaterno?.inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)

        txtCelularRefFamiliar?.setRestriction(Filters.filterNumber(PR.TLF_CELULAR_MAX_DIGITOS))
        txtCelularRefFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoRefFamiliar?.setRestriction(Filters.filterNumber(PR.TLF_CELULAR_MAX_DIGITOS))
        txtTelefonoRefFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtNoFamiliarPrimerNombre?.setRestriction(Filters.filterLetters(PR.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtNoFamiliarPrimerNombre?.inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
        txtNoFamiliarApellidoPaterno?.setRestriction(Filters.filterLetters(PR.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtNoFamiliarApellidoPaterno?.inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)

        txtCelularNoFamiliar?.setRestriction(Filters.filterNumber(PR.TLF_CELULAR_MAX_DIGITOS))
        txtCelularNoFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoNoFamiliar?.setRestriction(Filters.filterNumber(PR.TLF_CELULAR_MAX_DIGITOS))
        txtTelefonoNoFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtMetaMonto?.setRestriction(Filters.filterNumber(PR.MONTO_MAX_DIGITOS))
        txtMetaMonto?.inputType(InputType.TYPE_CLASS_NUMBER)
    }

    override fun initValidation() {

        txtRefFamiliarPrimerNombre?.addV(
            V(
                context, Validation.EMPTY,
                R.string.unete_paso4_valid_ref_familiar_primer_nombre_format
            )
        )
        txtRefFamiliarApellidoPaterno?.addV(
            V(
                context, Validation.EMPTY,
                R.string.unete_paso4_valid_ref_familiar_apellido_paterno_format
            )
        )

        txtCelularRefFamiliar?.addV(
            V(
                context, Validation.EMPTY,
                R.string.unete_paso4_valid_ref_familiar_celular_format
            )
        )
        txtTelefonoRefFamiliar?.addV(
            V(
                context, Validation.EMPTY,
                R.string.unete_paso4_valid_ref_familiar_telefono_format
            )
        )

        txtNoFamiliarPrimerNombre?.addV(
            V(
                context, Validation.EMPTY,
                R.string.unete_paso4_valid_no_familiar_primer_nombre_format
            )
        )
        txtNoFamiliarApellidoPaterno?.addV(
            V(
                context, Validation.EMPTY,
                R.string.unete_paso4_valid_no_familiar_apellido_paterno_format
            )
        )

        txtCelularNoFamiliar?.addV(
            V(
                context, Validation.EMPTY,
                R.string.unete_paso4_valid_no_familiar_celular_format
            )
        )
        txtTelefonoNoFamiliar?.addV(
            V(
                context, Validation.EMPTY,
                R.string.unete_paso4_valid_no_familiar_telefono_format
            )
        )

        txtMetaMonto?.addV(
            V(
                context, Validation.EMPTY,
                R.string.unete_paso4_valid_meta_monto_empty
            )
        )
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    txtRefFamiliarPrimerNombre,
                    txtRefFamiliarApellidoPaterno,
                    txtCelularRefFamiliar,
                    txtTelefonoRefFamiliar,
                    txtNoFamiliarPrimerNombre,
                    txtNoFamiliarApellidoPaterno,
                    txtCelularNoFamiliar,
                    txtTelefonoNoFamiliar,
                    txtMetaMonto,
                    spnRefFamiliarTipoVinculo
                )
            )
        }

        return lst
    }

}
