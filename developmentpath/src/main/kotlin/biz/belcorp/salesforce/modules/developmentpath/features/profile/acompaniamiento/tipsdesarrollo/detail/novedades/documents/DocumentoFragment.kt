package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.DocumentHelper
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.openLink
import biz.belcorp.salesforce.core.utils.shareText
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Opciones
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.AnalyticsConstants
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import kotlinx.android.synthetic.main.fragment_documento.*
import biz.belcorp.salesforce.core.utils.withArguments

class DocumentoFragment : BaseFragment(), DocumentoView, ClickEnArchivoListener {

    private var personaId: Long = -1L
    private lateinit var rol: Rol
    private lateinit var procedencia: String
    var opciones = emptyList<String>()

    private val presenter by injectFragment<DocumentosPresenter>()
    private val documentosAdapter by lazy { DocumentosAdapter() }

    private val downloadHelper by lazy { DocumentHelper(requireContext()) }

    private val faPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    override fun getLayout(): Int = R.layout.fragment_documento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
        inicializarPresenter()
    }

    override fun pintarDocumentos(data: List<DocumentoModel>) {
        if (!isAttached()) return
        textHeader?.visibility = View.VISIBLE
        (recyclerDocumentos?.adapter as? DocumentosAdapter)?.actualizar(data)
    }

    override fun pintarDocumentosVacio() {
        if (!isAttached()) return
        textHeader?.visibility = View.GONE
    }

    override fun clickEnCompartir(archivo: DocumentoModel) {
        val text = this.getString(
            R.string.txt_compartir_documento, archivo.nombreDocumento,
            archivo.urlDocumento
        )
        context?.shareText(text)
        when (procedencia) {
            PROCEDENCIA_CAMINO_BRILLANTE -> sendAnalitycs(
                TagAnalytics.EVENTO_TIPS_DESARROLLO_CAMINO_BRILLANTE_MATERIAL_COMPARTIR,
                AnalyticsConstants.LABEL_TIPS_DESARROLLO_MATERIAL_COMPARTIR + archivo.titulo
            )
            PROCEDENCIA_NOVEDADES -> sendAnalitycs(
                TagAnalytics.EVENTO_TIPS_DESARROLLO_NOVEDADES_MATERIAL_COMPARTIR,
                AnalyticsConstants.LABEL_TIPS_DESARROLLO_COMPARTIR + archivo.titulo
            )
            PROCEDENCIA_PROGRAMA_NUEVAS -> sendAnalitycs(
                TagAnalytics.EVENTO_TIPS_DESARROLLO_PROGRAMA_NUEVAS_COMPARTIR,
                AnalyticsConstants.LABEL_TIPS_DESARROLLO_COMPARTIR + archivo.titulo
            )
        }
    }

    override fun clickEnVerPDF(archivo: DocumentoModel) {
        context?.openLink(archivo.urlDocumento ?: Constant.EMPTY_STRING)
        when (procedencia) {
            PROCEDENCIA_CAMINO_BRILLANTE -> sendAnalitycs(
                TagAnalytics.EVENTO_TIPS_DESARROLLO_CAMINO_BRILLANTE_MATERIAL_VISUALIZAR,
                AnalyticsConstants.LABEL_TIPS_DESARROLLO_MATERIAL_VISUALIZAR + archivo.titulo
            )
            PROCEDENCIA_NOVEDADES -> sendAnalitycs(
                TagAnalytics.EVENTO_TIPS_DESARROLLO_NOVEDADES_MATERIAL_VISUALIZAR,
                AnalyticsConstants.LABEL_TIPS_DESARROLLO_VISUALIZAR + archivo.titulo
            )
            PROCEDENCIA_PROGRAMA_NUEVAS -> sendAnalitycs(
                TagAnalytics.EVENTO_TIPS_DESARROLLO_PROGRAMA_NUEVAS_VISUALIZAR,
                AnalyticsConstants.LABEL_TIPS_DESARROLLO_VISUALIZAR + archivo.titulo
            )
        }
    }

    override fun clickEnDescargarPDF(archivo: DocumentoModel) {
        downloadHelper.downloadDocument(archivo.urlDocumento)
        when (procedencia) {
            PROCEDENCIA_CAMINO_BRILLANTE -> sendAnalitycs(
                TagAnalytics.EVENTO_TIPS_DESARROLLO_CAMINO_BRILLANTE_MATERIAL_DESCARGAR,
                AnalyticsConstants.LABEL_TIPS_DESARROLLO_MATERIAL_DESCARGAR + archivo.titulo
            )
            PROCEDENCIA_NOVEDADES -> sendAnalitycs(
                TagAnalytics.EVENTO_TIPS_DESARROLLO_NOVEDADES_MATERIAL_DESCARGAR,
                AnalyticsConstants.LABEL_TIPS_DESARROLLO_DESCARGAR + archivo.titulo
            )
            PROCEDENCIA_PROGRAMA_NUEVAS -> sendAnalitycs(
                TagAnalytics.EVENTO_TIPS_DESARROLLO_PROGRAMA_NUEVAS_DESCARGAR,
                AnalyticsConstants.LABEL_TIPS_DESARROLLO_DESCARGAR + archivo.titulo
            )
        }
    }

    private fun sendAnalitycs(tag: TagAnalytics, name: String) {
        faPresenter.enviarEventoTagTipsDesarrollo(tag, name)
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
            procedencia = it.getString(ARG_PROCEDENCIA, "")
        }
    }

    private fun inicializar() {
        configurarRecyclerView()
        setearTitulo()
    }

    private fun inicializarPresenter() {
        val request = Params(personaId, rol, opciones)
        presenter.obtenerData(request)
    }

    private fun configurarRecyclerView() {
        documentosAdapter.setArchivoListener(this)
        recyclerDocumentos?.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = documentosAdapter
        }
    }

    private fun setearTitulo() {
        if (opciones.isEmpty()) return
        val title = when (opciones[0]) {
            Opciones.ND3 -> resources.getString(R.string.title_incentivos)
            Opciones.ND4 -> resources.getString(R.string.title_materiales)
            Opciones.ND5 -> resources.getString(R.string.title_materiales)
            else -> resources.getString(R.string.title_materiales)
        }
        textHeader?.text = title
    }

    companion object {

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"
        private const val ARG_PROCEDENCIA = "PROCEDENCIA"

        const val PROCEDENCIA_CAMINO_BRILLANTE = "CaminoBrillante"
        const val PROCEDENCIA_NOVEDADES = "Novedades"
        const val PROCEDENCIA_PROGRAMA_NUEVAS = "ProgramaNuevas"

        fun newInstance(personaId: Long, rol: Rol, procedencia: String) =
            DocumentoFragment().withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol,
                ARG_PROCEDENCIA to procedencia
            )
    }
}
