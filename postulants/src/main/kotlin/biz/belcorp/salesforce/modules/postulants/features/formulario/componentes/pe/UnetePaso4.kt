package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pe

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.PE
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoDocumento
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TipoMetaModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso4
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4.UnetePaso4View
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.cambiarVisibilidad
import biz.belcorp.salesforce.modules.postulants.utils.getText
import biz.belcorp.salesforce.modules.postulants.utils.toInt
import kotlinx.android.synthetic.main.unete_co_paso_2.view.*
import kotlinx.android.synthetic.main.unete_pe_paso_4.view.*
import java.util.*

@SuppressLint("ViewConstructor")
class UnetePaso4(mContext: Context, view: UnetePaso4View) :
    BaseUnetePaso<UnetePaso4View>(mContext, view), IUnetePaso4 {

    override fun initEvents() {

        seccionFiador?.setOnClickListener {
            chkRequiereAval?.toggle()
        }

        seccionMeta?.setOnClickListener {
            expMeta?.toggle()
        }

        chkRequiereAval?.setOnCheckedChangeListener { _, _ ->
            toggleFiador()
            initRequired()
        }

    }

    private fun toggleFiador() {
        llFiador?.cambiarVisibilidad()
    }

    override fun getLayout() = R.layout.unete_pe_paso_4


    override fun showTipoMeta(tiposMetas: List<TipoMetaModel>) {
        spnTipoMeta?.load(tiposMetas)
        val selected = view.getModel().tipoMeta?.toIntOrNull()
        spnTipoMeta?.selection<TipoMetaModel> { it?.idTipoMeta == selected }
    }

    override fun createModel(): PostulanteModel? {

        val model = view.getModel()

        val tipoMeta = spnTipoMeta?.selected<TipoMetaModel>()?.idTipoMeta ?: Constant.NUMERO_CERO

        model.requiereAval = chkRequiereAval?.isChecked?.toInt() ?: Constant.NUMERO_CERO

        if (model.requiereAval == Constant.NUMERO_UNO) {

            model.primerNombreAval = txtAvalPrimerNombre?.getText()
            model.segundoNombreAval = txtAvalSegundoNombre?.getText()
            model.apellidoPaternoAval = txtAvalApellidoPaterno?.getText()
            model.apellidoMaternoAval = txtAvalApellidoMaterno?.getText()
            model.tipoDocumentoAval = Constant.EMPTY_STRING
            model.numeroDocumentoAval = txtAvalDocumento?.getText()
            model.fechaNacimientoAval = txtAvalFechaNacimiento?.getText(formatLongT)
            model.direccionAval = txtAvalDireccion?.getText()
            model.celularAval = txtAvalTelefonoCelular?.getText()
            model.telefonoAval = txtAvalTelefonoFijo?.getText()
            model.requiereAprobacionSAC = true
        }

        if (tipoMeta < Constant.NUMERO_CERO) {
            model.tipoMeta = null
        } else {
            model.tipoMeta = tipoMeta.toString()
        }
        model.montoMeta = txtMetaMonto?.getText()
        model.descripcionMeta = txtMetaDescripcion?.getText()
        model.paso = Constant.NUMERO_CUATRO
        model.sincronizado = false
        model.termsAceptados = chkAcceptTerms?.isChecked ?: false

        return model
    }

    override fun loadModel() {

        val model = view.getModel()
        val checkAval = avalChecked(model)

        if (model.tipoDocumento == UneteTipoDocumento.OTROS.codigo) {
            lyAval?.gone()
            chkRequiereAval?.isChecked = false
        }

        chkRequiereAval?.isChecked = checkAval
        chkRequiereAval?.isEnabled = !checkAval
        seccionFiador?.isEnabled = !checkAval

        txtAvalDireccion?.setText(model.direccionEntrega)
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

        val fechaNacimiento = model.fechaNacimientoAval
            ?.changeDateFormat(formatLongT, formatShort2)

        txtAvalFechaNacimiento?.setText(fechaNacimiento)

    }

    private fun esPasaporteCE(model: PostulanteModel): Boolean {
        return (model.tipoDocumento in listOf(
            UneteTipoDocumento.CE.codigo,
            UneteTipoDocumento.PASAPORTE.codigo
        ))
    }

    private fun avalChecked(model: PostulanteModel): Boolean {
        return if (esPasaporteCE(model)) esPasaporteCE(model) else model.requiereAval == Constant.NUMERO_UNO
    }

    override fun validate(): Boolean {

        initRequired()

        val v = mutableListOf<Boolean?>()

        if (chkRequiereAval?.isChecked == true) {
            v += txtAvalPrimerNombre?.validate()
            v += txtAvalSegundoNombre?.validate()
            v += txtAvalApellidoPaterno?.validate()
            v += txtAvalApellidoMaterno?.validate()
            v += txtAvalDocumento?.validate()
            v += txtAvalFechaNacimiento?.validate()
            v += txtAvalDireccion?.validate()
            v += txtAvalTelefonoCelular?.validate()
            v += txtAvalTelefonoFijo?.validate()
        }

        v += spnTipoMeta?.validate()
        v += txtMetaMonto?.validate()
        v += txtMetaDescripcion?.validate()

        return v.all { it ?: false }
    }

    override fun initRequired() {

        val requiereAval = chkRequiereAval?.isChecked!!

        txtAvalPrimerNombre?.setRequired(requiereAval)
        txtAvalApellidoPaterno?.setRequired(requiereAval)
        txtAvalApellidoMaterno?.setRequired(requiereAval)
        txtAvalDocumento?.setRequired(requiereAval)
        txtAvalFechaNacimiento?.setRequired(requiereAval)
        txtAvalDireccion?.setRequired(requiereAval)
        txtAvalTelefonoCelular?.setRequired(requiereAval)
        txtAvalTelefonoFijo?.setRequired(requiereAval)

        spnTipoMeta?.required = true
        txtMetaMonto?.setRequired(true)
        txtMetaDescripcion?.setRequired(true)
    }

    override fun initRestriction() {

        txtAvalPrimerNombre?.setRestriction(Filters.filterLetters(PE.NOMBRES_MAX_LENGHT))
        txtAvalSegundoNombre?.setRestriction(Filters.filterLetters(PE.NOMBRES_MAX_LENGHT))
        txtAvalApellidoPaterno?.setRestriction(Filters.filterLetters(PE.APELLIDO_MAX_LENGHT))
        txtAvalApellidoMaterno?.setRestriction(Filters.filterLetters(PE.APELLIDO_MAX_LENGHT))
        txtAvalTelefonoCelular?.setRestriction(Filters.filterNumber(PE.TLF_CELULAR_MAX_LENGHT))
        txtAvalTelefonoFijo?.setRestriction(Filters.filterNumber(PE.TLF_FIJO_MAX_LENGHT))

        txtAvalDocumento?.setRestriction(Filters.filterNumber(PE.DOCUMENTO_AVAL_MAX_LENGHT))
        txtAvalDireccion?.setRestriction(
            arrayOf(
                Filters.filterDireccion(),
                Filters.filterMaxLength(PE.DIRECCION_MAX_LENGHT)
            )
        )
        txtMetaMonto?.setRestriction(Filters.filterNumber(PE.MONTO_MAX_LENGHT))
        txtMetaDescripcion?.setRestriction(Filters.filterAlphanumeric(PE.META_DESCRIPCION_MAX_LENGHT))

    }

    override fun initValidation() {

        spnTipoMeta?.addValidations(V(context, Validation.NO_SELECTION))
        txtMetaMonto?.addV(
            V(context, Validation.EMPTY, R.string.unete_pe_paso3_valid_meta_monto_empty)
        )
        txtMetaDescripcion?.addV(
            V(context, Validation.EMPTY, R.string.unete_pe_paso3_valid_meta_descripcion_empty)
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
            V(context, Validation.EMPTY, R.string.unete_valid_pe_aval_documento_empty),
            V(
                context, Validation.MIN_LENGTH, PE.DOCUMENTO_AVAL_MIN_LENGHT,
                R.string.unete_valid_pe_aval_documento_min_length
            )
        )
        txtAvalFechaNacimiento?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_pe_aval_nacimiento_empty)
        )
        txtAvalDireccion?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_pe_aval_direccion_empty)
        )
        txtAvalTelefonoCelular?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_pe_aval_telefono_celular_empty),
            V(
                context, Validation.MIN_LENGTH, PE.TLF_CELULAR_MIN_LENGHT,
                R.string.unete_pe_paso3_valid_aval_celular_format
            )
        )
        txtAvalTelefonoFijo?.addV(
            V(context, Validation.EMPTY, R.string.unete_valid_telefono_empty),
            V(
                context, Validation.MIN_LENGTH, PE.TLF_FIJO_MIN_LENGHT,
                R.string.unete_valid_pe_aval_telefono_format
            )
        )

        txtAvalFechaNacimiento?.minDate = Calendar.getInstance().minusYears(PE.EDAD_MAX)
        txtAvalFechaNacimiento?.maxDate = Calendar.getInstance().minusYears(PE.EDAD_MIN)
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {
        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    chkRequiereAval,
                    txtAvalPrimerNombre,
                    txtAvalSegundoNombre,
                    txtAvalApellidoPaterno,
                    txtAvalApellidoMaterno,
                    txtAvalDocumento,
                    txtAvalFechaNacimiento,
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

}
