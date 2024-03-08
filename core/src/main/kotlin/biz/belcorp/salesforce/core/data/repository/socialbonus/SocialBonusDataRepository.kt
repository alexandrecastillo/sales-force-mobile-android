package biz.belcorp.salesforce.core.data.repository.socialbonus

import biz.belcorp.salesforce.core.domain.entities.socialbonus.SocialBonus
import biz.belcorp.salesforce.core.domain.repository.socialbonus.SocialBonusRepository
import io.reactivex.Single

class SocialBonusDataRepository(
    private val socialBonusDBStore: SocialBonusDBDataStore,
    private val socialBonusMapper: SocialBonusMapper
) : SocialBonusRepository {

    override fun getSocialBonusList(): Single<List<SocialBonus>> {
        return socialBonusDBStore.all().map {
            socialBonusMapper.parseSocialBonus(it)
        }
    }

    override fun getSocialBonusSingle(codigoTipoBono: String): Single<SocialBonus> {
        return socialBonusDBStore.bonusById(codigoTipoBono).map {
            socialBonusMapper.parseSocialBonus(it)
        }
    }

    override suspend fun getSocialBonus(codeTypeBonus: String): SocialBonus? {
        val bonus = socialBonusDBStore.getBonusById(codeTypeBonus)
        return socialBonusMapper.parseSocialBonus(bonus)
    }
}
