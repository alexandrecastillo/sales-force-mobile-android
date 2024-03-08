package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.ProfileViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion.ConversacionDesarrolloFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.foco.FocoPersonaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.view.DetalleHabilidadesFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.planificacion.PlanificacionFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsvideo.TipsFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.puntajepremio.PuntajePremioFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class AcompaniamientoFragment : BaseFragment() {

    var btnRegistrarVisita: Button? = null

    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private val profileViewModel by sharedViewModel<ProfileViewModel>(from = { requireParentFragment() })

    private val personIdentifier by lazyPersonIdentifier()

    private val fragmentFoco
        get() = FocoPersonaFragment.newInstance(personIdentifier.id, personIdentifier.role)

    private val fragmentPlanificacion: PlanificacionFragment
        get() = PlanificacionFragment.newInstance(personIdentifier.id, personIdentifier.role).also {
            it.btnRegistrarVisita = btnRegistrarVisita
        }

    private val fragmentTips: TipsFragment
        get() = TipsFragment.newInstance(personIdentifier.id, personIdentifier.role)

    private val fragmentHabilidades: DetalleHabilidadesFragment
        get() = DetalleHabilidadesFragment.newInstance(personIdentifier.id, personIdentifier.role)

    private val fragmentConversacionDesarrollo: ConversacionDesarrolloFragment
        get() = ConversacionDesarrolloFragment.newInstance(personIdentifier.id)

    fun conBotonRegistroVisita(boton: Button?): AcompaniamientoFragment {
        this.btnRegistrarVisita = boton
        return this
    }

    override fun getLayout(): Int = R.layout.fragment_acompaniamiento

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalyticsPresenter.enviarPantallaPerfil(
            TagAnalytics.PERFIL_ACOMPANIAMIENTO,
            personIdentifier.role,
            personIdentifier.id
        )
        incrustarFragments()
    }

    private fun incrustarFragments() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.framelayout_foco, fragmentFoco, "foco")
            .replace(R.id.fl_planificacion, fragmentPlanificacion, "planificacion")
            .also { agregarTipsSegunRol(it) }
            .also { agregarGraficosSegunRol(it) }
            .also { agregarHabilidadesSiGZ(it) }
            .also { agregarConversarionSegunRol(it) }
            .commit()
    }

    private fun agregarGraficosSegunRol(ft: FragmentTransaction) {
        val fragment: Fragment = when (personIdentifier.role) {
            Rol.CONSULTORA -> PuntajePremioFragment.newInstance(personIdentifier.id, personIdentifier.role)
            else -> return
        }
        ft.replace(R.id.fl_ventas_graficos_habilidades, fragment, "ventasFragment")
    }

    private fun agregarHabilidadesSiGZ(ft: FragmentTransaction) {
        if (personIdentifier.role == Rol.GERENTE_ZONA) {
            ft.replace(
                R.id.fl_ventas_graficos_habilidades,
                fragmentHabilidades,
                "habilidadesFragment"
            )
        }
    }

    private fun agregarTipsSegunRol(ft: FragmentTransaction) {
        if (personIdentifier.role != Rol.GERENTE_REGION) {
            ft.replace(R.id.fl_tips, fragmentTips, "tips")
        }
    }

    private fun agregarConversarionSegunRol(ft: FragmentTransaction) {
        if (personIdentifier.role == Rol.GERENTE_REGION) {
            ft.replace(
                R.id.fl_conversacion_desarrollo,
                fragmentConversacionDesarrollo,
                "conversacionDesarrollo"
            )
        }
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) = AcompaniamientoFragment()
            .withPersonIdentifier(personIdentifier)
    }
}
