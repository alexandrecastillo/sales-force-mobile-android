package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta

import android.os.Bundle
import android.view.View
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.getDrawable
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.salesforce.components.exported.sharedToolbarDark
import biz.belcorp.salesforce.core.R
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.base.R as BaseR

abstract class BaseDatosDialogFragment : BaseDialogFragment() {

    protected var personaId: Long = -1L
    protected var rol = Rol.NINGUNO
    protected var title: String? = null

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    protected fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(PARAM_PERSONA_ID)
            rol = it.getSerializable(PARAM_ROL) as Rol
            title = it.getString(PARAM_TITLE) ?: ""
        }
    }

    private fun inicializar() {
        configurarToolbar()
        incrustarFragments()
    }

    private fun configurarToolbar() {
        sharedToolbarDark?.apply {
            navigationIcon = getDrawable(R.drawable.ic_backspace)?.tinted(getColor(BaseR.color.white))
            setNavigationOnClickListener { dismiss() }
            title = this@BaseDatosDialogFragment.title
        }
    }

    protected abstract fun incrustarFragments()

    companion object {

        const val PARAM_PERSONA_ID = "PERSONA_ID"
        const val PARAM_ROL = "ROL"
        const val PARAM_TITLE = "TITLE"

        inline fun <reified T : BaseDatosDialogFragment> newInstance(
            personaId: Long,
            rol: Rol,
            title: String?
        ): T {
            return T::class.java.newInstance()
                .withArguments(
                    PARAM_PERSONA_ID to personaId,
                    PARAM_ROL to rol,
                    PARAM_TITLE to title
                )
        }
    }
}
