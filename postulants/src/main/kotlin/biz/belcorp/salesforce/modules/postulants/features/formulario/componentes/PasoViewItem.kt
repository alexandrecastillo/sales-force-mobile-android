package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import android.content.Context
import android.widget.FrameLayout
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError

class PasoViewItem(context: Context) : FrameLayout(context), Step {

    init {
        init()
    }

    @Suppress("DEPRECATION")
    private fun init() = Unit

    override fun onSelected() = Unit

    override fun verifyStep(): VerificationError? = null

    override fun onError(error: VerificationError) = Unit

}
