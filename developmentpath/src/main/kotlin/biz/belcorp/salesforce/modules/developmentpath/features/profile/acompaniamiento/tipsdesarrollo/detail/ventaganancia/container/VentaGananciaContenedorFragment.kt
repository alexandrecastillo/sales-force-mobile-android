package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.container

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.fadeAnimation
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.VentaGananciaTipsFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.ventaganancia.VentaGananciaFragment
import kotlinx.android.synthetic.main.fragment_venta_ganancia_contenedor.*

class VentaGananciaContenedorFragment : BaseFragment() {

    private var personaId: Long = -1L
    private var rol = Rol.CONSULTORA

    var opciones = emptyList<String>()

    private val faPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private val ventaGananciaFragment by lazy {
        VentaGananciaFragment.newInstance(personaId, rol).apply {
            opciones = this@VentaGananciaContenedorFragment.opciones
        }
    }

    private val tipsFragment by lazy {
        VentaGananciaTipsFragment.newInstance(personaId, rol).apply {
            opciones = this@VentaGananciaContenedorFragment.opciones
        }
    }

    override fun getLayout(): Int = R.layout.fragment_venta_ganancia_contenedor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clContenedorVentaGanancia?.fadeAnimation()
        incrustarFragments()
        faPresenter.enviarPantallaVentaGanancia(
            TagAnalytics.ACOMPANIAMIENTO_VENTA_GANANCIA,
            rol,
            personaId
        )
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(PARAM_PERSONA_ID)
            rol = it.getSerializable(PARAM_ROL) as Rol
        }
    }

    private fun incrustarFragments() {
        if (!isAdded) return
        childFragmentManager
            .beginTransaction()
            .replace(R.id.framelayoutVentaGanancia, ventaGananciaFragment)
            .replace(R.id.framelayoutTips, tipsFragment)
            .commit()
    }

    companion object {

        private const val PARAM_PERSONA_ID = "PERSONA_ID"
        private const val PARAM_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol): VentaGananciaContenedorFragment {
            return VentaGananciaContenedorFragment()
                .withArguments(
                    PARAM_PERSONA_ID to personaId,
                    PARAM_ROL to rol
                )
        }
    }
}
