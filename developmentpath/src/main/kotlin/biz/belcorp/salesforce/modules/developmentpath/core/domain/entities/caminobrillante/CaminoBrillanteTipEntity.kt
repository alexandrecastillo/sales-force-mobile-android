package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.caminobrillante

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tips.TipEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tips.VideoEntity
import com.google.gson.annotations.SerializedName

data class CaminoBrillanteTipEntity(@SerializedName(TIPS)
                                    val tips: List<TipEntity> = emptyList(),
                                    @SerializedName(VIDEOS)
                                    val videos: List<VideoEntity> = emptyList()) {
    companion object {
        private const val TIPS = "tips"
        private const val VIDEOS = "videos"
    }
}
