package biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult

import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult.cloud.CalculatingResultCloudDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult.data.CalculatingResultDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult.mapper.CalculatingResultEntityDataMapper
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.CalculatorResult
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.calculatingresult.CalculatingResultRepository
import io.reactivex.Single

class CalculatingResultDataRepository(
    private val calculatingResultCloudDataStore: CalculatingResultCloudDataStore,
    private val calculatingResultDBDataStore: CalculatingResultDBDataStore,
    private val calculatingResultEntityDataMapper: CalculatingResultEntityDataMapper,
    private val configPreferences: ConfigSharedPreferences
) : CalculatingResultRepository {

    override fun list(
        codRegion: String?, codZona: String?, codSeccion: String?
    ): Single<List<CalculatorResult>> {
        return calculatingResultDBDataStore.all(codRegion, codZona, codSeccion).map {
            calculatingResultEntityDataMapper.parseCalculatingResult(it)
        }
    }

    override suspend fun all(
        codRegion: String?, codZona: String?, codSeccion: String?
    ): List<CalculatorResult> {
        val data = calculatingResultDBDataStore.getCalculatingResult(codRegion, codZona, codSeccion)
        return calculatingResultEntityDataMapper.parseCalculatingResult(data)
    }

    override suspend fun insert(calculadoraResultado: CalculatorResult) {
        return calculatingResultDBDataStore.insert(
            calculatingResultEntityDataMapper.parseCalculatingResult(calculadoraResultado)
        )
    }

    override suspend fun delete() = calculatingResultDBDataStore.delete()

    override suspend fun syncUp() {
        val list = calculatingResultDBDataStore.getPending()
        if (list.isNotEmpty()) {
            val result = calculatingResultCloudDataStore.sendResult(list.first())
            calculatingResultDBDataStore.checkDispatched(result.result)
        }
    }

    override suspend fun getFlagNewCalculator(codPais: String) =
        configPreferences.flagCalculator ?: false


}
