package biz.belcorp.salesforce.modules.postulants.features.formulario.input.autoComplete

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View
import android.widget.AutoCompleteTextView
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.debounce
import biz.belcorp.salesforce.core.utils.onTextChanged
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso2
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.entities.PlaceModel
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.BaseInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class UAutoCompletePlaces(mContext: Context, attrs: AttributeSet) :
    BaseInput(mContext, attrs) {

    @JvmField
    var inputComponent: AutoCompleteTextView? = null

    private var mAdapter: Adapter? = null

    var mainView: IUnetePaso2? = null

    override fun getLayout() = R.layout.input_unete_autocomplete_places

    override fun getValue() = inputComponent?.text.toString()

    fun getText() = inputComponent?.text.toString()

    fun setText(text: String?) = inputComponent?.setText(text)


    fun setRestriction(filters: Array<out InputFilter>) {
        inputComponent?.filters = filters
    }

    fun setRestriction(filters: InputFilter) {
        inputComponent?.filters = arrayOf(filters)
    }

    fun setR(filters: Array<out InputFilter>) {
        inputComponent?.filters = filters
    }

    fun setR(filters: InputFilter) {
        inputComponent?.filters = arrayOf(filters)
    }

    override fun setEnable(disable: Boolean) {
        inputComponent?.isEnabled = disable
    }

    override fun init() {
        super.init()
        inputComponent?.id = View.generateViewId()
        loadAdapter()
    }

    override fun initializeOthers() {
        inputComponent = rootview.findViewById(R.id.unete_input_txtComponent)

        inputComponent?.setOnFocusChangeListener { _, hasFocus ->
            if(placeholder.isNullOrBlank())
                return@setOnFocusChangeListener

            inputComponent?.hint = if (hasFocus) placeholder else Constant.EMPTY_STRING
        }
    }

    override fun initEvents() {
        GlobalScope.launch(context = Dispatchers.Main) {
            inputComponent?.onTextChanged()?.debounce(1000)?.consumeEach {
                mainView?.buscarPlaces(it)
            }
        }
    }

    fun mostrarResultados(lugares: List<PlaceModel>) {
        loadAdapter(lugares)
    }

    private fun loadAdapter(lugares: List<PlaceModel> = emptyList()) {
        mAdapter = Adapter(mContext, lugares)
        inputComponent?.threshold = 3
        inputComponent?.setAdapter(mAdapter)


        inputComponent?.setOnItemClickListener { adapterView, _, position, _ ->
            val place = adapterView.getItemAtPosition(position) as PlaceModel
            mainView?.onPlaceSelected(place)
        }

        mAdapter?.notifyDataSetChanged()
    }

    fun clear() {
        inputComponent?.setText("")
    }

    fun setCustomPlaceholder(placeholder: String) {
        this.placeholder = placeholder
    }

}
