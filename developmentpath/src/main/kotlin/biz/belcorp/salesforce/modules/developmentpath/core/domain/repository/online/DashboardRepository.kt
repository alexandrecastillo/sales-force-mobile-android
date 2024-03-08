package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.online

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import io.reactivex.Completable

interface DashboardRepository {
    fun sincronizar(request: Request): Completable
    class Request(val planId: Long?, val llaveUA: LlaveUA)
}
