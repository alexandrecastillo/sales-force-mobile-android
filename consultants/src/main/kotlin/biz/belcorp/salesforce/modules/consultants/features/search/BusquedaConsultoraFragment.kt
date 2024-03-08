package biz.belcorp.salesforce.modules.consultants.features.search


import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.hideKeyboard
import biz.belcorp.salesforce.analytics.core.domain.entities.Label
import biz.belcorp.salesforce.analytics.core.domain.entities.ScreenTag
import biz.belcorp.salesforce.analytics.features.trackAnalytics
import biz.belcorp.salesforce.components.features.search.adapters.EstadosRecyclerAdapter
import biz.belcorp.salesforce.components.features.search.adapters.SaldosRecyclerAdapter
import biz.belcorp.salesforce.components.features.search.adapters.SegmentosRecyclerAdapter
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.features.search.models.TipoEstadoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSaldoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSegmentoModel
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.features.list.ConsultantsListFragment
import biz.belcorp.salesforce.modules.consultants.features.search.adapters.*
import biz.belcorp.salesforce.modules.consultants.features.search.models.*
import biz.belcorp.salesforce.modules.consultants.features.sync.utils.startConsultantsLegacySyncWorker
import biz.belcorp.salesforce.modules.consultants.utils.AnalyticUtils
import kotlinx.android.synthetic.main.fragment_busqueda_consultora.*


class BusquedaConsultoraFragment : BaseFragment(), BusquedaConsultoraView {

    private val busquedaConsultoraPresenter by injectFragment<BusquedaConsultoraPresenter>()

    private val filtrosBusqueda get() = crearFiltrosBusqueda()

