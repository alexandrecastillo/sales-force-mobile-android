package biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.newcycles.NewCycleEntity
import biz.belcorp.salesforce.core.entities.newcycles.NewCycleEntity_
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import io.objectbox.kotlin.boxFor

class NewCycleDataStore {

    fun saveNewCycles(options: List<NewCycleEntity>) {
        boxStore.boxFor<NewCycleEntity>()
            .deleteAndInsert(options)
    }

    fun getNewCyclesByCampaigns(uaKey: LlaveUA, campaigns: List<String>): List<NewCycleEntity> {
        return boxStore.boxFor<NewCycleEntity>().query()
            .equal(NewCycleEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(NewCycleEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(NewCycleEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .inValues(NewCycleEntity_.campaign, campaigns.toTypedArray())
            .build()
            .find()
    }

    fun getNewCycleListByParent(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<NewCycleEntity> {
        return boxStore.boxFor<NewCycleEntity>().query()
            .inValues(NewCycleEntity_.campaign, campaigns.toTypedArray())
            .doIf(uaKey.roleAssociated.isDV()) {
                and().inValues(NewCycleEntity_.profile, getProfilesForDV())
            }
            .doIf(uaKey.roleAssociated.isGR()) {
                and().equal(NewCycleEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
                and().inValues(NewCycleEntity_.profile, getProfilesForGR())
            }
            .doIf(uaKey.roleAssociated.isGZ()) {
                and().equal(NewCycleEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
                and().inValues(NewCycleEntity_.profile, getProfilesForGZ())
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
