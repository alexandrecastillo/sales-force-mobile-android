package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.cobranza.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.collection.CollectionEntity
import biz.belcorp.salesforce.core.entities.collection.CollectionEntity_
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor

class DatosCobranzaCampaniaAnteriorGerenteZonaRegionDbStore {

    fun obtener(uaKey: LlaveUA, campaign: String): CollectionEntity? {
        return ObjectBox.boxStore.boxFor<CollectionEntity>().query()
            .equal(CollectionEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(CollectionEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(CollectionEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .equal(CollectionEntity_.campaign, campaign)
            .build()
            .findFirst()
    }
}
