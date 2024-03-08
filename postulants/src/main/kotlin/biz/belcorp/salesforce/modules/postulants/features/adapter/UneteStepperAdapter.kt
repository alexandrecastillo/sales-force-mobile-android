package biz.belcorp.salesforce.modules.postulants.features.adapter

import android.content.Context
import androidx.fragment.app.FragmentManager
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso1.UnetePaso1Fragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.UnetePaso2Fragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso3.UnetePaso3Fragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4.UnetePaso4Fragment
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso5.UnetePaso5Fragment
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter

class UneteStepperAdapter(fm: FragmentManager, m: Context, private val pasos: Int = 5) :
    AbstractFragmentStepAdapter(fm, m) {

    override fun getCount() = pasos

    override fun createStep(position: Int): Step {
        return when (position) {
            Constant.NUMERO_CERO -> UnetePaso1Fragment()
            Constant.NUMERO_UNO -> UnetePaso2Fragment()
            Constant.NUMERO_DOS -> getTabTwo()
            Constant.NUMERO_TRES -> getTabThree()
            Constant.NUMERO_CUATRO -> UnetePaso5Fragment()
            else -> throw IllegalArgumentException("Unsupported position: $position")
        }
    }

    private fun getTabTwo(): Step {
        return if (pasos == Constant.NUMERO_CUATRO) {
            UnetePaso4Fragment()
        } else {
            UnetePaso3Fragment()
        }
    }

    private fun getTabThree(): Step {
        return if (pasos == Constant.NUMERO_CUATRO) {
            UnetePaso5Fragment()
        } else {
            UnetePaso4Fragment()
        }
    }
}
