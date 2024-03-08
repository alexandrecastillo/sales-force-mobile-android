package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.differenceInDays
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.core.utils.toCalendar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.UnidadAdministrativaRepository
import io.reactivex.Single
import java.util.*

class RecuperarFechasCampaniaActual(private val campaniasRepository: CampaniasRepository,
                                    private val uaRepository: UnidadAdministrativaRepository,
                                    threadExecutor: ThreadExecutor,
                                    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun ejecutar(planId: Long, observer: BaseSingleObserver<FechasCampania>) {
        val single = recuperar(planId)
        execute(single, observer)
    }

    private fun recuperar(planId: Long): Single<FechasCampania> {
        return doOnSingle {
            val ua = requireNotNull(uaRepository.obtenerUaPorPlan(planId))
            val campania = requireNotNull(campaniasRepository.obtenerCampaniaActual(ua.llave))
            val fechaInicio = requireNotNull(campania.inicio?.toCalendar())
            val fechaFin = requireNotNull(campania.fin?.toCalendar())
            FechasCampania(fechaInicio, fechaFin)
        }
    }

    class FechasCampania(val fechaInicio: Calendar,
                         val fechaFin: Calendar
    ) {

        private val diasDuracion: Int
            get() {
                return fechaFin.differenceInDays(fechaInicio) + 1
            }

        private val diasTranscurridos: Int
            get() {
                val hoy = Calendar.getInstance()
                return hoy.differenceInDays(fechaInicio) + 1
            }

        val diasRestantes: Int
            get() {
                return diasDuracion - diasTranscurridos
            }

        val porcentajeAvance: Int
            get() {
                return if (diasDuracion == 0)
                    0
                else
                    (diasTranscurridos * 100L / diasDuracion).toInt()
            }
    }
}
