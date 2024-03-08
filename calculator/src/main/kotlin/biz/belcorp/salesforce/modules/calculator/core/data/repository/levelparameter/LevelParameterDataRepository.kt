package biz.belcorp.salesforce.modules.calculator.core.data.repository.levelparameter

import biz.belcorp.salesforce.modules.calculator.core.data.repository.levelparameter.data.LevelParameterDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.levelparameter.mapper.LevelParameterEntityDataMapper
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.LevelParameter
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.levelparameter.LevelParameterRepository
import io.reactivex.Single

class LevelParameterDataRepository (
    private val levelParameterDBDataStore: LevelParameterDBDataStore,
    private val levelParameterEntityDataMapper: LevelParameterEntityDataMapper) : LevelParameterRepository {

    override fun list(): Single<List<LevelParameter>> {
        return levelParameterDBDataStore.all().map {
            levelParameterEntityDataMapper.parseLevelParameterList(it)
        }
    }

    override suspend fun parametroPorNivel(nivelId: Int): LevelParameter? {
        val parameter = levelParameterDBDataStore.parametroPorNivel(nivelId)
        return levelParameterEntityDataMapper.parseLevelParameter(parameter)
    }

}
