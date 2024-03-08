package biz.belcorp.salesforce.core.data.repository.socialbonus

import biz.belcorp.salesforce.core.entities.sql.calculator.SocialBonusEntity
import biz.belcorp.salesforce.core.entities.sql.calculator.SocialBonusEntity_Table
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class SocialBonusDBDataStore {

    fun all(): Single<List<SocialBonusEntity>> {

        return doOnSingle {
            Select().from(SocialBonusEntity::class.java)
                .queryList()
        }
    }

    fun bonusById(codeTypeBonus: String): Single<SocialBonusEntity> {

        return doOnSingle {
            requireNotNull(
                Select().from(SocialBonusEntity::class.java)
                    .where(SocialBonusEntity_Table.CodigoTipoBono.eq(codeTypeBonus))
                    .querySingle()
            )
        }
    }

    fun getBonusById(codeTypeBonus: String): SocialBonusEntity? {
        return Select().from(SocialBonusEntity::class.java)
                .where(SocialBonusEntity_Table.CodigoTipoBono.eq(codeTypeBonus))
                .querySingle()
    }
}

