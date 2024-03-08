package biz.belcorp.salesforce.modules.developmentpath.features.video

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.utils.Config
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment


abstract class VideoFragment : BaseFragment() {

    private var mPlayer: YouTubePlayer? = null
    private var isFullScreen = false

    private val youTubePlayerFragment by lazy {
        YouTubePlayerSupportFragment.newInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarYoutubePlayerFragment()
        onBackPressedDispatcher()
    }

    private fun onBackPressedDispatcher() {
        activity?.onBackPressedDispatcher?.addCallback(this) {
            if (isFullScreen) {
                mPlayer?.setFullscreen(false)
            } else {
                activity?.onBackPressed()
            }
        }
    }

    private fun configurarYoutubePlayerFragment() {
        childFragmentManager
            .beginTransaction()
            .add(getVideoLayout(), youTubePlayerFragment)
            .commit()
    }

    abstract fun getVideoLayout(): Int

    protected fun initVideoYoutube(video: String) {
        youTubePlayerFragment.initialize(
            Config.YOUTUBE_API_KEY,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider?,
                    player: YouTubePlayer?,
                    wasRestored: Boolean
                ) {
                    player?.fullscreenControlFlags = 0
                    mPlayer = player
                    mPlayer?.setOnFullscreenListener {
                        isFullScreen = it
                    }
                    if (!wasRestored) {
                        try {
                            mPlayer?.cueVideo(video)
                        } catch (e: Exception) {
                            Logger.e(e)
                        }
                    }
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider?,
                    errorReason: YouTubeInitializationResult
                ) {
                    if (errorReason.isUserRecoverableError) {
                        errorReason.getErrorDialog(activity ?: return, RECOVERY_REQUEST).show()
                    } else {
                        activity?.also {
                            val mensaje = getString(R.string.player_error)
                            val error = String.format(mensaje, errorReason.toString())
                            it.toast(error)
                        }
                    }
                }
            })
    }

    companion object {

        private const val RECOVERY_REQUEST = 256

    }

}
