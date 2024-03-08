package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.sync

import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import com.google.gson.annotations.SerializedName

class NotificacionRequest {

    @SerializedName("AppID")
    val appId = 6

    @SerializedName("Pais")
    var pais: String = Constant.EMPTY_STRING

    @SerializedName("Zona")
    var zona: String = Constant.EMPTY_STRING
}
