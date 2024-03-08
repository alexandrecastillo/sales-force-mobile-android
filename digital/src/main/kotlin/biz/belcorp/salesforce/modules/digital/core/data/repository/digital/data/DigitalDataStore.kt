package biz.belcorp.salesforce.modules.digital.core.data.repository.digital.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.digital.DigitalInfoEntity
import biz.belcorp.salesforce.core.entities.digital.DigitalInfoEntity_
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import io.objectbox.kotlin.boxFor

class DigitalDataStore {

    fun saveDigitalInfo(entities: List<DigitalInfoEntity>) {
        return boxStore
            .boxFor<DigitalInfoEntity>()
            .deleteAndInsert(entities)
    }

    fun getDigitalInfo(campaign: String, uaKey: LlaveUA): DigitalInfoEntity? {
        return boxStore.boxFor<DigitalInfoEntity>().query()
            .equal(DigitalInfoEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(DigitalInfoEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(DigitalInfoEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .equal(DigitalInfoEntity_.campaign, campaign)
            .build()
            .findFirst()
    }

    fun getDigitalInfoByParent(campaign: String, uaKey: LlaveUA): List<DigitalInfoEntity> {
        return boxStore.boxFor<DigitalInfoEntity>().query()
            .equal(DigitalInfoEntity_.campaign, campaign)
            .doIf(uaKey.roleAssociated.isDV()) {
                and().inValues(DigitalInfoEntity_.profile, getProfilesForDV())
            }
            .doIf(uaKey.roleAssociated.isGR()) {
                and().equal(DigitalInfoEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
                and().inValues(DigitalInfoEntity_.profile, getProfilesForGR())
            }
            .doIf(uaKey.roleAssociated.isGZ()) {
                and().equal(DigitalInfoEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
                and().inValues(DigitalInfoEntity_.profile, getProfilesForGZ())
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
