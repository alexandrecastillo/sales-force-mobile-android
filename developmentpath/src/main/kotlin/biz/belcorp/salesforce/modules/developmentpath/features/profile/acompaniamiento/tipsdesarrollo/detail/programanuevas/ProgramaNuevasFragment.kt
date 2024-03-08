package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.programanuevas

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
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents.DocumentoFragment
import kotlinx.android.synthetic.main.fragment_programa_nuevas.*

class ProgramaNuevasFragment : BaseFragment() {

    private var personaId: Long = -1L
    private lateinit var rol: Rol
    var opciones = emptyList<String>()

    private val faPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private val tips by lazy {
        TipsProgramaNuevasFragment.newInstance(personaId, rol)
    }

    private val materiales by lazy {
        DocumentoFragment.newInstance(personaId, rol, DocumentoFragment.PROCEDENCIA_PROGRAMA_NUEVAS)
            .apply {
                opciones = this@ProgramaNuevasFragment.opciones.filter { it in Opciones.ND4 }
            }
    }

    override fun getLayout(): Int = R.layout.fragment_programa_nuevas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incrustarFragments()
        clContenedorProgramaNuevas?.fadeAnimation()
        faPresenter.enviarPantallaProgramaNuevas(
            TagAnalytics.ACOMPANIAMIENTO_PROGRAMA_NUEVAS,
            rol,
            personaId
        )
    }

    private fun incrustarFragments() {
        if (!isAdded) return

        childFragmentManager
            .beginTransaction()
            .replace(R.id.framelayoutTips, tips)
            .replace(R.id.framelayoutMateriales, materiales)
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
            ProgramaNuevasFragment()
                .withArguments(
                    ARG_PERSONA_ID to personaId,
                    ARG_ROL to rol
                )
    }

}
