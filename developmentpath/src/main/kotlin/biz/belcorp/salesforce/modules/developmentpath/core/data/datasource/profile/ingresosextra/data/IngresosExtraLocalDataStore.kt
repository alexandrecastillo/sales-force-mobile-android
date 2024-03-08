package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.ingresosextra.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.marcas.OtraMarcaDetalleEntity
import biz.belcorp.salesforce.core.entities.sql.marcas.OtraMarcaDetalleEntity_Table
import biz.belcorp.salesforce.core.entities.sql.marcas.OtraMarcaEntity
import biz.belcorp.salesforce.core.entities.sql.marcas.OtraMarcaEntity_Table
import biz.belcorp.salesforce.core.utils.doOnCompletable
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.kotlinextensions.*
import io.reactivex.Completable
import io.reactivex.Single

class IngresosExtraLocalDataStore {

    fun obtenerTodasLasMarcas(): List<OtraMarcaEntity> {
        val query = (select from OtraMarcaEntity::class
            orderBy (OtraMarcaEntity_Table.orden.asc()))
        return query.queryList()
    }

    fun obtenerMarcaDetalle(marcaId: Long, llaveUA: LlaveUA, personaId : Long): OtraMarcaDetalleEntity? {
        val query = (select from OtraMarcaDetalleEntity::class
            where (OtraMarcaDetalleEntity_Table.marcaId eq marcaId)
            and (OtraMarcaDetalleEntity_Table.consultoraId eq (llaveUA.consultoraId ?: personaId))
            and (OtraMarcaDetalleEntity_Table.region eq llaveUA.codigoRegion)
            and (OtraMarcaDetalleEntity_Table.zona eq llaveUA.codigoZona)
            and (OtraMarcaDetalleEntity_Table.seccion eq llaveUA.codigoSeccion))
        return query.querySingle()
    }

    fun obtenerMarcaPorId(marcaId: Long): OtraMarcaEntity? {
        val query = (select from OtraMarcaEntity::class
            where (OtraMarcaEntity_Table.marcaId eq marcaId))
        return query.querySingle()
    }

    fun obtenerMarcaDetalleNoEnviadas(): Single<List<OtraMarcaDetalleEntity>> {
        return doOnSingle {
            val query = (select from OtraMarcaDetalleEntity::class
                where (OtraMarcaDetalleEntity_Table.enviado eq false)
                or (OtraMarcaDetalleEntity_Table.marcaVentaId eq 0))
            query.queryList()
        }
    }

    fun marcarMarcasDetalleComoEnviadas(): Completable {
        return doOnCompletable {
            val result =
                (update<OtraMarcaDetalleEntity>()
                    set (OtraMarcaDetalleEntity_Table.enviado eq true)
                    where (OtraMarcaDetalleEntity_Table.enviado eq false))
            result.execute()
        }
    }

    fun actualizarMarcaDetalle(entity: OtraMarcaDetalleEntity): Completable {
        return doOnCompletable {
            entity.save()
        }
    }

    fun actualizarMarcaDetalleList(data: List<OtraMarcaDetalleEntity>): Completable {
        return doOnCompletable {
            data.forEach { it.save() }
        }
    }
}
