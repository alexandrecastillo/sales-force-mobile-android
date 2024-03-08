package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.foco

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.foco.MiRutaFocoFragment
import kotlinx.android.synthetic.main.fragment_foco_persona.*

class FocoPersonaFragment : BaseFragment(), FocoPersonaView {

    private var personaId: Long = -1
    private lateinit var rol: Rol

    private val focoPresenter by injectFragment<FocoPresenter>()

    private var tipId: Long? = null

    override fun getLayout() = R.layout.fragment_foco_persona

    companion object {

        private const val PERSONA_ID = "PERSONA_ID"
        private const val PERSONA_ROL = "PERSONA_ROL"

        fun newInstance(personaId: Long, personaRol: Rol) = FocoPersonaFragment()
            .withArguments(
                PERSONA_ID to personaId,
                PERSONA_ROL to personaRol
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            personaId = it.getLong(PERSONA_ID)
            rol = it.getSerializable(PERSONA_ROL) as Rol
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        escucharAcciones()
        focoPresenter.establecerView(this)
        focoPresenter.tieneFoco(personaId, rol)
    }

    override fun onDestroy() {
        focoPresenter.destruirFocoView()
        super.onDestroy()
    }

    override fun mostrarBanner() {
        layout_banner?.visibility = View.VISIBLE
    }

    override fun ocultarBanner() {
        layout_banner?.visibility = View.GONE
    }

    override fun recuperarTipId(tipId: Long) {
        this.tipId = tipId
    }

    private fun escucharAcciones() {
        layout_banner?.setOnClickListener {
            lanzarFocoDetalle()
        }
    }

    private fun lanzarFocoDetalle() {
        tipId?.apply {
            val fragment = MiRutaFocoFragment.newInstance(this.toInt())
            fragmentManager?.let { fragment.show(it, fragment.tag) }
        }
    }
}
