package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cl

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.View
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Localidad
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.FuenteIngreso
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso2
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.UnetePaso2View
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.entities.PlaceModel
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.BaseInput
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.INICIO
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import biz.belcorp.salesforce.modules.postulants.utils.toInt
import kotlinx.android.synthetic.main.unete_cl_paso_2.view.*
import kotlinx.android.synthetic.main.unete_cl_paso_2.view.llConsultoraRecomienda
import kotlinx.android.synthetic.main.unete_cl_paso_2.view.swtConsultoraAnteriormente
import kotlinx.android.synthetic.main.unete_cl_paso_2.view.swtRecomiendaConsultora
import kotlinx.android.synthetic.main.unete_cl_paso_2.view.txtCodigoConsultoraRecomienda
import kotlinx.android.synthetic.main.unete_cl_paso_2.view.txtNombreConsultoraRecomienda
import kotlinx.android.synthetic.main.unete_cl_paso_2.view.txtTelefonoCelular
import kotlinx.android.synthetic.main.unete_cl_paso_2.view.txtTelefonoFijo

@SuppressLint("ViewConstructor")
class UnetePaso2(mContext: Context, view: UnetePaso2View) :
    BaseUnetePaso<UnetePaso2View>(mContext, view), IUnetePaso2 {

    var isNewExperienceDireccion: Boolean = false
    var placeId: String? = null
    var hasChangedPlaceId = false

    override fun initEvents() {

        swtRecomiendaConsultora?.setOnCheckedChangeListener { _, _ ->
            toggleRecomiendaConsultora()
        }

        txtCodigoConsultoraRecomienda?.onTextChanged {
            val value = it.toString().trim()
            if (value.length == CL.COD_CONSULTORA_MAX_LENGHT)
                view.getConsultoraRecomienda(value)
            else
                txtNombreConsultoraRecomienda?.setText(Constant.EMPTY_STRING)
        }

        spnRegion?.onItemSelected { fromUser ->
            if (fromUser) {
                spnRegion?.selected<ParametroUneteModel>()?.also {
                    txtDireccionGoogle.clear()
                    txtDireccionManual.clear()

                    txtDireccionGoogle.visibility = View.GONE
                    txtDireccionManual.visibility = View.GONE

                    spnComuna.requestFocus()

                    view.getLugarNivel2(it.idParametroUnete)
                }
            }
        }

        spnComuna?.onItemSelected { fromUser ->
            if (fromUser) {
                spnComuna?.selected<ParametroUneteModel>()?.also {
                    txtDireccionManual.clear()
                    txtDireccionGoogle.clear()

                    txtDireccionManual.visibility = View.GONE
                    txtDireccionGoogle.visibility = View.VISIBLE

                    txtDireccionGoogle.requestFocus()
                }
            }
        }

        txtNombreConsultoraRecomienda?.setEnable(false)
        txtDireccionGoogle.mainView = this
    }

    private fun toggleRecomiendaConsultora() {
        llConsultoraRecomienda?.visibility =
            if (llConsultoraRecomienda?.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    override fun getLayout() = R.layout.unete_cl_paso_2

    override fun showLugarNivel1(lst: List<ParametroUneteModel>) {
        spnRegion?.load(lst)
        val selected = view.getModel()?.lugarPadre
        spnRegion?.selection<ParametroUneteModel> { it?.nombre == selected }

        if (selected != null) {
            val idLugarPadre = lst.find { it.nombre == selected }?.idParametroUnete ?: return
            view.getLugarNivel2(idLugarPadre)
        }
    }

    override fun showLugarNivel2(lst: List<ParametroUneteModel>) {
        spnComuna?.load(lst)
        val selected = view.getModel()?.lugarHijo
        spnComuna?.selection<ParametroUneteModel> { it?.nombre == selected }
    }

    override fun createModel(): PostulanteModel? {

        val model = view.getModel() ?: PostulanteModel()

        val experiencia = swtConsultoraAnteriormente?.isChecked?.toInt()
        val recomienda = swtRecomiendaConsultora?.isChecked?.toInt()
        val lugarpadre = spnRegion?.selected<ParametroUneteModel>()?.nombre
        val lugarHijo = spnComuna?.selected<ParametroUneteModel>()?.nombre

        model.celular = txtTelefonoCelular?.getText()
        model.telefono = txtTelefonoFijo?.getText()

        model.lugarPadre = lugarpadre
        model.lugarHijo = lugarHijo
        model.direccion = createDireccion()

        model.referencia = txtReferencia?.getText()
        model.tieneExperiencia = experiencia
        model.teRecomendoOtraConsultora = recomienda ?: Constant.NUMERO_CERO

        if (recomienda == Constant.NUMERO_CERO) {
            model.codigoConsultoraRecomienda = Constant.EMPTY_STRING
            model.nombreConsultoraRecomienda = Constant.EMPTY_STRING
        } else {
            model.codigoConsultoraRecomienda = txtCodigoConsultoraRecomienda?.getText()
            model.nombreConsultoraRecomienda = txtNombreConsultoraRecomienda?.getText()
        }

        model.paso = Constant.NUMERO_DOS
        model.sincronizado = false

        model.placeId = placeId
        model.hasChangedPlaceId = hasChangedPlaceId

        return model
    }

    override fun loadModel() {

        val model = view.getModel() ?: PostulanteModel()

        txtTelefonoCelular?.setText(model.celular)
        txtTelefonoFijo?.setText(model.telefono)
        txtDireccionGoogle?.setText(model.direccion)
        txtReferencia?.setText(model.referencia)

        swtConsultoraAnteriormente?.isChecked = (model.tieneExperiencia == Constant.NUMERO_UNO)
        swtRecomiendaConsultora?.isChecked =
            view.expandirConsultoraRecomendante(model.codigoConsultoraRecomienda)

        agregarDatosDeConsultoraRecomendante(model)
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

    private fun loadDireccion(model: PostulanteModel?) {
        txtTelefonoCelular.requestFocus()

        if (model?.lugarHijo.isNullOrEmpty()) {
            return
        }

        if(model?.direccion.isNullOrBlank()) {
            txtDireccionManual.gone()
            txtDireccionGoogle.visible()
            return
        }

        if (!isNewExperienceDireccion) {
            txtDireccionGoogle?.setText(model?.direccion)
            txtDireccionGoogle.visible()
            return
        }

        if (model?.direccion?.contains(String.format(Constant.FORMAT_DIRECTION_PLACE, model.lugarPadre.orEmpty())) == true ||
            model?.direccion?.contains(String.format(Constant.FORMAT_DIRECTION_PLACE, model.lugarHijo.orEmpty())) == true
        ) {
            txtDireccionManual.gone()
            txtDireccionGoogle?.visible()
            txtDireccionGoogle?.setText(model.direccion)
            return
        }

        txtDireccionGoogle.gone()
        txtDireccionManual?.visible()
        txtDireccionManual?.setText(model?.direccion)
    }

    private fun createDireccion(): String {
        return when (txtDireccionGoogle?.visibility) {
            View.VISIBLE -> {
                txtDireccionGoogle?.getText()
            }
            else -> txtDireccionManual?.getText()
        }.orEmpty()
    }

    private fun getDirectionValidate(): Boolean {
        return when (txtDireccionGoogle?.visibility) {
            View.VISIBLE -> {
                txtDireccionGoogle?.validate()
            }
            else -> txtDireccionManual?.validate()
        } ?: false
    }

    override fun validate(): Boolean {

        val v = mutableListOf<Boolean?>()

        if (isNewExperienceDireccion) {
            v += spnRegion?.validate()
            v += spnComuna?.validate()
        }
        v += txtTelefonoCelular?.validate()
        v += txtTelefonoFijo?.validate()
        v += getDirectionValidate()
        v += txtReferencia?.validate()

        return v.all { it ?: false }
    }

    override fun initRequired() {
        spnComuna?.required = true
        spnRegion?.required = true
        txtTelefonoCelular?.setRequired(true)
        txtDireccionGoogle?.setRequired(true)
        txtDireccionManual?.setRequired(true)
        txtReferencia?.setRequired(true)
        txtCodigoConsultoraRecomienda?.setRequired(true)
        txtNombreConsultoraRecomienda?.setRequired(true)
    }

    override fun initRestriction() {
        txtTelefonoCelular?.setRestriction(Filters.filterNumber(CL.TLF_CELULAR_MAX_LENGHT))
        txtTelefonoCelular?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtTelefonoFijo?.setRestriction(Filters.filterNumber(CL.TLF_FIJO_MAX_LENGHT))
        txtTelefonoFijo?.inputType(InputType.TYPE_CLASS_NUMBER)
        txtDireccionGoogle?.setRestriction(
            arrayOf(
                Filters.filterDireccion(),
                Filters.filterMaxLength(CL.DIRECCION_MAX_LENGHT)
            )
        )
        txtReferencia?.setRestriction(Filters.filterMaxLength(CL.DIRECCION_MAX_LENGHT))
        txtCodigoConsultoraRecomienda?.setRestriction(Filters.filterMaxLength(CL.COD_CONSULTORA_MAX_LENGHT))
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
        spnRegion?.addValidations(V(context, Validation.NO_SELECTION))
        spnComuna?.addValidations(V(context, Validation.NO_SELECTION))
        txtTelefonoCelular?.addV(
            V(context, EMPTY, R.string.unete_valid_celular_empty),
            V(context, MIN_LENGTH, CL.TLF_CELULAR_MIN_LENGHT, R.string.unete_valid_celular_format),
            V(context, INICIO, CL.TLF_CELULAR_DIGITO_INICIO, R.string.unete_valid_celular_format)
        )
        txtTelefonoFijo?.addV(
            V(context, MIN_LENGTH, CL.TLF_FIJO_MAX_LENGHT, R.string.unete_valid_celular_format)
        )
        txtDireccionGoogle?.addV(
            V(context, EMPTY, R.string.unete_valid_obligatorio)
        )
        txtDireccionManual?.addV(
            V(context, EMPTY, R.string.unete_valid_obligatorio)
        )
        txtReferencia?.addV(
            V(context, EMPTY, R.string.unete_valid_referencia_empty)
        )
    }

    override fun showDuplicateCelularError(message: String) {
        txtTelefonoCelular?.setEstado(BaseInput.ERROR, message)
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(
                mutableListOf(
                    spnRegion,
                    spnComuna,
                    txtTelefonoCelular,
                    txtTelefonoFijo,
                    txtDireccionGoogle,
                    txtDireccionManual,
                    txtReferencia,
                    swtConsultoraAnteriormente,
                    swtRecomiendaConsultora,
                    txtCodigoConsultoraRecomienda,
                    txtNombreConsultoraRecomienda
                )
            )
        }

        return lst
    }

    private fun getPreTextPlace(): String {
        return StringBuilder()
            .append(spnRegion.getText())
            .append(Constant.COMMA)
            .append(spnComuna.getText())
            .append(Constant.COMMA)
            .toString()
    }

    override fun mostrarPlaces(lugares: List<PlaceModel>) {
        txtDireccionGoogle.mostrarResultados(
            when (isNewExperienceDireccion) {
                true -> lugares.plus(PlaceModel(descripcion = context.getString(R.string.ingresar_direccion_manualmente)))
                else -> lugares
            }
        )
    }

    override fun buscarPlaces(cadenaBusqueda: String) {
        view.buscarPlaces("CL", getCadenaBusquedaCompat(cadenaBusqueda), getLocalidad())
    }

    private fun getCadenaBusquedaCompat(cadenaBusqueda: String): String {
        return (if (isNewExperienceDireccion) getPreTextPlace() else Constant.EMPTY_STRING).plus(cadenaBusqueda)
    }

    override fun onPlaceSelected(place: PlaceModel) {
        hasChangedPlaceId = true

        placeId = when (place.descripcion) {
            context.getString(R.string.ingresar_direccion_manualmente) -> {
                null
            }
            else -> place.id
        }

        when (isNewExperienceDireccion) {
            true -> {
                when (place.descripcion) {
                    context.getString(R.string.ingresar_direccion_manualmente) -> {
                        txtDireccionGoogle.visibility = View.GONE
                        txtDireccionManual.visibility = View.VISIBLE
                        txtDireccionManual.requestFocus()
                    }
                    else -> {
                        txtDireccionGoogle.visibility = View.VISIBLE
                        txtDireccionManual.visibility = View.GONE
                    }
                }
            }
            else -> Unit
        }
    }

    private fun getLocalidad(): Localidad? {
        return if (isNewExperienceDireccion) Localidad(
            spnComuna.getText().orEmpty(),
            spnRegion.getText().orEmpty()
        ) else null
    }

    override fun showNewExperienceDireccion() {
        isNewExperienceDireccion = true

        spnComuna.visibility = View.VISIBLE
        spnRegion.visibility = View.VISIBLE
        txtDireccionGoogle.visibility = View.GONE

        txtDireccionGoogle.setCustomPlaceholder(context.getString(R.string.direccion_placeholder))
        txtReferencia.setCustomPlaceholder(context.getString(R.string.referencia_placeholder))

        loadDireccion(view.getModel())
    }

}
