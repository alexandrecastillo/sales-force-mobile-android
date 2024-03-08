package biz.belcorp.salesforce.core.data.repository.socialbonus

import biz.belcorp.salesforce.core.domain.entities.socialbonus.SocialBonus
import biz.belcorp.salesforce.core.entities.sql.calculator.SocialBonusEntity

class SocialBonusMapper {

    fun parseSocialBonus(list: List<SocialBonusEntity>?): List<SocialBonus>? {
        val entity = arrayListOf<SocialBonus>()

        list?.forEach {
            entity.add(SocialBonus(
                it.codigoTipoBono,
                it.descripcionTipoBono,
                it.indicadorActivo,
                it.codigoTipoMedicion,
                it.indicadorTipoBono,
                it.montoUnitarioBono,
                it.ingresaCantidad,
                it.codConsulta))
        }

        return entity
    }

    fun parseSocialBonus(entity: SocialBonusEntity?): SocialBonus? {
        return entity?.let {
            SocialBonus(
                it.codigoTipoBono,
                it.descripcionTipoBono,
                it.indicadorActivo,
                it.codigoTipoMedicion,
                it.indicadorTipoBono,
                it.montoUnitarioBono,
                it.ingresaCantidad,
                it.codConsulta)
        }
    }
}
