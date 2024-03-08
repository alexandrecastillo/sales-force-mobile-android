package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.visitas

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas.VisitasPorFechaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPorFecha
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.visitas.VisitasPorFechaRepository
import io.reactivex.Completable
import io.reactivex.Single

class VisitasPorFechaDataRepository(private val dbStore: VisitasPorFechaDBDataStore) :
    VisitasPorFechaRepository {

    override fun actualizarNoEnviados() {
        dbStore.actualizarNoEnviados()
    }

    fun actualizarNoEnviadosCompletable(): Completable {
        return dbStore.actualizarNoEnviadosCompletable()
    }

    override fun obtenerNoEnviados(): List<VisitasPorFecha> {
        return dbStore.obtenerNoEnviados()
    }

    fun obtenerNoEnviadosSingle(): Single<List<VisitasPorFecha>> {
        return dbStore.obtenerNoEnviadosSingle()
    }

    override fun obtener(planId: Long): List<VisitasPorFecha> {
        return dbStore.obtener(planId)
    }

    override fun obtener(filtro: VisitasPorFechaRepository.Filtro): VisitasPorFecha? {
        return dbStore.obtener(filtro)
    }

    override fun guardar(visitasPorFecha: VisitasPorFecha) {
        return dbStore.guardar(visitasPorFecha)
    }
}
