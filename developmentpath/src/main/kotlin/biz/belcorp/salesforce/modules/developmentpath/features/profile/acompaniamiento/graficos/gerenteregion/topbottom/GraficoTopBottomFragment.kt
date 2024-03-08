package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.topbottom

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico
import kotlinx.android.synthetic.main.fragment_grafico_top_bottom.*

class GraficoTopBottomFragment : BaseFragment(),
    GraficoTopBottomView {

    private val presenter by injectFragment<GraficoTopBottomPresenter>()

    private var tipoGrafico = TipoGrafico.NINGUNO
    private var personaId: Long = 0

    override fun getLayout(): Int = R.layout.fragment_grafico_top_bottom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tipoGrafico = it.getSerializable(TIPO_GRAFICO) as TipoGrafico
            personaId = it.getLong(PERSONA_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.obtener(personaId, Rol.GERENTE_REGION, tipoGrafico)
        configurarRecyclerViews()
    }

    private fun configurarRecyclerViews() {
        rvTop?.layoutManager = LinearLayoutManager(context)
        rvTop?.adapter =
            GraficoTopBottomAdapter()

        rvBottom?.layoutManager = LinearLayoutManager(context)
        rvBottom?.adapter =
            GraficoTopBottomAdapter()
    }

    override fun pintarTops(tops: List<TopBottomModel>, mostrarEnDosLineas: Boolean) {
        if (tops.isNotEmpty()) {
            (rvTop?.adapter as? GraficoTopBottomAdapter)?.actualizar(tops, mostrarEnDosLineas)
        }
    }

    override fun pintarBottoms(bottoms: List<TopBottomModel>, mostrarEnDosLineas: Boolean) {
        if (bottoms.isNotEmpty()) {
            (rvBottom?.adapter as? GraficoTopBottomAdapter)?.actualizar(bottoms, mostrarEnDosLineas)
        }
    }

    override fun pintarCampaniaEnTitulo(campania: String) {
        tvTitulo?.text = getString(R.string.titulo_top_bottom, campania)
    }

    override fun pintarCampaniaEnTituloConComparativa(campania: String) {
        tvTitulo?.text = getString(R.string.titulo_top_bottom_con_comparativa, campania)
    }

    companion object {

        private const val TIPO_GRAFICO = "tipo_grafico"
        private const val PERSONA_ID = "PERSONA_ID"

        fun newInstance(personaId: Long, tipoGrafico: TipoGrafico) =
            GraficoTopBottomFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TIPO_GRAFICO, tipoGrafico)
                    putLong(PERSONA_ID, personaId)
                }
            }
    }
}
