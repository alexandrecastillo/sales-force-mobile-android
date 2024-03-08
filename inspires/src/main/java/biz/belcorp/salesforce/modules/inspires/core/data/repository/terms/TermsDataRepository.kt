package biz.belcorp.salesforce.modules.inspires.core.data.repository.terms

import biz.belcorp.salesforce.modules.inspires.core.data.repository.terms.data.TermsDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.terms.mapper.TermsEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraCondiciones
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.terms.TermsRepository
import io.reactivex.Single

class TermsDataRepository (
    private val termsDBDataStore: TermsDBDataStore,
    private val termsEntityDataMapper: TermsEntityDataMapper
) : TermsRepository {

    override fun all(): Single<List<InspiraCondiciones>> {
        return termsDBDataStore.all().map {
            termsEntityDataMapper.parseLevelParameterList(it)
        }
    }

}
