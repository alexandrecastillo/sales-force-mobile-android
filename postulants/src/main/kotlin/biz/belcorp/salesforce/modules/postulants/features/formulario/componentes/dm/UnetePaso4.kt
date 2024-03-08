package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.dm

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.DM
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso4
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4.UnetePaso4View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import kotlinx.android.synthetic.main.unete_dm_paso_4.view.*

@SuppressLint("ViewConstructor")
class UnetePaso4(mContext: Context, view: UnetePaso4View) :
        BaseUnetePaso<UnetePaso4View>(mContext, view), IUnetePaso4 {

    override fun showTipoVinculoFamiliar(tipoVinculo: List<TablaLogicaModel>) {
        spnRefFamiliarTipoVinculo?.load(tipoVinculo)
        val selected = view.getModel().tipoVinculoFamiliar.toString()
        spnRefFamiliarTipoVinculo?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun getLayout() = R.layout.unete_dm_paso_4

    override fun createModel(): PostulanteModel? {
        val model = view.getModel()

        model.nombreFamiliar = txtRefFamiliarPrimerNombre?.getText()
        model.apellidoFamiliar = txtRefFamiliarApellidoPaterno?.getText()
        model.celularFamiliar = txtCelularRefFamiliar?.getText()
        model.telefonoFamiliar = txtTelefonoRefFamiliar?.getText()
        model.nombreNoFamiliar = txtNoFamiliarPrimerNombre?.getText()
        model.apellidoNoFamiliar = txtNoFamiliarApellidoPaterno?.getText()
        model.celularNoFamiliar = txtNoFamiliarCelular?.getText()
        model.telefonoNoFamiliar = txtNoFamiliarTelefono?.getText()
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
        txtNoFamiliarCelular?.setText(model.celularNoFamiliar)
        txtNoFamiliarTelefono?.setText(model.telefonoNoFamiliar)
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
        v += txtNoFamiliarCelular?.validate()
        v += txtNoFamiliarTelefono?.validate()
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
        txtNoFamiliarCelular?.setRequired(true)
        txtNoFamiliarTelefono?.setRequired(false)
        txtMetaMonto?.setRequired(false)
    }

    override fun initRestriction() {

        txtRefFamiliarPrimerNombre?.setRestriction(Filters.filterLetters(DM.NOMBRES_REFERENCIA_MAX_DIGITOS))
        txtRefFamiliarPrimerNombre?.inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
        txtRefFamiliarApellidoPaterno?.setRestriction(Filters.filterLetters(DM.APELLIDOS_REFERENCIA_MAX_DIGITOS))
        txtRefFamiliarApellidoPaterno?.inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)

        txtCelularRefFamiliar?.setRestriction(Filters.filterNumber(DM.TLF_CELULAR_MAX_DIGITOS))
        txtCelularRefFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoRefFamiliar?.setRestriction(Filters.filterNumber(DM.TLF_CELULAR_MAX_DIGITOS))
        txtTelefonoRefFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtNoFamiliarPrimerNombre?.setRestriction(Filters.filterLetters(DM.NOMBRES_NO_FAMILIAR_MAX_DIGITOS))
        txtNoFamiliarPrimerNombre?.inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
        txtNoFamiliarApellidoPaterno?.setRestriction(Filters.filterLetters(DM.APELLIDOS_NO_FAMILIAR_MAX_DIGITOS))
        txtNoFamiliarApellidoPaterno?.inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)

        txtNoFamiliarCelular?.setRestriction(Filters.filterNumber(DM.TLF_CELULAR_MAX_DIGITOS))
        txtNoFamiliarCelular?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtNoFamiliarTelefono?.setRestriction(Filters.filterNumber(DM.TLF_CELULAR_MAX_DIGITOS))
        txtNoFamiliarTelefono?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtMetaMonto?.setRestriction(Filters.filterNumber(DM.MONTO_MAX_DIGITOS))
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
            V(context, Validation.EMPTY, R.string.unete_paso4_valid_ref_familiar_celular_format),
            V(
                context, Validation.INICIO, DM.TLF_CELULAR_DIGITO_INICIO,
                R.string.unete_paso4_valid_celular_format
            ),
            V(
                context, Validation.MIN_LENGTH, DM.TLF_CELULAR_MAX_DIGITOS,
                R.string.unete_bo_paso4_celular_ref_familiar_format
            )
        )
        txtTelefonoRefFamiliar?.addV(
            V(context, Validation.EMPTY, R.string.unete_paso4_valid_ref_familiar_telefono_format),
            V(
                context, Validation.INICIO, DM.TLF_CELULAR_DIGITO_INICIO,
                R.string.unete_paso4_valid_telefono_format
            ),
            V(
                context, Validation.MIN_LENGTH, DM.TLF_CELULAR_MAX_DIGITOS,
                R.string.unete_bo_paso4_celular_ref_familiar_format
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

        txtNoFamiliarCelular?.addV(
            V(context, Validation.EMPTY, R.string.unete_paso4_valid_no_familiar_celular_format),
            V(
                context, Validation.INICIO, DM.TLF_CELULAR_DIGITO_INICIO,
                R.string.unete_paso4_valid_celular_format
            ),
            V(
                context, Validation.MIN_LENGTH, DM.TLF_CELULAR_MAX_DIGITOS,
                R.string.unete_bo_paso4_celular_ref_familiar_format
            )
        )
        txtNoFamiliarTelefono?.addV(
            V(context, Validation.EMPTY, R.string.unete_paso4_valid_no_familiar_telefono_format),
            V(
                context, Validation.INICIO, DM.TLF_CELULAR_DIGITO_INICIO,
                R.string.unete_paso4_valid_telefono_format
            ),
            V(
                context, Validation.MIN_LENGTH, DM.TLF_CELULAR_MAX_DIGITOS,
                R.string.unete_bo_paso4_celular_ref_familiar_format
            )
        )

        txtMetaMonto?.addV(
            V(
                context,
                Validation.EMPTY,
                R.string.unete_paso4_valid_meta_monto_empty
            )
        )
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {
        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(mutableListOf(
                txtRefFamiliarPrimerNombre,
                txtRefFamiliarApellidoPaterno,
                txtCelularRefFamiliar,
                txtTelefonoRefFamiliar,
                txtNoFamiliarPrimerNombre,
                txtNoFamiliarApellidoPaterno,
                txtNoFamiliarCelular,
                txtNoFamiliarTelefono,
                txtMetaMonto,
                spnRefFamiliarTipoVinculo
            )
            )
        }

        return lst
    }
}
