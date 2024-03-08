package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.online

import io.reactivex.Completable

interface PlanDetalleRepository {
    fun sincronizar(planId: Long): Completable
}
