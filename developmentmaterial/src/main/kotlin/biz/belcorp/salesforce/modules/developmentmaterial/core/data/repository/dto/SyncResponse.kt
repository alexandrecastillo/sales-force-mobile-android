package biz.belcorp.salesforce.modules.developmentmaterial.core.data.repository.dto

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.materialdesarrollo.MaterialDesarrolloEntity
import com.google.gson.annotations.SerializedName


class SyncResponse : BaseResponse() {

    @SerializedName("resultado")
    var resultado: List<MaterialDesarrolloEntity> = emptyList()

}
