package biz.belcorp.salesforce.modules.inspires.core.domain.usecase

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraCondicionesLeyenda
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraCondicionesLeyendaDetalle
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.conditionslegenddetail.ConditionsLegendDetailRepository
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.legendconditions.LegendConditionsRepository
import io.reactivex.Single

class GetInspiraCondicionesLeyendaUseCase
constructor(
    private val repository: LegendConditionsRepository,
    private val repositoryDetail: ConditionsLegendDetailRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun all(observer: BaseSingleObserver<Response>) {

        val response = Response()

        val single = repository.all()
                .map { response.fillList(it) }
                .flatMap { repositoryDetail.all() }
                .map { response.fillListDetail(it) }
                .flatMap { make(response) }

        execute(single, observer)
    }

    class Response {

        var list: List<InspiraCondicionesLeyenda> = emptyList()
        var listDetail: List<InspiraCondicionesLeyendaDetalle> = emptyList()

        fun fillList(list: List<InspiraCondicionesLeyenda>): Response {
            this.list = list
            return this
        }

        fun fillListDetail(listDetail: List<InspiraCondicionesLeyendaDetalle>): Response {
            this.listDetail = listDetail
            return this
        }

    }

    private fun make(response: Response): Single<Response> {
        return doOnSingle {
            response
        }
    }
}
