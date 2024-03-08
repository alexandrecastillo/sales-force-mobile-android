package biz.belcorp.salesforce.modules.postulants.features.adapter

import android.content.Context
import androidx.fragment.app.FragmentManager
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ViewNotImplemented
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.paso1.UnetePrePaso1Fragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.paso2.UnetePrePaso2Fragment
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter

class UnetePreStepperAdapter(fm: FragmentManager, m: Context, private val pasos: Int = 2) :
    AbstractFragmentStepAdapter(fm, m) {

    override fun getCount(): Int {
        return pasos
    }

    override fun createStep(position: Int): Step {
        return when (position) {
            Constant.NUMERO_CERO -> UnetePrePaso1Fragment()
            Constant.NUMERO_UNO -> UnetePrePaso2Fragment()
            else -> throw ViewNotImplemented()
        }
    }
}
