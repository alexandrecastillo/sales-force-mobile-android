package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cr

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CR
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoCrediticio
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
import kotlinx.android.synthetic.main.unete_cr_paso_4.view.*

@SuppressLint("ViewConstructor")
class UnetePaso4(mContext: Context, view: UnetePaso4View) :
    BaseUnetePaso<UnetePaso4View>(mContext, view), IUnetePaso4 {

    override fun initEvents() {
        seccionMeta?.setOnClickListener { expMeta?.toggle() }

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
        val selected = view.getModel().tipoVinculoFamiliar.toString()
        spnVinculoReferencia?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun showTipoVinculoNoFamiliar(tipoVinculoNoFamiliar: List<TablaLogicaModel>) {
        spnVinculoReferenciaNoFamiliar?.load(tipoVinculoNoFamiliar)
        val selected = view.getModel().tipoVinculoNoFamiliar.toString()
        spnVinculoReferenciaNoFamiliar?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun getLayout(): Int {
        return R.layout.unete_cr_paso_4
    }

    override fun createModel(): PostulanteModel? {
        val model = view.getModel()

        val tipoVinculoReferencia = spnVinculoReferencia?.selected<TablaLogicaModel>()?.codigo
        val tipoVinculoNoFamiliar =
            spnVinculoReferenciaNoFamiliar?.selected<TablaLogicaModel>()?.codigo

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

        model.direccionEntrega = txtDireccion?.getText()

        if (isRequiereFiador()) {

            model.primerNombreAval = txtFiadorPrimerNombre?.getText()
            model.apellidoPaternoAval = txtFiadorApellidoPaterno?.getText()
            model.tipoDocumentoAval = Constant.EMPTY_STRING
            model.numeroDocumentoAval = txtFiadorDocumento?.getText()
            model.direccionAval = txtFiadorDireccion?.getText()
            model.celularAval = txtFiadorTelefonoCelular?.getText()
            model.telefonoAval = txtFiadorTelefonoFijo?.getText()
            model.nombreEmpresaAval = txtFiadorEmpresaTrabaja?.getText()
        }

        model.tipoMeta = null
        model.montoMeta = txtMetaMonto?.getText()
        model.paso = Constant.NUMERO_TRES
        model.sincronizado = false


        return model
    }

    private fun isRequiereFiador(): Boolean {
        return view.getModel().estadoBurocrediticio == UneteEstadoCrediticio.CARTA_DESCARGO_Y_FIADOR.value.toString() ||
            view.getModel().estadoBurocrediticio == UneteEstadoCrediticio.CONDICIONADA_FIADOR.value.toString()
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


        if (isRequiereFiador()) {
            seccionFiador?.visible()
            expFiador?.visible()
            txtFiadorPrimerNombre?.setText(model.primerNombreAval)
            txtFiadorApellidoPaterno?.setText(model.apellidoPaternoAval)
            txtFiadorDocumento?.setText(model.numeroDocumentoAval)
            txtFiadorDireccion?.setText(model.direccionAval)
            txtFiadorTelefonoCelular?.setText(model.celularAval)
            txtFiadorTelefonoFijo?.setText(model.telefonoAval)
            txtFiadorEmpresaTrabaja?.setText(model.nombreEmpresaAval)
        }

        txtMetaMonto?.setText(model.montoMeta)
    }

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += txtFiadorDireccion?.validate()
        v += txtNombreReferencia?.validate()
        v += txtApellidoReferencia?.validate()
        v += txtTelefonoCelularReferencia?.validate()
        v += txtTelefonoFijoReferencia?.validate()
        v += txtNombreReferenciaNoFamiliar?.validate()
        v += txtApellidoReferenciaNoFamiliar?.validate()
        v += txtTelefonoCelularReferenciaNoFamiliar?.validate()
        v += txtTelefonoFijoReferenciaNoFamiliar?.validate()
        v += txtFiadorPrimerNombre?.validate()
        v += txtFiadorApellidoPaterno?.validate()
        v += txtFiadorDocumento?.validate()
        v += txtFiadorDireccion?.validate()
        v += txtFiadorTelefonoCelular?.validate()
        v += txtFiadorTelefonoFijo?.validate()
        v += txtFiadorEmpresaTrabaja?.validate()
        v += spnVinculoReferencia?.validate()
        v += spnVinculoReferenciaNoFamiliar?.validate()
        v += txtMetaMonto?.validate()


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

        if (isRequiereFiador()) {
            txtFiadorPrimerNombre?.setRequired(true)
            txtFiadorApellidoPaterno?.setRequired(true)
            txtFiadorDocumento?.setRequired(true)
            txtFiadorDireccion?.setRequired(true)
            txtFiadorTelefonoCelular?.setRequired(true)
            txtFiadorTelefonoFijo?.setRequired(false)
            txtFiadorEmpresaTrabaja?.setRequired(true)
        }
    }

    override fun initRestriction() {

        txtDireccion?.setRestriction(
            arrayOf(
                Filters.filterDireccion(),
                Filters.filterMaxLength(CR.DIR_MAX_DIGITOS)
            )
        )

        txtNombreReferencia?.setRestriction(Filters.filterLetters(CR.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtApellidoReferencia?.setRestriction(Filters.filterLetters(CR.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtTelefonoCelularReferencia?.setRestriction(Filters.filterNumber(CR.CELULAR_TELEFONO_MAX_DIGITOS))
        txtTelefonoFijoReferencia?.setRestriction(Filters.filterNumber(CR.CELULAR_TELEFONO_MAX_DIGITOS))

        txtNombreReferenciaNoFamiliar?.setRestriction(Filters.filterLetters(CR.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtApellidoReferenciaNoFamiliar?.setRestriction(Filters.filterLetters(CR.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtTelefonoCelularReferenciaNoFamiliar?.setRestriction(Filters.filterNumber(CR.CELULAR_TELEFONO_MAX_DIGITOS))
        txtTelefonoFijoReferenciaNoFamiliar?.setRestriction(Filters.filterNumber(CR.CELULAR_TELEFONO_MAX_DIGITOS))

        if (view.getModel().estadoBurocrediticio == UneteEstadoCrediticio.CARTA_DESCARGO_Y_FIADOR.value.toString()
            || view.getModel().estadoBurocrediticio == UneteEstadoCrediticio.CONDICIONADA_FIADOR.value.toString()
        ) {

            txtFiadorPrimerNombre?.setRestriction(Filters.filterLetters(CR.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
            txtFiadorApellidoPaterno?.setRestriction(Filters.filterLetters(CR.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
            txtFiadorEmpresaTrabaja?.setRestriction(Filters.filterLetters(CR.EMPRESA_MAX_DIGITOS))
            txtFiadorDocumento?.setRestriction(Filters.filterNumber(CR.DOCUMENTO_OTROS_MAX))
            txtFiadorDireccion?.setRestriction(Filters.filterLetters(CR.DIR_MAX_DIGITOS))
            txtFiadorTelefonoCelular?.setRestriction(Filters.filterNumber(CR.CELULAR_TELEFONO_MAX_DIGITOS))
            txtFiadorTelefonoFijo?.setRestriction(Filters.filterNumber(CR.CELULAR_TELEFONO_MAX_DIGITOS))
        }
        txtMetaMonto?.setRestriction(Filters.filterNumber(CR.MONTO_MAX_DIGITOS))
    }

    override fun initValidation() {

        spnVinculoReferencia?.addValidations(V(context, Validation.NO_SELECTION))
        spnVinculoReferenciaNoFamiliar?.addValidations(V(context, Validation.NO_SELECTION))

        txtFiadorDireccion?.addV(V(context, Validation.EMPTY))

        txtNombreReferencia?.addV(V(context, Validation.EMPTY))
        txtApellidoReferencia?.addV(V(context, Validation.EMPTY))
        txtTelefonoCelularReferencia?.addV(
            V(context, Validation.EMPTY),
            V(
                context, Validation.MIN_LENGTH, CR.CELULAR_TELEFONO_MAX_DIGITOS,
                R.string.unete_bo_paso4_celular_ref_familiar_format
            )
        )
        txtTelefonoCelularReferencia?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoFijoReferencia?.addV(
            V(context, Validation.EMPTY),
            V(
                context, Validation.MIN_LENGTH, CR.CELULAR_TELEFONO_MAX_DIGITOS,
                R.string.unete_bo_paso4_celular_ref_familiar_format
            )
        )
        txtTelefonoFijoReferencia?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtNombreReferenciaNoFamiliar?.addV(V(context, Validation.EMPTY))
        txtApellidoReferenciaNoFamiliar?.addV(V(context, Validation.EMPTY))
        txtTelefonoCelularReferenciaNoFamiliar?.addV(
            V(context, Validation.EMPTY),
            V(
                context, Validation.MIN_LENGTH, CR.CELULAR_TELEFONO_MAX_DIGITOS,
                R.string.unete_paso4_valid_no_familiar_celular_format
            )
        )
        txtTelefonoCelularReferenciaNoFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoFijoReferenciaNoFamiliar?.addV(
            V(context, Validation.EMPTY),
            V(
                context, Validation.MIN_LENGTH, CR.CELULAR_TELEFONO_MAX_DIGITOS,
                R.string.unete_paso4_valid_no_familiar_celular_format
            )
        )
        txtTelefonoFijoReferenciaNoFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)


        if (view.getModel().estadoBurocrediticio == UneteEstadoCrediticio.CARTA_DESCARGO_Y_FIADOR.value.toString()
            || view.getModel().estadoBurocrediticio == UneteEstadoCrediticio.CONDICIONADA_FIADOR.value.toString()
        ) {
            txtFiadorPrimerNombre?.addV(V(context, Validation.EMPTY))
            txtFiadorApellidoPaterno?.addV(V(context, Validation.EMPTY))
            txtFiadorDocumento?.addV(
                V(context, Validation.EMPTY),
                V(
                    context, Validation.MIN_LENGTH, CR.DOCUMENTO_OTROS_MIN,
                    R.string.unete_valid_pe_aval_telefono_format
                )
            )
            txtFiadorDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)
            txtFiadorDireccion?.addV(V(context, Validation.EMPTY))
            txtFiadorTelefonoCelular?.addV(
                V(context, Validation.EMPTY),
                V(
                    context, Validation.MIN_LENGTH, CR.CELULAR_TELEFONO_MAX_DIGITOS,
                    R.string.unete_valid_pe_aval_telefono_format
                )
            )
            txtFiadorTelefonoCelular?.inputType(InputType.TYPE_CLASS_NUMBER)
            txtFiadorTelefonoFijo?.addV(
                V(context, Validation.EMPTY),
                V(
                    context, Validation.MIN_LENGTH, CR.CELULAR_TELEFONO_MAX_DIGITOS,
                    R.string.unete_valid_pe_aval_telefono_format
                )
            )
            txtFiadorTelefonoFijo?.inputType(InputType.TYPE_CLASS_NUMBER)
            txtFiadorEmpresaTrabaja?.addV(
                V(context, Validation.EMPTY)
            )
        }

        txtMetaMonto?.addV(V(context, Validation.EMPTY))
        txtMetaMonto?.inputType(InputType.TYPE_CLASS_NUMBER)
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    txtFiadorDireccion,
                    txtNombreReferencia,
                    txtApellidoReferencia,
                    txtTelefonoCelularReferencia,
                    txtTelefonoFijoReferencia,
                    txtNombreReferenciaNoFamiliar,
                    txtApellidoReferenciaNoFamiliar,
                    txtTelefonoCelularReferenciaNoFamiliar,
                    txtTelefonoFijoReferenciaNoFamiliar,
                    txtFiadorPrimerNombre,
                    txtFiadorApellidoPaterno,
                    txtFiadorDocumento,
                    txtFiadorDireccion,
                    txtFiadorTelefonoCelular,
                    txtFiadorTelefonoFijo,
                    txtFiadorEmpresaTrabaja,
                    txtMetaMonto
                )
            )
        }

        return lst
    }
}
