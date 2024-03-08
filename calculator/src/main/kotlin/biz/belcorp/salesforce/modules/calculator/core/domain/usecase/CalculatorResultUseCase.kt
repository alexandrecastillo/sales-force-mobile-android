package biz.belcorp.salesforce.modules.calculator.core.domain.usecase

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.CalculatorResult
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.calculatingresult.CalculatingResultRepository

class CalculatorResultUseCase(
    private val repository: CalculatingResultRepository,
    private val sessionManager: SessionRepository
) {

    private val session by lazy { requireNotNull(sessionManager.getSession()) }

    suspend fun list(): List<CalculatorResult> {
        val llaveUA = session.llaveUA
        return repository.all(llaveUA.codigoRegion, llaveUA.codigoZona, llaveUA.codigoSeccion)
    }

    suspend fun getFlagNewCalculator(countryCode: String): Boolean {
        return repository.getFlagNewCalculator(countryCode)
    }

    suspend fun insert(
        calculatorResult: CalculatorResult
    ) {
        repository.insert(calculatorResult)
        repository.syncUp()
    }

    suspend fun delete() {
        repository.delete()
    }
}
