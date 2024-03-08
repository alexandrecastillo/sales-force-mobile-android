package biz.belcorp.salesforce.modules.inspires.core.data.repository.ranking.data

import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraRankingEntity
import biz.belcorp.salesforce.core.entities.sql.inspires.InspiraRankingEntity_Table
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class RankingDBDataStore {

    fun all(): Single<List<InspiraRankingEntity>> {

        return doOnSingle {
            Select().from(InspiraRankingEntity::class.java)
                .queryList()
        }
    }

    fun oneWhenIsUser(): Single<InspiraRankingEntity> {

        return doOnSingle {
            Select().from(InspiraRankingEntity::class.java)
                .where(InspiraRankingEntity_Table.flagEsUsuario.eq(true))
                .querySingle()!!
        }
    }

}
