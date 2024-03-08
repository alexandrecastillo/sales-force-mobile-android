package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.base.BuildConfig
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.AnalyticsConstants
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import kotlinx.android.synthetic.main.fragment_novedades.*

class NovedadesFragment : BaseFragment(), NovedadesView,
    ClickEnNovedadesListener {

    private var personaId: Long = -1L
    private lateinit var rol: Rol
    var opciones = emptyList<String>()

    private val presenter by injectFragment<NovedadesPresenter>()

    private val mAdapterNovedades by lazy { NovedadesAdapter() }

    override fun getLayout(): Int = R.layout.fragment_novedades

    private val faPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
        inicializarPresenter()
    }

    override fun pintarNovedades(novedades: List<ListadoNovedadesModel>) {
        if (!isAttached()) return
        clContenedorVacio?.visibility = View.GONE
        mAdapterNovedades.actualizar(novedades)
    }

    override fun pintarNovedadesVacio() {
        if (!isAttached()) return
        clContenedorVacio?.visibility = View.VISIBLE
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    private fun inicializar() {
        configurarRecyclerView()
    }

    private fun inicializarPresenter() {
        val request = Params(personaId, rol, opciones)
        presenter.obtenerData(request)
    }

    private fun configurarRecyclerView() {
        mAdapterNovedades.setNovedadesListener(this)
        recyclerContent?.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapterNovedades
        }
    }

    override fun abrirVideo(model: DetalleNovedadesModel) {
        context?.openLink(model.url) {
            toast(getString(R.string.msg_open_video_error))
        }
    }

    override fun compartirImagen(bitmap: Bitmap) {
        val uri = requireContext().bitmapToUri(bitmap, BuildConfig.APPLICATION_ID)
        if (uri != null) context?.shareImageUri(uri)
    }

    override fun compartirVideo(link: String, titutlo: String) {
        context?.shareText(link)
        faPresenter.enviarEventoTagTipsDesarrollo(
            TagAnalytics.EVENTO_TIPS_DESARROLLO_NOVEDADES_COMPARTIR_VIDEO,
            AnalyticsConstants.LABEL_COMPARTIR_VIDEO + titutlo
        )
    }

    companion object {

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol) =
            NovedadesFragment()
                .withArguments(
                    ARG_PERSONA_ID to personaId,
                    ARG_ROL to rol
                )
    }
}
