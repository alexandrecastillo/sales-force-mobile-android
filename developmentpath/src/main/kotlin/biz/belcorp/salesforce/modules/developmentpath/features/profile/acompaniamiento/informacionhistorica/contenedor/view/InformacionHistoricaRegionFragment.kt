package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.contenedor.view

import android.os.Bundle
import android.view.View
import biz.belcorp.ffvv.presentation.feature.rdd.perfil.acompaniamiento.informacionhistorica.contenedor.view.InformacionHistoricaRegionView
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.nextPage
import biz.belcorp.salesforce.core.utils.previousPage
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.contenedor.model.GraficoResumenModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.contenedor.presenter.InformacionHistoricaRegionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view.GraficosResumenRegionesAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view.PaginaCambiable
import kotlinx.android.synthetic.main.fragment_informacion_historica_region.*

class InformacionHistoricaRegionFragment : BaseDialogFragment(),
    PaginaCambiable,
    InformacionHistoricaRegionView {

    private var indexSeleccionado: Int = 0

    private val presenter by injectFragment<InformacionHistoricaRegionPresenter>()

    fun seleccionadoEn(index: Int): InformacionHistoricaRegionFragment {
        indexSeleccionado = index
        return this
    }

    private val personaId: Long by lazy {
        arguments?.getLong(ARG_PERSONA_ID, 0) ?: -1
    }

    private val rol: Rol by lazy {
        Rol.GERENTE_REGION
    }

    private val graficosPagerAdapter: GraficosResumenRegionesAdapter by lazy {
        GraficosResumenRegionesAdapter(childFragmentManager)
    }

    override fun getLayout() = R.layout.fragment_informacion_historica_region

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    private fun inicializar() {
        configurarViewPager()
        escucharAcciones()
        presenter.establecerView(this)
        presenter.recuperar(personaId, rol)
    }

    private fun escucharAcciones() {
        imageBack?.setOnClickListener { closeDialog() }
    }

    private fun configurarViewPager() {
        viewPagerInformacionHistorica?.adapter = graficosPagerAdapter
        graficosPagerAdapter.establecerListener(this)
    }

    override fun alPresionarSiguiente() {
        viewPagerInformacionHistorica?.nextPage()
    }

    override fun alPresionarAnterior() {
        viewPagerInformacionHistorica?.previousPage()
    }

    override fun cargarGraficos(graficos: List<GraficoResumenModel>) {
        graficosPagerAdapter.actualizar(personaId, rol, graficos)
        mostrarEnIndiceSeleccionado()
    }

    private fun mostrarEnIndiceSeleccionado() {
        viewPagerInformacionHistorica?.currentItem = indexSeleccionado
    }

    companion object {

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        const val TAG = "InformacionHistoricaFragmentc"

        fun newInstance(personaId: Long): InformacionHistoricaRegionFragment {
            return InformacionHistoricaRegionFragment()
                .withArguments(ARG_PERSONA_ID to personaId)
        }
    }
}
