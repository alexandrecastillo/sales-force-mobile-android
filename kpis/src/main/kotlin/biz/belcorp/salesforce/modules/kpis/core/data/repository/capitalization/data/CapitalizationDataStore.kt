package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity
import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity_
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import io.objectbox.kotlin.boxFor

class CapitalizationDataStore {

    fun saveCapitalization(entities: List<CapitalizationEntity>) {

        boxStore.boxFor<CapitalizationEntity>()
            .deleteAndInsert(entities)
    }

    fun getKpiDataByCampaignsAndUa(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<CapitalizationEntity> {
        return boxStore.boxFor<CapitalizationEntity>()
            .query()
            .equal(CapitalizationEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(CapitalizationEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(CapitalizationEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .inValues(CapitalizationEntity_.campaign, campaigns.toTypedArray())
            .build()
            .find()
    }

    fun getConsolidatedDataByUa(
        uaKey: LlaveUA,
        campaign: String
    ): List<CapitalizationEntity> {
        val role = uaKey.roleAssociated
        return boxStore.boxFor<CapitalizationEntity>().query()
            .equal(CapitalizationEntity_.campaign, campaign)
            .doIf(role.isDV()) {
                and().inValues(CapitalizationEntity_.profile, getProfilesForDV())
            }
            .doIf(role.isGR()) {
                and().equal(
                    CapitalizationEntity_.region,
                    uaKey.codigoRegion.orEmpty().deleteHyphen()
                )
                    .and().inValues(CapitalizationEntity_.profile, getProfilesForGR())
            }
            .doIf(role.isGZ()) {
                and().equal(
                    CapitalizationEntity_.zone,
                    uaKey.codigoZona.orEmpty().deleteHyphen()
                )
                    .and().inValues(CapitalizationEntity_.profile, getProfilesForGZ())
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
