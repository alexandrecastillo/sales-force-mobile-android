package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.container

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.fadeAnimation
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades.NovedadesFragment
import kotlinx.android.synthetic.main.fragment_novedades_contenedor.*

class NovedadesContenedorFragment : BaseFragment() {

    private var personaId = -1L
    private var rol = Rol.NINGUNO
    var opciones = emptyList<String>()

    private val faPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private val datosNovedades by lazy {
        NovedadesFragment.newInstance(personaId, rol)
            .apply {
                opciones = this@NovedadesContenedorFragment.opciones
            }
    }

    override fun getLayout(): Int = R.layout.fragment_novedades_contenedor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incrustarFragments()
        clContenedorNovedades?.fadeAnimation()
        faPresenter.enviarPantallaAcompanamientoNovedades(
            TagAnalytics.ACOMPANIAMIENTO_NOVEDADES,
            rol,
            personaId
        )
    }

    private fun recuperarArgs() = arguments?.let { bundle ->
        personaId = bundle.getLong(ARG_PERSONA_ID)
        rol = bundle.getSerializable(ARG_ROL) as Rol
    }

    private fun incrustarFragments() {
        if (!isAdded) return
        childFragmentManager
            .beginTransaction()
            .replace(R.id.framelayoutNovedades, datosNovedades)
            .commit()
    }

    companion object {

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol) =
            NovedadesContenedorFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PERSONA_ID, personaId)
                    putSerializable(ARG_ROL, rol)
                }
            }
    }
}
