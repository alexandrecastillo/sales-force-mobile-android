package biz.belcorp.salesforce.modules.postulants.core.domain.repository

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.TablaLogica
import io.reactivex.Single

interface TablaLogicaRepository {

    fun list(tipoParametro: Int): Single<List<TablaLogica>>

}
