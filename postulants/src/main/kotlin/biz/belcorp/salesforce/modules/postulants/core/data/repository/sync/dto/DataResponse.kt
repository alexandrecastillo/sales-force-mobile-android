package biz.belcorp.salesforce.modules.postulants.core.data.repository.sync.dto

import biz.belcorp.salesforce.core.entities.sql.filtros.TipoMetaEntity
import biz.belcorp.salesforce.core.entities.sql.unete.ConfiguracionEntity
import biz.belcorp.salesforce.core.entities.sql.unete.ParametroUneteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.TablaLogicaEntity
import com.google.gson.annotations.SerializedName

internal class DataResponse {

    @SerializedName("listadoParametrosUnete")
    var listadoParametrosUnete: List<ParametroUneteEntity> = emptyList()

    @SerializedName("listadoTablaLogicaDatos")
    var listadoTablaLogica: List<TablaLogicaEntity> = emptyList()

    @SerializedName("uneteConfiguracion")
    var uneteConfiguracion: List<ConfiguracionEntity> = emptyList()

    @SerializedName("listadoTipoMeta")
    var listadoTipoMeta: List<TipoMetaEntity> = emptyList()

}
