package biz.belcorp.salesforce.modules.orders.features.search

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.analytics.core.domain.entities.Label
import biz.belcorp.salesforce.analytics.core.domain.entities.Label.FILTER_CAMPAIGN
import biz.belcorp.salesforce.analytics.core.domain.entities.Label.FILTER_CONSULTANT_SEGMENT
import biz.belcorp.salesforce.analytics.core.domain.entities.Label.FILTER_ORIGIN
import biz.belcorp.salesforce.analytics.core.domain.entities.Label.FILTER_PENDING_DEBT
import biz.belcorp.salesforce.analytics.core.domain.entities.Label.FILTER_SECTION
import biz.belcorp.salesforce.analytics.core.domain.entities.Label.FILTER_STATUS
import biz.belcorp.salesforce.analytics.core.domain.entities.Label.FILTER_TYPE_FILTER
import biz.belcorp.salesforce.components.features.search.adapters.EstadosRecyclerAdapter
import biz.belcorp.salesforce.components.features.search.adapters.SaldosRecyclerAdapter
import biz.belcorp.salesforce.components.features.search.adapters.SegmentosRecyclerAdapter
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.features.search.models.TipoEstadoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSaldoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSegmentoModel
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.orders.R
import biz.belcorp.salesforce.modules.orders.features.results.adapters.CampaniasRecyclerAdapter
import biz.belcorp.salesforce.modules.orders.features.search.adapters.OrigenRecyclerAdapter
import biz.belcorp.salesforce.modules.orders.features.search.adapters.SeccionesRecyclerAdapter
import biz.belcorp.salesforce.modules.orders.features.search.adapters.TipoFiltroRecyclerAdapter
import biz.belcorp.salesforce.modules.orders.features.search.model.*
import biz.belcorp.salesforce.modules.orders.utils.AnalyticUtils
import kotlinx.android.synthetic.main.fragment_filtros_pedidos_web.*

class FiltrosPedidosWebFragment : BaseFragment(), FiltrosPedidosWebView {

    private val filtrosPresenter: FiltrosPedidosWebPresenter by injectFragment()

    private var aplicarFiltros: ((FiltroPedidosWebModel, Boolean) -> Unit)? = null
    private var filtrosModel: FiltroPedidosWebModel? = null

    companion object {

        fun newInstance(): FiltrosPedidosWebFragment {
            return FiltrosPedidosWebFragment()
        }

    }

