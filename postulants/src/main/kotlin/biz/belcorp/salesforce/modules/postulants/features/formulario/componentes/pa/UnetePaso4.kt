package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pa

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.PA
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso4
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4.UnetePaso4View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import kotlinx.android.synthetic.main.unete_pa_paso_4.view.*

@SuppressLint("ViewConstructor")
class UnetePaso4(mContext: Context, view: UnetePaso4View) :
    BaseUnetePaso<UnetePaso4View>(mContext, view), IUnetePaso4 {

    override fun initEvents() {

        seccionMeta?.setOnClickListener {
            expMeta?.toggle()
        }

        chkMismaDireccion?.setOnCheckedChangeListener { _, b ->
            showDireccion(b)
        }

    }

    private fun showDireccion(checked: Boolean) {
        if (checked) {
            val direccion = UneteAlgorithms.getValueFromDireccion(
                view.getModel().direccion,
                Constant.NUMERO_DOS
            )
            txtDireccion?.setText(direccion)
            txtDireccion?.setEnable(false)
        } else {
            val model = view.getModel()
            txtDireccion?.setText(model.direccionEntrega)
            txtDireccion?.setEnable(true)
        }
    }

    override fun showTipoVinculoFamiliar(tipoVinculo: List<TablaLogicaModel>) {
        spnVinculoReferencia?.load(tipoVinculo)
        val selected = view.getModel().tipoVinculoFamiliar?.toString()
        spnVinculoReferencia?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun showTipoVinculoNoFamiliar(tipoVinculoNoFamiliar: List<TablaLogicaModel>) {
        spnVinculoReferenciaNoFamiliar?.load(tipoVinculoNoFamiliar)
        val selected = view.getModel().tipoVinculoNoFamiliar?.toString()
        spnVinculoReferenciaNoFamiliar?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun getLayout() = R.layout.unete_pa_paso_4


    override fun createModel(): PostulanteModel? {
        val model = view.getModel()

        val tipoVinculoReferencia = spnVinculoReferencia?.selected<TablaLogicaModel>()?.codigo
        val tipoVinculoNoFamiliar =
            spnVinculoReferenciaNoFamiliar?.selected<TablaLogicaModel>()?.codigo

        model.direccionEntrega = txtDireccion?.getText()

        model.nombreFamiliar = txtNombreReferencia?.getText()
        model.apellidoFamiliar = txtApellidoReferencia?.getText()
        model.tipoVinculoFamiliar = tipoVinculoReferencia?.toInt()
        model.celularFamiliar = txtTelefonoCelularReferencia?.getText()
        model.telefonoFamiliar = txtTelefonoFijoReferencia?.getText()

        model.nombreNoFamiliar = txtNombreReferenciaNoFamiliar?.getText()
        model.apellidoNoFamiliar = txtApellidoReferenciaNoFamiliar?.getText()
        model.tipoVinculoNoFamiliar = tipoVinculoNoFamiliar?.toInt()
        model.celularNoFamiliar = txtTelefonoCelularReferenciaNoFamiliar?.getText()
        model.telefonoNoFamiliar = txtTelefonoFijoReferenciaNoFamiliar?.getText()

        model.tipoMeta = null
        model.montoMeta = txtMetaMonto?.getText()
        model.paso = Constant.NUMERO_TRES
        model.sincronizado = false

        return model
    }

    override fun loadModel() {

        val model = view.getModel()

        txtDireccion?.setText(model.direccionEntrega)

        txtNombreReferencia?.setText(model.nombreFamiliar)
        txtApellidoReferencia?.setText(model.apellidoFamiliar)
        txtTelefonoCelularReferencia?.setText(model.celularFamiliar)
        txtTelefonoFijoReferencia?.setText(model.telefonoFamiliar)

        txtNombreReferenciaNoFamiliar?.setText(model.nombreNoFamiliar)
        txtApellidoReferenciaNoFamiliar?.setText(model.apellidoNoFamiliar)
        txtTelefonoCelularReferenciaNoFamiliar?.setText(model.celularNoFamiliar)
        txtTelefonoFijoReferenciaNoFamiliar?.setText(model.telefonoNoFamiliar)

        txtMetaMonto?.setText(model.montoMeta)

    }

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += txtNombreReferencia?.validate()
        v += txtApellidoReferencia?.validate()
        v += txtTelefonoCelularReferencia?.validate()
        v += txtTelefonoFijoReferencia?.validate()
        v += txtNombreReferenciaNoFamiliar?.validate()
        v += txtApellidoReferenciaNoFamiliar?.validate()
        v += txtTelefonoCelularReferenciaNoFamiliar?.validate()
        v += txtTelefonoFijoReferenciaNoFamiliar?.validate()
        v += txtMetaMonto?.validate()
        v += spnVinculoReferencia?.validate()
        v += spnVinculoReferenciaNoFamiliar?.validate()

        return v.all { it ?: false }
    }

    override fun initRequired() {

        txtDireccion?.setRequired(true)

        txtNombreReferencia?.setRequired(true)
        txtApellidoReferencia?.setRequired(true)
        spnVinculoReferencia?.required = true
        txtTelefonoCelularReferencia?.setRequired(true)
        txtTelefonoFijoReferencia?.setRequired(false)

        txtNombreReferenciaNoFamiliar?.setRequired(true)
        txtApellidoReferenciaNoFamiliar?.setRequired(true)
        spnVinculoReferenciaNoFamiliar?.required = true
        txtTelefonoCelularReferenciaNoFamiliar?.setRequired(true)
        txtTelefonoFijoReferenciaNoFamiliar?.setRequired(false)

    }

    override fun initRestriction() {

        txtDireccion?.setRestriction(
            arrayOf(
                Filters.filterDireccion(),
                Filters.filterMaxLength(PA.DIR_MAX_DIGITOS)
            )
        )

        txtNombreReferencia?.setRestriction(Filters.filterLetters(PA.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtApellidoReferencia?.setRestriction(Filters.filterLetters(PA.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtTelefonoCelularReferencia?.setRestriction(Filters.filterNumber(PA.CELULAR_TELEFONO_MAX_DIGITOS))
        txtTelefonoFijoReferencia?.setRestriction(Filters.filterNumber(PA.CELULAR_TELEFONO_MAX_DIGITOS))

        txtNombreReferenciaNoFamiliar?.setRestriction(Filters.filterLetters(PA.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtApellidoReferenciaNoFamiliar?.setRestriction(Filters.filterLetters(PA.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtTelefonoCelularReferenciaNoFamiliar?.setRestriction(Filters.filterNumber(PA.CELULAR_TELEFONO_MAX_DIGITOS))
        txtTelefonoFijoReferenciaNoFamiliar?.setRestriction(Filters.filterNumber(PA.CELULAR_TELEFONO_MAX_DIGITOS))

        txtMetaMonto?.setRestriction(Filters.filterNumber(PA.MONTO_MAX_DIGITOS))
    }

    override fun initValidation() {

        spnVinculoReferencia?.addValidations(V(context, Validation.NO_SELECTION))
        spnVinculoReferenciaNoFamiliar?.addValidations(V(context, Validation.NO_SELECTION))

        txtNombreReferencia?.addV(V(context, Validation.EMPTY))
        txtApellidoReferencia?.addV(V(context, Validation.EMPTY))
        txtTelefonoCelularReferencia?.addV(
            V(context, Validation.EMPTY),
            V(
                context, Validation.MIN_LENGTH, PA.CELULAR_TELEFONO_MAX_DIGITOS,
                R.string.unete_bo_paso4_celular_ref_familiar_format
            )
        )
        txtTelefonoCelularReferencia?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoFijoReferencia?.addV(
            V(context, Validation.EMPTY),
            V(
                context, Validation.MIN_LENGTH, PA.CELULAR_TELEFONO_MAX_DIGITOS,
                R.string.unete_bo_paso4_telefono_ref_familiar_format
            )
        )
        txtTelefonoFijoReferencia?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtNombreReferenciaNoFamiliar?.addV(V(context, Validation.EMPTY))
        txtApellidoReferenciaNoFamiliar?.addV(V(context, Validation.EMPTY))
        txtTelefonoCelularReferenciaNoFamiliar?.addV(
            V(context, Validation.EMPTY),
            V(
                context, Validation.MIN_LENGTH, PA.CELULAR_TELEFONO_MAX_DIGITOS,
                R.string.unete_bo_paso4_celular_ref_familiar_format
            )
        )
        txtTelefonoCelularReferenciaNoFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoFijoReferenciaNoFamiliar?.addV(
            V(context, Validation.EMPTY),
            V(
                context, Validation.MIN_LENGTH, PA.CELULAR_TELEFONO_MAX_DIGITOS,
                R.string.unete_bo_paso4_telefono_ref_familiar_format
            )
        )
        txtTelefonoFijoReferenciaNoFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtMetaMonto?.addV(V(context, Validation.EMPTY))
        txtMetaMonto?.inputType(InputType.TYPE_CLASS_NUMBER)
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    txtNombreReferencia,
                    txtApellidoReferencia,
                    txtTelefonoCelularReferencia,
                    txtTelefonoFijoReferencia,
                    txtNombreReferenciaNoFamiliar,
                    txtApellidoReferenciaNoFamiliar,
                    txtTelefonoCelularReferenciaNoFamiliar,
                    txtTelefonoFijoReferenciaNoFamiliar,
                    txtMetaMonto
                )
            )
        }

        return lst
    }

}
