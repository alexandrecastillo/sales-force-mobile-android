package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.propios

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity
import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity_Table
import biz.belcorp.salesforce.core.entities.sql.focos.FocoRddEntity
import biz.belcorp.salesforce.core.utils.guionSiNull
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import com.raizlabs.android.dbflow.kotlinextensions.*

class MisFocosLocalStore {

    fun recuperarFocosMaestros(): List<FocoRddEntity> {
        val query = (select from FocoRddEntity::class)
        return query.queryList()
    }

    fun recuperarFocosDetalle(llaveUA: LlaveUA): FocoDetalleRddEntity? {
        val llaveUaParseada = LlaveUA(
            codigoRegion = llaveUA.codigoRegion.guionSiNull(),
            codigoZona = llaveUA.codigoZona.guionSiNull(),
            codigoSeccion = llaveUA.codigoSeccion.guionSiNull(),
            consultoraId = llaveUA.consultoraId ?: Constant.MENOS_UNO.toLong()
        )

        val query = (select
            from FocoDetalleRddEntity::class
            where ((FocoDetalleRddEntity_Table.Region eq llaveUaParseada.codigoRegion)
            and (FocoDetalleRddEntity_Table.Zona eq llaveUaParseada.codigoZona)
            and (FocoDetalleRddEntity_Table.Seccion eq llaveUaParseada.codigoSeccion)))

        return query.querySingle()
    }
}
