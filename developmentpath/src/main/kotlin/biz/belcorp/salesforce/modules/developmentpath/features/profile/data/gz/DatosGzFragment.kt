package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view.RankingChartsFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzagananciaanterior.CollectionInfoFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.contacto.DatosContactoFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.last6campaigns.DesempenioGrGzFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.view.MetaPersonalFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.persona.DatosPersonaFragment

class DatosGzFragment : BaseFragment() {

    private val personIdentifier by lazyPersonIdentifier()

    override fun getLayout() = R.layout.fragment_datos_gz

    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private val datosContactoFragment: DatosContactoFragment by lazy {
        DatosContactoFragment.newInstance(personIdentifier)
    }

    private val rankingChartsFragment: RankingChartsFragment by lazy {
        RankingChartsFragment.newInstance(personIdentifier)
    }

    private val metaPersonalFragment: MetaPersonalFragment by lazy {
        MetaPersonalFragment.newInstance(personIdentifier.id, personIdentifier.role)
    }

    private val desempenioGrGzFragment: DesempenioGrGzFragment by lazy {
        DesempenioGrGzFragment.newInstance(personIdentifier.id, personIdentifier.role)
    }

    private val cobranzaCampaniaAnterior: CollectionInfoFragment by lazy {
        CollectionInfoFragment.newInstance(personIdentifier)
    }

    private val datosPersona: DatosPersonaFragment by lazy {
        DatosPersonaFragment.newInstance(personIdentifier)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalyticsPresenter.enviarPantallaPerfil(
            TagAnalytics.PERFIL_DATOS,
            personIdentifier.role,
            personIdentifier.id
        )

        incrustarDesplegables()
    }

    private fun incrustarDesplegables() {
        if (context == null) return

        childFragmentManager.beginTransaction()
            .replace(R.id.flDatosContacto, datosContactoFragment)
            .replace(R.id.flGraficos, rankingChartsFragment)
            .replace(R.id.flMetasPersonales, metaPersonalFragment)
            .replace(R.id.flDesempenioUC, desempenioGrGzFragment)
            .replace(R.id.flCobranzaCampaniaAnterior, cobranzaCampaniaAnterior)
            .replace(R.id.flDatosPersona, datosPersona)
            .commit()
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) =
            DatosGzFragment()
                .withPersonIdentifier(personIdentifier)
    }
}
