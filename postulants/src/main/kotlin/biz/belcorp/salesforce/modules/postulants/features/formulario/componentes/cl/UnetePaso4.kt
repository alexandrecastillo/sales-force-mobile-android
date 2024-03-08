package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cl

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.APELLIDO_MATERNO_AVAL_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.APELLIDO_PATERNO_AVAL_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.APELLIDO_REFERENCIA_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.DIRECCION_AVAL_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.DIRECCION_REFERENCIA_AVAL_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.META_MONTO_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.MONTO_DESCRIPCION_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.NOMBRE_REFERENCIA_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.PRIMER_NOMBRE_AVAL_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.SEGUNDO_NOMBRE_AVAL_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.TLF_CELULAR_AVAL_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.TLF_CELULAR_AVAL_MIN_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.TLF_CELULAR_DIGITO_INICIO
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.TLF_CELULAR_REFERENCIA_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.TLF_CELULAR_REFERENCIA_MIN_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.TLF_FIJO_AVAL_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL.TLF_FIJO_REFERENCIA_MAX_LENGHT
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoCrediticio
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TipoMetaModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso4
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4.UnetePaso4View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.CALLABLE
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.INICIO
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.core.utils.documents.RUT
import kotlinx.android.synthetic.main.unete_cl_paso_4.view.*

