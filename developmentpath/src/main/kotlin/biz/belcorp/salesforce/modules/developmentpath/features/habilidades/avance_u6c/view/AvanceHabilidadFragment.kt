package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.model.AvanceHabilidadModel
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.presenter.AvanceHabilidadPresenter
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix
import kotlinx.android.synthetic.main.fragment_avance_habilidades_u6c.*
import kotlinx.android.synthetic.main.item_avance_habilidad_u6c.*

class AvanceHabilidadFragment : BaseDialogFragment(), AvanceHabilidadView {

    private val presenter by injectFragment<AvanceHabilidadPresenter>()

    private var personaId = -1L
    lateinit var rol: Rol

    private var adapter: AvanceHabilidadesAdapter? = null

    companion object {

        private const val PERSONA_ID = "PERSONA_ID"
        private const val ROL = "rol"

        fun newInstance(personaId: Long, rol: Rol): AvanceHabilidadFragment {
            val bundle = Bundle()
            val fragment = AvanceHabilidadFragment()
            bundle.putLong(PERSONA_ID, personaId)
            bundle.putSerializable(ROL, rol)
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun recuperarArgumentos() {
        arguments?.let {
            personaId = it.getLong(PERSONA_ID)
            rol = it.getSerializable(ROL) as Rol
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    override fun getLayout() = R.layout.fragment_avance_habilidades_u6c

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRecylcer()
        presenter.establecerPersonaIdYRol(personaId, rol)
    }

    override fun onStart() {
        super.onStart()
        escucharAcciones()
        configurarFullScreen()
    }

    private fun configurarFullScreen() {
        dialog?.apply {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            window?.setLayout(width, height)
        }
    }

    private fun configurarRecylcer() {
        context?.apply {
            recycler_avance_habilidades?.layoutManager = LinearLayoutManager(this)
            recycler_avance_habilidades?.isNestedScrollingEnabled = false
            adapter = AvanceHabilidadesAdapter()
            recycler_avance_habilidades?.adapter = adapter
        }
    }

    private fun escucharAcciones() {
        image_back?.setOnClickListener { this.dismiss() }
    }

    override fun pintarHabilidades(avance: List<AvanceHabilidadModel>) {
        adapter?.actualizar(avance)
    }

    override fun pintarCampanias(campanias: List<String>) {
        val textViews = listOf(text_campania_x,
            text_campania_x_menos_1,
            text_campania_x_menos_2,
            text_campania_x_menos_3,
            text_campania_x_menos_4,
            text_campania_x_menos_5)

        textViews.forEachIndexed { index, textView ->
            if (campanias.size >= textViews.size)
                pintarCampania(textView, campanias[index])
        }
    }

    private fun pintarCampania(textView: TextView?, campania: String) {
        textView?.text = campania.maskCampaignWithPrefix()
    }
}
