package biz.belcorp.salesforce.modules.inspires.core.data.repository.preference

import biz.belcorp.salesforce.modules.inspires.core.data.repository.preference.data.InspiraPreferencesDataStore
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.preference.InspiraPreferencesRepository
import io.reactivex.Completable
import io.reactivex.Single

class InspiraPreferencesDataRepository(
    private val prefStore: InspiraPreferencesDataStore
) : InspiraPreferencesRepository {

    override fun getInspiraAttemp(): Single<Int> = prefStore.getInspiraAttemp()

    override fun setInspiraAttemp(attemp: Int): Completable = prefStore.setInspiraAttemp(attemp)

    override fun getInspiraShowPopup(): Single<Boolean> = prefStore.getInspiraShowPopup()

    override fun setInspiraShowPopup(show: Boolean): Completable = prefStore.setInspiraShowPopup(show)

}
