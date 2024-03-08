package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.last6campaigns

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.DesempenioAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.DesempenioModel
import biz.belcorp.salesforce.modules.developmentpath.utils.cambiarVisibilidad
import kotlinx.android.synthetic.main.fragment_desempenio_gr_gz.*

class DesempenioGrGzFragment : BaseFragment(), DesempenioGrGzView {

    private var personaId: Long = -1L
    private lateinit var rol: Rol

    private val presenterGr by injectFragment<DesempenioGrGzPresenter>()

    override fun getLayout() = R.layout.fragment_desempenio_gr_gz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personaId = arguments!!.getLong(ARG_PERSONA_ID)
        rol = arguments!!.getSerializable(ARG_PERSONA_ROL) as Rol
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenterGr.obtener(personaId, rol)
        configurarRecycler()
        flCabeceraDesempenio?.setOnClickListener {
            llDesempenio?.cambiarVisibilidad()
        }
    }

    private fun configurarRecycler() {
        val adapter = DesempenioAdapter(rol)
        rvDesempenio?.layoutManager = GridLayoutManager(context, 2)
        rvDesempenio?.adapter = adapter
    }

    override fun pintar(desempenios: List<DesempenioModel>) {
        (rvDesempenio?.adapter as? DesempenioAdapter)?.actualizar(desempenios)
    }

    companion object {

        private const val ARG_PERSONA_ID = "param1"
        private const val ARG_PERSONA_ROL = "param2"

        fun newInstance(personaId: Long, rol: Rol): DesempenioGrGzFragment {
            val fragment =
                DesempenioGrGzFragment()
            val args = Bundle()
            args.putLong(ARG_PERSONA_ID, personaId)
            args.putSerializable(ARG_PERSONA_ROL, rol)
            fragment.arguments = args
            return fragment
        }
    }
}
