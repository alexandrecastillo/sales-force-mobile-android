package biz.belcorp.salesforce.modules.postulants.core.domain.repository

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ParametroUnete
import io.reactivex.Single

interface ParametroUneteRepository {

    suspend fun listSuspend(tipoParametro: Int): List<ParametroUnete>

    fun list2(tipoParametro: Int, nombre: String): Single<List<ParametroUnete>>

    fun list(tipoParametro: Int): Single<List<ParametroUnete>>

    fun list(tipoParametro: Int, nombre: String): Single<ParametroUnete>

    fun listByPadre(parametroUnete: Int): Single<List<ParametroUnete>>

    fun listZonasSMS(tipoParametro: Int, zona: String): Single<List<ParametroUnete>>

    fun getParametroUnete(tipoParametro: Int, zona: String): Single<List<ParametroUnete>>

    fun getParametroUnete(tipoParametro: Int): Single<List<ParametroUnete>>
}
