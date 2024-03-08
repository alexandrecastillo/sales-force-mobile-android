package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias

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
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.consolidado.ConsolidadoAcuerdosFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.AcuerdosHistoricosFragment
import kotlinx.android.synthetic.main.fragment_acuerdos_ultimas_campanias.*

class AcuerdosUltimasCampaniasFragment : BaseFragment() {

    private var personaId = -1L
    private lateinit var rol: Rol

    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    override fun getLayout(): Int = R.layout.fragment_acuerdos_ultimas_campanias

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    private fun recuperarArgumentos() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager
            .beginTransaction()
            .replace(
                R.id.frameLayoutConsolidado,
                ConsolidadoAcuerdosFragment.newInstance(personaId, rol)
            )
            .replace(
                R.id.frameLayoutHistorico,
                AcuerdosHistoricosFragment.newInstance(personaId, rol)
            )
            .commit()
        clContendorAcuerdosCampanias.fadeAnimation()
        firebaseAnalyticsPresenter.enviarPantallaAcuerdos(
            TagAnalytics.PREPARARSE_ES_CLAVE_ACUERDOS,
            rol,
            personaId
        )
    }

    companion object {

        private const val ARG_PERSONA_ID = "param1"
        private const val ARG_ROL = "param2"

        fun newInstance(personaId: Long, rol: Rol) = AcuerdosUltimasCampaniasFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}
