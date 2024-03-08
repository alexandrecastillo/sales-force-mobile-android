package biz.belcorp.salesforce.modules.calculator.core.domain.repository.calculatingresult

import biz.belcorp.salesforce.modules.calculator.core.domain.entities.CalculatorResult
import io.reactivex.Completable
import io.reactivex.Single

interface CalculatingResultRepository {
    fun list(codRegion: String?, codZona: String?, codSeccion: String?): Single<List<CalculatorResult>>
    suspend fun all(codRegion: String?, codZona: String?, codSeccion: String?): List<CalculatorResult>
    suspend fun insert(calculadoraResultado: CalculatorResult)
    suspend fun delete()
    suspend fun syncUp()
    suspend fun getFlagNewCalculator(codPais: String): Boolean
}
