package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.co

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CO
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TipoMetaModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso4
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4.UnetePaso4View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import kotlinx.android.synthetic.main.unete_co_paso_4.view.*

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

    override fun getLayout() = R.layout.unete_co_paso_4

    override fun showTipoVinculoFamiliar(tipoVinculo: List<TablaLogicaModel>) {
        spnVinculoReferencia?.load(tipoVinculo)
        val selected = view.getModel().tipoVinculoFamiliar.toString()
        spnVinculoReferencia?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun showTipoMeta(tiposMetas: List<TipoMetaModel>) {
        spnTipoMeta?.load(tiposMetas)
        val selected = view.getModel().tipoMeta?.toInt() ?: Constant.NUMERO_CERO
        spnTipoMeta?.selection<TipoMetaModel> { it?.idTipoMeta == selected }
    }

    override fun createModel(): PostulanteModel? {

        val m = view.getModel()

        val referenciaVinculo = spnVinculoReferencia?.selected<TablaLogicaModel>()?.codigo
        val tipoMeta = spnTipoMeta?.selected<TipoMetaModel>()?.idTipoMeta.toString()

        m.nombreFamiliar = txtNombreReferencia?.getText()
        m.apellidoFamiliar = txtApellidoReferencia?.getText()
        m.tipoVinculoFamiliar = referenciaVinculo?.toInt()
        m.direccionFamiliar = txtDireccionReferenciaFam?.getText()
        m.celularFamiliar = txtTelefonoCelularReferencia?.getText()
        m.telefonoFamiliar = txtTelefonoFijoReferencia?.getText()

        m.nombreNoFamiliar = txtNombreReferenciaNoFamiliar?.getText()
        m.apellidoNoFamiliar = txtApellidoReferenciaNoFamiliar?.getText()
        m.direccionNoFamiliar = txtDireccionReferenciaNoFamiliar?.getText()
        m.celularNoFamiliar = txtTelefonoCelularReferenciaNoFamiliar?.getText()
        m.telefonoNoFamiliar = txtTelefonoFijoReferenciaNoFamiliar?.getText()

        m.tipoMeta = if (tipoMeta == "-1") null else tipoMeta
        m.montoMeta = txtMetaMonto?.getText()
        m.descripcionMeta = txtMetaDescripcion?.getText()

        m.paso = Constant.NUMERO_TRES
        m.sincronizado = false

        return m
    }

    override fun loadModel() {

        val m = view.getModel()

        txtNombreReferencia?.setText(m.nombreFamiliar)
        txtApellidoReferencia?.setText(m.apellidoFamiliar)
        txtDireccionReferenciaFam?.setText(m.direccionFamiliar)
        txtTelefonoCelularReferencia?.setText(m.celularFamiliar)
        txtTelefonoFijoReferencia?.setText(m.telefonoFamiliar)

        txtNombreReferenciaNoFamiliar?.setText(m.nombreNoFamiliar)
        txtApellidoReferenciaNoFamiliar?.setText(m.apellidoNoFamiliar)
        txtDireccionReferenciaNoFamiliar?.setText(m.direccionNoFamiliar)
        txtTelefonoCelularReferenciaNoFamiliar?.setText(m.celularNoFamiliar)
        txtTelefonoFijoReferenciaNoFamiliar?.setText(m.telefonoNoFamiliar)

        txtMetaMonto?.setText(m.montoMeta)
        txtMetaDescripcion?.setText(m.descripcionMeta)
    }

    override fun initRequired() {

        spnTipoMeta?.required = true
        spnVinculoReferencia?.required = true

        txtNombreReferencia?.setRequired(true)
        txtApellidoReferencia?.setRequired(true)
        txtDireccionReferenciaFam?.setRequired(true)
        spnVinculoReferencia?.required = true
        txtTelefonoCelularReferencia?.setRequired(true)

        txtNombreReferenciaNoFamiliar?.setRequired(true)
        txtApellidoReferenciaNoFamiliar?.setRequired(true)
        txtDireccionReferenciaNoFamiliar?.setRequired(true)
        txtTelefonoCelularReferenciaNoFamiliar?.setRequired(true)

        txtMetaMonto?.setRequired(false)
    }

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += spnTipoMeta?.validate()
        v += spnVinculoReferencia?.validate()

        v += txtNombreReferencia?.validate()
        v += txtApellidoReferencia?.validate()
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

        txtNombreReferencia?.setR(Filters.filterLetters(CO.NOMBRE_REFERENCIA_MAX_LENGHT))
        txtApellidoReferencia?.setR(Filters.filterLetters(CO.APELLIDO_REFERENCIA_MAX_LENGHT))
        txtTelefonoFijoReferencia?.setR(Filters.filterNumber(CO.TLF_FIJO_REFERENCIA_MAX_LENGHT))
        txtTelefonoCelularReferencia?.setR(Filters.filterNumber(CO.TLF_CELULAR_REFERENCIA_MAX_LENGHT))

        txtNombreReferenciaNoFamiliar?.setR(Filters.filterLetters(CO.NOMBRE_REFERENCIA_MAX_LENGHT))
        txtApellidoReferenciaNoFamiliar?.setR(Filters.filterLetters(CO.APELLIDO_REFERENCIA_MAX_LENGHT))
        txtTelefonoFijoReferenciaNoFamiliar?.setR(Filters.filterNumber(CO.TLF_FIJO_REFERENCIA_MAX_LENGHT))
        txtTelefonoCelularReferenciaNoFamiliar?.setR(Filters.filterNumber(CO.TLF_CELULAR_REFERENCIA_MAX_LENGHT))

        txtMetaMonto?.setR(Filters.filterNumber(CO.META_MONTO_MAX_LENGHT))
        txtMetaDescripcion?.setR(Filters.filterAlphanumeric(CO.MONTO_DESCRIPCION_MAX_LENGHT))
    }

    override fun initValidation() {

        spnTipoMeta?.addValidations(V(context, Validation.NO_SELECTION))
        spnVinculoReferencia?.addValidations(V(context, Validation.NO_SELECTION))

        txtNombreReferencia?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_familiar_nombres_empty)
        )
        txtApellidoReferencia?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_familiar_apellidos_empty)
        )
        txtTelefonoCelularReferencia?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_obligatorio),
            V(
                context, Validation.MIN_LENGTH, CO.TLF_CELULAR_REFERENCIA_MIN_LENGHT,
                R.string.unete_valid_formato_invalido
            ),
            V(
                context, Validation.INICIO, CO.TLF_CELULAR_DIGITO_INICIO,
                R.string.unete_valid_formato_invalido
            )
        )
        txtTelefonoFijoReferencia?.addV(
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
                context, Validation.MIN_LENGTH, CO.TLF_CELULAR_REFERENCIA_MIN_LENGHT,
                R.string.unete_valid_formato_invalido
            ),
            V(
                context, Validation.INICIO, CO.TLF_CELULAR_DIGITO_INICIO,
                R.string.unete_valid_formato_invalido
            )
        )
        txtTelefonoFijoReferenciaNoFamiliar?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_obligatorio)
        )
        txtMetaMonto?.addV(V(context, Validation.EMPTY, R.string.unete_valid_obligatorio))
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
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
}