    override fun getLayout() = R.layout.fragment_busqueda_consultora

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        busquedaConsultoraPresenter.cargarCampania()
        busquedaConsultoraPresenter.cargarFiltros()
        reiniciarIndicadorFiltros()
        initEvents()
        startSync()
    }

    override fun onResume() {
        super.onResume()
        sendAnalytics()
    }

    private fun sendAnalytics() {
        AnalyticUtils.screen()
        trackAnalytics(ScreenTag.SEARCH_CONSULTANT)
    }

    private fun initEvents() {
        ivBack.setOnClickListener {
            it?.hideKeyboard()
            activity?.onBackPressed()
        }

        cvSection.setOnClickListener {
            it?.hideKeyboard()
            cambiarEstadoFiltro(cvSectionAdapter, ivTriangleSection, Label.FILTER_SECTION)
        }

        cvStatus.setOnClickListener {
            it?.hideKeyboard()
            cambiarEstadoFiltro(rvStatus, ivTriangleStatus, Label.FILTER_STATUS)
        }

        cvSegment.setOnClickListener {
            it?.hideKeyboard()
            cambiarEstadoFiltro(rvSegment, ivTriangleSegment, Label.FILTER_CONSULTANT_SEGMENT)
        }

        cvRequested.setOnClickListener {
            it?.hideKeyboard()
            cambiarEstadoFiltro(
                rvRequested,
                ivTriangleRequested,
                Label.FILTER_CONSULTANT_BILLED_ORDER
            )

        }

        cvAuthorized.setOnClickListener {
            it?.hideKeyboard()
            cambiarEstadoFiltro(
                rvAuthorized,
                ivTriangleAuthorized,
                Label.FILTER_CONSULTANT_AUTHORIZED
            )
        }

        cvResidue.setOnClickListener {
            it?.hideKeyboard()
            cambiarEstadoFiltro(rvResidue, ivTriangleResidue, Label.FILTER_PENDING_DEBT)
        }

        cvType.setOnClickListener {
            it?.hideKeyboard()
            cambiarEstadoFiltro(rvType, ivTriangleType, Label.FILTER_CREDITO)
        }

        btnMoreFilters?.setOnClickListener {
            it?.hideKeyboard()
            cambiarEstadoFiltros()
        }

        btnConsultantSearch?.setOnOneClickListener {
            it.hideKeyboard()
            buscarConsultora()
        }

        btnConsultantSearchKeyboard?.setOnOneClickListener {
            it.hideKeyboard()
            buscarConsultora()
        }

        rootLayout?.onKeyboardVisibilityChanged({
            onKeyboardVisible()
        }, {
            onKeyboardHidden()
        })

    }

    private fun crearFiltrosBusqueda(): FiltroConsultoraModel {
        return FiltroConsultoraModel().apply {
            code = etConsultantCode?.text?.toString().orEmpty()
            name = etConsultantName?.text?.toString().orEmpty()
            document = etConsultantDocument?.text?.toString().orEmpty()
            section = etConsultantSection?.text?.toString().orEmpty()
            status = etConsultantStatus.tag as? Int
            segment = etConsultantSegment.text?.toString().orEmpty()
            requested = etConsultantRequested.tag as? Int
            authorized = etAuthorized.tag as? Int
            residue = etConsultantResidue.tag as? Int
            type = etConsultantType.tag as? Int
            limit = defaultLimit
            offset = defaultOffset
        }
    }

    private fun buscarConsultora() {
        busquedaConsultoraPresenter.verificarResultados(filtrosBusqueda)
    }

    override fun mostrarCampaniaVenta(nombreCorto: String) {
        tvCampaign?.text = getString(R.string.campania_tipo_v, nombreCorto)
    }

    override fun mostrarCampaniaFacturacion(nombreCorto: String) {
        tvCampaign?.text = getString(R.string.campania_tipo_f, nombreCorto)
    }

    override fun mostrarFiltroSeccion(items: List<SeccionModel>) {
        cvSection?.visible()
        rvSections?.layoutManager = LinearLayoutManager(context)
        rvSections?.adapter = SeccionesRecyclerAdapter(items) {
            etConsultantSection?.setText(it.codigo)
            etConsultantSection?.tag = it.codigo
            cvSection?.performClick()
        }
    }

    override fun mostrarFiltroEstados(items: List<TipoEstadoModel>) {
        rvStatus?.layoutManager = LinearLayoutManager(context)
        rvStatus?.adapter = EstadosRecyclerAdapter(items) {
            etConsultantStatus?.setText(it.descripcion)
            etConsultantStatus?.tag = it.idEstadoActividad
            cvStatus?.performClick()
        }
    }

    override fun mostrarFiltroPedidos(items: List<TipoPedidoModel>) {
        rvRequested?.layoutManager = LinearLayoutManager(context)
        rvRequested?.adapter = PedidosRecyclerAdapter(items) {
            etConsultantRequested?.setText(it.descripcion)
            etConsultantRequested?.tag = it.idPedido
            cvRequested?.performClick()
        }
    }

    override fun mostrarFiltroAutorizado(items: List<TipoAutorizadoModel>) {
        rvAuthorized?.layoutManager = LinearLayoutManager(context)
        rvAuthorized?.adapter = AutorizadasRecyclerAdapter(items) {
            etAuthorized?.setText(it.descripcion)
            etAuthorized?.tag = it.idAutorizado
            cvAuthorized?.performClick()
        }
    }

    override fun mostrarFiltroSaldos(items: List<TipoSaldoModel>) {
        rvResidue?.layoutManager = LinearLayoutManager(context)
        rvResidue?.adapter = SaldosRecyclerAdapter(items) {
            etConsultantResidue?.setText(it.descripcion)
            etConsultantResidue?.tag = it.idSaldo
            cvResidue?.performClick()
        }
    }

    override fun mostrarFiltroSegmentos(items: List<TipoSegmentoModel>) {
        rvSegment?.layoutManager = LinearLayoutManager(context)
        rvSegment?.adapter = SegmentosRecyclerAdapter(items) {
            etConsultantSegment?.setText(it.descripcion)
            etConsultantSegment?.tag = it.segmentoInternoId
            cvSegment?.performClick()
        }
    }

    override fun mostrarFiltroTipo(items: List<TipoPaymentModel>) {
        rvType?.layoutManager = LinearLayoutManager(context)
        rvType?.adapter = TypesRecyclerAdapter(items) {
            etConsultantType?.setText(it.description)
            etConsultantType?.tag = it.id
            cvType?.performClick()
        }
    }

    override fun ocultarFiltroSeccion() {
        cvSection?.gone()
    }

    override fun mostrarMensajeError(id: Int) {
        tvConsultantSearchError?.setText(id)
        tvConsultantSearchErrorKeyboard?.setText(id)
        AnalyticUtils.busqueda(Label.SEARCH_EMPTY)
    }

    override fun limpiarMensajeError() {
        tvConsultantSearchError?.text = ""
        tvConsultantSearchErrorKeyboard?.text = ""
    }

    override fun mostrarMensajeResultadosGZ(count: Int) {
        tvSubtitle?.text = getString(R.string.consultant_title_length, count.toString())
    }

    override fun mostrarMensajeResultadosSE(count: Int) {
        tvSubtitle?.text = getString(R.string.consultant_title_length_se, count.toString())
    }

    override fun mostrarResultados() {
        if (!isAttached()) return
        val fragment = obtenerConsultorasListFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.resultsLayout, fragment, fragment.javaClass.name)
            .addToBackStack(fragment.javaClass.name)
            .commit()
        ocultarFiltros()
        AnalyticUtils.busqueda(Label.SEARCH_OK)
    }

    private fun ocultarFiltros() {
        resultsLayout?.visible()
        ivBack?.visible()
        filtrosLayout?.gone()
    }

    private fun mostrarFiltros() {
        resultsLayout?.gone()
        ivBack?.invisible()
        filtrosLayout?.visible()
        mostrarSubtituloFiltros()
    }

    private fun mostrarSubtituloFiltros() {
        tvSubtitle?.setText(R.string.text_completa_al_menos_una_accion)
    }

    private fun obtenerConsultorasListFragment(): Fragment {
        val fragment = ConsultantsListFragment.newInstance(filtrosBusqueda)
        fragment.setOnBackPressedListener { mostrarFiltros() }
        return fragment
    }

    private fun cambiarEstadoFiltro(
        view: View?, triangleView: ImageView?,
        analyticLabel: String
    ) {
        val visible = view?.isVisible() ?: return
        reiniciarFiltros()
        reiniciarIndicadorFiltros()
        if (!visible) {
            view.visible()
            AnalyticUtils.filtro(analyticLabel)
        }
        triangleView?.isEnabled = view.isVisible()
    }

    private fun cambiarEstadoFiltros() {
        otherFiltersLayout?.toggleVisibility()
        if (otherFiltersLayout?.isVisible() == true) {
            btnMoreFilters?.setText(R.string.hide_filters)
        } else {
            btnMoreFilters?.setText(R.string.see_more_filters)
        }
        busquedaConsultoraPresenter.habilitarSecciones()
    }

    private val filtros by lazy {
        listOf<View>(
            cvSectionAdapter,
            rvStatus,
            rvSegment,
            rvRequested,
            rvResidue,
            rvAuthorized,
            rvType
        )
    }

    private fun reiniciarFiltros() {
        filtros.forEach {
            it.gone()
        }
    }

    private val chevronFiltros by lazy {
        listOf<View>(
            ivTriangleSection,
            ivTriangleStatus,
            ivTriangleSegment,
            ivTriangleRequested,
            ivTriangleResidue,
            ivTriangleAuthorized,
            ivTriangleType
        )
    }

    private fun reiniciarIndicadorFiltros() {
        chevronFiltros.forEach {
            it.isEnabled = false
        }
    }

    private fun onKeyboardVisible() {
        btnConsultantSearch?.invisible()
        bottomKeyboardGroup?.visible()
    }

    private fun onKeyboardHidden() {
        btnConsultantSearch?.visible()
        bottomKeyboardGroup?.gone()
    }

    override fun onDestroy() {
        super.onDestroy()
        busquedaConsultoraPresenter.destroy()
    }

    private fun startSync() {
        context?.startConsultantsLegacySyncWorker()
    }

    companion object {

        const val defaultLimit = 100
        const val defaultOffset = 0

    }

}
