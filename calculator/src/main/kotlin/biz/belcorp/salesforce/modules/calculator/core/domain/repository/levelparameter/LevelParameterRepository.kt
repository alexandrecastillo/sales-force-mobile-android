package biz.belcorp.salesforce.modules.calculator.core.domain.repository.levelparameter

import biz.belcorp.salesforce.modules.calculator.core.domain.entities.LevelParameter
import io.reactivex.Single

interface LevelParameterRepository {
    fun list(): Single<List<LevelParameter>>
    suspend fun parametroPorNivel(nivelId: Int): LevelParameter?
}
