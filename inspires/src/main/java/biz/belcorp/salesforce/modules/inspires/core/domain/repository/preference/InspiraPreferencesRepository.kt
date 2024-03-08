package biz.belcorp.salesforce.modules.inspires.core.domain.repository.preference

import io.reactivex.Completable
import io.reactivex.Single

interface InspiraPreferencesRepository {
    fun getInspiraAttemp(): Single<Int>
    fun setInspiraAttemp(attemp: Int): Completable
    fun getInspiraShowPopup(): Single<Boolean>
    fun setInspiraShowPopup(show: Boolean): Completable
}
