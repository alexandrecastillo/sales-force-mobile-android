package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.visitas

import com.google.gson.annotations.SerializedName

class ResponsePedidosConsultoras(@SerializedName("resultado")
                                 val  montos: List<MontoConsultora>) {

    class MontoConsultora(@SerializedName("consultoraID")
                          val id: Long,
                          @SerializedName("monto")
                          val monto: Double)
}
