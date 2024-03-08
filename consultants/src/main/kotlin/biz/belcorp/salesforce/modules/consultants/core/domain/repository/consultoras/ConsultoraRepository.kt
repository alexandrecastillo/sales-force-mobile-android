package biz.belcorp.salesforce.modules.consultants.core.domain.repository.consultoras


import biz.belcorp.salesforce.modules.consultants.core.domain.entities.Consultora
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.FilterSearchConsultant
import io.reactivex.Observable
import io.reactivex.Single

interface ConsultoraRepository {

    fun consultoras(sectionCode: String?, idIndicator: Int, idList: Int):
        Observable<List<Consultora>>

    fun find(params: FilterSearchConsultant): Observable<List<Consultora>>

    fun findSize(params: FilterSearchConsultant): Observable<Int>

    fun findPDV(nivel: String, seccion: String): Single<List<Consultora>>

    fun getPossibleLevelChanges(seccion: String): Single<List<Consultora>>

    fun getEndPeriod(level: String, seccion: String): Single<List<Consultora>>

    suspend fun getConsultant(id: Int): Consultora

}
