package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas

import biz.belcorp.salesforce.core.entities.sql.path.VisitaXFechaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.path.VisitaXFechaRDDEntity_Table
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.visitas.VisitasPorFechasMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toShortString
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPorFecha
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.visitas.VisitasPorFechaRepository
import com.raizlabs.android.dbflow.kotlinextensions.*
import io.reactivex.Completable
import io.reactivex.Single

class VisitasPorFechaDBDataStore(private val visitasPorFechasMapper: VisitasPorFechasMapper) {

    fun actualizarNoEnviados() {
        val update = com.raizlabs.android.dbflow.kotlinextensions.update<VisitaXFechaRDDEntity>()
            .set(VisitaXFechaRDDEntity_Table.Enviado eq 1)
            .where(VisitaXFechaRDDEntity_Table.Enviado eq 0)

        update.execute()
    }

    fun actualizarNoEnviadosCompletable(): Completable {
        return Completable.create {
            val update = com.raizlabs.android.dbflow.kotlinextensions.update<VisitaXFechaRDDEntity>()
                .set(VisitaXFechaRDDEntity_Table.Enviado eq 1)
                .where(VisitaXFechaRDDEntity_Table.Enviado eq 0)

            update.execute()

            it.onComplete()
        }
    }

    fun obtenerNoEnviados(): List<VisitasPorFecha> {
        val select = (select
            from VisitaXFechaRDDEntity::class
            where (VisitaXFechaRDDEntity_Table.Enviado eq 0))

        return visitasPorFechasMapper.parse(select.queryList())
    }

    fun obtenerNoEnviadosSingle(): Single<List<VisitasPorFecha>> {
        return Single.create<List<VisitasPorFecha>> {
            val select = (select
                from VisitaXFechaRDDEntity::class
                where (VisitaXFechaRDDEntity_Table.Enviado eq 0))

            it.onSuccess(visitasPorFechasMapper.parse(select.queryList()))
        }
    }

    fun obtener(planId: Long): List<VisitasPorFecha> {
        val select = (select from VisitaXFechaRDDEntity::class
            where (VisitaXFechaRDDEntity_Table.ID.withTable() eq planId))

        return visitasPorFechasMapper.parse(select.queryList())
    }

    fun obtener(filtro: VisitasPorFechaRepository.Filtro): VisitasPorFecha? {
        val where = (select from VisitaXFechaRDDEntity::class
            where (VisitaXFechaRDDEntity_Table.ID eq filtro.planId)
            and (VisitaXFechaRDDEntity_Table.FechaVisita eq filtro.fecha.toShortString()))

        return visitasPorFechasMapper.parse(where.querySingle() ?: return null)
    }

    fun guardar(visitasPorFecha: VisitasPorFecha) {
        val model = VisitaXFechaRDDEntity()
        model.planId = visitasPorFecha.planId
        model.fechaVisita = visitasPorFecha.fecha.time.toShortString()
        model.cantidadVisita = visitasPorFecha.visitas
        model.enviado = 0

        model.save()
    }
}
