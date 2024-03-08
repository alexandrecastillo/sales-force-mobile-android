package biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository.dto

import biz.belcorp.salesforce.core.data.network.dto.BaseResponse
import biz.belcorp.salesforce.core.entities.sql.metodologia.GrupoSegEntity
import biz.belcorp.salesforce.core.entities.sql.metodologia.SegmentacionEntity
import com.google.gson.annotations.SerializedName


class SyncResponse : BaseResponse() {

    @SerializedName("resultado")
    var resultado: Resultado? = null

    class Resultado {

        @SerializedName("listadoMensajeSegmentacion")
        var listadoMensajeSegmentacion: List<SegmentacionEntity> = emptyList()

        @SerializedName("listadoGrupoMensajeSegmentacion")
        var listadoGrupoMensajeSegmentacion: List<GrupoSegEntity> = emptyList()

    }

}
