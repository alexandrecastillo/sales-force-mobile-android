package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.container

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
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.ProgresoCaminoBrillanteFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.TipsFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents.DocumentoFragment
import kotlinx.android.synthetic.main.fragment_camino_brillante.*

class CaminoBrillanteFragment : BaseFragment() {

    private var personaId = -1L
    private var rol = Rol.NINGUNO
    var opciones = emptyList<String>()

    private val fragmentoProgreso by lazy {
        ProgresoCaminoBrillanteFragment.newInstance(
            personaId,
            rol
        )
    }

    private val faPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private val tipsFragment by lazy {
        TipsFragment.newInstance(personaId, rol, TipsFragment.PROCEDENCIA_CAMINO_BRILLANTE).apply {
            opciones = this@CaminoBrillanteFragment.opciones.filter { it == Opciones.CB2 }
        }
    }

    private val materiales by lazy {
        DocumentoFragment.newInstance(
            personaId,
            rol,
            DocumentoFragment.PROCEDENCIA_CAMINO_BRILLANTE
        )
            .apply {
                opciones = this@CaminoBrillanteFragment.opciones.filter { it in Opciones.ND5 }
            }
    }

    override fun getLayout(): Int = R.layout.fragment_camino_brillante

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clContenedorCaminoBrillante?.fadeAnimation()
        incrustrarFragmentos()
        faPresenter.enviarPantallaCaminoBrillante(
            TagAnalytics.ACOMPANIAMIENTO_CAMINO_BRILLANTE,
            rol,
            personaId
        )
    }

    private fun recuperarArgumentos() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    private fun incrustrarFragmentos() {
        childFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentoProgreso,
                fragmentoProgreso,
                fragmentoProgreso::class.java.simpleName
            )
            .replace(
                R.id.fragmentoTeRecomendamos,
                tipsFragment,
                tipsFragment::class.java.simpleName
            )
            .replace(R.id.fragmentoMateriales, materiales)
            .commit()
    }

    companion object {
        const val ARG_PERSONA_ID = "id"
        const val ARG_ROL = "rol"

        fun newInstance(personaId: Long, rol: Rol) = CaminoBrillanteFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}
