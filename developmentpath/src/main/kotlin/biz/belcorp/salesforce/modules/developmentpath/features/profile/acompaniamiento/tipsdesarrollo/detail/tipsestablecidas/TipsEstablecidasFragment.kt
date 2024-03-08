package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.tipsestablecidas

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
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.programanuevas.TipsProgramaNuevasFragment
import kotlinx.android.synthetic.main.fragment_tips_establecidas.*

class TipsEstablecidasFragment : BaseFragment() {

    private var personaId: Long = -1L
    private lateinit var rol: Rol
    var opciones = emptyList<String>()

    private val faPresenter: FirebaseAnalyticsPresenter by injectFragment()

    private val tips by lazy {
        TipsProgramaNuevasFragment.newInstance(personaId, rol)
    }

    override fun getLayout() = R.layout.fragment_tips_establecidas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incrustarFragments()
        clContenedorTipsEstablecidas?.fadeAnimation()
        faPresenter.enviarPantallaProgramaNuevasYTipsEstablecidas(
            TagAnalytics.ACOMPANIAMIENTO_TIPS_ESTABLECIDAS,
            rol,
            personaId
        )
    }

    private fun incrustarFragments() {
        if (!isAdded) return

        childFragmentManager
            .beginTransaction()
            .replace(R.id.framelayoutTips, tips)
            .commit()
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    companion object {

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol) =
            TipsEstablecidasFragment().withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }

}
