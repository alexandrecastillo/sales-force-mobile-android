package biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete


import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.unete.detalle.DetalleIndicadorUneteEntity
import com.google.gson.annotations.SerializedName

class DetalleIndicadorUneteResponse : BaseResponse() {

    @SerializedName("resultado")
    var resultado: List<DetalleIndicadorUneteEntity>? = null
}
