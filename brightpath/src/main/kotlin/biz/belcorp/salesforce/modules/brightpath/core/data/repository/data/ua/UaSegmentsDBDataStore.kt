package biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.ua

import biz.belcorp.salesforce.core.data.exceptions.ErrorException
import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.core.entities.sql.ua.RegionEntity
import biz.belcorp.salesforce.core.entities.sql.ua.ZonaEntity
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select
import io.reactivex.Single

class UaSegmentsDBDataStore {

    private val nonData = "No data"


    fun getRegions(): Single<List<RegionEntity>> {
        return Single.create { s ->
            try {
                val list = (select from RegionEntity::class).queryList()

                if (list.isNotEmpty()) {
                    s.onSuccess(list)
                } else {
                    s.onError(ErrorException(nonData))
                }

            } catch (e: Exception) {
                s.onError(
                    NetworkConnectionException()
                )
            }
        }
    }

    fun getZones(): Single<List<ZonaEntity>> {
        return Single.create { s ->
            try {
                val list = (select from ZonaEntity::class).queryList()

                if (list.isNotEmpty()) {
                    s.onSuccess(list)
                } else {
                    s.onError(ErrorException(nonData))
                }

            } catch (e: Exception) {
                s.onError(NetworkConnectionException())
            }
        }
    }

}
