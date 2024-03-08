package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsvideo.youtubevideo

import android.content.Intent
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.video.VideoFragment
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import kotlinx.android.synthetic.main.fragment_tips_video.*


class TipsVideoFragment : VideoFragment() {

    private lateinit var youTubePlayerFragment: YouTubePlayerSupportFragment

    private var mUrlVideo = ""
    private var mArgUrlVideo = ""
    private var mDescriptionVideo = ""
    private lateinit var rol: Rol

    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    override fun getLayout(): Int = R.layout.fragment_tips_video

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mArgUrlVideo = it.getString(ARG_URL_VIDEO, "")
            mDescriptionVideo = it.getString(ARG_DESCRIPTION, "")
            rol = it.getSerializable(ROL) as Rol
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance()
        txtShared.setOnClickListener { initSharedView(mUrlVideo) }
        iconShared.setOnClickListener { initSharedView(mUrlVideo) }
        setDetailTips(mArgUrlVideo, mDescriptionVideo)
    }

    override fun getVideoLayout() = R.id.youtube_fragment

    private fun setDetailTips(mArgUrlVideo: String?, mDescriptionVideo: String?) {
        txtTitleVideo?.text = mDescriptionVideo
        mUrlVideo = mArgUrlVideo.toString().trim()
        if (mUrlVideo != "null") {
            rlViewVideo?.visibility = View.VISIBLE
            var murlVideo = mUrlVideo
            if (murlVideo.split("&t=").size > 1) {
                murlVideo = mUrlVideo.split("&t=")[0]
            }
            val tipsVideo = murlVideo.split("?v=")
            val video = if (tipsVideo.size > 1) {
                murlVideo.split("?v=")[1]
            } else {
                if (murlVideo.split("be/").size > 1) {
                    murlVideo.split("be/")[1]
                } else {
                    Constant.EMPTY_STRING
                }
            }
            if (video.isEmpty()) {
                rlViewVideo?.visibility = View.GONE
            }
            initVideoYoutube(video)
        } else {
            rlViewVideo?.visibility = View.GONE
        }
    }

    private fun initSharedView(urlVideo: String) {
        firebaseAnalyticsPresenter.enviarEventoPorRol(TagAnalytics.EVENTO_COMPARTIR_VIDEO, rol)
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, urlVideo)
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    companion object {

        private const val ARG_URL_VIDEO = "ARG_URL_VIDEO"
        private const val ARG_DESCRIPTION = "ARG_DESCRIPTION"
        private const val ROL = "Rol"

        fun newInstance(urlVideo: String?, descriptionVideo: String?, rol: Rol) =
            TipsVideoFragment()
                .withArguments(
                    ARG_URL_VIDEO to urlVideo,
                    ARG_DESCRIPTION to descriptionVideo,
                    ROL to rol
                )
    }
}
