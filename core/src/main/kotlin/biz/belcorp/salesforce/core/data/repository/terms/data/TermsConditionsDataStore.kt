package biz.belcorp.salesforce.core.data.repository.terms.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox
import biz.belcorp.salesforce.core.entities.politicstermsconditions.ApproveTermsConditionsEntity
import biz.belcorp.salesforce.core.entities.politicstermsconditions.TermsConditionsEntity
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import io.objectbox.kotlin.boxFor

class TermsConditionsDataStore {

    fun saveTermsConditions(list: List<TermsConditionsEntity>) {
        ObjectBox.boxStore.boxFor<TermsConditionsEntity>()
            .deleteAndInsert(list)
    }

    fun getTermsConditions(): List<TermsConditionsEntity> {
        return ObjectBox.boxStore.boxFor<TermsConditionsEntity>().query()
            .build()
            .find().sortedBy {
                it.position
            }
    }

    fun getTerm(termCode: String): TermsConditionsEntity? {
        return getTermsConditions().firstOrNull {
            it.termsCode == termCode
        }
    }

    fun saveApproveTermsConditions(list: List<ApproveTermsConditionsEntity>) {
        ObjectBox.boxStore.boxFor<ApproveTermsConditionsEntity>()
            .deleteAndInsert(list)
    }

    fun getApproveTermsConditions(): List<ApproveTermsConditionsEntity> {
        return ObjectBox.boxStore.boxFor<ApproveTermsConditionsEntity>().query()
            .build()
            .find()
    }

    fun getApproveTerms(termCode: String): ApproveTermsConditionsEntity? {
        return getApproveTermsConditions().firstOrNull {
            it.termsCode == termCode
        }
    }

}