    override fun getLayout() = R.layout.fragment_filtros_pedidos_web

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filtrosPresenter.cargarFiltros()
        reiniciarIndicadorFiltros()
        iniciarEventos()
    }

    private fun iniciarEventos() {

        iconCerrar?.setOnClickListener {
            it?.dismissKeyboard()
            actualizarFiltros(false)
        }

        cvCampania?.setOnClickListener {
            it?.dismissKeyboard()
            cambiarEstadoFiltro(rvCampania, ivTriangleCampania, FILTER_CAMPAIGN)
        }

        cvTipo?.setOnClickListener {
            it?.dismissKeyboard()
            cambiarEstadoFiltro(rvTipo, ivTriangleTipo, FILTER_TYPE_FILTER)
        }

        cvSection?.setOnClickListener {
            it?.dismissKeyboard()
            cambiarEstadoFiltro(cvSectionAdapter, ivTriangleSection, FILTER_SECTION)
        }

        cvStatus?.setOnClickListener {
            it?.dismissKeyboard()
            cambiarEstadoFiltro(rvStatus, ivTriangleStatus, FILTER_STATUS)
        }

        cvSegment?.setOnClickListener {
            it?.dismissKeyboard()
            cambiarEstadoFiltro(rvSegment, ivTriangleSegment, FILTER_CONSULTANT_SEGMENT)
        }

        cvResidue?.setOnClickListener {
            it?.dismissKeyboard()
            cambiarEstadoFiltro(rvResidue, ivTriangleResidue, FILTER_PENDING_DEBT)
        }

        cvOrigen?.setOnClickListener {
            it?.dismissKeyboard()
            cambiarEstadoFiltro(rvOrigen, ivTriangleOrigen, FILTER_ORIGIN)
        }

        btnMoreFilters?.setOnClickListener {
            it?.dismissKeyboard()
            cambiarEstadoFiltros()
        }

        btnAplicarFiltros?.setOnClickListener {
            it?.dismissKeyboard()
            actualizarFiltros(true)
        }

        cvFechaDesde?.setOnClickListener {
            it?.dismissKeyboard()
            filtrosPresenter.seleccionarFechaDesde(etFechaDesde.text.toString())
            AnalyticUtils.send(Label.FILTER_DATE_FROM)
        }

        cvFechaHasta?.setOnClickListener {
            it?.dismissKeyboard()
            filtrosPresenter.seleccionarFechaHasta(etFechaHasta.text.toString())
            AnalyticUtils.send(Label.FILTER_DATE_TO)
        }

        ivCloseFechaDesde?.setOnClickListener {
            etFechaDesde?.setText("")
            ivCloseFechaDesde?.gone()
        }

        ivCloseFechaHasta?.setOnClickListener {
            etFechaHasta?.setText("")
            ivCloseFechaHasta?.gone()
        }

    }

    fun escucharFiltrosAplicados(f: (FiltroPedidosWebModel, Boolean) -> Unit) {
        aplicarFiltros = f
    }

    fun mostrarFiltrosActuales(filtrosModel: FiltroPedidosWebModel?) {
        this.filtrosModel = filtrosModel
    }

    private fun actualizarFiltros(aplicar: Boolean) {
        val filtros = crearFiltroPedidosWebModel()
        aplicarFiltros?.invoke(filtros, aplicar)
        activity?.onBackPressed()
    }

    private fun crearFiltroPedidosWebModel(): FiltroPedidosWebModel {
        return FiltroPedidosWebModel().apply {
            tipoFiltro = etConsultantTipo?.tag as? String?
            campania = etConsultantCampania?.tag as? String?
            seccion = etConsultantSection?.tag as? String?
            estadoPedido = etConsultantStatus?.tag as? Int?
            fechaDesde = etFechaDesde?.text?.toString()
            fechaHasta = etFechaHasta?.text?.toString()
            codigoConsultora = etConsultantCode?.text?.toString()
            nombreConsultora = etConsultantName?.text?.toString()
            segmentoId = etConsultantSegment?.tag as? Int?
            deudaId = etConsultantResidue?.tag as? Int?
            origen = etConsultantOrigen?.tag as? String?
            otrosFiltros = otherFiltersLayout?.isVisible() == true
        }
    }

    override fun mostrarCampaniaDefecto(item: CampaniaModel) {
        if (filtrosModel != null) return
        mostrarCampaniaSeleccionada(item)
    }

    override fun mostrarFiltroCampania(items: List<CampaniaModel>) {
        rvCampania?.layoutManager = LinearLayoutManager(context)
        rvCampania?.adapter = CampaniasRecyclerAdapter(items) {
            mostrarCampaniaSeleccionada(it)
            cvCampania?.performClick()
        }
        verificarFiltroCampaniaAnterior(items)
    }

    private fun verificarFiltroCampaniaAnterior(items: List<CampaniaModel>) {
        val item = items.find { it.codigo == filtrosModel?.campania }
        item?.also { mostrarCampaniaSeleccionada(it) }
    }

    private fun mostrarCampaniaSeleccionada(item: CampaniaModel) {
        etConsultantCampania?.setText(item.nombreCorto)
        etConsultantCampania?.tag = item.codigo
    }

    override fun mostrarTipoFiltroDefecto(item: TipoFiltroModel) {
        if (filtrosModel != null) return
        mostrarTipoFiltroSeleccionada(item)
    }

    override fun mostrarTipoFiltro(items: List<TipoFiltroModel>) {
        rvTipo?.layoutManager = LinearLayoutManager(context)
        rvTipo?.adapter = TipoFiltroRecyclerAdapter(items) {
            mostrarTipoFiltroSeleccionada(it)
            cvTipo?.performClick()
        }
        verificarTipoFiltroAnterior(items)
    }

    private fun verificarTipoFiltroAnterior(items: List<TipoFiltroModel>) {
        val item = items.find { it.codigo == filtrosModel?.tipoFiltro }
        item?.also { mostrarTipoFiltroSeleccionada(it) }
    }

    private fun mostrarTipoFiltroSeleccionada(item: TipoFiltroModel) {
        etConsultantTipo?.setText(item.descripcion)
        etConsultantTipo?.tag = item.codigo
    }

    override fun mostrarFiltroSeccion(items: List<SeccionModel>) {
        cvSection?.visible()
        rvSections?.layoutManager = LinearLayoutManager(context)
        rvSections?.adapter = SeccionesRecyclerAdapter(items) {
            mostrarFiltroSeccionSeleccionada(it)
            cvSection?.performClick()
        }
        verificarFiltroSeccionAnterior(items)
    }

    private fun verificarFiltroSeccionAnterior(items: List<SeccionModel>) {
        val item = items.find { it.codigo == filtrosModel?.seccion }
        item?.also { mostrarFiltroSeccionSeleccionada(it) }
    }

    private fun mostrarFiltroSeccionSeleccionada(item: SeccionModel) {
        etConsultantSection?.setText(item.codigo)
        etConsultantSection?.tag = item.codigo
    }

    override fun mostrarFiltroEstados(items: List<TipoEstadoModel>) {
        rvStatus?.layoutManager = LinearLayoutManager(context)
        rvStatus?.adapter = EstadosRecyclerAdapter(items) {
            mostrarFiltroEstadoSeleccionada(it)
            cvStatus?.performClick()
        }
        verificarFiltroEstadosAnterior(items)
    }

    private fun verificarFiltroEstadosAnterior(items: List<TipoEstadoModel>) {
        val item = items.find { it.idEstadoActividad == filtrosModel?.estadoPedido }
        item?.also { mostrarFiltroEstadoSeleccionada(it) }
    }

    private fun mostrarFiltroEstadoSeleccionada(item: TipoEstadoModel) {
        etConsultantStatus?.setText(item.descripcion)
        etConsultantStatus?.tag = item.idEstadoActividad
    }

    override fun mostrarFiltroSaldos(items: List<TipoSaldoModel>) {
        rvResidue?.layoutManager = LinearLayoutManager(context)
        rvResidue?.adapter = SaldosRecyclerAdapter(items) {
            etConsultantResidue?.setText(it.descripcion)
            etConsultantResidue?.tag = it.idSaldo
            cvResidue?.performClick()
        }
        verificarFiltroSaldosAnterior(items)
    }

    private fun verificarFiltroSaldosAnterior(items: List<TipoSaldoModel>) {
        val item = items.find { it.idSaldo == filtrosModel?.deudaId }
        item?.also { mostrarFiltroSaldoSeleccionada(it) }
    }

    private fun mostrarFiltroSaldoSeleccionada(item: TipoSaldoModel) {
        etConsultantResidue?.setText(item.descripcion)
        etConsultantResidue?.tag = item.idSaldo
    }

    override fun mostrarFiltroSegmentos(items: List<TipoSegmentoModel>) {
        rvSegment?.layoutManager = LinearLayoutManager(context)
        rvSegment?.adapter = SegmentosRecyclerAdapter(items) {
            mostrarFiltroSegmentoSeleccionada(it)
            cvSegment?.performClick()
        }
        verificarFiltroSegmentosAnterior(items)
    }

    private fun verificarFiltroSegmentosAnterior(items: List<TipoSegmentoModel>) {
        val item = items.find { it.segmentoInternoId == filtrosModel?.segmentoId }
        item?.also { mostrarFiltroSegmentoSeleccionada(it) }
    }

    private fun mostrarFiltroSegmentoSeleccionada(item: TipoSegmentoModel) {
        etConsultantSegment?.setText(item.descripcion)
        etConsultantSegment?.tag = item.segmentoInternoId
    }

    override fun mostrarFiltroOrigen(items: List<TipoOrigenModel>) {
        rvOrigen?.layoutManager = LinearLayoutManager(context)
        rvOrigen?.adapter = OrigenRecyclerAdapter(items) {
            etConsultantOrigen?.setText(it.descripcion)
            etConsultantOrigen?.tag = it.codigo
            cvOrigen?.performClick()
        }
        verificarFiltroOrigenAnterior(items)
    }

    private fun verificarFiltroOrigenAnterior(items: List<TipoOrigenModel>) {
        val item = items.find { it.codigo == filtrosModel?.origen }
        item?.also { mostrarFiltroOrigenSeleccionada(it) }
    }

    private fun mostrarFiltroOrigenSeleccionada(item: TipoOrigenModel) {
        etConsultantOrigen?.setText(item.descripcion)
        etConsultantOrigen?.tag = item.codigo
    }

    override fun mostrarFiltrosPrevios() {
        etConsultantName?.setText(filtrosModel?.nombreConsultora)
        etConsultantCode?.setText(filtrosModel?.codigoConsultora)
        mostrarFechaDesdeSeleccionada(filtrosModel?.fechaDesde)
        mostrarFechaHastaSeleccionada(filtrosModel?.fechaHasta)
        if (filtrosModel?.otrosFiltros == true) {
            cambiarEstadoFiltros()
        }
    }

    override fun ocultarFiltroSeccion() {
        cvSection?.gone()
    }

    override fun ocultarTipoFiltro() {
        cvTipo?.gone()
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
            AnalyticUtils.send(analyticLabel)
        }
        triangleView?.isEnabled = view.isVisible()
    }

    private fun cambiarEstadoFiltros() {
        otherFiltersLayout?.toggleVisibility()
        if (otherFiltersLayout?.isVisible() == true) {
            btnMoreFilters.setText(R.string.hide_filters)
        } else {
            btnMoreFilters.setText(R.string.see_more_filters)
        }
    }

    private val filtros
        get() = listOf<View>(
            rvCampania,
            rvTipo,
            cvSectionAdapter,
            rvStatus,
            rvSegment,
            rvResidue,
            rvOrigen
        )

    private fun reiniciarFiltros() {
        filtros.forEach {
            it.gone()
        }
    }

    private val chevronFiltros
        get() = listOf<View>(
            ivTriangleCampania,
            ivTriangleTipo,
            ivTriangleSection,
            ivTriangleStatus,
            ivTriangleSegment,
            ivTriangleResidue,
            ivTriangleOrigen
        )

    private fun reiniciarIndicadorFiltros() {
        chevronFiltros.forEach {
            it.isEnabled = false
        }
    }

    override fun abrirDatePickerDesde(year: Int, month: Int, day: Int) {
        DatePickerDialog(
            requireContext(),
            R.style.Theme_Modules_Orders_Dialog,
            onDateDesdeSelected,
            year,
            month,
            day
        )
            .show()
    }

    override fun abrirDatePickerHasta(year: Int, month: Int, day: Int) {
        DatePickerDialog(
            requireContext(),
            R.style.Theme_Modules_Orders_Dialog,
            onDateHastaSelected,
            year,
            month,
            day
        )
            .show()
    }

    private val onDateDesdeSelected = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        filtrosPresenter.fechaDesdeSeleccionada(year, month, day)
    }

    private val onDateHastaSelected = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        filtrosPresenter.fechaHastaSeleccionada(year, month, day)
    }

    override fun mostrarFechaDesdeSeleccionada(fecha: String?) {
        fecha?.takeIf { it.isNotBlank() }?.also {
            etFechaDesde?.setText(fecha)
            ivCloseFechaDesde?.visible()
        }
    }

    override fun mostrarFechaHastaSeleccionada(fecha: String?) {
        fecha?.takeIf { it.isNotBlank() }?.also {
            etFechaHasta?.setText(fecha)
            ivCloseFechaHasta?.visible()
        }
    }

}
