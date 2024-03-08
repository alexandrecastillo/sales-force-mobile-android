package biz.belcorp.salesforce.modules.brightpath.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.ua.Region
import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.core.domain.entities.ua.Zona
import io.reactivex.Single

interface UaSegmentsRepository {
    fun getSections(): Single<List<Seccion>>
    fun getZones(): Single<List<Zona>>
    fun getRegions(): Single<List<Region>>
}
