package biz.belcorp.salesforce.modules.termsconditions.features.termsconditions

import biz.belcorp.salesforce.core.domain.entities.terms.TermsConditions
import biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.model.TermsConditionsModel

class TermsConditionsMapper {
    fun map(list: List<TermsConditions>): List<TermsConditionsModel> {
        return list.asSequence().map { map(it) }.toList()
    }

    private fun map(document: TermsConditions): TermsConditionsModel {
        return TermsConditionsModel(
            id = document.id,
            titulo = document.description,
            url = document.url,
            urlValido = document.active
        )
    }
}
