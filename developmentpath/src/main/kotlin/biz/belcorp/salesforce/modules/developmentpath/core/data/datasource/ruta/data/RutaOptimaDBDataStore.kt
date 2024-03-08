package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data

import biz.belcorp.salesforce.core.entities.sql.geo.RutaOptimaEntity
import biz.belcorp.salesforce.core.entities.sql.geo.RutaOptimaEntity_Table
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta.RutaOptimaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.PeticionRutaOptima
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.RespuestaRuta
import com.raizlabs.android.dbflow.kotlinextensions.and
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.kotlinextensions.where
import com.raizlabs.android.dbflow.sql.language.OrderBy
import com.raizlabs.android.dbflow.sql.language.Select

class RutaOptimaDBDataStore(private val mapper: RutaOptimaMapper) {

    fun guardar(peticion: PeticionRutaOptima, respuesta: RespuestaRuta) {
        if (respuesta.recuperarPuntos().isNotEmpty()) {
            val id = recuperarUltimoId() + 1
            val entidad = mapper.parse(peticion, respuesta, id)
            entidad.insert()
        }
    }

    private fun recuperarUltimoId(): Long {
        val query = Select()
            .from(RutaOptimaEntity::class.java)
            .orderBy(OrderBy.fromProperty(RutaOptimaEntity_Table.ID).descending())
            .querySingle()
        return query?.id ?: 0
    }

    fun recuperarSimilares(peticion: PeticionRutaOptima): List<RutaOptimaEntity> {
        return (select
            from RutaOptimaEntity::class
            where RutaOptimaEntity_Table.PuntosDePaso.eq(peticion.waypoints)
            and RutaOptimaEntity_Table.Destino.eq(peticion.destino))
            .queryList()
    }

    fun recuperarUltimo(): RutaOptimaEntity? {
        return (Select()
            .from(RutaOptimaEntity::class.java)
            .orderBy(OrderBy.fromProperty(RutaOptimaEntity_Table.ID).descending())
            .querySingle())
    }
}
