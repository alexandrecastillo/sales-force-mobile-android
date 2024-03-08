package biz.belcorp.salesforce.modules.postulants.features.widget.validUnete

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoParametro
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel
import biz.belcorp.salesforce.modules.postulants.features.entities.TipoMetaModel
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteListaModel
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class USpinner(mContext: Context, attrs: AttributeSet) : BaseInput(mContext, attrs) {

    var inputComponent: Spinner? = null
    var txtHintMain: TextView? = null
    var onItemSelectedListener: AdapterView.OnItemSelectedListener? = null
    var hasSelection: Boolean = false

    private var isFirstValidation: Boolean = true
    private lateinit var modelList: MutableList<Any>

    override fun init() {
        super.init()
        inputComponent?.id = View.generateViewId()
    }

    override fun initializeOthers() {
        inputComponent = rootview.findViewById(R.id.spnComponent)
        txtHintMain = rootview.findViewById(R.id.txtHint)
    }

    override fun getValue(): String {
        return inputComponent?.selectedItemId.toString()
    }

    override fun getLayout(): Int {
        return R.layout.input_unete_spinner
    }

    fun selectedItem(): Any? {
        return inputComponent?.selectedItem
    }

    fun adapter(adapter: SpinnerAdapter) {
        inputComponent?.adapter = adapter
    }

    fun load(model: List<Any>) {
        modelList = model.toMutableList()
        if (hasSelection) {
            modelList = addSeleccione(modelList) as MutableList<Any>
        }
        val adapter = ArrayAdapter<Any>(mContext, R.layout.spinner_item_unete, modelList)
        inputComponent?.adapter = adapter
    }

    fun selection(pos: Int) {
        inputComponent?.setSelection(pos)
    }

    fun selectionId(id: Int?) {
        val pos: Int = getPositionById(id)
        inputComponent?.setSelection(pos)
    }

    private fun getPositionById(id: Int?): Int {
        when (modelList[0]) {
            is TipoMetaModel -> {
                for ((pos, item) in modelList.toList().withIndex()) {
                    val p = item as TipoMetaModel
                    if (p.idTipoMeta == id) {
                        return pos
                    }
                }
            }

            is UneteListaModel -> {
                for ((pos, item) in modelList.toList().withIndex()) {
                    val t = item as UneteListaModel
                    if (t.id == id) {
                        return pos
                    }
                }
            }
        }
        return -1
    }

    fun onItemSelectedListener(listener: AdapterView.OnItemSelectedListener) {
        inputComponent?.onItemSelectedListener = listener
    }

    override fun validate(): Boolean {
        if (!isFirstValidation) {
            when (val itemSelected = selectedItem()) {
                is ParametroUneteModel -> {
                    return validateModel(itemSelected.valor)
                }
                is TablaLogicaModel -> {
                    return validateModel(itemSelected.codigo)
                }
            }
            setEstado(OK)
            return true
        }

        isFirstValidation = false
        setEstado(NEUTRO)
        return true
    }

    private fun validateModel(value: String?): Boolean {
        if (valids.isNotEmpty()) {
            if (required!! || getValue().isNotBlank()) {
                valids.forEach {
                    if (!Validation.validate(it.tipo, value ?: Constant.EMPTY_STRING, it.value)
                    ) {
                        setEstado(ERROR, it.errorMsg)
                        return false
                    }
                }
            }
            setEstado(OK)
            return true
        }
        setEstado(NEUTRO)
        return true
    }

    override fun setEnable(disable: Boolean) {
        inputComponent?.isEnabled = disable
    }

    override fun setAttributes() {
        super.setAttributes()
        txtHintMain?.text =
            if (hint.isNullOrEmpty()) Constant.EMPTY_STRING else (mandatoryField + hint)
        val attrSpinner = context.theme.obtainStyledAttributes(attrs, R.styleable.USpinner, 0, 0)
        hasSelection = attrSpinner.getBoolean(R.styleable.USpinner_hasSelection, false)
    }

    override fun initEvents() {

        inputComponent?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            //Performing action onItemSelected and onNothing selected
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onItemSelectedListener?.onItemSelected(parent, view, position, id)
                validate()
            }

            override fun onNothingSelected(arg0: AdapterView<*>) = Unit

        }
    }

    private fun addSeleccione(model: MutableList<Any>): List<Any> {
        if (model.isNotEmpty()) {
            when (model[0]) {
                is ParametroUneteModel -> model.add(0, getParametroUneSeleccione())
                is TablaLogicaModel -> model.add(0, getTablaLogicaSeleccione())
                is TipoMetaModel -> model.add(0, getTipoMetaSeleccione())
            }
        }
        return model
    }

    private fun getParametroUneSeleccione(): ParametroUneteModel {
        val p = ParametroUneteModel()
        p.idParametroUnete = -1
        p.idParametroUnetePadre = 0
        p.tipoParametro = UneteTipoParametro.SUFIJOS_DOCS_BOLIVIA.tipo
        p.nombre = "Seleccione"
        p.descripcion = "Seleccione"
        p.valor = "-1"

        return p
    }

    private fun getTablaLogicaSeleccione(): TablaLogicaModel {
        val t = TablaLogicaModel()
        t.codigo = "-1"
        t.descripcion = "Seleccione"

        return t
    }

    private fun getTipoMetaSeleccione(): TipoMetaModel {
        val t = TipoMetaModel()
        t.idTipoMeta = -1
        t.descripcion = "Seleccione"
        return t
    }

}
