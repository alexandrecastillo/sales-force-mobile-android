package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.cobranza

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.collection.CollectionEntity
import biz.belcorp.salesforce.core.utils.formatearConComas
import biz.belcorp.salesforce.core.utils.toPercentageFormat
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.cobranza.data.DatosCobranzaCampaniaAnteriorGerenteZonaRegionDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cobranza.CobranzaCampaniaAnterior
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza.DatosCobranzaCampaniaAnteriorRepository

class DatosCobranzaCampaniaAnteriorDataRepository(
    private val dbStore: DatosCobranzaCampaniaAnteriorGerenteZonaRegionDbStore
) : DatosCobranzaCampaniaAnteriorRepository {

    override suspend fun obtener(uaKey: LlaveUA, campaign: String): CobranzaCampaniaAnterior {
        return parse(dbStore.obtener(uaKey, campaign))
    }

    private fun parse(entity: CollectionEntity?): CobranzaCampaniaAnterior {
        return CobranzaCampaniaAnterior(
            montoFacturadoNeto = entity?.invoicedSale.zeroIfNull().formatearConComas(),
            montoRecuperado = entity?.amountCollected.zeroIfNull().formatearConComas(),
            consultorasConDeuda = entity?.debtorConsultants.zeroIfNull().toString(),
            recuperacion = entity?.percentage.zeroIfNull().toPercentageFormat(),
            saldoDeuda = (entity?.invoicedSale.zeroIfNull() - entity?.amountCollected.zeroIfNull()).formatearConComas()
        )
    }
}
