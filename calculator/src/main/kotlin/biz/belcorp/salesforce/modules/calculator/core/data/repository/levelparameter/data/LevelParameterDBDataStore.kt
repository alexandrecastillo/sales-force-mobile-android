package biz.belcorp.salesforce.modules.calculator.core.data.repository.levelparameter.data

import biz.belcorp.salesforce.core.entities.calculator.LevelParameterEntity
import biz.belcorp.salesforce.core.entities.calculator.LevelParameterEntity_Table
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class LevelParameterDBDataStore {

    fun all(): Single<List<LevelParameterEntity>> {

        return doOnSingle {
            Select().from(LevelParameterEntity::class.java)
                .queryList()
        }
    }

    fun parametroPorNivel(nivelId: Int): LevelParameterEntity? {

        return requireNotNull(
            Select().from(LevelParameterEntity::class.java)
                .where(LevelParameterEntity_Table.ID.eq(nivelId))
                .querySingle())
    }
}
