package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard

import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores.DvRDDIndicatorRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository

class ObtenerAvanceVisitasMiEquipoUseCase(
    private val repository: DvRDDIndicatorRepository,
    private val sesionManager: SessionPersonRepository,
    campaniasRepository: CampaniasRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    val sesion: Sesion get() = sesionManager.get()
    val campaniaActual = campaniasRepository.obtenerCampaniaActual(sesion.llaveUA)
        ?: error("Error al obtener campania")

    fun obtenerAvanceVisitas(subscriber: BaseSingleObserver<AvanceVisitasMiEquipo>) {
        val single = repository
            .obtenerIndicadorVisitasEquipo(checkNotNull(campaniaActual.codigo), sesion.zonificacion)
            .map {
                val avanceGr =
                    (it.visitadasGR.toInt() * PORCIENTO) / it.visitasPlanificadasGR.toInt()
                val avanceGz =
                    (it.visitadasGZ.toInt() * PORCIENTO) / it.visitasPlanificadasGZ.toInt()
                val avanceSe =
                    (it.visitadasSE.toInt() * PORCIENTO) / it.visitasPlanificadasSE.toInt()

                AvanceVisitasMiEquipo(avanceGr, avanceGz, avanceSe)
            }
        execute(single, subscriber)
    }

    class AvanceVisitasMiEquipo(val avanceGr: Int, val avanceGz: Int, val avanceSe: Int)

    companion object {
        private const val PORCIENTO = 100
    }
}
