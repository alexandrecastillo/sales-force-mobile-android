package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.container

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.fadeAnimation
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Opciones
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.herramientasdigitales.HerramientaDigitalFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.TipsFragment
import kotlinx.android.synthetic.main.fragment_herramientas_digitales_contenedor.*

class HerramientaDigitalContenedorFragment : BaseFragment() {

    private var personaId: Long = -1L
    private var rol = Rol.CONSULTORA

    var opciones = emptyList<String>()

    private val faPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private val herramientasDigitalesFragment by lazy {
        HerramientaDigitalFragment.newInstance(personaId, rol).apply {
            opciones = this@HerramientaDigitalContenedorFragment.opciones
        }
    }

    private val tipsFragment by lazy {
        TipsFragment.newInstance(personaId, rol, TipsFragment.PROCEDENCIA_HERRAMIENTA_DIGITAL).apply {
            opciones = this@HerramientaDigitalContenedorFragment.opciones.filter { it == Opciones.DG1 }
        }
    }

    override fun getLayout(): Int = R.layout.fragment_herramientas_digitales_contenedor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clContenedorDigital?.fadeAnimation()
        incrustarFragments()
        faPresenter.enviarPantallaAcompanamientoDigital(
            TagAnalytics.ACOMPANIAMIENTO_DIGITAL,
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
            .replace(R.id.framelayoutHerramientaDigital, herramientasDigitalesFragment)
            .replace(R.id.framelayoutTips, tipsFragment)
            .commit()
    }

    companion object {

        private const val PARAM_PERSONA_ID = "PERSONA_ID"
        private const val PARAM_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol): HerramientaDigitalContenedorFragment {
            return HerramientaDigitalContenedorFragment()
                .withArguments(
                    PARAM_PERSONA_ID to personaId,
                    PARAM_ROL to rol
                )
        }
    }
}
