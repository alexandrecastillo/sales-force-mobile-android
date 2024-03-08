package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import com.google.gson.annotations.SerializedName

data class ListPostulanteApi(val name: String = Constant.EMPTY_STRING) {

    @SerializedName("pais")
    var pais: String? = null
    @SerializedName("postulantes")
    var postulantes: List<PostulanteEntity>? = null

}
