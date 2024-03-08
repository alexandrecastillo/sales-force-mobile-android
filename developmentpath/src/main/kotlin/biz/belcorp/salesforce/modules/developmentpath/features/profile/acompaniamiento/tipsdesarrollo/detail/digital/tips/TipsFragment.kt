package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.AnalyticsConstants
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.TipModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.VideoModel
import biz.belcorp.salesforce.modules.developmentpath.features.video.VideoFragment
import kotlinx.android.synthetic.main.fragment_tips_recomendaciones.*
import kotlinx.android.synthetic.main.fragment_venta_ganancia_tips.rvTips

class TipsFragment : VideoFragment(), TipsContract.View {

    private val mPresenter by injectFragment<TipsContract.Presenter>()

    private val mTipsAdapter by lazy { TipsAdapter() }

    private var personaId: Long = -1L
    private lateinit var rol: Rol
    private lateinit var procedencia: String

    var opciones = emptyList<String>()

    private lateinit var mVideoModel: VideoModel

    override fun getLayout(): Int = R.layout.fragment_tips_recomendaciones

    private val faPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun getVideoLayout() = R.id.youtube_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun mostrarTips(data: List<TipModel>) {
        if (!isAttached()) return
        txtSinTips?.gone()
        mTipsAdapter.actualizarData(data)
    }

    override fun mostrarTipsVacio() {
        if (!isAttached()) return
        txtSinTips?.visible()
    }

    override fun mostrarVideo(video: VideoModel) {
        if (!isAttached()) return
        clContenedorVideo?.visible()
        mVideoModel = video
        initVideoYoutube(mVideoModel.videoId ?: return)
    }

    override fun mostrarVideoVacio() {
        if (!isAttached()) return
        clContenedorVideo?.gone()
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
            procedencia = it.getString(ARG_PROCEDENCIA, "")
        }
    }

    private fun inicializar() {
        configurarTipsRecyclerView()
        inicializarPresenter()
        inicializarEventos()
    }

    private fun configurarTipsRecyclerView() {
        rvTips?.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mTipsAdapter
        }
    }

    private fun inicializarPresenter() {
        val request = Params(personaId, rol, opciones)
        mPresenter.obtener(request)
    }

    private fun inicializarEventos() {
        btnCompartir?.setOnClickListener {
            context?.shareText(mVideoModel.videoUrl ?: return@setOnClickListener)
            when (procedencia) {
                PROCEDENCIA_CAMINO_BRILLANTE ->
                    sendAnalitycs(
                        TagAnalytics.EVENTO_TIPS_DESARROLLO_CAMINO_BRILLANTE_COMPARTIR,
                        AnalyticsConstants.LABEL_COMPARTIR_VIDEO
                    )
                PROCEDENCIA_HERRAMIENTA_DIGITAL ->
                    sendAnalitycs(
                        TagAnalytics.EVENTO_TIPS_DESARROLLO_DIGITAL_COMPARTIR,
                        AnalyticsConstants.LABEL_TIPS_DESARROLLO_DIGITAL_COMPARTIR
                    )
            }
        }
    }

    private fun sendAnalitycs(tag: TagAnalytics, name: String) {
        faPresenter.enviarEventoTagTipsDesarrollo(tag, name)
    }

    companion object {

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"

        private const val ARG_PROCEDENCIA = "PROCEDENCIA"

        const val PROCEDENCIA_CAMINO_BRILLANTE = "CaminoBrillante"
        const val PROCEDENCIA_HERRAMIENTA_DIGITAL = "HerramientaDigital"

        fun newInstance(personaId: Long, rol: Rol, procedencia: String) = TipsFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol,
                ARG_PROCEDENCIA to procedencia
            )
    }
}
