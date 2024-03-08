package biz.belcorp.salesforce.core.domain.repository.socialbonus

import biz.belcorp.salesforce.core.domain.entities.socialbonus.SocialBonus
import io.reactivex.Single

interface SocialBonusRepository {
    fun getSocialBonusList(): Single<List<SocialBonus>>
    fun getSocialBonusSingle(codigoTipoBono: String): Single<SocialBonus>
    suspend fun getSocialBonus(codeTypeBonus: String): SocialBonus?
}
