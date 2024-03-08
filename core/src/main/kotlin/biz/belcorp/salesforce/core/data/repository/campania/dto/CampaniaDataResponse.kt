package biz.belcorp.salesforce.core.data.repository.campania.dto

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import com.google.gson.annotations.SerializedName

class CampaniaDataResponse {

    @SerializedName("campaniasUA")
    var campaniasUaList: List<CampaniaUaEntity> = emptyList()

}