@SuppressLint("ViewConstructor")
class UnetePaso4(mContext: Context, view: UnetePaso4View) :
    BaseUnetePaso<UnetePaso4View>(mContext, view), IUnetePaso4 {

    override fun initEvents() {
        seccionReferencias?.setOnClickListener {
            expReferencia?.toggle()
        }
        seccionFiador?.setOnClickListener {
            expFiador?.toggle()
        }
        seccionMeta?.setOnClickListener {
            expMeta?.toggle()
        }
    }

    override fun getLayout() = R.layout.unete_cl_paso_4

    override fun showNivelEducativo(nivelEducativo: List<TablaLogicaModel>) {
        spnNivelEducativo?.load(nivelEducativo)
        val selected = view.getModel().nivelEducativo
        spnNivelEducativo?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun showEstadoCivil(estadoCivil: List<TablaLogicaModel>) {
        spnEstadoCivil?.load(estadoCivil)
        val selected = view.getModel().estadoCivil
        spnEstadoCivil?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun showTipoVinculoFamiliar(tipoVinculo: List<TablaLogicaModel>) {
        spnVinculoReferencia?.load(tipoVinculo)
        val selected = view.getModel().tipoVinculoFamiliar?.toString()
        spnVinculoReferencia?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun showTipoVinculoAval(tipoVinculo: List<TablaLogicaModel>) {
        spnAvalVinculo?.load(tipoVinculo)
        val selected = view.getModel().tipoVinculoAval?.toString()
        spnAvalVinculo?.selection<TablaLogicaModel> { it?.codigo == selected }
    }

    override fun showTipoMeta(tiposMetas: List<TipoMetaModel>) {
        spnTipoMeta?.load(tiposMetas)
        val selected = view.getModel().tipoMeta?.toIntOrNull()
        spnTipoMeta?.selection<TipoMetaModel> { it?.idTipoMeta == selected }
    }

    override fun createModel(): PostulanteModel? {

        val model = view.getModel()
        val tipoMeta = spnTipoMeta?.selected<TipoMetaModel>()?.idTipoMeta ?: Constant.NUMERO_CERO
        val avalVinculo = spnAvalVinculo?.selected<TablaLogicaModel>()?.codigo
        val referenciaVinculo = spnVinculoReferencia?.selected<TablaLogicaModel>()?.codigo
        val nivelEducativo = spnNivelEducativo?.selected<TablaLogicaModel>()?.codigo
        val estadoCivil = spnEstadoCivil?.selected<TablaLogicaModel>()?.codigo

        model.estadoCivil = estadoCivil
        model.nivelEducativo = nivelEducativo
        model.nombreFamiliar = txtNombreReferencia?.getText()
        model.apellidoFamiliar = txtApellidoReferencia?.getText()
        model.direccionFamiliar = txtDireccionReferencia?.getText()
        model.tipoVinculoFamiliar = referenciaVinculo?.toInt()
        model.celularFamiliar = txtTelefonoCelularReferencia?.getText()
        model.telefonoFamiliar = txtTelefonoFijoReferencia?.getText()

        model.primerNombreAval = txtAvalPrimerNombre?.getText()
        model.segundoNombreAval = txtAvalSegundoNombre?.getText()
        model.apellidoPaternoAval = txtAvalApellidoPaterno?.getText()
        model.apellidoMaternoAval = txtAvalApellidoMaterno?.getText()
        model.tipoDocumentoAval = Constant.EMPTY_STRING
        model.numeroDocumentoAval = txtAvalDocumento?.getText()
        model.direccionAval = txtAvalDireccion?.getText()
        model.celularAval = txtAvalTelefonoCelular?.getText()
        model.telefonoAval = txtAvalTelefonoFijo?.getText()
        model.tipoVinculoAval = avalVinculo?.toInt()

        if (tipoMeta < Constant.NUMERO_CERO) {
            model.tipoMeta = null
        } else {
            model.tipoMeta = tipoMeta.toString()
        }
        model.montoMeta = txtMetaMonto?.getText()
        model.descripcionMeta = txtMetaDescripcion?.getText()

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
        txtAvalDireccion?.setText(view.getModel().direccionEntrega)

        txtAvalPrimerNombre?.setText(model.primerNombreAval)
        txtAvalSegundoNombre?.setText(model.segundoNombreAval)
        txtAvalApellidoPaterno?.setText(model.apellidoPaternoAval)
        txtAvalApellidoMaterno?.setText(model.apellidoMaternoAval)
        txtAvalDocumento?.setText(model.numeroDocumentoAval)
        txtAvalDireccion?.setText(model.direccionAval)
        txtAvalTelefonoCelular?.setText(model.celularAval)
        txtAvalTelefonoFijo?.setText(model.telefonoAval)

        txtMetaMonto?.setText(model.montoMeta)
        txtMetaDescripcion?.setText(model.descripcionMeta)

    }

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()

        v += txtNombreReferencia?.validate()
        v += txtApellidoReferencia?.validate()
        v += txtDireccionReferencia?.validate()
        v += spnVinculoReferencia?.validate()
        v += spnNivelEducativo?.validate()
        v += spnEstadoCivil?.validate()
        v += txtTelefonoCelularReferencia?.validate()
        v += txtTelefonoFijoReferencia?.validate()

        if (view.getModel().estadoBurocrediticio ==
            UneteEstadoCrediticio.PODRIA_SER_CONSULTORA.value.toString()
        ) {
            v += txtAvalPrimerNombre?.validate()
            v += txtAvalSegundoNombre?.validate()
            v += txtAvalApellidoPaterno?.validate()
            v += txtAvalApellidoMaterno?.validate()
            v += spnAvalVinculo?.validate()
            v += txtAvalDocumento?.validate()
            v += txtAvalDireccion?.validate()
            v += txtAvalTelefonoCelular?.validate()
            v += txtAvalTelefonoFijo?.validate()
        }

        v += txtMetaMonto?.validate()
        v += txtMetaDescripcion?.validate()

        return v.all { it ?: false }
    }

    override fun initRequired() {

        spnEstadoCivil?.required = true
        spnNivelEducativo?.required = true
        spnVinculoReferencia?.required = true
        txtNombreReferencia?.setRequired(true)
        txtApellidoReferencia?.setRequired(true)
        txtDireccionReferencia?.setRequired(true)
        txtTelefonoCelularReferencia?.setRequired(true)
        txtTelefonoFijoReferencia?.setRequired(true)

        if (view.getModel().estadoBurocrediticio ==
            UneteEstadoCrediticio.PODRIA_SER_CONSULTORA.value.toString()
        ) {
            txtAvalPrimerNombre?.setRequired(true)
            txtAvalSegundoNombre?.setRequired(true)
            txtAvalApellidoPaterno?.setRequired(true)
            txtAvalApellidoMaterno?.setRequired(true)
            txtAvalDocumento?.setRequired(true)
            txtAvalDireccion?.setRequired(true)
            txtAvalTelefonoCelular?.setRequired(true)
            txtAvalTelefonoFijo?.setRequired(true)
            spnAvalVinculo?.required = true
        }

        spnTipoMeta?.required = true
        txtMetaMonto?.setRequired(true)
        txtMetaDescripcion?.setRequired(true)

    }

    override fun initRestriction() {

        txtNombreReferencia?.setR(Filters.filterLetters(NOMBRE_REFERENCIA_MAX_LENGHT))
        txtApellidoReferencia?.setR(Filters.filterLetters(APELLIDO_REFERENCIA_MAX_LENGHT))
        txtDireccionReferencia?.setR(
            arrayOf(
                Filters.filterDireccion(),
                Filters.filterMaxLength(DIRECCION_REFERENCIA_AVAL_MAX_LENGHT)
            )
        )

        txtTelefonoFijoReferencia?.setR(Filters.filterNumber(TLF_FIJO_REFERENCIA_MAX_LENGHT))
        txtTelefonoFijoReferencia?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoCelularReferencia?.setR(Filters.filterNumber(TLF_CELULAR_REFERENCIA_MAX_LENGHT))
        txtTelefonoCelularReferencia?.inputType(InputType.TYPE_CLASS_NUMBER)

        spnNivelEducativo?.addValidations(
            V(context, Validation.NO_SELECTION, R.string.not_selected)
        )
        spnVinculoReferencia?.addValidations(
            V(context, Validation.NO_SELECTION, R.string.not_selected)
        )
        spnAvalVinculo?.addValidations(V(context, Validation.NO_SELECTION, R.string.not_selected))
        spnEstadoCivil?.addValidations(V(context, Validation.NO_SELECTION, R.string.not_selected))

        txtAvalPrimerNombre?.setR(Filters.filterLetters(PRIMER_NOMBRE_AVAL_MAX_LENGHT))
        txtAvalSegundoNombre?.setR(Filters.filterLetters(SEGUNDO_NOMBRE_AVAL_MAX_LENGHT))
        txtAvalApellidoPaterno?.setR(Filters.filterLetters(APELLIDO_PATERNO_AVAL_MAX_LENGHT))
        txtAvalApellidoMaterno?.setR(Filters.filterLetters(APELLIDO_MATERNO_AVAL_MAX_LENGHT))
        txtAvalDireccion?.setR(
            arrayOf(Filters.filterDireccion(), Filters.filterMaxLength(DIRECCION_AVAL_MAX_LENGHT))
        )

        txtAvalTelefonoCelular?.setR(Filters.filterNumber(TLF_CELULAR_AVAL_MAX_LENGHT))
        txtAvalTelefonoCelular?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtAvalTelefonoFijo?.setR(Filters.filterNumber(TLF_FIJO_AVAL_MAX_LENGHT))
        txtAvalTelefonoFijo?.inputType(InputType.TYPE_CLASS_NUMBER)
        spnTipoMeta?.addValidations(V(context, Validation.NO_SELECTION, R.string.not_selected))
        txtMetaMonto?.setR(Filters.filterNumber(META_MONTO_MAX_LENGHT))
        txtMetaMonto?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtMetaDescripcion?.setR(Filters.filterAlphanumeric(MONTO_DESCRIPCION_MAX_LENGHT))

    }

    override fun initValidation() {

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
                context, MIN_LENGTH, TLF_CELULAR_REFERENCIA_MIN_LENGHT,
                R.string.unete_valid_formato_invalido
            ),
            V(context, INICIO, TLF_CELULAR_DIGITO_INICIO, R.string.unete_valid_formato_invalido)
        )
        txtTelefonoFijoReferencia?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_obligatorio),
            V(context, MIN_LENGTH, TLF_FIJO_AVAL_MAX_LENGHT, R.string.unete_valid_formato_invalido)
        )

        txtAvalPrimerNombre?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_pe_aval_primer_nombre_empty)
        )
        txtAvalApellidoPaterno?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_pe_aval_primer_apellido_empty)
        )
        txtAvalApellidoMaterno?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_pe_aval_segundo_apellido_empty)
        )

        txtAvalDocumento?.addV(
            V(context, Validation.EMPTY, R.string.unete_paso1_valid_rut),
            V(context, MIN_LENGTH, CL.RUT_MIN_LENGHT, R.string.unete_paso1_valid_rut),
            fieldDocumentoValid()
        )

        txtAvalDireccion?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_pe_aval_direccion_empty)
        )
        txtAvalTelefonoCelular?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_pe_aval_telefono_celular_empty),
            V(
                context, MIN_LENGTH, TLF_CELULAR_AVAL_MIN_LENGHT,
                R.string.unete_pe_paso3_valid_aval_celular_format
            )
        )
        txtAvalTelefonoFijo?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_telefono_empty)
        )

        txtMetaMonto?.addV(
            V(context, Validation.EMPTY, R.string.unete_pe_paso3_valid_meta_monto_empty)
        )
        txtMetaDescripcion?.addV(
            V(context, Validation.EMPTY, R.string.unete_pe_paso3_valid_meta_descripcion_empty)
        )
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    spnEstadoCivil,
                    spnNivelEducativo,
                    spnVinculoReferencia,
                    spnAvalVinculo,
                    txtNombreReferencia,
                    txtApellidoReferencia,
                    txtDireccionReferencia,
                    txtTelefonoCelularReferencia,
                    txtTelefonoFijoReferencia,
                    txtAvalPrimerNombre,
                    txtAvalSegundoNombre,
                    txtAvalApellidoPaterno,
                    txtAvalApellidoMaterno,
                    txtAvalDocumento,
                    txtAvalDireccion,
                    txtAvalTelefonoCelular,
                    txtAvalTelefonoFijo,
                    spnTipoMeta,
                    txtMetaMonto,
                    txtMetaDescripcion
                )
            )
        }

        return lst

    }

    private fun fieldDocumentoValid(): V {
        val a = object : Validation.Companion.ValidCommand {
            override fun execute(): Boolean {
                return RUT.validate(txtAvalDocumento?.getText().orEmpty())
            }
        }
        return V(context, CALLABLE, a, R.string.unete_paso1_valid_rut)
    }

}
