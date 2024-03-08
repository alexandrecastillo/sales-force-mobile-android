package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.container

import android.os.Bundle
import android.view.View
import android.widget.Button
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.planificacion.PlanificacionFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section.TipsDesarrolloFragment

class AcompaniamientoCoFragment : BaseFragment() {

    var btnRegistrarVisita: Button? = null

    private val personIdentifier by lazyPersonIdentifier()

    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private val planificationFagment by lazy {
        PlanificacionFragment.newInstance(
            personIdentifier.id,
            personIdentifier.role
        ).also {
            it.btnRegistrarVisita = btnRegistrarVisita
        }
    }

    private val tipsFragment by lazy {
        TipsDesarrolloFragment.newInstance(personIdentifier)
    }

    override fun getLayout(): Int = R.layout.fragment_acompaniamiento_co

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analiticasEnviarPantallaPerfil()
        incrustarFragmentos()
    }

    private fun analiticasEnviarPantallaPerfil() {
        firebaseAnalyticsPresenter.enviarPantallaPerfil(
            TagAnalytics.PERFIL_ACOMPANIAMIENTO,
            personIdentifier.role,
            personIdentifier.id
        )
    }

    private fun incrustarFragmentos() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fl_planificacion, planificationFagment)
            .replace(R.id.fl_tips_desarrollo, tipsFragment)
            .commit()
    }

    fun conBotonRegistroVisita(boton: Button?): AcompaniamientoCoFragment {
        this.btnRegistrarVisita = boton
        return this
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) = AcompaniamientoCoFragment()
            .withPersonIdentifier(personIdentifier)
    }
}
