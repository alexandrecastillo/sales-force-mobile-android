package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.filtros

import biz.belcorp.salesforce.core.domain.entities.search.TipoEstado
import biz.belcorp.salesforce.core.domain.entities.search.TipoSegmento
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.FiltrosBusqueda
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.TipoPedido
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.TipoSaldo
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros.TipoEstadoRepository
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros.TipoPedidoRepository
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros.TipoSaldoRepository
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros.TipoSegmentoRepository
import io.reactivex.Observable
import io.reactivex.functions.Function4

class ObtenerFiltrosBusquedaUseCase(
    private val tipoEstadoRepository: TipoEstadoRepository,
    private val tipoPedidoRepository: TipoPedidoRepository,
    private val tipoSaldoRepository: TipoSaldoRepository,
    private val tipoSegmentoRepository: TipoSegmentoRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun getAll(observer: BaseObserver<FiltrosBusqueda>) {
        execute(getPdvNonObservable(), observer)
    }

    private fun getPdvNonObservable() =
        Observable.zip(
            tipoEstadoRepository.all,
            tipoPedidoRepository.all,
            tipoSaldoRepository.all,
            tipoSegmentoRepository.all,
            Function4 { t1: List<TipoEstado>,
                        t2: List<TipoPedido>,
                        t3: List<TipoSaldo>,
                        t4: List<TipoSegmento> ->
                FiltrosBusqueda(t1, t2, t3, t4)
            }
        )


}
