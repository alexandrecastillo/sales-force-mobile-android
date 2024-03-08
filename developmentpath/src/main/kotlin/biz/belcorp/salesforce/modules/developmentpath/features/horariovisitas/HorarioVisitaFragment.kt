package biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Constants
import kotlinx.android.synthetic.main.fragment_horario_visita.*

class HorarioVisitaFragment : BaseFragment(), HorarioVisitaContract.View,
    HorarioVisitaAdapter.Callback {

    private val presenter by injectFragment<HorarioVisitaContract.Presenter>()

    private var personaId: Long = Constants.MENOS_UNO.toLong()
    private var rol = Rol.CONSULTORA

    private val adapter by lazy {
        HorarioVisitaAdapter().apply {
            callback = this@HorarioVisitaFragment
        }
    }

    override fun getLayout() = R.layout.fragment_horario_visita

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun mostrarData(data: List<HorarioVisitaModel>) {
        adapter.actualizar(data)
    }

    override fun onHorarioSeleccionadoItem(item: HorarioVisitaModel, posicion: Int) {
        presenter.saveOffLineHorarioSeleccionado(personaId, item)
        adapter.actualizarhorarioSeleccionado(item.horarioVisitaId)
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    private fun inicializar() {
        configurarRecyclerView()
        inicializarPresenters()
    }

    private fun configurarRecyclerView() {
        val context = context ?: return
        recycler_schedule?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = this@HorarioVisitaFragment.adapter
        }
    }

    private fun inicializarPresenters() {
        presenter.obtener(personaId)
    }

    companion object {
        val TAG = HorarioVisitaFragment::class.java.simpleName

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol): HorarioVisitaFragment {
            return HorarioVisitaFragment()
                .withArguments(
                    ARG_PERSONA_ID to personaId,
                    ARG_ROL to rol
                )
        }
    }
}
