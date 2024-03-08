package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete

import biz.belcorp.salesforce.core.entities.sql.unete.IndicadorUneteEntity
import com.google.gson.annotations.SerializedName

class CabeceraIndicadorUneteResponse {

    @SerializedName("resultado")
    var resultado: List<IndicadorUneteEntity>? = null
}
