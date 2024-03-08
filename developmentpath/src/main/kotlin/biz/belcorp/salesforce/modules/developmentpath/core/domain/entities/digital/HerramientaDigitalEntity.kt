package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.digital

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tips.TipEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tips.VideoEntity
import com.google.gson.annotations.SerializedName

data class HerramientaDigitalEntity(@SerializedName(ID)
                                    val id: Long = 0,
                                    @SerializedName(NOMBRE)
                                    val nombre: String? = null,
                                    @SerializedName(USA)
                                    val usa: Boolean = false,
                                    @SerializedName(TIPS)
                                    val tips: List<TipEntity> = emptyList(),
                                    @SerializedName(VIDEOS)
                                    val videos: List<VideoEntity> = emptyList()) {
    companion object {
        private const val ID = "id"
        private const val NOMBRE = "nombre"
        private const val USA = "usa"
        private const val TIPS = "tips"
        private const val VIDEOS = "videos"
    }
}
