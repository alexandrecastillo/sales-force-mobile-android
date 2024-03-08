package biz.belcorp.salesforce.modules.inspires.core.data.repository.preference.data

import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.utils.doOnCompletable
import biz.belcorp.salesforce.core.utils.doOnSingle
import io.reactivex.Completable
import io.reactivex.Single

class InspiraPreferencesDataStore
constructor(
    private val userPreferences: UserSharedPreferences) {

    fun getInspiraAttemp(): Single<Int> = doOnSingle { userPreferences.attemptInspira }

    fun setInspiraAttemp(attemp: Int): Completable = doOnCompletable { userPreferences.attemptInspira = attemp }

    fun getInspiraShowPopup(): Single<Boolean> = doOnSingle { userPreferences.showInspiraPopup }

    fun setInspiraShowPopup(show: Boolean): Completable = doOnCompletable { userPreferences.showInspiraPopup = show }

}
