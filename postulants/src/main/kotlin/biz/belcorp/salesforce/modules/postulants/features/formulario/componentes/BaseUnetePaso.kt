package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Input

abstract class BaseUnetePaso<out V>(mContext: Context, val view: V) : IUnetePaso, LinearLayout(mContext) {

    init {
        init()
    }

    fun init() {
        initView()
        initEvents()
        initControls()
        loadModel()
        initRequired()
        initRestriction()
        initValidation()
    }

    abstract fun getLayout(): Int

    open fun initView() {
        val view = inflate(context, getLayout(), null)
        view.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        addView(view)
    }

    open fun initEvents() = Unit

    open fun initControls() = Unit

    open fun initRequired() = Unit

    open fun initRestriction() = Unit

    open fun initValidation() = Unit

    open fun getDisables(enEdicion: Boolean): MutableList<View> {
        return mutableListOf()
    }

    override fun disable(enEdicion: Boolean) {
        getDisables(enEdicion).forEach {
            (it as? Input)?.setEnable(false)
            it.isEnabled = false
        }
    }

    abstract fun loadModel()

}
