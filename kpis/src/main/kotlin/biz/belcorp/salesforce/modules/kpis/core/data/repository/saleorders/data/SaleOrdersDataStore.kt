package biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity_
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersOrderEntity
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersSaleEntity
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import io.objectbox.kotlin.boxFor

class SaleOrdersDataStore {

    fun saveSaleOrders(items: List<SaleOrdersEntity>) {
        with(boxStore) {
            runInTx {
                boxFor<SaleOrdersOrderEntity>().removeAll()
                boxFor<SaleOrdersSaleEntity>().removeAll()
                boxFor<SaleOrdersEntity>().removeAll()
                boxFor<SaleOrdersEntity>().put(items)
            }
        }
    }

    fun getSalesOrdersByCampaigns(uaKey: LlaveUA, campaign: List<String>): List<SaleOrdersEntity> {
        return boxStore.boxFor<SaleOrdersEntity>().query()
            .equal(SaleOrdersEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            .and()
            .equal(SaleOrdersEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            .and()
            .equal(SaleOrdersEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            .and()
            .inValues(SaleOrdersEntity_.campaign, campaign.toTypedArray())
            .build()
            .find()
    }

    fun getSaleOrdersByParent(uaKey: LlaveUA, campaign: String): List<SaleOrdersEntity> {
        return boxStore.boxFor<SaleOrdersEntity>().query()
            .equal(SaleOrdersEntity_.campaign, campaign)
            .doIf(uaKey.roleAssociated.isDV()) {
                and().inValues(SaleOrdersEntity_.profile, getProfilesForDV())
            }
            .doIf(uaKey.roleAssociated.isGR()) {
                and().equal(SaleOrdersEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
                and().inValues(SaleOrdersEntity_.profile, getProfilesForGR())

            }
            .doIf(uaKey.roleAssociated.isGZ()) {
                and().equal(SaleOrdersEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
                and().inValues(SaleOrdersEntity_.profile, getProfilesForGZ())
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
