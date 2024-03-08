package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.ec

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.EC
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoDocumento
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso4
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4.UnetePaso4View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UAlgorithms
import kotlinx.android.synthetic.main.unete_ec_paso_4.view.*

@SuppressLint("ViewConstructor")
class UnetePaso4(mContext: Context, view: UnetePaso4View) :
    BaseUnetePaso<UnetePaso4View>(mContext, view), IUnetePaso4 {

    override fun initEvents() {

        seccionReferencias?.setOnClickListener {
            expReferencia?.toggle()
        }

        seccionReferenciasNoFamiliar?.setOnClickListener {
            expReferenciaNoFamiliar?.toggle()
        }

        seccionMeta?.setOnClickListener {
            expMeta?.toggle()
        }

    }

    override fun getLayout() = R.layout.unete_ec_paso_4


    override fun showOrigenIngreso(origenesIngreso: List<TablaLogicaModel>) {
        spnOrigenIngreso?.load(origenesIngreso)
        val selected = view.getModel().origenIngreso
        spnOrigenIngreso?.selection<TablaLogicaModel> { it?.codigo == selected.toString() }
    }

    override fun showTipoPersona(tiposPersona: List<TablaLogicaModel>) {
        spnTipoPersona?.load(tiposPersona)
        val selected = view.getModel().tipoPersona
        spnTipoPersona?.selection<TablaLogicaModel> { it?.codigo == selected.toString() }
    }

    override fun showNivelEducativo(nivelEducativo: List<TablaLogicaModel>) {
        spnNivelEducativo?.load(nivelEducativo)
        val selected = view.getModel().nivelEducativo
        spnNivelEducativo?.selection<TablaLogicaModel> { it?.codigo == selected.toString() }
    }

    override fun showEstadoCivil(estadoCivil: List<TablaLogicaModel>) {
        spnEstadoCivil?.load(estadoCivil)
        val selected = view.getModel().estadoCivil
        spnEstadoCivil?.selection<TablaLogicaModel> { it?.codigo == selected.toString() }
    }

    override fun showTipoVinculoFamiliar(tipoVinculo: List<TablaLogicaModel>) {
        spnVinculoReferencia?.load(tipoVinculo)
        val selected = view.getModel().tipoVinculoFamiliar
        spnVinculoReferencia?.selection<TablaLogicaModel> { it?.codigo == selected.toString() }
    }

    override fun createModel(): PostulanteModel? {

        val m = view.getModel()

        val referenciaVinculo = spnVinculoReferencia?.selected<TablaLogicaModel>()?.codigo
        val nivelEducativo = spnNivelEducativo?.selected<TablaLogicaModel>()?.codigo
        val estadoCivil = spnEstadoCivil?.selected<TablaLogicaModel>()?.codigo
        val tipoPersona = spnTipoPersona?.selected<TablaLogicaModel>()?.codigo
        val origenIngreso = spnOrigenIngreso?.selected<TablaLogicaModel>()?.codigo

        m.estadoCivil = estadoCivil
        m.nivelEducativo = nivelEducativo
        m.tipoPersona = tipoPersona?.toInt()
        m.origenIngreso = origenIngreso?.toInt()

        m.nombreFamiliar = txtNombreReferencia?.getText()
        m.apellidoFamiliar = txtApellidoReferencia?.getText()
        m.tipoVinculoFamiliar = referenciaVinculo?.toInt()
        m.celularFamiliar = txtTelefonoCelularReferencia?.getText()
        m.telefonoFamiliar = txtTelefonoFijoReferencia?.getText()

        m.nombreNoFamiliar = txtNombreReferenciaNoFamiliar?.getText()
        m.apellidoNoFamiliar = txtApellidoReferenciaNoFamiliar?.getText()
        m.celularNoFamiliar = txtTelefonoCelularReferenciaNoFamiliar?.getText()
        m.telefonoNoFamiliar = txtTelefonoFijoReferenciaNoFamiliar?.getText()

        m.primerNombreAval = Constant.EMPTY_STRING
        m.segundoNombreAval = Constant.EMPTY_STRING
        m.apellidoPaternoAval = Constant.EMPTY_STRING
        m.apellidoMaternoAval = Constant.EMPTY_STRING
        m.tipoDocumentoAval = UneteTipoDocumento.EC_CEDULA.codigo
        m.numeroDocumentoAval = Constant.EMPTY_STRING
        m.direccionAval = Constant.EMPTY_STRING
        m.celularAval = Constant.EMPTY_STRING
        m.telefonoAval = Constant.EMPTY_STRING

        m.tipoMeta = null
        m.montoMeta = txtMetaMonto?.getText()

        m.paso = Constant.NUMERO_CUATRO
        m.sincronizado = false

        return m
    }

    override fun loadModel() {

        val m = view.getModel()

        txtNombreReferencia?.setText(m.nombreFamiliar)
        txtApellidoReferencia?.setText(m.apellidoFamiliar)
        txtTelefonoCelularReferencia?.setText(m.celularFamiliar)
        txtTelefonoFijoReferencia?.setText(m.telefonoFamiliar)

        txtNombreReferenciaNoFamiliar?.setText(m.nombreNoFamiliar)
        txtApellidoReferenciaNoFamiliar?.setText(m.apellidoNoFamiliar)
        txtTelefonoCelularReferenciaNoFamiliar?.setText(m.celularNoFamiliar)
        txtTelefonoFijoReferenciaNoFamiliar?.setText(m.telefonoNoFamiliar)


        txtMetaMonto?.setText(m.montoMeta)

    }

    override fun initRequired() {

        val m = view.getModel()

        spnOrigenIngreso?.required = true
        spnTipoPersona?.required = true
        spnEstadoCivil?.required = true
        spnNivelEducativo?.required = true

        txtNombreReferencia?.setRequired(true)
        txtApellidoReferencia?.setRequired(true)
        spnVinculoReferencia?.required = true
        txtTelefonoCelularReferencia?.setRequired(true)

        txtNombreReferenciaNoFamiliar?.setRequired(true)
        txtApellidoReferenciaNoFamiliar?.setRequired(true)
        txtTelefonoCelularReferenciaNoFamiliar?.setRequired(true)

        txtMetaMonto?.setRequired(true)

    }

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += spnEstadoCivil?.validate()
        v += spnNivelEducativo?.validate()
        v += spnOrigenIngreso?.validate()
        v += spnTipoPersona?.validate()

        v += txtNombreReferencia?.validate()
        v += txtApellidoReferencia?.validate()
        v += spnVinculoReferencia?.validate()
        v += txtTelefonoCelularReferencia?.validate()
        v += txtTelefonoFijoReferencia?.validate()

        v += txtNombreReferenciaNoFamiliar?.validate()
        v += txtApellidoReferenciaNoFamiliar?.validate()
        v += txtTelefonoCelularReferenciaNoFamiliar?.validate()
        v += txtTelefonoFijoReferenciaNoFamiliar?.validate()

        v += txtMetaMonto?.validate()

        return v.all { it ?: false }
    }

    override fun initRestriction() {

        txtNombreReferencia?.setR(Filters.filterLetters(EC.NOMBRE_REFERENCIA_MAX_LENGHT))
        txtApellidoReferencia?.setR(Filters.filterLetters(EC.APELLIDO_REFERENCIA_MAX_LENGHT))
        txtTelefonoFijoReferencia?.setR(Filters.filterNumber(EC.TLF_FIJO_REFERENCIA_MAX_LENGHT))
        txtTelefonoFijoReferencia?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoCelularReferencia?.setR(Filters.filterNumber(EC.TLF_CELULAR_REFERENCIA_MAX_LENGHT))
        txtTelefonoCelularReferencia?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtNombreReferenciaNoFamiliar?.setR(Filters.filterLetters(EC.NOMBRE_REFERENCIA_MAX_LENGHT))
        txtApellidoReferenciaNoFamiliar?.setR(Filters.filterLetters(EC.APELLIDO_REFERENCIA_MAX_LENGHT))
        txtTelefonoFijoReferenciaNoFamiliar?.setR(Filters.filterNumber(EC.TLF_FIJO_REFERENCIA_MAX_LENGHT))
        txtTelefonoFijoReferenciaNoFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoCelularReferenciaNoFamiliar?.setR(Filters.filterNumber(EC.TLF_CELULAR_REFERENCIA_MAX_LENGHT))
        txtTelefonoCelularReferenciaNoFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtMetaMonto?.setR(Filters.filterNumber(EC.META_MONTO_MAX_LENGHT))
        txtMetaMonto?.inputType(InputType.TYPE_CLASS_NUMBER)

    }

    override fun initValidation() {

        spnTipoPersona?.addValidations(V(context, Validation.NO_SELECTION))
        spnEstadoCivil?.addValidations(V(context, Validation.NO_SELECTION))
        spnNivelEducativo?.addValidations(V(context, Validation.NO_SELECTION))
        spnOrigenIngreso?.addValidations(V(context, Validation.NO_SELECTION))
        spnVinculoReferencia?.addValidations(V(context, Validation.NO_SELECTION))

        txtNombreReferencia?.addV(
            V(
                context, Validation.EMPTY,
                R.string.unete_valid_familiar_nombres_empty
            )
        )
        txtApellidoReferencia?.addV(
            V(
                context, Validation.EMPTY,
                R.string.unete_valid_familiar_apellidos_empty
            )
        )
        txtTelefonoCelularReferencia?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_obligatorio),
            V(
                context, Validation.MIN_LENGTH, EC.TLF_CELULAR_REFERENCIA_MIN_LENGHT,
                R.string.unete_valid_formato_invalido
            )
        )
        txtTelefonoFijoReferencia?.addV(
            V(
                context, Validation.MIN_LENGTH, EC.TLF_FIJO_MAX_LENGHT,
                R.string.unete_valid_formato_invalido
            ),
            V(context, Validation.EMPTY, R.string.unete_valid_obligatorio)
        )

        txtNombreReferenciaNoFamiliar?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_obligatorio)
        )
        txtApellidoReferenciaNoFamiliar?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_obligatorio)
        )
        txtTelefonoCelularReferenciaNoFamiliar?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_obligatorio),
            V(
                context, Validation.MIN_LENGTH, EC.TLF_CELULAR_REFERENCIA_MIN_LENGHT,
                R.string.unete_valid_formato_invalido
            )
        )
        txtTelefonoFijoReferenciaNoFamiliar?.addV(
            V(
                context, Validation.MIN_LENGTH, EC.TLF_FIJO_MAX_LENGHT,
                R.string.unete_valid_formato_invalido
            ),
            V(context, Validation.EMPTY, R.string.unete_valid_obligatorio)
        )

        txtMetaMonto?.addV(V(context, Validation.EMPTY, R.string.unete_valid_obligatorio))
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(

                    spnNivelEducativo,
                    spnEstadoCivil,
                    spnTipoPersona,
                    spnOrigenIngreso,

                    txtNombreReferencia,
                    txtApellidoReferencia,
                    spnVinculoReferencia,
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


    private fun fieldDocumentoValid(): V {
        val a = object : Validation.Companion.ValidCommand {
            override fun execute(): Boolean {
                return UAlgorithms.validarCedulaEC(Constant.EMPTY_STRING)
            }
        }
        return V(context, Validation.CALLABLE, a, R.string.unete_paso1_valid_documento_format)
    }

}
