package biz.belcorp.salesforce.modules.postulants.features.indicador

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.analytics.core.domain.entities.Label
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.features.trackAnalytics
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.core.utils.AppUri.CODE_KEY
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.UneteActivity
import biz.belcorp.salesforce.modules.postulants.features.indicador.adapter.IndicadorAdapter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.LoadingView
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.ConsolidadoFragment
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.ResumenConsolidadoFragment
import biz.belcorp.salesforce.modules.postulants.features.indicador.entities.BaseGeo
import biz.belcorp.salesforce.modules.postulants.features.indicador.entities.IndicadorUneteModel
import biz.belcorp.salesforce.modules.postulants.features.sync.utils.startApplicationsSyncWorker
import biz.belcorp.salesforce.modules.postulants.utils.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_indicador.*
import kotlinx.android.synthetic.main.view_indicator_header.*


class IndicadorFragment : BaseFragment(), IndicadorView,
    ResumenConsolidadoFragment.ConsolidadoResumenFragmentListener,
    ConsolidadoFragment.ConsolidadoFragmentListener,
    LoadingView {

    override fun getLayout(): Int = R.layout.fragment_indicador

    private val code by lazy { arguments?.getString(CODE_KEY) }

    private val indicadorPresenter: IndicadorPresenter by injectFragment()

    private var geoList: List<BaseGeo> = emptyList()

    private var filterMode: Int = Constant.UNO_NEGATIVO

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAdded) return
        indicadorPresenter.setView(this)
        initListeners()
        indicadorPresenter.init(code)
        initViews()
    }

    private fun initViews() {
        when (indicadorPresenter.getCountryCode()) {
            Pais.PUERTORICO -> showPreInscritas()
            Pais.COLOMBIA -> Unit
            else -> showRegularizarDocumento()
        }

        when(indicadorPresenter.getRol()) {
            Rol.GERENTE_REGION.codigoRol -> btnAgregarPostulante.gone()
            else -> btnAgregarPostulante.visible()
        }
    }

    private fun showPreInscritas() {
        tv_title_pre_inscritos?.visible()
        tv_pre_inscritas?.visible()
        showRegularizarDocumento()
    }

    private fun showRegularizarDocumento() {
        tv_title_regularizar_documento?.visible()
        tv_regularizar_documento?.visible()
    }

    override fun onResume() {
        super.onResume()
        startSync()
        sendAnalytics()
    }

    private fun sendAnalytics() {
        AnalyticUtils.screen()
        trackAnalytics(ScreenTag.UNETE)
    }

    override fun onDestroy() {
        super.onDestroy()
        indicadorPresenter.onDestroy()
    }

    override fun showIndicadorUnete(model: IndicadorUneteModel?, isReloadingHeader: Boolean) {
        requireActivity().runOnUiThread {
            initHeader(model)
            if (isReloadingHeader) return@runOnUiThread

            initTabLayout(tipoFiltro = filterMode)
        }
    }

    override fun applyFilterMode(filterMode: Int) {
        requireActivity().runOnUiThread {
            this.filterMode = filterMode
        }
    }

    override fun showSections(sections: List<BaseGeo>) {
        requireActivity().runOnUiThread {
            this.geoList = sections
        }
    }

    override fun showLoading() {
        requireActivity().runOnUiThread {
            progressBar?.visible()
        }
    }

    override fun hideLoading() {
        requireActivity().runOnUiThread {
            progressBar?.gone()
        }
    }

    private fun initHeader(model: IndicadorUneteModel?) {
        tv_header_cycle_title_solicitudes?.text =
            getString(R.string.you_have_detail_cycle, model?.campaniaActual)
        tv_header_cycle_title?.text =
            getString(R.string.you_achieve_detail_cycle, model?.campaniaActual)
        tv_conversion_number?.text =
            model?.conversion.toString().plus(getString(R.string.percentage))


        tv_pre_inscritas?.text = model?.preInscritas.toString()
        tv_evaluacion?.text = model?.enEvaluacion.toString()
        tv_aprobadas?.text = model?.aprobadas.toString()
        tv_preaprobadas?.text = model?.preAprobadas.toString()
        tv_ing_anticipados?.text = model?.ingresosAnticipados.toString()

        val totalReg = model?.regularizarDocumento ?: Constant.NUMERO_CERO
        tv_regularizar_documento?.text = totalReg.toString()
    }

    override fun onHeaderClick(modo: Int) {
        initTabLayout(currentItem = Constant.NUMERO_CERO, tipoFiltro = modo)
    }

    override fun onSelectZone(zone: String) {
        indicadorPresenter.onChangeSelectedZone(zone)
    }

    override fun onChangeZone(zone: String) {
        indicadorPresenter.onChangeSelectedZone(zone)
    }

    override fun context() = context!!

    private fun initTabLayout(
        currentItem: Int = Constant.UNO_NEGATIVO,
        tipoFiltro: Int = Constant.UNO_NEGATIVO
    ) {
        val codRol = indicadorPresenter.getRol()
        context?.let {
            viewpager?.adapter =
                IndicadorAdapter(
                    childFragmentManager, it, rol = indicadorPresenter.getRol(),
                    mTipoListado = tipoFiltro, geoModelList = geoList
                )
        }

        viewpager?.isPagingEnabled = false

        if (currentItem == Constant.UNO_NEGATIVO) setupInitViewpager(codRol)
        else viewpager?.currentItem = currentItem

        val params = tabLayoutIndicador?.layoutParams as? ViewGroup.MarginLayoutParams ?: return
        params.setMargins(0, 0, ScreenUtils.convertDpToPx(viewpager.context, 70f), 0)
        tabLayoutIndicador?.layoutParams = params
        tabLayoutIndicador?.setupWithViewPager(viewpager)
        tabLayoutIndicador?.addOnTabSelectedListener(TabLayoutListener())
        context?.let {
            tabLayoutIndicador?.setSelectedTabIndicatorColor(
                ContextCompat.getColor(it, R.color.indicator_unete_light)
            )
        }
        viewpager?.offscreenPageLimit = Constant.NUMERO_UNO
        initTabLayoutStyles()
    }

    private fun setupInitViewpager(codRol: String) {
        when (codRol.toUpperCase()) {
            Rol.GERENTE_ZONA.codigoRol -> getGZPage()
            Rol.GERENTE_REGION.codigoRol -> getGRPage()
            Rol.DIRECTOR_VENTAS.codigoRol -> getDVPage()
            else -> getSEPage()
        }
    }

    private fun getSEPage() {
        viewpager?.currentItem = Constant.NUMERO_CERO
    }

    private fun getDVPage() {
        if (indicadorPresenter.getRegion().isEmpty()) {
            viewpager?.currentItem = Constant.NUMERO_CERO
        } else {
            viewpager?.currentItem = Constant.NUMERO_UNO
        }
    }

    private fun getGRPage() {
        if (indicadorPresenter.getZone().isEmpty()) {
            viewpager?.currentItem = Constant.NUMERO_CERO
        } else {
            viewpager?.currentItem = Constant.NUMERO_UNO
        }
    }

    private fun getGZPage() {
        viewpager?.currentItem = Constant.NUMERO_CERO
    }

    private fun initTabLayoutStyles() {
        for (i in Constant.NUMERO_CERO until tabLayoutIndicador.tabCount) {
            val tab = tabLayoutIndicador.getTabAt(i)
            tab?.let {
                shouldBoldTabText(it, it.position == viewpager?.currentItem)
            }

        }
    }

    private fun shouldBoldTabText(tab: TabLayout.Tab?, shouldBold: Boolean) {
        val wantedTabIndex = tab?.position ?: return
        val selectedTabTextView =
            getTabTextView(wantedTabIndex, Constant.NUMERO_CERO, tabLayoutIndicador) as? TextView
                ?: return

        if (shouldBold) selectedTabTextView.setBlackFont() else selectedTabTextView.setBookFont()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            selectedTabTextView.letterSpacing = 0.2f
        }

        if (indicadorPresenter.getRol().toUpperCase() in arrayOf(
                Rol.GERENTE_REGION.codigoRol,
                Rol.GERENTE_ZONA.codigoRol
            ) && shouldBold
        ) {
            context?.let {
                scroll.setBackgroundColor(
                    ContextCompat.getColor(
                        it, if (tab.position == Constant.NUMERO_CERO) R.color.white
                        else R.color.sections_drilldown_detail_background
                    )
                )
            }
        }
    }

    private fun getTabTextView(
        wantedTabIndex: Int, childPosition: Int, tabLayout: TabLayout
    ): View {
        val textView =
            ((tabLayout.getChildAt(Constant.NUMERO_CERO) as LinearLayout).getChildAt(wantedTabIndex)
                as LinearLayout).getChildAt(childPosition)
        return if (textView is TextView) {
            textView
        } else {
            getTabTextView(wantedTabIndex, childPosition + 1, tabLayout)
        }
    }

    private inner class TabLayoutListener : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab) {
            shouldBoldTabText(tab, true)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            shouldBoldTabText(tab, false)
        }

        override fun onTabSelected(tab: TabLayout.Tab) {
            viewpager?.currentItem = tab.position
            shouldBoldTabText(tab, true)
            tab.text?.let {
                AnalyticUtils.option(it.toString().capitalizeAll())
            }
        }
    }

    private fun initListeners() {
        ivBack?.setOnClickListener {
            activity?.onBackPressed()
        }
        btnAgregarPostulante.visible()
        btnAgregarPostulante.setOnClickListener {
            AnalyticUtils.option(Label.UNETE_ADD)
            UneteActivity.getCallingIntent(it.context, null, 0, null)
        }
        btnAgregarPostulante?.bringToFront()
    }

    private fun startSync() {
        context?.startApplicationsSyncWorker()
    }

}
