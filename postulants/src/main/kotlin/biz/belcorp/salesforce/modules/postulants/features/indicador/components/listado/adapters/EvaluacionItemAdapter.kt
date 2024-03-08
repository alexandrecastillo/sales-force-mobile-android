package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.adapters

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ViewNotImplemented

import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.PasoViewItem
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractStepAdapter

class EvaluacionItemAdapter(context: Context, private val pasos: Int = Constant.NUMERO_CINCO) :
    AbstractStepAdapter(context) {

    private val pages = SparseArray<Step>()

    override fun createStep(position: Int): Step {
        when (position) {
            Constant.NUMERO_CERO, Constant.NUMERO_UNO, Constant.NUMERO_DOS,
            Constant.NUMERO_TRES, Constant.NUMERO_CUATRO -> return PasoViewItem(context)
            else -> throw ViewNotImplemented()
        }

    }

    override fun findStep(position: Int): Step? {
        return if (pages.size() > 0) pages.get(position) else null
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        var step: Step? = pages.get(position)
        if (step == null) {
            step = createStep(position)
            pages.put(position, step)
        }

        val stepView = step as View
        container.addView(stepView)

        return stepView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun getCount(): Int {
        return pasos
    }

}
