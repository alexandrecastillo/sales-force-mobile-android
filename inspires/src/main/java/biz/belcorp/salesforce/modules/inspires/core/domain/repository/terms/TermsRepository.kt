package biz.belcorp.salesforce.modules.inspires.core.domain.repository.terms

import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraCondiciones
import io.reactivex.Single

interface TermsRepository {
    fun all(): Single<List<InspiraCondiciones>>
}
