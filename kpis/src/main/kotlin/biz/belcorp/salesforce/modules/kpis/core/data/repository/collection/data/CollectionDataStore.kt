package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.collection.CollectionEntity
import biz.belcorp.salesforce.core.entities.collection.CollectionEntity_
import biz.belcorp.salesforce.core.entities.collection.CollectionOrderEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.core.utils.inValues
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import io.objectbox.kotlin.boxFor

class CollectionDataStore {

    fun saveCollections(entities: List<CollectionEntity>) {
        with(boxStore) {
            runInTx {
                boxFor<CollectionOrderEntity>().removeAll()
                boxFor<CollectionEntity>().removeAll()
                boxFor<CollectionEntity>().put(entities)
            }
        }
    }

    fun getCollectionByCampaigns(uaKey: LlaveUA, campaigns: List<String>): List<CollectionEntity> {
        return boxStore.boxFor<CollectionEntity>().query()
            .equal(CollectionEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(CollectionEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(CollectionEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .inValues(CollectionEntity_.campaign, campaigns.toTypedArray())
            .build()
            .find()
    }

    fun getCollectionsByParent(
        uaKey: LlaveUA,
        campaign: String,
        days: String
    ): List<CollectionEntity> {
        return boxStore.boxFor<CollectionEntity>().query()
            .equal(CollectionEntity_.campaign, campaign)
            .and().equal(CollectionEntity_.days, days)
            .doIf(uaKey.roleAssociated.isDV()) {
                and().inValues(CollectionEntity_.profile, getProfilesForDV())
            }
            .doIf(uaKey.roleAssociated.isGR()) {
                and().equal(CollectionEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
                and().inValues(CollectionEntity_.profile, getProfilesForGR())
            }
            .doIf(uaKey.roleAssociated.isGZ()) {
                and().equal(CollectionEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
                and().inValues(CollectionEntity_.profile, getProfilesForGZ())
            }
            .build()
            .find()
    }

    private fun getProfilesForDV() =
        arrayOf(Rol.DIRECTOR_VENTAS.codigoRol, Rol.GERENTE_REGION.codigoRol)

    private fun getProfilesForGR() =
        arrayOf(Rol.GERENTE_REGION.codigoRol, Rol.GERENTE_ZONA.codigoRol)

    private fun getProfilesForGZ() =
        arrayOf(Rol.GERENTE_ZONA.codigoRol, Rol.SOCIA_EMPRESARIA.codigoRol)

}
