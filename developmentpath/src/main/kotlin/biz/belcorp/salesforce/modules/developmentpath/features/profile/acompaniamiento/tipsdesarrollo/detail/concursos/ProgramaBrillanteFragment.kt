package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.base.R as BaseR
import biz.belcorp.salesforce.components.utils.decoration.BoxOffsetDecoration
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.include_concursos.*

class ProgramaBrillanteFragment : BaseFragment(), ConcursosFragmentContract.View {

    private val presenter by injectFragment<ConcursosFragmentContract.Presenter>()
    private val adapter by lazy { ProgramaBrillanteAdapter(R.layout.item_programa_brillante) }

    private var personaId: Long = -1L
    private lateinit var rol: Rol

    var tipoConcurso = ""

    override fun getLayout(): Int = R.layout.fragment_programa_brillante

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pintarTitulo()
        configurarRecycler()
        presenter.obtenerConcursosPorTipo(personaId, rol, tipoConcurso)
    }

    override fun mostarConcursos(concursos: List<ConcursoViewModel>) {
        adapter.actualizar(concursos)
    }

    override fun mostarSinDatos() {
        clVistaSinDatos?.visible()
        rvConcursos?.gone()
        txtSinDatos?.text = getString(R.string.programa_brillante_vacio)
    }

    override fun ocultarSinDatos() {
        clVistaSinDatos?.gone()
        rvConcursos?.visible()
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    private fun pintarTitulo() {
        txtTitulo?.text = getString(R.string.title_programa_brillante)
    }

    private fun configurarRecycler() {
        rvConcursos?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                BoxOffsetDecoration(
                    requireContext(),
                    BaseR.dimen.content_inset_half,
                    BaseR.dimen.zero
                )
            )
            adapter = this@ProgramaBrillanteFragment.adapter
        }
    }

    companion object {

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol) = ProgramaBrillanteFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}
