package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.fadeAnimation
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.concursos.TipoConcurso
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import kotlinx.android.synthetic.main.acomp_concursos_fragment.*

class ConcursosFragment : BaseFragment() {

    private var personaId: Long = -1L
    private lateinit var rol: Rol

    private val faPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private val fragmentRegaloPorPedido by lazy {
        RegaloPorPedidoFragment.newInstance(personaId, rol).apply {
            tipoConcurso = TipoConcurso.REGALO_POR_PEDIDO
        }
    }

    private val fragmentProgramaBrillante by lazy {
        ProgramaBrillanteFragment.newInstance(personaId, rol).apply {
            tipoConcurso = TipoConcurso.PROGRAMA_BRILLANTE
        }
    }

    override fun getLayout(): Int = R.layout.acomp_concursos_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clContenedorConcursos?.fadeAnimation()
        incrustarFragmentos()
        faPresenter.enviarPantallaConcursos(TagAnalytics.ACOMPANIAMIENTO_CONCURSOS, rol, personaId)
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    private fun incrustarFragmentos() {
        childFragmentManager.beginTransaction()
            .replace(R.id.frameRegaloPorPedidos, fragmentRegaloPorPedido)
            .replace(R.id.frameProgramaBrillante, fragmentProgramaBrillante)
            .commit()
    }

    companion object {

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol) = ConcursosFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}
