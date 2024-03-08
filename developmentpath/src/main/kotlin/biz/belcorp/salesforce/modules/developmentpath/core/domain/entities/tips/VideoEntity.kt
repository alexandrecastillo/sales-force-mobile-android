package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tips

import com.google.gson.annotations.SerializedName

data class VideoEntity(@SerializedName(ID)
                       val id: Long = 0,
                       @SerializedName(DESCRIPTION)
                       val descripcion: String? = null,
                       @SerializedName(URL)
                       val url: String? = null) {
    companion object {
        private const val ID = "id"
        private const val DESCRIPTION = "descripcion"
        private const val URL = "videourl"
    }
}
