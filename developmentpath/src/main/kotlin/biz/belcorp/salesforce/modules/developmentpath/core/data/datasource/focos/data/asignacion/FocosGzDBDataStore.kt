package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.asignacion

import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity
import biz.belcorp.salesforce.core.entities.sql.campania.CampaniaUaEntity_Table
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity
import biz.belcorp.salesforce.core.entities.sql.directorio.DirectorioVentaUsuarioEntity_Table
import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity
import biz.belcorp.salesforce.core.entities.sql.focos.FocoDetalleRddEntity_Table
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.FocoRddMapper
import com.raizlabs.android.dbflow.kotlinextensions.*

class FocosGzDBDataStore(private val focoRddMapper: FocoRddMapper) {

    fun obtenerFocosGZ(personaId: Long): Array<Long> {
        val where = (select from FocoDetalleRddEntity::class
            innerJoin DirectorioVentaUsuarioEntity::class
            on (DirectorioVentaUsuarioEntity_Table.CodZona.withTable()
            eq FocoDetalleRddEntity_Table.Zona.withTable())
            innerJoin CampaniaUaEntity::class
            on ((FocoDetalleRddEntity_Table.Campania.withTable()
            eq CampaniaUaEntity_Table.Codigo.withTable())
            and (FocoDetalleRddEntity_Table.Region.withTable()
            eq CampaniaUaEntity_Table.Region.withTable())
            and (FocoDetalleRddEntity_Table.Zona.withTable()
            eq CampaniaUaEntity_Table.Zona.withTable())
            and (FocoDetalleRddEntity_Table.Seccion.withTable()
            eq CampaniaUaEntity_Table.Seccion.withTable())
            and (CampaniaUaEntity_Table.Orden.withTable()
            eq CampaniaUaEntity.ORDEN_CAMPANIA_ACTUAL))
            where (DirectorioVentaUsuarioEntity_Table.consultoraID.withTable() eq personaId))

        val modeloDetalles = where.querySingle()

        return focoRddMapper.parsearIds(modeloDetalles?.focos)
    }
}
