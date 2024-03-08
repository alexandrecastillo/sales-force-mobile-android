package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.bo

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.BO
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso4
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4.UnetePaso4View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.CIBolivia
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import kotlinx.android.synthetic.main.unete_bo_paso_4.view.*

@SuppressLint("ViewConstructor")
class UnetePaso4(mContext: Context, view: UnetePaso4View) :
    BaseUnetePaso<UnetePaso4View>(mContext, view), IUnetePaso4 {

    override fun initEvents() {

        seccionFiador?.setOnClickListener {
            expFiador?.toggle()
        }
        seccionMeta?.setOnClickListener {
            expMeta?.toggle()
        }

    }

    override fun getLayout() = R.layout.unete_bo_paso_4

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
        spnTipoVinculoFamiliar?.load(tipoVinculo)
        val selected = view.getModel().tipoVinculoFamiliar
        spnTipoVinculoFamiliar?.selection<TablaLogicaModel> { it?.codigo == selected?.toString() }
    }

    override fun createModel(): PostulanteModel? {
        val model = view.getModel()

        val tipoVinculoFamiliar = spnTipoVinculoFamiliar?.selected<TablaLogicaModel>()?.codigo
        val nivelEducativo = spnNivelEducativo?.selected<TablaLogicaModel>()?.codigo
        val estadoCivil = spnEstadoCivil?.selected<TablaLogicaModel>()?.codigo

        model.tipoVinculoFamiliar = tipoVinculoFamiliar?.toInt()
        model.nivelEducativo = nivelEducativo
        model.estadoCivil = estadoCivil

        model.nombreFamiliar = txtPrimerNombreRefFamiliar?.getText()
        model.apellidoFamiliar = txtApellidoPaternoRefFamiliar?.getText()
        model.nit = txtNITRefFamiliar?.getText()
        model.celularFamiliar = txtTelefonoCelularRefFamiliar?.getText()
        model.telefonoFamiliar = txtTelefonoFijoRefFamiliar?.getText()

        model.primerNombreAval = txtAvalPrimerNombre?.getText()
        model.segundoNombreAval = txtAvalSegundoNombre?.getText()
        model.apellidoPaternoAval = txtAvalApellidoPaterno?.getText()
        model.apellidoMaternoAval = txtAvalApellidoMaterno?.getText()
        model.tipoDocumentoAval = Constant.NUMERO_UNO.toString()
        model.numeroDocumentoAval = CIBolivia.encode(
            model.tipoDocumentoAval!!,
            txtAvalDocumento?.getText().orEmpty()
        )
        model.fechaNacimientoAval = txtAvalCiudad?.getText()
        model.direccionAval = txtAvalDireccion?.getText()
        model.celularAval = txtAvalTelefonoCelular?.getText()
        model.telefonoAval = txtAvalTelefonoFijo?.getText()

        model.nombreNoFamiliar = txtNoFamiliarPrimerNombre?.getText()
        model.apellidoNoFamiliar = txtNoFamiliarApellidoPaterno?.getText()
        model.celularNoFamiliar = txtNoFamiliarCelular?.getText()
        model.telefonoNoFamiliar = txtNoFamiliarTelefono?.getText()

        model.montoMeta = txtMetaMonto?.getText()

        model.paso = Constant.NUMERO_CUATRO
        model.sincronizado = false

        return model
    }

    override fun loadModel() {

        val model = view.getModel()

        txtPrimerNombreRefFamiliar?.setText(model.nombreFamiliar)
        txtApellidoPaternoRefFamiliar?.setText(model.apellidoFamiliar)
        txtNITRefFamiliar?.setText(model.nit)
        txtTelefonoCelularRefFamiliar?.setText(model.celularFamiliar)
        txtTelefonoFijoRefFamiliar?.setText(model.telefonoFamiliar)

        txtAvalDireccion?.setText(model.direccionEntrega)

        txtAvalPrimerNombre?.setText(model.primerNombreAval)
        txtAvalSegundoNombre?.setText(model.segundoNombreAval)
        txtAvalApellidoPaterno?.setText(model.apellidoPaternoAval)
        txtAvalApellidoMaterno?.setText(model.apellidoMaternoAval)
        txtAvalDocumento?.setText(CIBolivia.decode(model.numeroDocumentoAval))

        txtAvalCiudad?.setText(model.fechaNacimientoAval)
        txtAvalDireccion?.setText(model.direccionAval)
        txtAvalTelefonoCelular?.setText(model.celularAval)
        txtAvalTelefonoFijo?.setText(model.telefonoAval)

        txtNoFamiliarPrimerNombre?.setText(model.nombreNoFamiliar)
        txtNoFamiliarApellidoPaterno?.setText(model.apellidoNoFamiliar)
        txtNoFamiliarCelular?.setText(model.celularNoFamiliar)
        txtNoFamiliarTelefono?.setText(model.telefonoNoFamiliar)

        txtMetaMonto?.setText(model.montoMeta)

    }

    override fun validate(): Boolean {

        initRequired()
        initValidation()

        val v = mutableListOf<Boolean?>()

        v += spnEstadoCivil?.validate()
        v += spnNivelEducativo?.validate()
        v += spnTipoVinculoFamiliar?.validate()
        v += txtPrimerNombreRefFamiliar?.validate()
        v += txtApellidoPaternoRefFamiliar?.validate()
        v += txtNITRefFamiliar?.validate()
        v += txtTelefonoCelularRefFamiliar?.validate()
        v += txtTelefonoFijoRefFamiliar?.validate()

        setRequiredAreaAval(isRegisterAval())

        if (view.esZonaRiesgo() || isRegisterAval()) {
            v += txtAvalPrimerNombre?.validate()
            v += txtAvalSegundoNombre?.validate()
            v += txtAvalApellidoPaterno?.validate()
            v += txtAvalDocumento?.validate()
            v += txtAvalCiudad?.validate()
            v += txtAvalDireccion?.validate()
            v += txtAvalTelefonoCelular?.validate()
            v += txtAvalTelefonoFijo?.validate()
        }

        v += txtNoFamiliarPrimerNombre?.validate()
        v += txtNoFamiliarApellidoPaterno?.validate()
        v += txtNoFamiliarCelular?.validate()
        v += txtNoFamiliarTelefono?.validate()

        v += txtMetaMonto?.validate()

        return v.all { it ?: false }
    }

    private fun isRegisterAval(): Boolean {
        return !txtAvalPrimerNombre?.getText().isNullOrEmpty() ||
            !txtAvalSegundoNombre?.getText().isNullOrEmpty() ||
            !txtAvalApellidoPaterno?.getText().isNullOrEmpty() ||
            !txtAvalApellidoMaterno?.getText().isNullOrEmpty() ||
            !txtAvalDocumento?.getText().isNullOrEmpty() ||
            !txtAvalCiudad?.getText().isNullOrEmpty() ||
            !txtAvalDireccion?.getText().isNullOrEmpty() ||
            !txtAvalTelefonoCelular?.getText().isNullOrEmpty() ||
            !txtAvalTelefonoFijo?.getText().isNullOrEmpty()
    }

    override fun initRequired() {

        spnEstadoCivil?.required = true
        spnNivelEducativo?.required = true
        txtPrimerNombreRefFamiliar?.setRequired(true)
        txtApellidoPaternoRefFamiliar?.setRequired(true)
        txtTelefonoCelularRefFamiliar?.setRequired(true)

        if (view.esZonaRiesgo()) {
            setRequiredAreaAval(true)
        }

        txtNoFamiliarPrimerNombre?.setRequired(true)
        txtNoFamiliarApellidoPaterno?.setRequired(true)
        txtNoFamiliarCelular?.setRequired(true)
        txtNoFamiliarTelefono?.setRequired(false)

        txtMetaMonto?.setRequired(false)
    }

    private fun setRequiredAreaAval(isRequired: Boolean) {
        txtAvalPrimerNombre?.setRequired(isRequired)
        txtAvalSegundoNombre?.setRequired(false)
        txtAvalApellidoPaterno?.setRequired(isRequired)
        txtAvalApellidoMaterno?.setRequired(false)
        txtAvalDocumento?.setRequired(isRequired)
        txtAvalCiudad?.setRequired(false)
        txtAvalDireccion?.setRequired(false)
        txtAvalTelefonoCelular?.setRequired(false)
        txtAvalTelefonoFijo?.setRequired(false)
    }

    override fun initRestriction() {

        spnEstadoCivil?.addValidations(V(context, Validation.NO_SELECTION))
        spnNivelEducativo?.addValidations(V(context, Validation.NO_SELECTION))
        spnTipoVinculoFamiliar?.addValidations(V(context, Validation.NO_SELECTION))

        txtPrimerNombreRefFamiliar?.setRestriction(Filters.filterLetters(BO.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtApellidoPaternoRefFamiliar?.setRestriction(Filters.filterLetters(BO.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtNITRefFamiliar?.setRestriction(Filters.filterNumber(Constant.NUMERO_TRECE))
        txtNITRefFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoCelularRefFamiliar?.setRestriction(Filters.filterNumber(BO.CELULAR_MAX_DIGITOS))
        txtTelefonoCelularRefFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoFijoRefFamiliar?.setRestriction(Filters.filterNumber(BO.TLF_MAX_DIGITOS))
        txtTelefonoFijoRefFamiliar?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtNoFamiliarPrimerNombre?.setRestriction(Filters.filterLetters(BO.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtNoFamiliarApellidoPaterno?.setRestriction(Filters.filterLetters(BO.NOMBRE_APELLIDO_REF_MAX_DIGITOS))
        txtNoFamiliarCelular?.setRestriction(Filters.filterNumber(BO.CELULAR_MAX_DIGITOS))
        txtNoFamiliarTelefono?.setRestriction(Filters.filterNumber(BO.TLF_MAX_DIGITOS))
        txtNoFamiliarTelefono?.inputType(InputType.TYPE_CLASS_NUMBER)

        txtAvalPrimerNombre?.setRestriction(Filters.filterLetters(BO.NOMBRE_APELLIDO_AVAL_MAX_DIGITOS))
        txtAvalSegundoNombre?.setRestriction(Filters.filterLetters(BO.NOMBRE_APELLIDO_AVAL_MAX_DIGITOS))
        txtAvalApellidoPaterno?.setRestriction(Filters.filterLetters(BO.NOMBRE_APELLIDO_AVAL_MAX_DIGITOS))
        txtAvalApellidoMaterno?.setRestriction(Filters.filterLetters(BO.NOMBRE_APELLIDO_AVAL_MAX_DIGITOS))
        txtAvalTelefonoCelular?.setRestriction(Filters.filterNumber(BO.CELULAR_MAX_DIGITOS))
        txtAvalTelefonoCelular?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtAvalTelefonoFijo?.setRestriction(Filters.filterNumber(BO.TLF_MAX_DIGITOS))
        txtAvalTelefonoFijo?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtAvalCiudad?.setRestriction(Filters.filterMaxLength(BO.CIUDAD_MAX_DIGITOS))
        txtAvalDocumento?.setRestriction(Filters.filterMaxLength(BO.CI_MAX_DIGITOS))
        txtAvalDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtAvalDireccion?.setRestriction(Filters.filterDireccion())

        txtMetaMonto?.setRestriction(Filters.filterNumber(BO.MONTO_MAX_DIGITOS))
        txtMetaMonto?.inputType(InputType.TYPE_CLASS_NUMBER)

    }

    override fun initValidation() {

        spnEstadoCivil?.addValidations(
            V(context, Validation.NO_SELECTION, R.string.unete_paso4_valid_estado_civil_empty)
        )
        spnNivelEducativo?.addValidations(
            V(context, Validation.NO_SELECTION, R.string.unete_paso4_valid_nivel_educativo_empty)
        )

        txtPrimerNombreRefFamiliar?.addV(
            V(context, Validation.EMPTY, R.string.unete_bo_paso4_primer_nombre_ref_familiar_empty)
        )
        txtApellidoPaternoRefFamiliar?.addV(
            V(
                context, Validation.EMPTY,
                R.string.unete_bo_paso4_apellido_paterno_ref_familiar_empty
            )
        )
        txtTelefonoCelularRefFamiliar?.addV(
            V(
                context, Validation.EMPTY,
                R.string.unete_bo_paso4_telefono_celular_ref_familiar_empty
            ),
            V(
                context, Validation.MIN_LENGTH, BO.CELULAR_MAX_DIGITOS,
                R.string.unete_bo_paso4_celular_ref_familiar_format
            )
        )
        txtTelefonoFijoRefFamiliar?.addV(
            V(
                context, Validation.MIN_LENGTH, BO.TLF_MAX_DIGITOS,
                R.string.unete_bo_paso4_telefono_ref_familiar_format
            )
        )
        txtNITRefFamiliar?.addV(
            V(
                context, Validation.MIN_LENGTH, Constant.NUMERO_NUEVE,
                R.string.unete_bo_paso4_nit_ref_familiar_format
            )
        )

        txtMetaMonto?.addV(
            V(context, Validation.EMPTY, R.string.unete_bo_paso4_valid_meta_monto_empty)
        )

        txtNoFamiliarPrimerNombre?.addV(V(context, Validation.EMPTY))
        txtNoFamiliarApellidoPaterno?.addV(V(context, Validation.EMPTY))
        txtNoFamiliarCelular?.addV(
            V(context, Validation.EMPTY),
            V(
                context, Validation.MIN_LENGTH, BO.CELULAR_MAX_DIGITOS,
                R.string.unete_bo_paso4_celular_ref_familiar_format
            )
        )
        txtNoFamiliarTelefono?.addV(
            V(
                context, Validation.MIN_LENGTH, BO.TLF_MAX_DIGITOS,
                R.string.unete_bo_paso4_telefono_ref_familiar_format
            )
        )

        txtAvalPrimerNombre?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_bo_aval_primer_nombre_empty)
        )
        txtAvalApellidoPaterno?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_bo_aval_primer_apellido_empty)
        )
        txtAvalApellidoMaterno?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_bo_aval_segundo_apellido_empty)
        )
        txtAvalDocumento?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_bo_aval_documento_empty),
            V(
                context, Validation.MIN_LENGTH, Constant.NUMERO_SEIS,
                R.string.unete_valid_bo_aval_documento_min_length
            )
        )
        txtAvalCiudad?.addV(
            V(context, Validation.EMPTY, R.string.unete_bo_paso4_aval_ciudad_empty)
        )
        txtAvalDireccion?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_bo_aval_direccion_empty)
        )
        txtAvalTelefonoCelular?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_bo_aval_telefono_celular_empty),
            V(
                context, Validation.MIN_LENGTH, BO.CELULAR_MAX_DIGITOS,
                R.string.unete_bo_paso4_valid_aval_celular_format
            )
        )
        txtAvalTelefonoFijo?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_telefono_empty),
            V(
                context, Validation.MIN_LENGTH, BO.TLF_MAX_DIGITOS,
                R.string.unete_valid_bo_aval_telefono_format
            )
        )
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    spnEstadoCivil,
                    spnNivelEducativo,
                    spnTipoVinculoFamiliar,
                    txtAvalCiudad,
                    txtAvalDireccion,
                    txtAvalTelefonoCelular,
                    txtAvalTelefonoFijo
                )
            )
        }

        return lst
    }

}
