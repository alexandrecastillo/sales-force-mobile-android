package biz.belcorp.salesforce.core.data.repository.uainfo.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.ua.RegionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.ZonaEntity
import biz.belcorp.salesforce.core.entities.ua.UaEntity
import biz.belcorp.salesforce.core.entities.ua.UaEntity_
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.core.utils.isSE
import io.objectbox.kotlin.boxFor

class UaInfoDataStore {

    fun saveUa(ua: UaEntity) {
        boxStore.boxFor<UaEntity>()
            .put(ua)
    }

    fun saveChildrenUas(uas: List<UaEntity>) {
        boxStore.boxFor<UaEntity>()
            .deleteAndInsert(uas)
    }

    fun getAssociatedUaListByUaKey(uaKey: LlaveUA): UaEntity? {
        if (uaKey.roleAssociated.isSE()) return null
        return boxStore.boxFor<UaEntity>().query()
            .equal(UaEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(UaEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(UaEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .build()
            .findFirst()
    }

    fun saveLegacyRegions(regions: List<RegionEntity>) = regions.deleteAndInsert()

    fun saveLegacyZones(zones: List<ZonaEntity>) = zones.deleteAndInsert()

    fun saveLegacySections(sections: List<SeccionEntity>) = sections.deleteAndInsert()

}
